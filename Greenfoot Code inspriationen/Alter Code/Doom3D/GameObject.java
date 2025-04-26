import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class GameObject  extends Actor
{
    protected double myX,myY,myZ;
    protected Object3D myObject;
    boolean rendered = false;
    
    public abstract void act();

    public void drawObject(GreenfootImage image){
        Raster.drawObject(image,myObject);
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
    
    public boolean isRendered(){
        return rendered;
    }
    
    public void clearRendered(){
        rendered = false;
    }
    
    public Object3D getObject(){
        return myObject;
    }
    
    public double[] getPosition(){
        double[] pos = {myX,myY,myZ};
        return pos;
    }
    
    public void rotate(double a,double b,double c){
        myObject.rotate(a,b,c);
    }
}
