/**
 * Write a description of class Vector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Vector  
{
    // instance variables - replace the example below with your own
    double x,y;

    public Vector(double a, double b)
    {
        x = a;
        y = b;
    }

    
    public double xPart(){
        return x;
    }
    
    public double yPart(){
        return y;
    }
    
    public double magnitude(){
        return Math.sqrt(x*x+y*y);
    }
    
    public Vector getUnitVector(){
        return new Vector(x/magnitude(),y/magnitude());
    }
    
    public double dot(Vector v){
        return x*v.x + y*v.y;
    }
    
    public double cross(Vector v){
        return x*v.y - y*v.x;
    }
    
    public Vector scale(double s){
        return new Vector(x*s,y*s);
    }
    
    public Vector subtract(Vector v){
        return new Vector(x-v.x,y-v.y);
    }
    
    public Vector add(Vector v){
        return new Vector(x+v.x,y+v.y);
    }
}
