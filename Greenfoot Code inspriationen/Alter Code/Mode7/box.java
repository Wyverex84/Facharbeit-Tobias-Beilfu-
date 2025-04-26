import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.List;

public class box extends Actor
{
    double[] x = {-13,13,13,-13};
    double[] y = {-13,-13,13,13};
    double[] xprev = new double[4];
    double[] yprev = new double[4];
    double[] xv = {0,0,0,0};
    double[] yv = {0,0,0,0};
    double a,b,c,d,l,ang;
    int[] conect1 = {0,1,2,3,0,1};
    int[] conect2 = {1,2,3,0,2,3};
    double[] dl = new double[6];
    
    List springs;
    
    double x1,y1,x2=0,y2=0,r1,r2;
    int p=0;
    int[] points = new int[4];
    int pointer=0;
    boolean drag=false;
    boolean stick=false;
    int mx,my;
    worldness world;
    
    int type=1;
    boolean tied=true;
    boolean simple=false;
    
    int point1,point2;
    
    public box(int image){
        if(image==2)
            setImage("Die2.bmp");
        Color alpha = new Color(0,0,0,0);
        for(int x=0;x<26;x++){
            for(int y=0;y<26;y++){
                if(getImage().getColorAt(x,y).equals(Color.white))
                    getImage().setColorAt(x,y,alpha);
            }
        }
    
        for(int f=0;f<6;f++){
            dl[f] = Math.sqrt(Math.pow((x[conect1[f]] - x[conect2[f]]),2)+Math.pow((y[conect1[f]] - y[conect2[f]]),2));
        }
        //getImage().setTransparency(20);
    }
    
    public box(boolean s){
        for(int f=0;f<6;f++){
            dl[f] = Math.sqrt(Math.pow((x[conect1[f]] - x[conect2[f]]),2)+Math.pow((y[conect1[f]] - y[conect2[f]]),2));
        }
        stick = true;
    }
    
    protected void addedToWorld(World w){
        world = (worldness)w;
        for(int i=0;i<x.length;i++){
            x[i] += getX();
            y[i] += getY();
        }
        springs = getWorld().getObjects(box.class);
        for(int i=0;i<springs.size();i++){
            ((box)springs.get(i)).get();
        }
        for(int i=0;i<4;i++){
            xprev[i] = x[i];
            yprev[i] = y[i];
        }
    }
//*****************************************************************************

    public void get(){
        springs = getWorld().getObjects(box.class);
    }
    
//*****************************************************************************   
    public void act() 
    {
        //if(stick)return;

        pointer=0;
        for(int i=0;i<springs.size();i++){
            if((box)springs.get(i) != this){
                if(collision((box)springs.get(i),true)){
                    rebound((box)springs.get(i));
                }
            }
        }
            
       
    }    
    
//*****************************************************************************

    public boolean collision(box s,boolean record){
        boolean in = false;
        
        for(int i=0;i<x.length;i++){
            in = in || s.pointIn(x[i],y[i]);
            if(s.pointIn(x[i],y[i])){
                if(record){
                    points[pointer] = i;
                    pointer++;
                }
            }
        }
       
        return in;
    }
    
//******************************************************************************   
    
    public boolean pointIn(double a,double b){
        int[] testPoints = {0,1,2,3,0};
        boolean in=true;
        double x1,x0,y1,y0;
        
        for(int i=0;i<x.length;i++){
            x0 = x[testPoints[i]];
            x1 = x[testPoints[i+1]];
            y0 = y[testPoints[i]];
            y1 = y[testPoints[i+1]];
            in = in && ((b-y0)*(x1-x0)-(a-x0)*(y1-y0)>=0);
        }
        return in;
    }
//******************************************************************************   
    
