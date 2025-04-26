import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.awt.Color;

public class Xwing  extends Actor
{

    double x,y,z,xa=0,ya=0,za,roll;
    double vx,vy,vz;
    int mx=200,my=200,rolling=0,rolled=30;
    Object3D myship;
    double vel=0;
    int speed=1;
    crosshair cross = new crosshair();
    speedMeter meter = new speedMeter();
    
    boolean aiming=false;
    
    //AIMING DATA
    double cx,cy,cz;
    double aimx,aimy,aimz;
    
    public Xwing(double a,double b,double c){
        x=a;
        y=b;
        z=c;
    }
    
    protected void addedToWorld(World world){
        myship = ((raster)getWorld()).newXwing();
        ((raster)getWorld()).addToRaster(myship);
        myship.setLocation(x,y,z);
        getWorld().addObject(cross,0,0);
        getWorld().addObject(meter,383,200);
    }
    
    public void act() 
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse!=null){
            if(mx-mouse.getX()>40 && rolled==0)rolling=-1;
            if(mx-mouse.getX()<-40 && rolled==0)rolling=1;
            mx=mouse.getX();
            my=mouse.getY();
        }

        ya = ya - (mx-200)/2300.0;
        xa = (my-200)/150.0;
        
        
        vx = -Math.sin(ya)*Math.cos(xa);
        vy = Math.sin(xa);
        vz = Math.cos(ya)*Math.cos(xa);
        
        myship.move(vel*vx,vel*vy,vel*vz);
        
        myship.setRotation(xa,(mx-200)/150.0+roll,ya);
        if(Greenfoot.isKeyDown("up") && vel<14)
            vel=vel+.05;
        if(Greenfoot.isKeyDown("down") && vel>0)
            vel=vel-.05;
        meter.setX((int)(vel*25));
        x = getPosition()[0];
        y = getPosition()[1];
        z = getPosition()[2];
        
        cx = vx;
        cy = vy;
        cz = vz;
        
        cross.myship.setColor(Color.black,Color.red);
        if(aiming)
            isGoingToHit();

        cross.myship.setLocation(x+14*cx,y+14*cy,z+14*cz);
        cross.myship.setRotation(xa,0,ya);  
        
        if(Greenfoot.mousePressed(null) || Greenfoot.isKeyDown("space")){
            if(Greenfoot.mousePressed(null) && mouse.getButton()!=1){
                aiming = !aiming;
            }else{
                getWorld().addObject(new bullet(x-vz,y+vy,z+vx,(19+vel)*cx,(19+vel)*cy,(19+vel)*cz,xa,ya),0,0);
                getWorld().addObject(new bullet(x+vz,y+vy,z-vx,(19+vel)*cx,(19+vel)*cy,(19+vel)*cz,xa,ya),0,0);
            }
        }
            
        if(rolling!=0){
            rolled=30;
            roll=roll+rolling*(Math.PI*2+.2 - rolling*roll)/20.0;
            if(rolling*roll>Math.PI*2){
                roll=0;
                rolling=0;
            }
        }
        if(rolled>0)rolled--;
       
        collision();
    }    
    
    public double[] getPosition(){
        return myship.getLocation();
    }
    
    public double[] getDirection(){
        return myship.getRotation();
    }
        
    
    public void collision(){
        List asteroids = ((raster)getWorld()).getAsteroids();
        boolean hit=false;
        double r;
        for(int i=0;i<asteroids.size();i++){
            asteroid a = (asteroid) asteroids.get(i);
            r=a.radius();
            if(Math.abs(a.getPosition()[0]-x)<3+r &&
               Math.abs(a.getPosition()[1]-y)<1+r &&
               Math.abs(a.getPosition()[2]-z)<4+r) a.explode();
        }
    }
    
    public void isGoingToHit(){
        List fighters = ((raster)getWorld()).getFighters();
        Enemy a;
        cross.myship.setColor(Color.red,Color.red);
        boolean hit=false;
        boolean fixed=true;
        double r;
        for(int i=0;i<fighters.size();i++){
            a = (Enemy) fighters.get(i);
            double ax=a.getPosition()[0]-x,ay=a.getPosition()[1]-y,az=a.getPosition()[2]-z;
            r=10*a.r();
            double result = Math.pow(cx*ax+cy*ay+cz*az,2) - (ax*ax+ay*ay+az*az) + r*r;
            if(result>=0 && fixed && Math.cos(ya+Math.atan2(ax,az))>.9 && (ax*ax+ay*ay+az*az)<10000000){
                fixed=false;
                cx = ax;
                cy = ay;
                cz = az;
                double magnitude = Math.sqrt(cx*cx+cy*cy+cz*cz);
                cx=cx/magnitude;
                cy=cy/magnitude;
                cz=cz/magnitude;
                cross.myship.setColor(Color.green,Color.green);
                meter.setEn((int)(a.vel()*25));
            }
        }
        if(Math.abs(Math.atan2(cx,cz)-Math.atan2(vx,vz))>1){
            cx=vx;
            cy=vy;
            cz=vz;
        }
    }
    
    public void rock(){
    }
    
}
