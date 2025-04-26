import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class blur here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class blur  extends Actor
{
    int alpha;
    sphere s;
    
    public blur(int a,sphere sa){
        alpha = a;
        s = sa;
        setImage(new GreenfootImage(300,300));
        getImage().setTransparency(alpha);
    }
    
    
    public void act() 
    {
        getImage().clear();
        getImage().drawImage(s.getImage(),0,0);
    }    
}
