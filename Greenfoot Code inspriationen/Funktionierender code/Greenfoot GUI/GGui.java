import greenfoot.*;

/**
 * GGui os the standard interface for PIRocks' greenfoot gui.
 * 
 * All objects that are part of greenfoot gui must implements GGUi.
 * 
 * @author PiRocks
 * @version 1.0
 */
public interface GGui  
{
    /**
     * Gets an image to be drawn.
     */
    public GreenfootImage getPic();
    /**
     * Update the image.
     */
    public void update();
    /**
     * Handle all key/mouse input. Callers of this method must specify the acctual coordinates of the objects render center.
     * This needs to be specified because only in top-level containers does "getX()" and "getY()" perform properly. In other containers they won't even work...
     */
    public void handleInput(MouseInfo m,int realX,int realY);
}
