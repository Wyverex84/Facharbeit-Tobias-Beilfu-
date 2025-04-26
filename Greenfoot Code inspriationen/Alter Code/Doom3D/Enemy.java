import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class Enemy  extends GameObject
{
    //protected double myX,myY,myZ;
    protected double nX,nY,nZ;
    protected double velX,velY,velZ;
    protected double angX,angY;  
    
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
    
    public Enemy(double[][] m){
        map = m;
        myObject = new Object3D("ball");
        myObject.setLocation(myX,myY,myZ);
    }
    
    public Enemy(){
        myObject = new Object3D("ball");
        myObject.setLocation(myX,myY,myZ);
    }
    
    protected void addedToWorld(World w){
        world = (world)w;
    }
    
    public void act() 
    {}
    
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
    
    
    
}
