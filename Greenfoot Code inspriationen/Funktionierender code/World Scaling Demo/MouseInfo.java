import greenfoot.*;

/**
 * CLASS: MouseInfo (subclass of Object)<br>
 * AUTHOR: danpost (greenfoot.org username)<br>
 * VERSION: March 10, 2015<br>
 * <br>
 * DESCRIPTION: a class to create objects that mimic greenfoot.MouseInfo objects;
 * the Zoomer class uses this class to give a project, whose worlds are displayed
 * by way of the images of a PIP Actor object, normal mouse functioning with only
 * minimal modifications to the classes
 */
public class MouseInfo
{
    private Actor actor;
    private int x;
    private int y;
    private int clicks;
    private int button;
    
    /** creates a MouseInfo object */
    public MouseInfo()
    {
        greenfoot.MouseInfo mouse = Greenfoot.getMouseInfo();
        button = mouse.getButton();
        clicks = mouse.getClickCount();
        x = mouse.getX();
        y = mouse.getY();
    }
    
    /**
     * sets the given values to be the x and y coordinates for this object
     * @param mseX the new x-coordinate value
     * @param mseY the new y-coordinate value
     */
    public void setCoordinates(int mseX, int mseY)
    {
        x = mseX;
        y = mseY;
    }
    
    /**
     * sets the given actor to be the actor for this object
     * @param obj the Actor object to be associated with the mouse action
     */
    public void setActor(Actor obj)
    {
        actor = obj;
    }
    
    /**
     * mimics the 'getX' method of the 'greenfoot.MouseInfo' class and
     * returns the x-coordinate value of the apparent location of the mouse
     * @return the apparent x-coordinate value for the location of the mouse
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * mimics the 'getY' method of the 'greenfoot.MouseInfo' class and
     * returns the y-coordinate value of the apparent location of the mouse
     * @return the apparent y-coordinate value for the location of the mouse
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * mimics the 'getActor' method of the 'greenfoot.MouseInfo' class and
     * returns the actor apparent associated with the last mouse action
     * @return the apparent actor associated with the last mouse action
     */
    public Actor getActor()
    {
        return actor;
    }
    
    /**
     * mimics the 'getButton' method of the 'greenfoot.MouseInfo' class and
     * returns the number of the button associated with the mouse action
     * @return the number of the mouse button associated with the mouse action
     */
    public int getButton()
    {
        return button;
    }
    
    /**
     * mimics the 'getClickCount' method of the 'greenfoot.MouseInfo' class
     * and returns the click count associated with the last mouse action
     * @return the number of clicks associated with the mouse action
     */
    public int getClickCount()
    {
        return clicks;
    }
}