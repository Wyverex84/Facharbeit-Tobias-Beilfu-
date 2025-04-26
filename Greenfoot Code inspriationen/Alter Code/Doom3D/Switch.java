import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Switch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Switch  extends GameObject
{
    boolean thrown = false;
    world w;
    
    public Switch(double r){
        myObject = new Object3D("Switch",true);
        rotate(0,0,r);
    }
    
    protected void addedToWorld(World world){
        w = (world)world;
    }
    
    public void act() 
    {
        if(Greenfoot.isKeyDown("e") && !thrown && w.isNear(this)){
            thrown = true;
            rotate(0,Math.PI,0);
            w.removeObjects(w.getObjects(Laser.class));
        }
    }    
}
