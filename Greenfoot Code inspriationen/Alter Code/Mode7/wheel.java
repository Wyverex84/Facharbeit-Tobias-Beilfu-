import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class wheel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class wheel  extends Actor
{

    public void act() 
    { }    
    
    public void set(double f){
        setRotation((int)(900*f));
    }
}
