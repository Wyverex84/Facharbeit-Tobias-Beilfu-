import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

public class Turret extends Enemy
{
    Object3D myship;
    double x,y,z,vel=17;
    double vx,vy,vz,mag,dist;
    int counter = Greenfoot.getRandomNumber(20);
    Xwing X;
    
    public Turret(double a, double b, double c){
        x = a;
        y = b;
        z = c;
    }
    
    protected void addedToWorld(World world){
        myship = ((raster)getWorld()).newTurret();
        ((raster)getWorld()).addToRaster(myship);
        myship.setLocation(x,y,z);
        myship.scale(10);
        X = ((raster)getWorld()).fighter;
    }
    
    public void act(){
        myship.rotate(0,0,.07);
        counter++;
        dist = Math.sqrt(Math.pow(x-X.x,2)+Math.pow(y-X.y,2)+Math.pow(z-X.z,2));
        if(counter%20==0 && dist < 1500){
            vx = (X.x+(dist*X.vel*X.vx)/vel)-x;
            vy = (X.y+(dist*X.vel*X.vy)/vel)-y;
            vz = (X.z+(dist*X.vel*X.vz)/vel)-z;
            mag = Math.sqrt(vx*vx+vy*vy+vz*vz);
            vx = vx/mag;
            vy = vy/mag;
            vz = vz/mag;
            getWorld().addObject(new beem(x,y,z,vel*vx,vel*vy,vel*vz,0,Math.atan2(vz,vx)-Math.PI/2.0),0,0);
        }
    }
     
    public double[] getPosition(){
        return myship.getLocation();
    }
    
    public double r(){
        return 10;
    }
    
    public void hurt(){
    }
    
    public Color color(){
        return Color.blue;
    }
    
}