    public void rebound(box s){
        
        double time=0;
        boolean in = true;  
        simple=false;
        
        
        Vector[] normals = new Vector[pointer];
        for(int i=0;i<pointer;i++){
            normals[i] = s.normal(xprev[points[i]],yprev[points[i]]);
            if(normals[i] == null){
                simple=true;
            } 
            normals[i] = normals[i].getUnitVector();
        }
        
        
        //pause();
        double[] biasA = new double[pointer];
        double[] biasB = new double[pointer];
        double dist;
        for(int i=0;i<pointer;i++){
            dist = Math.sqrt(Math.pow(s.x[s.point1]-s.x[s.point2],2)+Math.pow(s.y[s.point1]-s.y[s.point2],2));
            biasA[i] = Math.sqrt(Math.pow(s.x[s.point1]-x[points[i]],2)+Math.pow(s.y[s.point1]-y[points[i]],2));
            biasB[i] = Math.sqrt(Math.pow(s.x[s.point2]-x[points[i]],2)+Math.pow(s.y[s.point2]-y[points[i]],2));
            biasA[i] = (dist-biasA[i])/dist;
            biasB[i] = (dist-biasB[i])/dist;
            
            if(biasB[i]>.95 || biasB[i]<.05){
                for(int f=0;f<4;f++){
                    double vx = getX()-s.getX();
                    double vy = getY()-s.getY();
                    xv[f] = .0001*vx;
                    yv[f] = .0001*vy;
                    s.xv[f] = -.0001*vx;
                    s.yv[f] = -.0001*vy;
                }
                while(collision(s,false) && !Greenfoot.isKeyDown("q")){
                    for(int f=0;f<4;f++){
                        x[f] += xv[f];
                        y[f] += yv[f];
                        s.x[f] += s.xv[f];
                        s.y[f] += s.yv[f];
                    }
                }
                return;
            }
            
        }
        
        while(in && !Greenfoot.isKeyDown("q")){
            in = false;
            for(int i=0;i<pointer;i++){
                if(!stick){
                    x[points[i]] += normals[i].xPart()/50.0;
                    y[points[i]] += normals[i].yPart()/50.0;
                    xv[points[i]] += normals[i].xPart()/100.0;
                    yv[points[i]] += normals[i].yPart()/100.0;
                }
                s.nudge(-normals[i].xPart()/50.0 , -normals[i].yPart()/50.0, biasA[i], biasB[i]);
                in = in || collision(s,false) || s.collision(this,false);
            }
        }
        
        
        
               
    }
//******************************************************************************   
    public Vector normal(double a, double b){
        int[] testPoints = {0,1,2,3,0};
        double x1,x0,y1,y0;
        Vector n = null;
        int tries=0;
        
        for(int i=0;i<x.length;i++){
            x0 = xprev[testPoints[i]];
            x1 = xprev[testPoints[i+1]];
            y0 = yprev[testPoints[i]];
            y1 = yprev[testPoints[i+1]];
            if((b-y0)*(x1-x0)-(a-x0)*(y1-y0)<=0){
                point1 = testPoints[i];
                point2 = testPoints[i+1];
                n = new Vector(y1-y0,x0-x1);//x1-x0);
                tries++;
            }    
        }
        if(n == null)
            System.out.println("error!!!!");
        if(tries>1){
            System.out.println("overload!!");
            simple=true;
        }
        return n;
    }
//******************************************************************************   
    public void unstick(){
        tied=false;
    }
//******************************************************************************   
    public void step(double time, int dir){
        for(int i=0;i<4;i++){
            x[i] += dir*time*xv[i];
            y[i] += dir*time*yv[i];
            if(!stick)
                yv[i] += dir*time*.01;
        }
    }
//******************************************************************************   
    public void nudge(double a, double b,double c, double d){
        //if(stick)return;
        xv[point1] += a*c;
        xv[point2] += a*d;
        yv[point1] += b*c;
        yv[point2] += b*d;
    }
 //******************************************************************************   
 
    public void simulate(int k){
 
        for(int i=0;i<4;i++){
            xprev[i] = x[i];
            yprev[i] = y[i];
        }
  
        
        if(tied){
            yv[0] += -y[0]/550000.0;
            xv[0] += (150 - x[0])/550000.0;
        }
        

        for(int f=0;f<4;f++){
            yv[f] = yv[f]+.00001;
            if(Greenfoot.isKeyDown("right"))
                xv[f] = xv[f]-.00002;
            if(Greenfoot.isKeyDown("left"))
                xv[f] = xv[f]+.00002;
        }
        
        for(int m=0;m<k;m++){
        
            for(int f=0;f<6;f++){
                l = Math.sqrt(Math.pow((x[conect1[f]] - x[conect2[f]]),2)+Math.pow((y[conect1[f]] - y[conect2[f]]),2));
                l = dl[f]-l;
                l = l/10;
            
            
                ang = Math.atan2(y[conect1[f]]-y[conect2[f]],x[conect1[f]]-x[conect2[f]]);
                xv[conect1[f]] = xv[conect1[f]] + l*Math.cos(ang);
                xv[conect2[f]] = xv[conect2[f]] - l*Math.cos(ang);
                yv[conect1[f]] = yv[conect1[f]] + l*Math.sin(ang);
                yv[conect2[f]] = yv[conect2[f]] - l*Math.sin(ang);
            }
        
        
        
            for(int f=0;f<4;f++){
                xv[f] = .9999*xv[f];
                yv[f] = .9999*yv[f];
            
                x[f] = x[f] + xv[f];
                y[f] = y[f] + yv[f];
                if(y[f]>255){
                    yv[f] = 0;
                    y[f]=255;
                    xv[f]=.99*xv[f];
                }
                if(y[f]<0){
                    yv[f] = 0;
                    y[f]=0;
                    xv[f]=.99*xv[f];
                }
                if(x[f]<0){
                    yv[f]=.9*yv[f];
                    x[f]=0;
                    xv[f]=0;
                }
                if(x[f]>300){
                    yv[f]=.9*yv[f];
                    x[f]=300;
                    xv[f]=0;
                }
            }
            
                    
        }
        
        x1 = (x[0]+x[1]+x[2]+x[3])/4.0;
        y1 = (y[0]+y[1]+y[2]+y[3])/4.0;
        r1 = 180*Math.atan2(y[0]-y[1],x[0]-x[1])/Math.PI;
        setLocation((int)x2,(int)y2);
        setRotation((int)r2);
        if(Math.abs(x1-x2)>1.5 || Math.abs(y1-y2)>1.5 || Math.abs(r1-r2)>1.5){
            r2 = r1;
            x2 = x1;
            y2 = y1;
        }
        if(Greenfoot.isKeyDown("q")){
            Greenfoot.stop();
        }
        
        
    }
    
    
}
