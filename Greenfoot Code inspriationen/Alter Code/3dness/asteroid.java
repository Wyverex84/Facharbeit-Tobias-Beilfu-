import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

public class asteroid  extends Actor
{
    double x,y,z,r;
    Object3D myship;
    boolean dead=false,hit=false;
    int delay=0;
    
    public asteroid(double a,double b,double c){
        x=a;
        y=b;
        z=c;
    }
    
    protected void addedToWorld(World world){
        myship = ((raster)getWorld()).newAsteroid();
        ((raster)getWorld()).addToRaster(myship);
        myship.setLocation(x,y,z);
        myship.rotate(Math.PI/2.0,0,0);
        int color = Greenfoot.getRandomNumber(70)+20;
        myship.setColor(new Color(color*2,color,20));
        r = 8*Math.random()+1;
        myship.scale(r);
        r=r+2;
    }
    
    public void act() 
    {
        if(hit){
            delay--;
            if(delay==0)explode();
        }
        myship.rotate(0,0,.01);
    }    
    
    public double[] getPosition(){
        return myship.getLocation();
    }
    
    public double radius(){
        return r;
    }
    
    public void explode(){
        if(dead)return;
        dead=true;
        ((raster)getWorld()).removeFromRaster(myship);
        for(int i=0;i<16;i++){
            getWorld().addObject(new rubble(x,y,z,r,1),0,0);
        }
        ((raster)getWorld()).explode(x,y,z);
        getWorld().removeObject(this);
    }
    
    public void setDelay(int d){
        if(!hit)
            delay=d;
        hit=true;
        //myship.setColor(Color.red);
    }
    
    public double off(){
        return 2*r*Math.random()-r;
    }

}
