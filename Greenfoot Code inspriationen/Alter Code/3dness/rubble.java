import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

public class rubble extends Actor
{
    double x,y,z,dx,dy,dz,radius;
    Object3D myship;
    int counter=Greenfoot.getRandomNumber(40)+20;
    int type;
    
    public rubble(double a,double b,double c,double r,int t){
        x=a;
        y=b;
        z=c;
        radius=r;
        type=t;
    }
    
    protected void addedToWorld(World world){
        myship = ((raster)getWorld()).newRubble();
        ((raster)getWorld()).addToRaster(myship);
        myship.rotate(Math.random(),Math.random(),0);
        int color = Greenfoot.getRandomNumber(70)+20;
        double speed=0;
        if(type==1){
            myship.setColor(new Color(color*2,color,20));
            myship.scale(2*Math.random());
            speed=2;
        }
        if(type==2){
            myship.setColor(new Color(color*2,color*2,color*2));
            myship.scale(.4*Math.random());
            speed=.4;
        }
        if(type==3){
            color = Greenfoot.getRandomNumber(70)+50;
            myship.setColor(new Color(color,color,color));
            myship.scale(.1*Math.random());
            speed=.1;
        }
        dx = speed*Math.random()-speed/2.0;
        dy = speed*Math.random()-speed/2.0;
        dz = speed*Math.random()-speed/2.0;
        x=x+radius*dx;
        y=y+radius*dy;
        z=z+radius*dz;
        myship.setLocation(x,y,z);
    }
    
    public void act() 
    {
        myship.move(dx,dy,dz);
        counter--;
        if(counter==0){
            ((raster)getWorld()).removeFromRaster(myship);
            getWorld().removeObject(this);
        }
    }
}
