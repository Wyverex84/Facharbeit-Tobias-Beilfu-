import greenfoot.*; 

import java.util.*;
import java.awt.geom.Point2D;

public class Map3D  
{
    int right,left;
    double b1,b2,b3,b4;
    int renderMode = 1;
    
    double[] x1 = new double[4];
    double[] y1 = new double[4];
    double[] z1 = new double[4];
    double[] x2 = new double[4];
    double[] y2 = new double[4];
    double[] z2 = new double[4];
    double[] zs1 = new double[4];
    double[] zs2 = new double[4];

    int[] xp = {0,0,1,1};
    int[] zp = {0,1,1,0};  
    int[] scx = new int[4];
    int[] scy = new int[4];
    int[] scx2 = new int[4];
    int[] scy2 = new int[4];
    double cx;
    double cy;
    double cz;
    double[] zScore = new double[4];
    double[] zScoreBack = new double[4];
    boolean special = false;
    double[] tempX = new double[4];
    double[] tempY = new double[4];
    double[] tempZ = new double[4];
    double h;
    
    int rx,ry;
    double xTemp1,yTemp1,zTemp1,xTemp2,yTemp2,zTemp2;
    boolean renderable = false;
    boolean notRenderable;
    
    GreenfootImage image;
    
    double[][] mapFloor;
    double[][] mapRoof;
    Color[][][] mapColor;
    boolean[][] mapRend;
    
    double camX,camY,camZ,angX,angY;
    Color[] mapColors = {Color.white,Color.blue,Color.red,Color.green};
    ArrayList<GameObject> objects = new ArrayList<GameObject>();
    ArrayList<GameObject> currentObjects = new ArrayList<GameObject>();
    World world;
    Camera camera;
    
    //EDIT DATA
    boolean edit = false;
    int edX=5,edZ=5;
    
    ArrayList<int[]> points = new ArrayList<int[]>();
    
    public Map3D(int m,GreenfootImage i,ArrayList<GameObject> o,World w,Camera c){
        world = w;
        objects = o;
        image = i;
        mapFloor = Map3D_Data.getFloor(m);
        mapRoof = Map3D_Data.getRoof(m);
        mapColor = Map3D_Data.getColor(m);
        mapRend = new boolean[mapFloor.length][mapFloor[0].length];
        camera = c;
        
        for(int x=-20;x<20;x++){
            for(int y=-20;y<20;y++){
                int[] point = {x,y};
                points.add(point);
            }
        }
        
        int swaps = 1;
        while(swaps!=0 && !Greenfoot.isKeyDown("q")){
            swaps = 0;
            for(int j=0;j<points.size()-1;j++){
                int[] p1 = points.get(j);
                int[] p2 = points.get(j+1);
                if(p1[0]*p1[0] + p1[1]*p1[1]  <  p2[0]*p2[0] + p2[1]*p2[1]){
                    points.set(j,p2);
                    points.set(j+1,p1);
                    swaps++;
                }
            }
        }
        
    }
    
