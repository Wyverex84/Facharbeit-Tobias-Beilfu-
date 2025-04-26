import greenfoot.*;  
import java.awt.Color;
import java.util.List;

public class beem  extends Actor
{
    double x,y,z;
    double dx,dy,dz,vel;
    double xa,ya;
    Object3D myship;
    int counter=200;
    Xwing fighter;
    static asteroid a;
    List asteroids;
    
    public beem(double a,double b,double c, double d,double e, double f,double g, double h){
        x=a;
        y=b;
        z=c;
        
        dx = d;
        dy = e;
        dz = f;
        vel = Math.sqrt(dx*dx+dy*dy+dz*dz);
        dx = dx/vel;
        dy = dy/vel;
        dz = dz/vel;
        
        xa=g;
        ya=h;
    }
    
    protected void addedToWorld(World world){
        myship = ((raster)getWorld()).newBullet();
        ((raster)getWorld()).addToRaster(myship);
        myship.setLocation(x,y,z);
        myship.setRotation(xa,0,ya);
        isGoingToHit();
        fighter = ((raster)getWorld()).fighter;
        myship.setColor(new Color(255,0,0));
    }
    
    public void act() 
    {
        myship.move(vel*dx,vel*dy,vel*dz);
        collision();
        if(counter==0){
            ((raster)getWorld()).removeFromRaster(myship);
            getWorld().removeObject(this);
        }
        counter--;  
        
    }    
    
    public void isGoingToHit(){ 
        asteroids = ((raster)getWorld()).getAsteroids();
        boolean hit=false;
        boolean fixed=true;
        double r;
        double ax,ay,az,result;
        for(int i=0;i<asteroids.size();i++){
            a = (asteroid) asteroids.get(i);
            ax=a.getPosition()[0]-x;
            ay=a.getPosition()[1]-y;
            az=a.getPosition()[2]-z;
            r=a.r;
            result = Math.pow(dx*ax+dy*ay+dz*az,2) - (ax*ax+ay*ay+az*az) + r*r;
            if(result>=0)
                a.setDelay((int)(Math.sqrt(ax*ax+ay*ay+az*az)/vel));
        }
    }
    
    public void collision(){
        double r;
        double ax,ay,az,result;
        x=myship.getLocation()[0];
        y=myship.getLocation()[1];
        z=myship.getLocation()[2];

        ax=fighter.getPosition()[0]-x;
        ay=fighter.getPosition()[1]-y;
        az=fighter.getPosition()[2]-z;
        r=3;
        result = Math.pow(dx*ax+dy*ay+dz*az,2) - (ax*ax+ay*ay+az*az) + r*r;
        if(result>=0 && ax*ax+ay*ay+az*az<vel*vel){
            counter=0;
            fighter.rock();
            for(int j=0;j<8;j++){
                getWorld().addObject(new rubble(x,y,z,.2,3),0,0);
            }
            ((raster)getWorld()).explode(x,y,z);
        }
    }
            
}

