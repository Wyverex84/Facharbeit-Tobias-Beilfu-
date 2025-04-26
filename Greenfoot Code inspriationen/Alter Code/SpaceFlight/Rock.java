import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Rock tumbling in space.
 * 
 * @author Michael Lastufka
 * @version Dec 2008
 */
public class Rock extends SpaceObject
{
    protected String m_fileName;
    private int m_rotation;
    private int m_spin;
    
    public Rock()
    {
        this("rock.png");
    }
    
    public Rock(String fileName)
    {
        super(fileName, "Rock", "A random, icy spaceroid.");
        m_fileName = fileName;
        m_spin = Greenfoot.getRandomNumber(5)+1;
        m_rotation = Greenfoot.getRandomNumber(360);
        setRotation(m_rotation);
    }
    
    /**
     * 
     */
    public void act() 
    {
        setImage(m_fileName);
        m_rotation += m_spin;
        setRotation(m_rotation);
        super.act();
    }
   
}
