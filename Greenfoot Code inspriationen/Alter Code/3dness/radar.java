import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.awt.Color;

public class radar  extends Actor
{
    List fighters;
    Xwing xwing;
    double x,y,x1,ang;
    double a1,a2;
    
    protected void addedToWorld(World world){
        xwing = ((raster)getWorld()).fighter;
        setImage(new GreenfootImage(50,50));
    }


    public void act() 
    {
        fighters = ((raster)getWorld()).getFighters();
        getImage().clear();
        getImage().setColor(new Color(128,128,128));
        getImage().fillOval(0,0,49,49);
        getImage().setColor(Color.black);
        getImage().fillOval(2,2,45,45);
        getImage().setColor(Color.red);
        ang = -xwing.ya + Math.PI;
        a1 = Math.cos(ang);
        a2 = Math.sin(ang);
        for(int i=0;i<fighters.size();i++){
            x = ((Enemy)fighters.get(i)).getPosition()[0] - xwing.x;
            y = ((Enemy)fighters.get(i)).getPosition()[2] - xwing.z; 
            x1 = (x*a1 - y*a2)/-200.0;
            y = (y*a1 + x*a2)/200.0;
            
            getImage().setColor(((Enemy)fighters.get(i)).color());
            if(x1*x1+y*y<484)
                getImage().fillRect(25+(int)x1,25+(int)y,2,2);
        }
        //setRotation((int)(-180.0*(xwing.ya/Math.PI))+180);
    }    
}
