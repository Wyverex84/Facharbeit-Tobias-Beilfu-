import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

public class StationSmall extends Enemy
{
    Object3D myship;
    double x,y,z;
    
    public StationSmall(double a, double b, double c){
        x = a;
        y = b;
        z = c;
    }
    
    protected void addedToWorld(World world){
        myship = ((raster)getWorld()).newStationSmall();
        ((raster)getWorld()).addToRaster(myship);
        myship.setLocation(x,y,z);
        myship.scale(50);
    }
    
    public double[] getPosition(){
        return myship.getLocation();
    }
    
    public void act(){
        myship.rotate(0,0,-.004);
    }
    
    public double r(){
        return 50;
    }
    
    public void hurt(){
    }
    
    public Color color(){
        return Color.green;
    }
    
}