import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)



public class Laser  extends GameObject
{
    
    public Laser(double x,double y,double z,double ang,double length){
        //double x1 = length*Math.cos(ang);
        //double x2 = -length*Math.cos(ang);
        //double z1 = length*Math.sin(ang);
        //double z2 = -length*Math.sin(ang);
        double[] xa = {length,length,-length,-length};
        double[] ya = {.1,-.1,-.1,.1};
        double[] za = {0,0,0,0};
        int[][] fa = {{0,1,2,3}};
        Color[] ca = {Color.red};
        myObject = new Object3D(xa,ya,za,fa,ca,false);
        myObject.setLocation(x,y,z);
        myObject.rotate(0,0,ang);
    }
    
    public Laser(double ang,double length){
        //double x1 = length*Math.cos(ang);
        //double x2 = -length*Math.cos(ang);
        //double z1 = length*Math.sin(ang);
        //double z2 = -length*Math.sin(ang);
        double[] xa = {length,length,-length,-length};
        double[] ya = {.1,-.1,-.1,.1};
        double[] za = {0,0,0,0};
        int[][] fa = {{0,1,2,3}};
        Color[] ca = {Color.red};
        myObject = new Object3D(xa,ya,za,fa,ca,false);
        myObject.rotate(0,0,ang);
    }
    
    public void act() 
    {
        int red = (int)(Math.random()*50);
        myObject.setColor(new Color(255,red,red));
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
    
    
    
}