    public void display() 
    {
        if(edit){
            String str = Greenfoot.getKey();
            //System.out.println(str);
            if(str==null){
                str = "";
            }
            if(str.equals("p")){
                System.out.println();
                System.out.print("{");
                for(int i=0;i<mapFloor.length;i++){
                    System.out.print("{");
                    for(int j=0;j<mapFloor[0].length;j++){
                        System.out.print((((int)(mapFloor[i][j]*1000))/1000.0)+",");
                    }
                    System.out.println("},");
                }
                System.out.println("};");
                System.out.println("\n\n");
                System.out.print("{");
                for(int i=0;i<mapRoof.length;i++){
                    System.out.print("{");
                    for(int j=0;j<mapRoof[0].length;j++){
                        System.out.print((((int)(mapRoof[i][j]*1000))/1000.0)+",");
                    }
                    System.out.println("},");
                }
                System.out.println("};");
            }
            if(str.equals("up")){
                edX++;
            }
            if(str.equals("down")){
                edX--;
            }
            if(str.equals("right")){
                edZ++;
            }
            if(str.equals("left")){
                edZ--;
            }
            if(str.equals("9")){
                if(Greenfoot.isKeyDown("space")){
                    mapFloor[edX][edZ] += .2;
                }else{
                    mapFloor[edX][edZ] += 1;
                }
            }
            if(str.equals("6")){
                if(Greenfoot.isKeyDown("space")){
                    mapFloor[edX][edZ] -= .2;
                }else{
                    mapFloor[edX][edZ] -= 1;
                }
            }
            if(str.equals("7")){
                if(Greenfoot.isKeyDown("space")){
                    mapRoof[edX][edZ] += .2;
                }else{
                    mapRoof[edX][edZ] += 1;
                }
            }
            if(str.equals("4")){
                if(Greenfoot.isKeyDown("space")){
                    mapRoof[edX][edZ] -= .2;
                }else{
                    mapRoof[edX][edZ] -= 1;
                }
            }
        }
        
        
        
        if(renderMode==1){
            image.setColor(Color.black);
            image.fill();
        }
        for(int i=0;i<objects.size();i++){
            objects.get(i).clearRendered();
        }
        camX = camera.getX();
        camY = camera.getY();
        camZ = camera.getZ();
        angX = camera.getRotationX();
        angY = camera.getRotationY();
        
        b1 = camera.d1;
        b2 = camera.d2;
        b3 = camera.d3;
        b4 = camera.d4;
        for(int i=0;i<mapRend.length;i++){
            for(int j=0;j<mapRend[0].length;j++){
                mapRend[i][j] = false;
            }
        }

        /*
        for(int rad = 20;rad>0;rad--){
            for(int i=rad;i>=0;i--){
                renderSection((int)camX+i,(int)camZ+rad);
                renderSection((int)camX+i,(int)camZ-rad);
                renderSection((int)camX-i,(int)camZ+rad);
                renderSection((int)camX-i,(int)camZ-rad);
                
                renderSection((int)camX+rad,(int)camZ+i);
                renderSection((int)camX+rad,(int)camZ-i);
                renderSection((int)camX-rad,(int)camZ+i);
                renderSection((int)camX-rad,(int)camZ-i);
                if(Greenfoot.isKeyDown("x")){
                    world.repaint();
                }
            }
            if(Greenfoot.isKeyDown("z")){
                world.repaint();
            }
        }
        */
        for(int i=0;i<points.size();i++){
            renderSection((int)camX-points.get(i)[0],(int)camZ-points.get(i)[1]);
            if(Greenfoot.isKeyDown("z") && i%20==0){
                world.repaint();
            }
        }
        renderSection((int)camX,(int)camZ);
    }
    
    
    
    public void renderSection(int x,int z){
        if(x<0 || x>=mapRend.length || z<0 || z>=mapRend[0].length || mapRend[x][z]){
            return;
        }
        if(Greenfoot.isKeyDown("v")){
            world.repaint();
        }
        mapRend[x][z] = true;
        if(camY < mapFloor[x][z]){
            renderRoof(x,z);
            renderObjects(x,z);
            renderFloor(x,z);
        }else if(camY > mapRoof[x][z]){
            renderFloor(x,z); 
            renderObjects(x,z);
            renderRoof(x,z);
        }else{
            renderRoof(x,z);
            renderFloor(x,z);
            renderObjects(x,z);
        }
    }
    
