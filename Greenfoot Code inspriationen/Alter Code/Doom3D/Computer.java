import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class computer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Computer  extends Scenery
{
    public Computer(){
        myObject = new Object3D("Computer",true);
        rotate(0,0,-.5);
    }
    
    public Computer(double r){
        myObject = new Object3D("Computer",true);
        rotate(0,0,-.5 + r);
    }
    
    public void act() 
    {
    }    
}
