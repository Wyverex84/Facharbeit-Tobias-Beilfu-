import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * PopUpInfo for Screen Objects.
 * If one exists when a new one is created, it is destroyed
 * then the new one takes its place on the screen.
 * There is only one at a time!
 * 
 * @author Michael Lastufka
 * @version Dec 2008
 */
public class PopUpInfo extends Actor
{
    
    public static final int k_centerX = 490;
    public static final int k_centerY = 524;
    private static PopUpInfo s_info = null;
    
    private GreenfootImage m_board = null;
    private IPopUpInfo m_obj;

    /**
     * Public Constructor for PopUpInfo.
     * @param obj the object whoes info is to be displayed.
     */
    public PopUpInfo (IPopUpInfo obj)
    { // s_info and m_obj are either both null or not.
        if (s_info != null) dispose();
        s_info = null;
        m_obj = obj;
        m_board = m_obj.getInfo(216, 145);
        setImage(m_board);
        s_info = this;
    }

    /**
     * Draws the owner's information to the information display area.
     */
    public void act() 
    {
        m_board = m_obj.getInfo(216, 145);
        setImage(m_board);
    }
    
    /**
     * Handshakes with the source of this information and the Image popup
     * to remove this and the image popup from the world.
     */
    public void dispose()
    {
        if (s_info.m_obj != null) s_info.m_obj.releaseInfo();
        s_info = null;
    }
    
    /**
     * Tells whether information is being displayed.
     * @return true when no information is displayed.
     */
    public static boolean isClear() {return s_info == null;}
}
