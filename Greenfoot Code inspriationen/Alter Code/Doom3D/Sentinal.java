import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class Sentinal extends Enemy
{
    //private Object3D myObject;
    //private double myX,myY,myZ;
    //private double nX,nY,nZ;
    //private double velX,velY,velZ;
    //private double angX,angY;  
    //private boolean rendered = false;
    
    private boolean stuck = false;
    private int stuckTime = 0;
    
    private boolean right = false;
    
    private ArrayList<Point3D> waypoints = new ArrayList<Point3D>();
    private double[][] map;
    private world world;
    private int counter = 0;
    
    double bob = 0;
    double spin = 0;
    double spinVel = .1;
    
    public Sentinal(double[][] m){
        map = m;
        myObject = new Object3D("ball");
        myObject.setLocation(myX,myY,myZ);
    }
    
    public Sentinal(){
        myObject = new Object3D("ball");
        myObject.setLocation(myX,myY,myZ);
    }
    
    protected void addedToWorld(World w){
        world = (world)w;
    }
    
    public void act() 
    {
        myX += velX;
        stuck = true;
        if(world.hit(myX,myY+.2,myZ,.51)){
            myX -= velX;
            velX = 0;
        }else{
            stuck = false;
        }
        myY += velY;
        myZ += velZ;
        if(world.hit(myX,myY+.2,myZ,.51)){
            myZ -= velZ;
            velZ = 0;
        }else{
            stuck = false;
        }
        
        velX *= .93;
        velZ *= .93;
        if(velX*velX+velZ*velZ < .01){
            stuckTime++;
        }else{
            stuckTime = 0;
        }
        
        if(Greenfoot.mousePressed(null)){
            double dx = myX - world.myx;
            double dz = myZ - world.myz;
            double mag = Math.sqrt(dx*dx+dz*dz);
            dx /= mag;
            dz /= mag;
            velX += dx/3;
            velZ += dz/3;
            spinVel += 2*Math.random()-1;
        }
        
        
        
        
        if(world.canSee(myObject)){
            engage(world.myx,world.myy,world.myz);
        }else{
            counter = 0;
            double dx = nX-myX;
            double dz = nZ-myZ;
            double mag = Math.sqrt(dx*dx+dz*dz); 
            dx /= mag;
            dz /= mag;
            velX += dx/100;
            velZ += dz/100;
            if(mag<.1 || stuck || stuckTime>50){
                stuckTime = -30;
                while(dx*dx + dz*dz < 4  && stuckTime<0){
                    double[] temp = world.castRay(myX,myY,myZ,0,2*Math.random()*Math.PI,.4);
                    nX = temp[0];
                    nZ = temp[2];
                    dx = nX-myX;
                    dz = nZ-myZ;
                    stuckTime++;
                }
                stuckTime = -30;
            }
        }
        setLocation(myX,1.7+.2*Math.sin(bob),myZ);
        bob += .1;
        spin += spinVel;
        spinVel += (.1-spinVel)/50;
        myObject.rotate(0,0,spinVel/10);
    } 
    
    /*
    public void drawObject(GreenfootImage image){
        Raster.drawObject(image,myObject);
    }
    */
    
    public void engage(double x,double y,double z){
        counter++;
        nX = (int)(x+.5);
        nY = (int)(y+.5);
        nZ = (int)(z+.5);
        if(counter%400 < 200){
            double dx = x-myX;
            double dz = z-myZ;
            double mag = Math.sqrt(dx*dx+dz*dz); 
            dx /= mag;
            dz /= mag;
            
            double x1 = myX+1.7*dz;
            double z1 = myZ-1.7*dx;
            double x2 = myX-1.7*dz;
            double z2 = myZ+1.7*dx;
            if(right && world.canSee(myX,myY,myZ,x1,myY,z1) && world.canSee(x1,myY,z1)){
                velX += dz/100;
                velZ -= dx/100;
            }else{
                right = false;
            }
            if(!right && world.canSee(myX,myY,myZ,x2,myY,z2) && world.canSee(x2,myY,z2)){
                velX -= dz/100;
                velZ += dx/100;
            }else{
                right = true;
            }
            if(mag>10 || mag<2){
                counter = 300;
            }
            
            
            
        }else{
            double dx = x-myX;
            double dz = z-myZ;
            double mag = Math.sqrt(dx*dx+dz*dz);
            dx /= mag;
            dz /= mag;
            if(mag > 6){
                velX += dx/100;
                velZ += dz/100;
            }else{
                if(world.canSee(myX,myY,myZ,myX-dx*4,myY,myZ-dz*4)){
                    velX -= dx/100;
                    velZ -= dz/100;
                }else{
                    counter = 0;
                }
                
            }
        }
        //setLocation(myX,myY,myZ);
    }
    
    /*
    public Object3D getObject(){
        return myObject;
    }
    
    public boolean renderTime(boolean r[][],double h){
        if(rendered){
            return false;
        }
        myX = myObject.getX();
        myY = myObject.getY();
        myZ = myObject.getZ();
        int x1 = (int)(myX-.49),z1 = (int)(myZ-.49);
        int x2 = (int)(myX+.49),z2 = (int)(myZ-.49);
        int x3 = (int)(myX-.49),z3 = (int)(myZ+.49);
        int x4 = (int)(myX+.49),z4 = (int)(myZ+.49);
        rendered = r[x1][z1] && r[x2][z2] && r[x3][z3] && r[x4][z4];
        return rendered;
    }
    
    public void setLocation(double x,double y,double z){
        myX = x;
        myY = y;
        myZ = z;
        myObject.setLocation(x,y,z);
    }
    
    public double[] getPosition(){
        double[] loc = {myX,myY,myZ};
        return loc;
    }
    
    public boolean isRendered(){
        return rendered;
    }
    
    public void clearRendered(){
        rendered = false;
    }
    */
    
}

