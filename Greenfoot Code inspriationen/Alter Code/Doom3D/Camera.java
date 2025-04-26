/**
 * Write a description of class Camera here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Camera  
{
    private double myX,myY,myZ;
    private double angX,angY;
    public double d1,d2,d3,d4;
    private double lenseC;
    
    
    public Camera()
    {
        
    }
    
    public Camera(double x,double y,double z)
    {
        myX = x;
        myY = y;
        myZ = z;
        lenseC = 300;
    }
    
    public Camera(double x,double y,double z,double xa,double ya)
    {
        myX = x;
        myY = y;
        myZ = z;
        angX = xa;
        angY = ya;
        lenseC = 300;
    }
    
    public Camera(double x,double y,double z,double xa,double ya,double l)
    {
        myX = x;
        myY = y;
        myZ = z;
        angX = xa;
        angY = ya;
        lenseC = l;
    }
    
    public void rotate(double dx,double dy){
        setRotation(getRotationX()+dx,getRotationY()+dy);
    }
    
    public void setRotation(double xa,double ya){
        angX = xa;
        angY = ya;
        d1 = Math.cos(angY);
        d2 = Math.sin(angY);
        d3 = Math.cos(angX);
        d4 = Math.sin(angX);
    }
    
    public double getRotationX(){
        return angX;
    }
    
    public double getRotationY(){
        return angY;
    }
    
    public void move(double dx,double dy,double dz){
        setLocation(getX()+dx,getY()+dy,getZ()+dz);
    }
    
    public void setLocation(double x,double y,double z){
        myX = x;
        myY = y;
        myZ = z;
    }
    
    public double getX(){
        return myX;
    }
    
    public double getY(){
        return myY;
    }
    
    public double getZ(){
        return myZ;
    }
    
    public void setLenseConstant(double l){
        lenseC = l;
    }
    
    public double getLense(){
        return lenseC;
    }
}
