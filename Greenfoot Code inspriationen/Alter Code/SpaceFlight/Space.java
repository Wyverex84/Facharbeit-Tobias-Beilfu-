import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Space - all the action happens here.
 * 
 * @author Michael Lastufka
 * @version Dec 2008
 */
public class Space extends World
{
    private boolean m_init = false;
    
    private ArrayList<SpaceObject> m_objects = null;
    
    /**
     * Constructor for objects of class Space.
     */
    public Space()
    {    
        super(600, 600, 1);
        setBackground("space1.gif"); // the background doesn't move yet.
        addObject(Cockpit.getOnly(),300,300);
        setPaintOrder(PopUpInfo.class, PopUpImage.class, Cockpit.class);
        m_objects = new ArrayList<SpaceObject>(10);
    }
    
    /**
     * Initialize the 3-D world with something simple to navigate through.
     */
    public void act()
    {
        if (!m_init)
        {
            for (int count = 0; count < 10; count++)
            {
                Rock rock = new Rock();
                addObject(rock,
                    Greenfoot.getRandomNumber(500) + 50, 
                    Greenfoot.getRandomNumber(400) + 50);
                m_objects.add(rock);
            }
            m_init = true;
        }
    }

    /**
     * Rotates each object's space coordinates.
     * @param pov The rotational point-of-view.
     * @param rotCommand The rotation angles in degrees to be applied.
     */
    public void rotateWorld(Vector3D pov, Vector3D rotCommand)
    {   // rotate all the viewable objects in space.
        for (SpaceObject so : m_objects)
        {
            so.rotatePosition(pov, rotCommand);
        }
    }
}
