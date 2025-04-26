import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


import java.text.DecimalFormat;

/**
 * A SpaceObject is any object in space.
 * This object maintains two coordinate frames.
 * One is the 3-D space frame.
 * The other is the 2-D Greenfoot Cockpit screen.
 * This object is only seen when on the Cockpit screen.
 * For other locations, it is a point at (0,0) in 2-D space
 * but can be anywhere in 3-D space outside the 3-D support of
 * the 2-D screen.
 * 
 * @author Michael Lastufka
 * @version Dec 2008
 */
public class SpaceObject extends Actor implements IPopUpInfo
{
    private Vector3D m_position;
    protected double m_size = 1;
    protected double m_w = 0;
    protected double m_h = 0;
    
    protected String m_id;
    protected String m_description;
    
    private final String m_imageFileName;
    private Cockpit m_pov; //Point of view

    protected PopUpInfo m_popup = null;
    protected PopUpImage m_popupIm = null;

    DecimalFormat f_milli = new DecimalFormat("0.000");

    /**
     * Constructor for SpaceObjects.
     * @param imageFileName the name of the image file for this object.
     */
    public SpaceObject(String imageFileName, String id, String description)
    {
        m_imageFileName = imageFileName;
        m_id = id;
        m_description = description;
    }

    /**
     * Space objects are added to the world is some location.
     * If (0,0), then it is not viewable on the cockpit screen.
     * @param world is the 2-D cockpit screen.
     */
    public void addedToWorld(World world)
    {
        double x = getX();
        double y = getY();
        double z = Greenfoot.getRandomNumber(600)+ 400;
        m_position = new Vector3D(x, y, z);
        m_pov = Cockpit.getOnly();
        m_w = getWidth();
        m_h = getHeight();
        Vector3D data2D = m_pov.setOnScreen(m_position);
        setLocation((int)data2D.x, (int)data2D.y);
        m_size = data2D.z; // initially > 0 has to be on the screen
        scaleImage();
    }
    
    /**
     * Gets the short id of the object.
     * @return a unique id.
     */
    public String getId() {return m_id;}

    /**
     * Gets a description of the object.
     * @return the description.
     */
    public String getDescription() {return m_description;}
    
    /**
     * Places and scales the 3-D position of the object on the 2-D screen
     * if visible. Performs whatever actions this object does.
     */
    public void act()
    {
        Vector3D data2D = m_pov.setOnScreen(m_position);
        setLocation((int)data2D.x, (int)data2D.y);
        m_size = data2D.z;
        if (m_size < 0) 
        {   // reset image to total transparency
            //setImage("transparent.png");
        }
        else scaleImage();
    }
    
    /**
     * Scales the object image for display on the 2-D cockpit screen.
     */
    private void scaleImage()
    {
        int w = (int)(m_w*m_size);
        int h = (int)(m_h*m_size);
        if (w == 0) w = 1;
        if (h == 0) h = 1;
        getImage().scale(w, h);
    }
    
    /**
     * Gets the viewscreen size of the object or its
     * visibility code. (see Cockpit.setOnScreen())
     * @returns the magnification factor for visualizing the object.
     */
    public double getSize() {return m_size;}

    /**
     * Called by Cockpit when it intercepts a click for this object.
     * Displays an information display and image for this object.
     */
    public void clicked()
    {
        Greenfoot.playSound("pop.wav");
        if (m_popup == null) 
        {
            m_popup = new PopUpInfo(this);
            getWorld().addObject(m_popup, m_popup.k_centerX, m_popup.k_centerY);
            if (m_popupIm == null) 
            {
                m_popupIm = new PopUpImage(m_imageFileName);
                getWorld().addObject(m_popupIm, m_popupIm.k_centerX, m_popupIm.k_centerY);
            }
        }
        else  m_popup.dispose();
    }

    /**
     * Draws generic space object information for the popup info panel.
     * Override to provide info specific to derived space objects.
     * @param width Info panel width in pixels.
     * @param height Info panel height in pixels.
     * @return the image to display on the Info panel
     */
    public GreenfootImage getInfo(int width, int height)
    {
        Font font = new Font("TimesRoman", Font.BOLD, 12);
        GreenfootImage slate = new GreenfootImage(width, height);
        slate.setColor(Color.YELLOW);
        slate.fill();
        slate.setColor(Color.GREEN);
        slate.drawString(getId(), 60, 10);
        slate.drawString(getDescription(), 10, 20);
        Vector3D v = getPosition3D();
        slate.drawString("x " + Integer.toString((int)v.x), 10, 40);
        slate.drawString("y " + Integer.toString((int)v.y), 10, 50);
        slate.drawString("z " + Integer.toString((int)v.z), 10, 60);
        slate.drawString("X " + Integer.toString(getX()), 100, 40);
        slate.drawString("Y " + Integer.toString(getY()), 100, 50);
        slate.drawString("scale " + f_milli.format(getSize()), 100, 60);
        return slate;
    }
    
    /**
     * Removes the PopUoInfo and PopUpImage for this object.
     */
    public void releaseInfo()
    {
        World space = getWorld();
        if (m_popup != null) space.removeObject(m_popup);
        m_popup = null;
        if (m_popupIm != null) space.removeObject(m_popupIm);
        m_popupIm = null;
    }

    /**
     * Gets the 3-D position of this object.
     * @return 3-D position.
     */
    public Vector3D getPosition3D() {return m_position;}
        
    /**
     * Rotates this object's space coordinates.
     * @param pov The rotational point-of-view.
     * @param rotCommand The rotation angles in degrees to be applied.
     */
    public void rotatePosition(Vector3D pov, Vector3D rotCommand)
    {   // Rotate this object in space.
        Vector3D delta = m_position.add(pov.scale(-1)); 
        if (rotCommand.x != 0) {
            delta = delta.rotateAroundX(rotCommand.x);
            m_position = pov.add(delta);
        }
        if (rotCommand.y != 0) {
            delta = delta.rotateAroundY(rotCommand.y);
            m_position = pov.add(delta);
        }
        if (rotCommand.z != 0) {
            delta = delta.rotateAroundZ(rotCommand.z);
            m_position = pov.add(delta);
        }
    }
}
