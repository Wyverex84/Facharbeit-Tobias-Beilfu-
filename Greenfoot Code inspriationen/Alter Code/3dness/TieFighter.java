import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

public class TieFighter  extends Enemy
{
    //SHIP DATA
    double x,y,z,vx=1,vy=1,vz=1;
    double xa=0,ya=0,za=0,vel,defv;
    Object3D myship;
    double turn = .5;
    double r=2;
    Xwing fighter;
    
    //HEALTH DATA
    boolean dead=false;
    int health=10;
    
    //AI DATA
    int status=0;  //0 = following   1 = getting ready for attack  2 = attacking  3 retreating
    boolean away = false;
    int point = 0;
    double[][] path;
    double distance,mag;
    int frozen=0,counter=0;
    double sx,sy,sz,rx,ry,rz;
    
    
    public TieFighter(double a,double b,double c,double[][] p,double v){
        x=a;
        y=b;
        z=c;
        path = new double[3][p[0].length];
        for(int i=0;i<p[0].length;i++){
            path[0][i] = p[0][i];
            path[1][i] = p[1][i];
            path[2][i] = p[2][i];
        }    
        defv = v;
        vel = defv;
    }
    
    protected void addedToWorld(World world){
        myship = ((raster)getWorld()).newTieFighter();
        ((raster)getWorld()).addToRaster(myship);
        myship.setLocation(x,y,z);
        myship.scale(2);
        fighter = ((raster)getWorld()).fighter;
        //myship.setColor(Color.white);
    }
    
    public void act() 
    {    
        x = myship.X;
        y = myship.Y;
        z = myship.Z;
        
        distance = Math.pow(fighter.x-x,2)+Math.pow(fighter.y-y,2)+Math.pow(fighter.z-z,2);
        mag = (distance*fighter.vel)/(vel+4);
        sx = fighter.x;//+fighter.vx*mag;
        sy = fighter.y;//+fighter.vy*mag;
        sz = fighter.z;//+fighter.vz*mag;
        
        if(status==0){
            vel = defv;
            seek(path[0][point],path[1][point],path[2][point],50000);
            if(Math.pow(x-path[0][point],2)+Math.pow(y-path[1][point],2)+Math.pow(z-path[2][point],2) < 90000){
                point++;
                if(point==path[0].length)point=0;
            }
            if(distance < 500000){
                //myship.setColor(Color.yellow);
                //status = 1;
            }
        }
        if(status==1){
            vel = 5;
            counter++;
            seek(sx,sy,sz,10000);
            if(distance < 100000){
                status = 2;
                myship.setColor(Color.red);
                counter=0;
            }
            if(distance > 1000000){
                status = 1;
                myship.setColor(Color.white);
                counter=0;
            }
            if(counter%20 == 0)
                getWorld().addObject(new beem(x,y,z,(8+vel)*vx,(8+vel)*vy,(8+vel)*vz,xa,ya),0,0);
        }
        if(status==2){
            
            counter++;
            if(frozen < 10){
                if(distance>8000){
                    seek(sx,sy,sz,100);
                    vel = 2;
                }else{
                    vx = vx + .1;
                    seek(x,y,z,100);
                    frozen++;
                    vel = 3.5;
                }
            }
            if(distance>40000)frozen=0;
            if(counter==500){
                myship.setColor(Color.blue);
                status=3;
                counter=0;
            }
            if(counter%10 == 0)
                getWorld().addObject(new beem(x,y,z,(4+vel)*vx,(4+vel)*vy,(4+vel)*vz,xa,ya),0,0);
        }
        
        if(status==3){
            vel = 5;
            if(counter%150 == 10){
                rx = Greenfoot.getRandomNumber(5000);
                ry = 0;
                rz = Greenfoot.getRandomNumber(5000);
            }
            if(counter<10)
                vx += .1;
            counter++;
            seek(sx,sy,0,-1000);
            if(distance > 1000000){
                status=0;
                myship.setColor(Color.white);
            }
        }
                
        
        //seek(fighter.x,fighter.y,fighter.z,8000);
        
        
        
        myship.setRotation(xa,za,ya);
        myship.move(vel*vx,vel*vy,vel*vz);
        //if(Greenfoot.isKeyDown("w"))
        //    myship.move(10,0,0);
        //if(Greenfoot.isKeyDown("s"))
        //    myship.move(-10,0,0);
        //if(Greenfoot.isKeyDown("a"))
        //    myship.move(0,0,10);
        //if(Greenfoot.isKeyDown("d"))
        //    myship.move(0,0,-10);
        
        
        
    }    
    
    public double[] getPosition(){
        return myship.getLocation();
    }
    
    public double r(){
        return 1;
    }
    
    public double vel(){
        return vel;
    }
    
    public void explode(){
        if(dead)return;
        dead=true;
        ((raster)getWorld()).removeFromRaster(myship);
        for(int i=0;i<16;i++){
            getWorld().addObject(new rubble(myship.getLocation()[0],myship.getLocation()[1],myship.getLocation()[2],1,2),0,0);
        }
        getWorld().removeObject(this);
    }
    
    public void hurt(){
        health--;
        if(health==0)explode();
        //status=3;
        //myship.setColor(Color.blue);
        counter=0;
    }
    
    public void seek(double dx, double dy, double dz,int sharpness){
        vx = vx + (dx - x)/sharpness;
        vy = vy + (dy - y)/sharpness;
        vz = vz + (dz - z)/sharpness;
        double mag = Math.sqrt( vx*vx + vy*vy + vz*vz);
        vx = (vx/mag);
        vy = (vy/mag);
        vz = (vz/mag);
        ya = Math.atan2(vz,vx)+Math.PI/2.0;
        xa = Math.atan2(Math.sqrt(vx*vx+vz*vz),vy)+Math.PI/2.0;
    }
    
    public Color color(){
        return Color.red;
    }
}
