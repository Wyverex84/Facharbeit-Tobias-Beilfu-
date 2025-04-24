import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * GCanvas is part of PiRocks' greenfoot gui package. It represents a Canvas object.
 * 
 * @author PiRocks
 * @version 1.0
 */
public class GCanvas  extends Actor implements GGui
{
    
    /** The picture to render onto */
    private GreenfootImage pic;
    
    /**
     * Get the canvas's picture.
     * Please note that drawing to this picture is not drawing onto the canvas.
     */
    public GreenfootImage getPic() { return new GreenfootImage(pic); }
    
    /**
     * Creates a new GCanvas with the specified width and height.
     */
    public GCanvas(int w, int h) {
        pic = new GreenfootImage(w,h);
    }
    
    /**
     * Gets the GreenfootImage reflected by this GCanvas.
     * Used by the greenfoot gui package to draw onto.
     */
    protected GreenfootImage getDrawPic() {
        return pic;
    }
    
    /**
     * Does nothing, must be overwritten in subclasses.
     */
    public void update() { }
    
    /**
     * Does nothing, must be overwritten in subclasses.
     */
    public void handleInput(MouseInfo m,int realX,int realY) { }
}
