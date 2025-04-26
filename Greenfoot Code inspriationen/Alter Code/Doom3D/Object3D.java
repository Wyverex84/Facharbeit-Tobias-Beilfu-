
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Object3D  
{
    //POINT DATA
    double[] x;
    double[] y;
    double[] z;
    double[] tx;
    double[] ty;
    double[] tz;
    int[][] myShapes;
    int[][] myPoints;
    double[] distance;
    Color[] myColors;
    int[] renderable;
    boolean[] back;
    
    //TEMPORARY VARIABLES
    double xTemp1;
    double yTemp1;
    double zTemp1;
    double xTemp2;
    double yTemp2;
    double zTemp2;
    double xTemp3;
    double yTemp3;
    double zTemp3;
    double[] nx;
    double[] ny;
    double[] nz;
    double[] zScore;
    int tr1,tr2,tr3,tr4,tr5;
    
    //OBJECT DATA
    double X=0,Y=0,Z=0,xa=0,ya=0,za=0,scale=1;
    double a1=1,a2=0,a3=1,a4=0,a5=1,a6=0;
    double dist=0,cx,cy,cz;
    boolean backface=true,shadeless;
    int lowPolly;
    double lit;    
    
    //SPECIAL DATA
    int rx,ry;
    
    public Object3D(double[] xp,double[] yp,double[] zp,int[][] sp,Color[] c,boolean bk) 
    {
        x=xp;
        y=yp;
        z=zp;
        myShapes=sp;
        myPoints = new int[2][x.length];
        distance = new double[x.length];
        back = new boolean[x.length];
        myColors = c;
        backface = bk;
        nx = new double[x.length];
        ny = new double[x.length];
        nz = new double[x.length];
        tx = new double[x.length];
        ty = new double[x.length];
        tz = new double[x.length];
        zScore = new double[x.length];
        renderable = new int[x.length];
    }
    
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //INITILIZATION FROM OBJ FILE
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    private void initilizeFile(String file){
        
        ArrayList<String> MaterialLines = new ArrayList<String>();
        try{
            URL path = getClass().getClassLoader().getResource("3DObjects/" + file + ".mtl");
            InputStream input = path.openStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
            String line = buffer.readLine(); 
            while(line != null)
            {
                if (line != null)
                MaterialLines.add(line);
                line = buffer.readLine();
            }
            input.close();
            buffer.close();
        }catch (Exception e){
            System.out.println("Error: " + e);
            return;
        }
        
        ArrayList<String> ObjectLines = new ArrayList<String>();
        try{
            URL path = getClass().getClassLoader().getResource("3DObjects/" + file + ".obj");
            InputStream input = path.openStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
            String line = buffer.readLine(); 
            while(line != null)
            {
                if (line != null)
                ObjectLines.add(line);
                line = buffer.readLine();
            }
            input.close();
            buffer.close();
        }catch (Exception e){
            System.out.println("Error: " + e);
            return;
        }
        
        
        //try{                                                     /**/
            
        //Scanner in = new Scanner(new File("3DObjects/" + file+".mtl"));         /**/
        //Scanner in = new Scanner(new File(file+".mtl"));         /**/
        
        ArrayList<String> materials = new ArrayList<String>();
        ArrayList<Integer> redV = new ArrayList<Integer>();
        ArrayList<Integer> greenV = new ArrayList<Integer>();
        ArrayList<Integer> blueV = new ArrayList<Integer>();
        //while(in.hasNextLine()){                                 /**/
        for(int i=0;i<MaterialLines.size();i++){               /**/
            //String temp = in.nextLine();                         /**/
            String temp = MaterialLines.get(i);                /**/
            Scanner line = new Scanner(temp);
            if(line.hasNext()){
                String str = line.next();
                if(str.equals("newmtl")){
                    materials.add(line.next());
                }
                if(str.equals("Kd")){
                    redV.add((int)(255*Double.parseDouble(line.next())/.8));
                    greenV.add((int)(255*Double.parseDouble(line.next())/.8));
                    blueV.add((int)(255*Double.parseDouble(line.next())/.8));
                }
            }
        }
         
        
             
            
        //in = new Scanner(new File("3DObjects/" + file+".obj"));                /**/
        ArrayList<Double> xVerts = new ArrayList<Double>();
        ArrayList<Double> yVerts = new ArrayList<Double>();
        ArrayList<Double> zVerts = new ArrayList<Double>();
        ArrayList<ArrayList<Integer>> faces = new ArrayList<ArrayList<Integer>>();
        ArrayList<Color> colors = new ArrayList<Color>();
        Color currentMaterial = Color.black;
            
        //in = new Scanner(new File("3DObjects/" + file+".obj"));                /**/
        //in = new Scanner(new File(file+".obj"));                /**/
            
        //while(in.hasNextLine()){                                /**/
        for(int i=0;i<ObjectLines.size();i++){                /**/
            //Scanner line = new Scanner(in.nextLine());          /**/
            Scanner line = new Scanner(ObjectLines.get(i));   /**/
            String str = line.next();
            if(str.equals("usemtl")){
                int index = materials.indexOf(line.next());
                currentMaterial = new Color(redV.get(index),greenV.get(index),blueV.get(index));
            }
            if(str.equals("v")){  
                xVerts.add(Double.parseDouble(line.next()));
                yVerts.add(Double.parseDouble(line.next()));
                zVerts.add(Double.parseDouble(line.next()));
            }
            if(str.equals("f")){
                ArrayList<Integer> edges = new ArrayList<Integer>();
                while(line.hasNext()){
                    edges.add(Integer.parseInt(line.next()));
                }
                faces.add(edges);
                colors.add(currentMaterial);
            }
        }
        
        
            
        x = new double[xVerts.size()];
        y = new double[yVerts.size()];
        z = new double[zVerts.size()];
        myShapes = new int[faces.size()][];
        myColors = new Color[faces.size()];
        for(int i=0;i<xVerts.size();i++){
            x[i] = xVerts.get(i);
            y[i] = yVerts.get(i);
            z[i] = zVerts.get(i);
        }
        for(int i=0;i<faces.size();i++){
            int[] edges = new int[faces.get(i).size()];
            for(int j=0;j<edges.length;j++){
                edges[j] = faces.get(i).get(j)-1;
            }
            myShapes[i] = edges;
        }
        for(int i=0;i<faces.size();i++){
            myColors[i] = colors.get(i);
        }
        
        //}catch (FileNotFoundException e){               /**/
        //    System.out.println(e);                      /**/
        //}
    }
    
    public Object3D(String file,boolean back){
        initilizeFile(file);
        
        nx = new double[x.length];
        ny = new double[x.length];
        nz = new double[x.length];
        myPoints = new int[2][x.length];
        distance = new double[x.length];
        zScore = new double[x.length];
        tx = new double[x.length];
        ty = new double[x.length];
        tz = new double[x.length];
        //back = new boolean[x.length];
        renderable = new int[x.length];
        backface = back;
    }
    
    
    
    public Object3D(String file){
        initilizeFile(file);
        
        nx = new double[x.length];
        ny = new double[x.length];
        nz = new double[x.length];
        myPoints = new int[2][x.length];
        distance = new double[x.length];
        zScore = new double[x.length];
        tx = new double[x.length];
        ty = new double[x.length];
        tz = new double[x.length];
        //back = new boolean[x.length];
        renderable = new int[x.length];
    }
    
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //GET POINTS
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    
    public void getPoints(double dx, double dy, double dz, double b1,double b2, double b3, double b4){//, boolean trig){    
       
        dist = 100000+Math.pow(dx-X,2)+Math.pow(dy-Y,2)+Math.pow(dz-Z,2);
        cx = dx;
        cy = dy;
        cz = dz;
        
        for(int i=0;i<x.length;i++){ 
            xTemp1 = scale*x[i]*a1 - scale*y[i]*a2;
            yTemp1 = scale*y[i]*a1 + scale*x[i]*a2;
            
            zTemp1 = scale*z[i]*a3 - yTemp1*a4;
            yTemp2 = yTemp1*a3 + scale*z[i]*a4;
            
            xTemp2 = xTemp1*a5 - zTemp1*a6;
            zTemp2 = zTemp1*a5 + xTemp1*a6;
            
            nx[i] = xTemp2;
            ny[i] = yTemp2;
            nz[i] = zTemp2;     
            
            xTemp1 = xTemp2 + X - dx;
            yTemp1 = yTemp2 + Y - dy;
            zTemp1 = zTemp2 + Z - dz;
            
            xTemp2 = xTemp1*b1 - zTemp1*b2;
            zTemp1 = zTemp1*b1 + xTemp1*b2;
            
            yTemp2 = -(yTemp1*b3 - zTemp1*b4);
            zTemp2 = zTemp1*b3 + yTemp1*b4;
             
            renderable[i] = (zTemp2>0) ? 1 : 0;
            
            distance[i] = Math.pow(xTemp2,2)+Math.pow(yTemp2,2)+Math.pow(zTemp2+9,2); 
            
            myPoints[0][i] = 300+(int)(300*(xTemp2/zTemp2));                        
            myPoints[1][i] = 200+(int)(300*(yTemp2/zTemp2));
            zScore[i] = zTemp2;
        }
        
    }
    
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //GETPOLYGON
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    
    public int[][] getPolygon(int num){     
        tr1 = myShapes[num][0];           
        tr2 = myShapes[num][1];           
        tr3 = myShapes[num][2];          
        if(myShapes[num].length==4){         
            tr4 = myShapes[num][3];
            int[][] triangle ={{myPoints[0][tr1],myPoints[0][tr2],myPoints[0][tr3],myPoints[0][tr4]},
                               {myPoints[1][tr1],myPoints[1][tr2],myPoints[1][tr3],myPoints[1][tr4]}};              
            return triangle;
        }
        if(myShapes[num].length==3){
            int[][] triangle ={{myPoints[0][tr1],myPoints[0][tr2],myPoints[0][tr3]},
                               {myPoints[1][tr1],myPoints[1][tr2],myPoints[1][tr3]}};
            return triangle;
        }
        if(myShapes[num].length==5){         
            tr4 = myShapes[num][3];
            tr5 = myShapes[num][4];
            int[][] triangle ={{myPoints[0][tr1],myPoints[0][tr2],myPoints[0][tr3],myPoints[0][tr4],myPoints[0][tr5]},
                               {myPoints[1][tr1],myPoints[1][tr2],myPoints[1][tr3],myPoints[1][tr4],myPoints[1][tr5]}};              
            return triangle;
        }
        return null;
    }
    
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    //RENDERABLE         0:not renderable     1:fully renderable     2:special render
    //**************************************************************************************************************************
    //**************************************************************************************************************************
    
    public int renderable(int num){ 
        
        tr1 = myShapes[num][0];       
        tr2 = myShapes[num][1];
        tr3 = myShapes[num][2];
        if(myShapes[num].length==4){
            tr4 = myShapes[num][3];
            int sum = renderable[tr1]+renderable[tr2]+renderable[tr3]+renderable[tr4];
            if(sum==0){
                return 0;
            }
            if(sum!=4){
                return 2;
            }
            int x1=myPoints[0][tr1],x2=myPoints[0][tr2],x3=myPoints[0][tr3],x4=myPoints[0][tr4];
            int y1=myPoints[1][tr1],y2=myPoints[1][tr2],y3=myPoints[1][tr3],y4=myPoints[1][tr4];              
            if((x1<0 && x2<0 && x3<0 && x4<0) || (y1<0 && y2<0 && y3<0 && y4<0) || (x1>600 && x2>600 && x3>600 && x4>600) || (y1>400 && y2>400 && y3>400 && y4>400)){
                return 0;
            }
            
            if(backface){
                xTemp1 = (ny[tr1]-ny[tr3])*(nz[tr2]-nz[tr3]) - (nz[tr1]-nz[tr3])*(ny[tr2]-ny[tr3]);
                yTemp1 = (nz[tr1]-nz[tr3])*(nx[tr2]-nx[tr3]) - (nx[tr1]-nx[tr3])*(nz[tr2]-nz[tr3]);
                zTemp1 = (nx[tr1]-nx[tr3])*(ny[tr2]-ny[tr3]) - (ny[tr1]-ny[tr3])*(nx[tr2]-nx[tr3]);
                if(xTemp1*(nx[tr2]+X-cx) + yTemp1*(ny[tr2]+Y-cy) + zTemp1*(nz[tr2]+Z-cz) > 0){
                    return 0;
                }
            }
            return 1;
            
        }else{
            int sum = renderable[tr1]+renderable[tr2]+renderable[tr3];
            if(sum==0){
                return 0;
            }
            if(sum!=3){
                return 2;
            }
            int x1=myPoints[0][tr1],x2=myPoints[0][tr2],x3=myPoints[0][tr3];
            int y1=myPoints[1][tr1],y2=myPoints[1][tr2],y3=myPoints[1][tr3];  
            if((x1<0 && x2<0 && x3<0) || (y1<0 && y2<0 && y3<0) || (x1>600 && x2>600 && x3>600) || (y1>400 && y2>400 && y3>400)){
                return 0;
            }
                
            if(backface){
                xTemp1 = (ny[tr1]-ny[tr3])*(nz[tr2]-nz[tr3]) - (nz[tr1]-nz[tr3])*(ny[tr2]-ny[tr3]);
                yTemp1 = (nz[tr1]-nz[tr3])*(nx[tr2]-nx[tr3]) - (nx[tr1]-nx[tr3])*(nz[tr2]-nz[tr3]);
                zTemp1 = (nx[tr1]-nx[tr3])*(ny[tr2]-ny[tr3]) - (ny[tr1]-ny[tr3])*(nx[tr2]-nx[tr3]);
                if(xTemp1*(nx[tr2]+X-cx) + yTemp1*(ny[tr2]+Y-cy) + zTemp1*(nz[tr2]+Z-cz) > 0){
                    return 0;
                }
            }
            return 1;

        }
    }
    
    /*
    public double lit(double lx, double ly, double lz){
        if(shadeless)return 0;
        
        lit = Math.sqrt(xTemp1*xTemp1+yTemp1*yTemp1+zTemp1*zTemp1);
        xTemp1 = xTemp1/lit;
        yTemp1 = yTemp1/lit;
        zTemp1 = zTemp1/lit;
        
        
        lx = nx[tr2]+X-lx;
        ly = ny[tr2]+Y-ly;
        lz = nz[tr2]+Z-lz;
        
        lit = Math.sqrt(lx*lx+ly*ly+lz*lz);
        lx = lx/lit;
        ly = ly/lit;
        lz = lz/lit;
        
        lit = (xTemp1*lx + yTemp1*ly + zTemp1*lz)/(lit/500.0+1);
        if(lit>0)lit=0;
        //System.out.println(lit);
        return -lit;
    }
    */
            
            
    public int numPoly(){     
        return myShapes.length;
    }
    
    public int numPoints(int num){ 
        return myShapes[num].length;
    }
    
    public double distance(int num){  
        int tr1 = myShapes[num][0];
        int tr2 = myShapes[num][1];      
        int tr3 = myShapes[num][2];       
        if(myShapes[num].length==4){
            int tr4 = myShapes[num][3];  
            return (distance[tr1]+distance[tr2]+distance[tr3]+distance[tr4])/4.0;
        }else{
            return (distance[tr1]+distance[tr2]+distance[tr3])/3.0;
        }
    }
    
    
    
    public Color getColor(int num){
        if(shadeless){
            return myColors[num];   
        }else{
            double dist = 0;
            for(int i=0;i<myShapes[num].length;i++){
                dist += zScore[myShapes[num][i]];
            }
            dist /= myShapes[num].length;
            double shade = .9-dist*dist/400.0;
            if(shade<0){
                shade = 0;
            }
            return new Color((int)(myColors[num].getRed()*shade),(int)(myColors[num].getGreen()*shade),(int)(myColors[num].getBlue()*shade));
        }
    }
    
    public Object3D duplicate(){   
        Color[] cn = new Color[myColors.length];
        for(int i=0;i<myColors.length;i++){
            cn[i] = new Color(myColors[i].getRed(),myColors[i].getBlue(),myColors[i].getGreen(),myColors[i].getAlpha());
        }
        return new Object3D(x,y,z,myShapes,cn,backface);
    }
    
    public void setLocation(double a, double b, double c){ 
        X=a;                                               
        Y=b;                                               
        Z=c;
    }
    
    public void move(double a, double b, double c){         
        X=X+a;                                            
        Y=Y+b;                                             
        Z=Z+c;
    }
    
    public void setRotation(double a, double b, double c){          
        xa=a;                                             
        ya=b;      
        za=c;
        a1 = Math.cos(ya);                         
        a2 = Math.sin(ya);
        a3 = Math.cos(xa);
        a4 = Math.sin(xa);  
        a5 = Math.cos(za);
        a6 = Math.sin(za);  
    }
    
    public void rotate(double a, double b,double c){                
        xa=xa+a;                                           
        ya=ya+b;  
        za=za+c;
        updateRotation();
    }
    
    public void updateRotation(){
        a1 = Math.cos(ya);                         
        a2 = Math.sin(ya);
        a3 = Math.cos(xa);
        a4 = Math.sin(xa);  
        a5 = Math.cos(za);
        a6 = Math.sin(za);  
    }
    
    public double[] getLocation(){         
        double[] pos = {X,Y,Z};
        return pos;
    }
    
    public double getX(){
        return X;
    }
    
    public double getY(){
        return Y;
    }
    
    public double getZ(){
        return Z;
    }
    
    public double[] getRotation(){         
        double[] rot = {xa,ya,za};
        return rot;
    }
    
    public void setColor(Color c1,int num){
        myColors[num] = c1;
    }
    
    public void setColor(Color c1){
        for(int i=0;i<myColors.length;i++){
            setColor(c1,i);
        }
    }
    
    public void scale(double s){
        scale = s;
    }
    
    public void moveTo(double a,double b,double c,double d,double e,double f,double str){
        X += (a-X)/str;
        Y += (b-Y)/str;
        Z += (c-Z)/str;
        xa += (d-xa)/str;
        ya += (e-ya)/str;
        za += (f-za)/str;
        updateRotation();
    }
    
    public int[][] getSpecialPolygon(int polyN,double b1,double b2,double b3,double b4,double camX,double camY,double camZ){
        //return getPolygon(i);
        return renderSpecial(polyN,b1,b2,b3,b4,camX,camY,camZ);
    }
    
    //**********************************************************************************
    //**********************************************************************************
    //**********************************************************************************
    //**********************************************************************************
    
    public int[][] renderSpecial(int polyN,double b1,double b2,double b3,double b4,double camX,double camY,double camZ){
        double[] x1 = new double[4];
        double[] y1 = new double[4];
        double[] z1 = new double[4];
        double[] x2 = new double[4];
        double[] y2 = new double[4];
        double[] z2 = new double[4];
        double[] zs1 = new double[4];
        double[] zs2 = new double[4];
        int sides = myShapes[polyN].length;

        boolean done = true;
        for(int i=0;i<4;i++){
            done = done && zScore[i]>0;
        }
        if(done){
            int[] scrx = new int[sides];
            int[] scry = new int[sides];
            boolean discardXR = true;
            boolean discardXL = true;
            boolean discardYR = true;
            boolean discardYL = true;
        
            for(int i=0;i<sides;i++){
                renderPoint(x[myShapes[polyN][i]],y[myShapes[polyN][i]],z[myShapes[polyN][i]],b1,b2,b3,b4,camX,camY,camZ);
            
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
        
            //image.fillPolygon(scrx,scry,scrx.length);
            //if(renderMode == 1){
            //    image.drawPolygon(scrx,scry,scrx.length);
            //}
            //return;
        }
        done = true;
        for(int i=0;i<4;i++){
            done = done && zScore[i]<0;
        }
        if(done){
            //return;
        }
        
        int[] wrap = {0,1,2,3,4,5,6,7,8};
        wrap[sides] = 0;
        
        for(int i=0;i<sides;i++){
            x1[i] = x[myShapes[polyN][wrap[i]]];
            x2[i] = x[myShapes[polyN][wrap[i+1]]];
            y1[i] = y[myShapes[polyN][wrap[i]]];
            y2[i] = y[myShapes[polyN][wrap[i+1]]];
            z1[i] = z[myShapes[polyN][wrap[i]]];
            z2[i] = z[myShapes[polyN][wrap[i+1]]];
            zs1[i] = zScore[myShapes[polyN][wrap[i]]];
            zs2[i] = zScore[myShapes[polyN][wrap[i+1]]];
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
        
        for(int i=0;i<sides;i++){
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
                if(d<0){
                    ratio += .01;
                }else{
                    ratio -= .01;
                }
                
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
        
        for(int i=0;i<sides;i++){
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
             
        int[][] scr = new int[2][size];
        boolean discardXR = true;
        boolean discardXL = true;
        boolean discardYR = true;
        boolean discardYL = true;
        
        for(int i=0;i<size;i++){
            renderPoint(px[i],py[i],pz[i],b1,b2,b3,b4,camX,camY,camZ);
            
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
            
            scr[0][i] = rx;
            scr[1][i] = ry;
        }

        return scr;
    }
    
    
    public void renderPoint(double x, double y, double z,double b1,double b2,double b3,double b4,double camX,double camY,double camZ){   
        /*
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
            //renderable = true;
        }
        if(zTemp2<0){
            //notRenderable = true;
        }
        
        rx = 300+(int)(300*(xTemp2/zTemp2));                        
        ry = 200+(int)(300*(yTemp2/zTemp2)); 
        */
        xTemp1 = scale*x*a1 - scale*y*a2;
        yTemp1 = scale*y*a1 + scale*x*a2;
            
        zTemp1 = scale*z*a3 - yTemp1*a4;
        yTemp2 = yTemp1*a3 + scale*z*a4;
            
        xTemp2 = xTemp1*a5 - zTemp1*a6;
        zTemp2 = zTemp1*a5 + xTemp1*a6;
            
        //nx[i] = xTemp2;
       // ny[i] = yTemp2;
       // nz[i] = zTemp2;     
            
        xTemp1 = xTemp2 + X - camX;
        yTemp1 = yTemp2 + Y - camY;
        zTemp1 = zTemp2 + Z - camZ;
            
        xTemp2 = xTemp1*b1 - zTemp1*b2;
        zTemp1 = zTemp1*b1 + xTemp1*b2;
            
        yTemp2 = -(yTemp1*b3 - zTemp1*b4);
        zTemp2 = zTemp1*b3 + yTemp1*b4;
             
        //renderable[i] = (zTemp2>0) ? 1 : 0;
             
        //distance[i] = Math.pow(xTemp2,2)+Math.pow(yTemp2,2)+Math.pow(zTemp2+9,2); 
            
        rx = 300+(int)(300*(xTemp2/zTemp2));                        
        ry = 200+(int)(300*(yTemp2/zTemp2));
        //zScore[i] = zTemp2;
    }
}
