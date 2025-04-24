import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * GComponent is part of PiRocks' GReenfoot GUI package.
 * 
 * More methods will be added in future releasese to increase versatility,
 * but these two constructors are good enough for now.
 * 
 * @author PiRocks
 * @version 1.0
 */
public class GComponent  extends GCanvas
{
    /**
     * Creates a new GComponent - standard constructor.
     */
    public GComponent()
    {
        super(1,1);
    }
    
    /**
     * Creates a new GComponent with the specified width and height.
     */
    public GComponent(int w, int h) {
        super(w,h);
    }
}