    public void renderRoof(int x,int z){
        double y = mapRoof[x][z];
        cx = camX;
        cy = camY;
        cz = camZ;
        
        renderable = false;
        notRenderable = false;
        
        Color tempC = mapColor[x][z][1];
        double shade = .9-(int)((x-camX)*(x-camX)+(z-camZ)*(z-camZ))/400.0;
        if(shade<0){
            shade = 0;
        }
        image.setColor(new Color((int)(tempC.getRed()*shade),(int)(tempC.getGreen()*shade),(int)(tempC.getBlue()*shade)));
        if(edit && x==edX && z==edZ){
            image.setColor(Color.yellow);
        }
        
        for(int i=0;i<4;i++){
            renderPoint(x+xp[i],y,z+zp[i]);
            scx[i] = rx;
            scy[i] = ry;
            scx2[i] = rx;
            scy2[i] = ry;
            zScore[i] = zTemp2-.001;
            zScoreBack[i] = zTemp2-.001;
        }
        
        special = false;
        for(int i=0;i<4;i++){
            tempX[i] = x+xp[i];
            tempY[i] = y;
            tempZ[i] = z+zp[i];  
        }
        renderSpecial(tempX,tempY,tempZ,zScore);
        tempC = mapColor[x][z][2];
        image.setColor(new Color((int)(tempC.getRed()*shade),(int)(tempC.getGreen()*shade),(int)(tempC.getBlue()*shade)));
        if(edit && x==edX && z==edZ){
            image.setColor(Color.yellow);
        }
        
        if((int)camX>x && x!=mapFloor.length-1 && mapRoof[x+1][z] > y){
            h = mapRoof[x+1][z];
            notRenderable = false;
            renderable = false;
            tempX[0] = x+1;
            tempX[1] = x+1;
            tempZ[0] = z;
            tempZ[1] = z+1;
            tempY[0] = h;
            tempY[1] = h;
            renderZScore(x+1,h,z);
            zScore[0] = zTemp2-.001;
            renderZScore(x+1,h,z+1);
            zScore[1] = zTemp2-.001;        
            renderSpecial(tempX,tempY,tempZ,zScore);
        }
        
        if((int)camX<x && x!=0 && mapRoof[x-1][z] > y){
            h = mapRoof[x-1][z];
            notRenderable = false;
            renderable = false;
            tempX[2] = x;
            tempX[3] = x;
            tempZ[2] = z+1;
            tempZ[3] = z;
            tempY[2] = h;
            tempY[3] = h;
            renderZScore(x,h,z+1);
            zScore[2] = zTemp2-.001;
            renderZScore(x,h,z);
            zScore[3] = zTemp2-.001;   
            renderSpecial(tempX,tempY,tempZ,zScore);
        }
        
        for(int i=0;i<4;i++){
            scx[i] = scx2[i];
            scy[i] = scy2[i];
            zScore[i] = zScoreBack[i];
        }
        for(int i=0;i<4;i++){
            tempX[i] = x+xp[i];
            tempY[i] = y;
            tempZ[i] = z+zp[i];
        }
        
        if((int)camZ>z && z!=mapFloor.length-1 && mapRoof[x][z+1] > y){
            h = mapRoof[x][z+1];
            notRenderable = false;
            renderable = false;
            tempX[0] = x;
            tempX[3] = x+1;
            tempZ[0] = z+1;
            tempZ[3] = z+1;
            tempY[0] = h;
            tempY[3] = h;
            renderZScore(x,h,z+1);
            zScore[0] = zTemp2-.001;
            renderZScore(x+1,h,z+1);
            zScore[3] = zTemp2-.001;     
            renderSpecial(tempX,tempY,tempZ,zScore);
        }  
        
        if((int)camZ<z && z!=0 && mapRoof[x][z-1] > y){
            h = mapRoof[x][z-1];
            notRenderable = false;
            renderable = false;
            tempX[1] = x;
            tempX[2] = x+1;
            tempZ[1] = z;
            tempZ[2] = z; 
            tempY[1] = h;
            tempY[2] = h;
            renderZScore(x,h,z);
            zScore[1] = zTemp2-.001;
            renderZScore(x+1,h,z);
            zScore[2] = zTemp2-.001;       
            renderSpecial(tempX,tempY,tempZ,zScore);
        }
    }
    
