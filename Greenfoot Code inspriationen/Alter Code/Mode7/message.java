import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class message here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class message  extends Actor
{
    int counter=75;
    static GreenfootImage  mes = new GreenfootImage("message.png");
    static GreenfootImage screen = new GreenfootImage(300,150);
    
    public message(int type){
        screen.clear();
        setImage(screen);
        screen.drawImage(mes,0,-150*type);
    }
    
    
    public void act() 
    {
        counter--;
        if(counter==0)getWorld().removeObject(this);
    }    
}
