import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Square here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Square  extends GComponent
{
   private java.util.Random r = new java.util.Random();
    public Square() {
        super(1,1);
        getDrawPic().scale(r.nextInt(50)+1,r.nextInt(50)+1);
        getDrawPic().setColor(new greenfoot.Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
        getDrawPic().fill();
    }
}
