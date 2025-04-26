import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A PopUpImage for an artefact.
 * 
 * @author Michael Lastufka
 * @version Dec 2008
 */
public class PopUpImage extends Actor
{
    public static final int k_centerX = 300;
    public static final int k_centerY = 525;

    private GreenfootImage m_board = null;

    /**
     * Constructor for PopUpImage.
     * @param fileName the name of the object's catalog image.
     */
    public PopUpImage (String fileName)
    {
        m_board = new GreenfootImage(fileName);
        setImage(m_board);
    }

    /**
     * 
     */
    public void act() 
    {
        
    }    
}