    public void renderFloor(int x,int z){
        double y = mapFloor[x][z];
        cx = camX;
        cy = camY;
        cz = camZ;
        special = false;
        
        Color tempC = mapColor[x][z][0];
        image.setColor(tempC);
        double shade = 1-(int)((x-camX)*(x-camX)+(z-camZ)*(z-camZ))/400.0;
        if(shade<0){
            shade = 0;
        }
        image.setColor(new Color((int)(tempC.getRed()*shade),(int)(tempC.getGreen()*shade),(int)(tempC.getBlue()*shade)));
        if(edit && x==edX && z==edZ){
            image.setColor(Color.yellow);
        }
        
        renderable = false;
        notRenderable = false;
        double[] zScore = new double[4];
        double[] zScoreBack = new double[4];
        
        for(int i=0;i<4;i++){
            renderPoint(x+xp[i],y,z+zp[i]);
            scx[i] = rx;
            scy[i] = ry;
            scx2[i] = rx;
            scy2[i] = ry;
            zScore[i] = zTemp2-.001;
            zScoreBack[i] = zTemp2-.001;
        }
        
        special = false;
        //tempX = new double[4];
        //tempY = new double[4];
        //tempZ = new double[4];
        for(int i=0;i<4;i++){
            tempX[i] = x+xp[i];
            tempY[i] = y;
            tempZ[i] = z+zp[i];  
        }
        if(y<camY){
            renderSpecial(tempX,tempY,tempZ,zScore);
        }
               
        special = true;
        notRenderable = true;
        
        shade = .9-(int)(((x-camX)*(x-camX)+(z-camZ)*(z-camZ)))/400.0;
        if(shade<0){
            shade = 0;
        }
        tempC = mapColor[x][z][2];
        image.setColor(new Color((int)(tempC.getRed()*shade),(int)(tempC.getGreen()*shade),(int)(tempC.getBlue()*shade)));
        if(edit && x==edX && z==edZ){
            image.setColor(Color.yellow);
        }
        
        for(int i=0;i<4;i++){
            tempX[i] = x+xp[i];
            tempY[i] = y;
            tempZ[i] = z+zp[i];
        }
        
        if((int)camX>x && x!=mapFloor.length && mapFloor[x+1][z] < y){
            h = mapFloor[x+1][z];
            notRenderable = false;
            renderable = false;
            tempX[0] = x+1;
            tempX[1] = x+1;            
            tempZ[0] = z;
            tempZ[1] = z+1;
            tempY[0] = h;
            tempY[1] = h;
            renderZScore(x+1,h,z);
            zScore[0] = zTemp2-.001;
            renderZScore(x+1,h,z+1);
            zScore[1] = zTemp2-.001; 
            renderSpecial(tempX,tempY,tempZ,zScore);
        }
        
        if((int)camX<x && x!=mapFloor.length && mapFloor[x-1][z] < y){
            h = mapFloor[x-1][z];
            notRenderable = false;
            renderable = false;
            tempX[2] = x;
            tempX[3] = x;
            tempZ[2] = z+1;
            tempZ[3] = z;
            tempY[2] = h;
            tempY[3] = h;
            renderZScore(x,h,z+1);
            zScore[2] = zTemp2-.001;
            renderZScore(x,h,z);
            zScore[3] = zTemp2-.001;       
            renderSpecial(tempX,tempY,tempZ,zScore);
        }
        
        for(int i=0;i<4;i++){
            scx[i] = scx2[i];
            scy[i] = scy2[i];
            zScore[i] = zScoreBack[i];
        }
        for(int i=0;i<4;i++){
            tempX[i] = x+xp[i];
            tempY[i] = y;
            tempZ[i] = z+zp[i];
        }
        
        if((int)camZ>z && z!=mapFloor.length && mapFloor[x][z+1] < y){
            h = mapFloor[x][z+1];
            notRenderable = false;
            renderable = false;
            tempX[0] = x;
            tempX[3] = x+1;
            tempZ[0] = z+1;
            tempZ[3] = z+1;
            tempY[0] = h;
            tempY[3] = h;
            renderZScore(x,h,z+1);
            zScore[0] = zTemp2-.001;
            renderZScore(x+1,h,z+1);
            zScore[3] = zTemp2-.001;       
            renderSpecial(tempX,tempY,tempZ,zScore);
        }
        
        
        if((int)camZ<z && z!=0 && mapFloor[x][z-1] < y){
            h = mapFloor[x][z-1];
            notRenderable = false;
            renderable = false;
            tempX[1] = x;
            tempX[2] = x+1;         
            tempZ[1] = z;
            tempZ[2] = z;
            tempY[1] = h;
            tempY[2] = h;
            renderZScore(x,h,z);
            zScore[1] = zTemp2-.001;
            renderZScore(x+1,h,z);
            zScore[2] = zTemp2-.001;         
            renderSpecial(tempX,tempY,tempZ,zScore);
        }
    }
    
    public void renderZScore(double x, double y, double z){    
        xTemp2 = x;
        yTemp2 = y;
        zTemp2 = z;   
            
        xTemp1 = xTemp2 - camX;
        yTemp1 = yTemp2 - camY;
        zTemp1 = zTemp2 - camZ;
            
        xTemp2 = xTemp1*b1 - zTemp1*b2;
        zTemp1 = zTemp1*b1 + xTemp1*b2;
            
        yTemp2 = -(yTemp1*b3 - zTemp1*b4);
        zTemp2 = zTemp1*b3 + yTemp1*b4;  
    }
    
