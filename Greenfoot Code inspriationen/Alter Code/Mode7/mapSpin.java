import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class mapSpin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class mapSpin  extends Actor
{
    
    public mapSpin(){
        setImage(new GreenfootImage("m1.gif"));
        getImage().scale(100,100);
    }

    public void act() 
    {
        setRotation(getRotation()+1);
    }    
    public void set(int i){
        setImage(((worldness)getWorld()).getMap(i));
        getImage().scale(100,100);
    }
    
}