    public void renderPoint(double x, double y, double z){    
        xTemp2 = x;
        yTemp2 = y;
        zTemp2 = z;   
            
        xTemp1 = xTemp2 - camX;
        yTemp1 = yTemp2 - camY;
        zTemp1 = zTemp2 - camZ;
            
        xTemp2 = xTemp1*b1 - zTemp1*b2;
        zTemp1 = zTemp1*b1 + xTemp1*b2;
            
        yTemp2 = -(yTemp1*b3 - zTemp1*b4);
        zTemp2 = zTemp1*b3 + yTemp1*b4;  
            
        if(zTemp2>0){
            renderable = true;
        }
        if(zTemp2<0){
            notRenderable = true;
        }
        
        rx = 300+(int)(300*(xTemp2/zTemp2));                        
        ry = 200+(int)(300*(yTemp2/zTemp2)); 
    }
    
    public void renderSpecial(double[] sx,double[] sy,double[] sz,double[] zScore){
        /*
        double[] x1 = new double[4];
        double[] y1 = new double[4];
        double[] z1 = new double[4];
        double[] x2 = new double[4];
        double[] y2 = new double[4];
        double[] z2 = new double[4];
        double[] zs1 = new double[4];
        double[] zs2 = new double[4];
        */

        boolean done = true;
        for(int i=0;i<4;i++){
            done = done && zScore[i]>0;
        }
        if(done){
            int[] scrx = new int[sx.length];
            int[] scry = new int[sx.length];
            boolean discardXR = true;
            boolean discardXL = true;
            boolean discardYR = true;
            boolean discardYL = true;
        
            for(int i=0;i<sx.length;i++){
                renderPoint(sx[i],sy[i],sz[i]);
            
                scrx[i] = rx;
                scry[i] = ry;
                
                if(rx>=0){
                    discardXR = false;
                }
                if(rx<=600){
                    discardXL = false;
                }
                if(ry>=0){
                    discardYR = false;
                }
                if(ry<=400){
                    discardYL = false;
                }
            }
            
            if(discardXR || discardYR || discardXL || discardYL){
                return;
            }
        
            image.fillPolygon(scrx,scry,scrx.length);
            if(renderMode == 1){
                image.drawPolygon(scrx,scry,scrx.length);
            }
            return;
        }
        done = true;
        for(int i=0;i<4;i++){
            done = done && zScore[i]<0;
        }
        if(done){
            return;
        }
        
        int[] wrap = {0,1,2,3,0};
        
        for(int i=0;i<4;i++){
            x1[i] = sx[wrap[i]];
            x2[i] = sx[wrap[i+1]];
            y1[i] = sy[wrap[i]];
            y2[i] = sy[wrap[i+1]];
            z1[i] = sz[wrap[i]];
            z2[i] = sz[wrap[i+1]];
            zs1[i] = zScore[wrap[i]];
            zs2[i] = zScore[wrap[i+1]];
        }
        
        boolean[] kill = {false,false,false,false};
        boolean[] clip = {false,false,false,false};
        
        for(int i=0;i<4;i++){
            if((zs1[i]>0) != (zs2[i]>0)){
                clip[i] = true;
            }
            if(zs1[i]<0 && zs2[i]<0){
                kill[i] = true;
            }
            
        }
        
        for(int i=0;i<4;i++){
            if(clip[i]){
                double a = x1[i];
                double b = y1[i];
                double c = z1[i];
                double d = zs1[i];
                double e = x2[i];
                double f = y2[i];
                double g = z2[i];
                double h = zs2[i];
                
                double total = d - h;
                double ratio = d / total;
                
                double fx = a + ratio*(e - a);
                double fy = b + ratio*(f - b);
                double fz = c + ratio*(g - c);
                
                if(d<0){
                    x1[i] = fx;
                    y1[i] = fy;
                    z1[i] = fz;
                }else{
                    x2[i] = fx;
                    y2[i] = fy;
                    z2[i] = fz;
                }
            }
        }

        double[] px = new double[8];
        double[] py = new double[8];
        double[] pz = new double[8];
        int size = 0;
        
        for(int i=0;i<4;i++){
            if(!kill[i]){
                px[size] = x1[i];
                py[size] = y1[i];
                pz[size] = z1[i];
                size++;
                
                if(Math.abs(x2[i]-x1[wrap[i+1]])>.00001 || Math.abs(y2[i]-y1[wrap[i+1]])>.00001 || Math.abs(z2[i]-z1[wrap[i+1]])>.00001){
                    px[size] = x2[i];
                    py[size] = y2[i];
                    pz[size] = z2[i];
                    size++;
                }
            }
        }
             
        int[] scrx = new int[size];
        int[] scry = new int[size];
        boolean discardXR = true;
        boolean discardXL = true;
        boolean discardYR = true;
        boolean discardYL = true;
        
        for(int i=0;i<size;i++){
            renderPoint(px[i],py[i],pz[i]);
            
            if(rx>=0){
                discardXR = false;
            }
            if(rx<=600){
                discardXL = false;
            }
            if(ry>=0){
                discardYR = false;
            }
            if(ry<=400){
                discardYL = false;
            }
            
            scrx[i] = rx;
            scry[i] = ry;
        }
        
        if(discardXR || discardYR || discardXL || discardYL){
            return;
        }
        
        image.fillPolygon(scrx,scry,scrx.length);
        if(renderMode == 1){
            image.drawPolygon(scrx,scry,scrx.length);
        }
    }
    
    public void renderObjects(int x,int z){
        currentObjects.clear();
        double tx,tz;
        for(int i=0;i<objects.size();i++){
            GameObject now = objects.get(i);
            //if(x>=(int)(now.getObject().getX()-.499) && x<=(int)(now.getObject().getX()+.499) && z>=(int)(now.getObject().getZ()-.499) && z<=(int)(now.getObject().getZ()+.499)){
            if(now.renderTime(mapRend,camY)){
                currentObjects.add(now);
                now.drawObject(image);
                tx = now.getPosition()[0];
                tz = now.getPosition()[2];
                
                int x1 = (int)(tx-.49),z1 = (int)(tz-.49);
                int x2 = (int)(tx+.49),z2 = (int)(tz-.49);
                int x3 = (int)(tx-.49),z3 = (int)(tz+.49);
                int x4 = (int)(tx+.49),z4 = (int)(tz+.49);
                if(camY<mapFloor[x1][z1] || camY<mapFloor[x2][z2] || camY<mapFloor[x3][z3] || camY<mapFloor[x4][z4]){
                    //need to be sorted to fix small display bug
                    //happens on uneven terrain
                    renderFloor(x1,z1);
                    renderFloor(x2,z2);
                    renderFloor(x3,z3);
                    renderFloor(x4,z4);
                }

                if(camX > (int)(tx+.499)+1){
                    if(camZ>tz){
                        renderFloor((int)(tx+.499)+1,(int)(tz-.499));
                        renderFloor((int)(tx+.499)+1,(int)(tz+.499));
                    }else{
                        renderFloor((int)(tx+.499)+1,(int)(tz+.499));
                        renderFloor((int)(tx+.499)+1,(int)(tz-.499));
                    }
                }
                if(camX < (int)(tx-.499)){
                    if(camZ>tz){
                        renderFloor((int)(tx-.499)-1,(int)(tz-.499));
                        renderFloor((int)(tx-.499)-1,(int)(tz+.499));
                    }else{
                        renderFloor((int)(tx-.499)-1,(int)(tz+.499));
                        renderFloor((int)(tx-.499)-1,(int)(tz-.499));
                    }
                }
                if(camZ > (int)(tz+.499)+1){
                    if(camX > tx){
                        renderFloor((int)(tx-.499),(int)(tz+.499)+1);
                        renderFloor((int)(tx+.499),(int)(tz+.499)+1);
                    }else{
                        renderFloor((int)(tx+.499),(int)(tz+.499)+1);
                        renderFloor((int)(tx-.499),(int)(tz+.499)+1);
                    }
                }
                if(camZ < (int)(tz-.499)){
                    if(camX > tx){
                        renderFloor((int)(tx-.499),(int)(tz-.499)-1);
                        renderFloor((int)(tx+.499),(int)(tz-.499)-1);
                    }else{
                        renderFloor((int)(tx+.499),(int)(tz-.499)-1);
                        renderFloor((int)(tx-.499),(int)(tz-.499)-1);
                    }
                }
            }
        }
        
        for(int i=0;i<currentObjects.size();i++){
            //currentObjects.get(i).getRenderedImage().drawImage(image);
        }
        if(currentObjects.size()==0){
            return;
        }

    }
    
    public void setRenderMode(int mode){
        renderMode = mode;
    }
    
    
}
