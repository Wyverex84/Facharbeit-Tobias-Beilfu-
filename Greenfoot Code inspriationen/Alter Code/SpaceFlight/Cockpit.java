import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;



/**
 * Cockpit - where the controls are and where the universe is conquered!
 * 
 * @author Michael Lastufka 
 * @version Dec 2008
 */
public class Cockpit extends Actor implements IPopUpInfo
{
    private static Cockpit pilot = null;

    // Actual coordinates for the point of view
    protected Vector3D m_pov;
    private final Vector3D m_attitude = new Vector3D(0, 0, 0);

    protected final double m_fov = 45; // Half field of view angle.
    private boolean m_linearMode = true; // false is rotational mode
    private int cmd_xAxisRot = 0; // -1, 0, 1 slow rotation - one degree/act
    private int cmd_yAxisRot = 0; // -1, 0, 1
    private int cmd_zAxisRot = 0; // -1, 0, 1

    private static PopUpInfo m_info;
    
    private RCS m_rcsInd;
    private MouseOn m_mouseInd;
    private Thrust m_thrustInd;
    private Thrust [] m_posInd;
    private AxisAngle [] m_axis3Ind;
    
    private double m_speed = 0.0; // fore / aft
    
    private boolean m_mouseNavOn = false;
    private MouseInfo m_lastMpos = null;
    
    private enum Direction {
        None (0, 0),
        Up (1, 1),
        Down (2, 2),
        Left (3, 4),
        Right(4, 8),
        Fore(5, 16),
        Aft(6, 32);
        final public int cmd;
        final public int mask;
        Direction(int cmd, int mask)
        {this.cmd = cmd; this.mask = mask;}
    }
        
    /**
     * Get the one and only cockpit.
     */
    public static Cockpit getOnly()
    { 
        if (pilot == null) pilot = new Cockpit();
        return pilot;
    }
    
    /**
     * Create the cockpit.
     */
    private Cockpit () 
    {
        m_mouseInd = new MouseOn();
        m_mouseInd.setValue(m_mouseNavOn);
        m_rcsInd = new RCS();
        m_rcsInd.setValue(m_linearMode);
        m_thrustInd = new Thrust(5, 580);
        m_thrustInd.setValue(m_speed);
        m_axis3Ind = new AxisAngle[3];
        m_axis3Ind[0] = new AxisAngle("X", 15);
        m_axis3Ind[1] = new AxisAngle("Y", 35);
        m_axis3Ind[2] = new AxisAngle("Z", 55);
        m_posInd = new Thrust[3];
        for (int ind = 0; ind < 3; ind++) m_posInd[ind] = new Thrust(60, 15+ind*20);
    }
   
    /**
     * When added to the world, finds out where it was added.
     * @param world the world it is in.
     */
    public void addedToWorld(World world)
    {
        m_pov = new Vector3D(getX(), getY(), 0.0);
        m_speed = 0.0;
        GreenfootImage gi = getImage();
        gi.drawImage(m_mouseInd.getImage(), m_mouseInd.x, m_mouseInd.y);
        gi.drawImage(m_rcsInd.getImage(), m_rcsInd.x, m_rcsInd.y);
        gi.drawImage(m_thrustInd.getImage(), m_thrustInd.x, m_thrustInd.y);
        gi.drawString("Rotation", 5, 10);
        gi.drawString("Position", 60, 10);
        for (int ind = 0; ind < 3; ind++)
        { 
            m_axis3Ind[ind].setValue(0);
            gi.drawImage(m_axis3Ind[ind].getImage(), m_axis3Ind[ind].x, m_axis3Ind[ind].y);
            m_posInd[ind].setValue(m_pov.axis(ind));
            gi.drawImage(m_posInd[ind].getImage(), m_posInd[ind].x, m_posInd[ind].y);
        }
        m_info = new PopUpInfo(this);
        getWorld().addObject(m_info, m_info.k_centerX, m_info.k_centerY);
    }
    
    /**
     * 
     */
    public void act() 
    {
        processKeys();
    }
    
    private void processKeys()
    {
        int dir = Direction.None.mask;
        if (Greenfoot.mouseClicked(this))
        {   // all clicks are captured by the cockpit object, then passed on
            MouseInfo mi = Greenfoot.getMouseInfo();
            if (mi.getButton() == 1)
            {   // left button
                Actor obj = getOneObjectAtOffset(mi.getX()-300, mi.getY()-300, null);
                if (obj != null && obj instanceof SpaceObject)
                    ((SpaceObject)obj).clicked();
                else if (m_rcsInd.isTouching(mi.getX(), mi.getY()))
                {   // change RCS movement mode via a click
                    m_linearMode = !m_linearMode;
                    m_rcsInd.setValue(m_linearMode);
                    getImage().drawImage(m_rcsInd.getImage(), m_rcsInd.x, m_rcsInd.y);
                }
                else if (m_mouseInd.isTouching(mi.getX(), mi.getY()))
                {   // change mouse movement mode via a click
                    m_mouseNavOn = !m_mouseNavOn;
                    m_mouseInd.setValue(m_mouseNavOn);
                    getImage().drawImage(m_mouseInd.getImage(), m_mouseInd.x, m_mouseInd.y);
                }
                if (PopUpInfo.isClear())
                {   // if nothing else shows info, show the controls
                    m_info = new PopUpInfo(this);
                    getWorld().addObject(m_info, m_info.k_centerX, m_info.k_centerY);
                }
            }
            else // move depending on and direction from center
            {
                m_mouseNavOn = !m_mouseNavOn;
                m_mouseInd.setValue(m_mouseNavOn);
                getImage().drawImage(m_mouseInd.getImage(), m_mouseInd.x, m_mouseInd.y);
            }
        }
       // if (Greenfoot.mouseDragged(this))   m_mouseNavOn = true;
       // if (Greenfoot.mouseDragEnded(this)) m_mouseNavOn = false;
        if (m_mouseNavOn)
        {   // navigate acording to mouse position
            MouseInfo mi = Greenfoot.getMouseInfo();
            if (mi != null) m_lastMpos = mi;
            else if (m_lastMpos != null)
            {
                int dx = m_lastMpos.getX() - 300;
                int dy = m_lastMpos.getY() - 300;
                if (dx < -50) dir += Direction.Left.mask;
                if (dx > 50)  dir += Direction.Right.mask;
                if (dy < -50) dir += Direction.Up.mask;
                if (dy > 50)  dir += Direction.Down.mask;
            }
        }

        String token = Greenfoot.getKey();
        //System.out.println("key = [" + token + "]");
        if (token != null && token.equals("enter"))
        {
            m_linearMode = !m_linearMode;
            m_rcsInd.setValue(m_linearMode);
            getImage().drawImage(m_rcsInd.getImage(), m_rcsInd.x, m_rcsInd.y);
        }
        if (Greenfoot.isKeyDown("up"))    dir += Direction.Up.mask;
        if (Greenfoot.isKeyDown("down"))  dir += Direction.Down.mask;
        if (Greenfoot.isKeyDown("left"))  dir += Direction.Left.mask;
        if (Greenfoot.isKeyDown("right")) dir += Direction.Right.mask;
        if (Greenfoot.isKeyDown(","))     dir += Direction.Fore.mask;
        if (Greenfoot.isKeyDown("."))     dir += Direction.Aft.mask;
        if (Greenfoot.isKeyDown("+")
         || Greenfoot.isKeyDown("="))     
        {    // increase speed
             m_speed += 1;
             m_thrustInd.setValue(m_speed);
             getImage().drawImage(m_thrustInd.getImage(), m_thrustInd.x, m_thrustInd.y);
        }
        if (Greenfoot.isKeyDown("-"))
        {    // decrease speed
             m_speed -= 1;
             m_thrustInd.setValue(m_speed);
             getImage().drawImage(m_thrustInd.getImage(), m_thrustInd.x, m_thrustInd.y);
        }
        if (Greenfoot.isKeyDown("0"))
        {    // stop
             m_speed = 0.0;
             m_thrustInd.setValue(m_speed);
             getImage().drawImage(m_thrustInd.getImage(), m_thrustInd.x, m_thrustInd.y);
        }

        if ((dir & Direction.Up.mask) == Direction.Up.mask)
        {
            if (m_linearMode) m_pov.y = setPosDisplay(1, m_pov.y, 5);
            else cmd_xAxisRot = 1;
        }
        if ((dir & Direction.Down.mask) == Direction.Down.mask)
        {
            if (m_linearMode) m_pov.y = setPosDisplay(1, m_pov.y, -5);
            else cmd_xAxisRot = -1;
        }
        if ((dir & Direction.Left.mask) == Direction.Left.mask)
        {
            if (m_linearMode) m_pov.x = setPosDisplay(0, m_pov.x, -5);
            else cmd_yAxisRot = 1;
        }
        if ((dir & Direction.Right.mask) == Direction.Right.mask)
        {
            if (m_linearMode) m_pov.x = setPosDisplay(0, m_pov.x, 5);
            else cmd_yAxisRot = -1;
        }
        if ((dir & Direction.Fore.mask) == Direction.Fore.mask)
        {
            if (m_linearMode) m_pov.z = setPosDisplay(2, m_pov.z, 5);
            else cmd_zAxisRot = -1;
        }
        if ((dir & Direction.Aft.mask) == Direction.Aft.mask)
        {
            if (m_linearMode) m_pov.z = setPosDisplay(2, m_pov.z, -5);
            else cmd_zAxisRot = 1;
        }
        
        if (m_speed != 0.0) m_pov.z = setPosDisplay(2, m_pov.z, m_speed);
        
        // if any rotation commanded, rotate all viewable objects in space.
        if (cmd_xAxisRot != 0 || cmd_yAxisRot != 0 || cmd_zAxisRot != 0)
        {
            if (cmd_xAxisRot != 0) 
                m_attitude.x = setAxisDisplay(0, m_attitude.x, cmd_xAxisRot);
            if (cmd_yAxisRot != 0) 
                m_attitude.y = setAxisDisplay(1, m_attitude.y, cmd_yAxisRot);
            if (cmd_zAxisRot != 0) 
                m_attitude.z = setAxisDisplay(2, m_attitude.z, cmd_zAxisRot);
            ((Space)getWorld()).rotateWorld(
                m_pov, 
                new Vector3D(cmd_xAxisRot, cmd_yAxisRot, cmd_zAxisRot));
        }
        cmd_xAxisRot = 0; // no rotation
        cmd_yAxisRot = 0; // no rotation
        cmd_zAxisRot = 0; // no rotation
    }
    
    /**
     * Gets the viewpoint.
     * @return the viewpoint in pixels
     */
    public Vector3D getPov() {return m_pov;}
        
    /**
     * Gets half the field of view (fov) of the viewpoint.
     * @return the fov in degrees.
     */
    public double getFov() {return m_fov;}
    
    /**
     * Sets the screen coordinates and scale of an object.
     * A 3-D vector is used for this: 
     * x = 2-D x, y = 2-D y, z = image scale
     * A negative returned size is a code:
     *  -1: behind display
     *  -2: Outside of field of view (fov)
     *  -3: hit the windshield
     * @param pos object's 3-D position.
     * @return 2-D position and scale of the object on the screen, negative if not on screen.
     */
    public Vector3D setOnScreen(Vector3D pos)
    {
        Vector3D delta = pos.add(m_pov.scale(-1));
        if (delta.z <= 0) return new Vector3D(0,0,-1.0); // behind display
        double offSet = Math.sqrt(delta.x*delta.x+delta.y*delta.y);
        double viewAngle = 0.01; // degree
        if (offSet > viewAngle)
            viewAngle = Math.toDegrees(Math.atan(offSet/delta.z));
        if (viewAngle > m_fov) return new Vector3D(0,0,-2.0);
        // This is on the display - but where exactly?
        double r = 300*viewAngle/m_fov;
        double dist = Math.sqrt(offSet*offSet+delta.z*delta.z);
        double dir = Math.toDegrees(Math.atan2(delta.y, delta.x));
        // dir from 180 to -180 degrees.
        double dx = r * Math.cos(Math.toRadians(dir)) + 300;
        double dy = 300 - r * Math.sin(Math.toRadians(dir));   
        if (dist < 50.0) return new Vector3D(0,0,-3.0); // hit the windshield
        return new Vector3D(dx,dy,200/dist);
    }

    /**
     * Draws default information for the popup info panel.
     * @param width Info panel width in pixels.
     * @param height Info panel height in pixels.
     * @return the image to display on the Info panel
     */
    public GreenfootImage getInfo(int width, int height)
    {
        Font font = new Font("TimesRoman", Font.BOLD, 12);
        GreenfootImage slate = new GreenfootImage(width, height);
        slate.setFont(font);
        slate.setColor(Color.YELLOW);
        slate.fill();
        slate.setColor(Color.GREEN);
        slate.drawString("Controls", 90, 10);
        
        slate.setColor(Color.BLUE);
        slate.drawString("<enter>", 6, 20);
        slate.drawString("toggle linear / rotation motion", 52, 20);
        
        slate.setColor(Color.GREEN);
        slate.drawString("Key", 10, 30);
        slate.drawString("Linear", 80, 30);
        slate.drawString("Rotation ", 150, 30);

        slate.setColor(Color.BLUE);
        slate.drawString(", (comma)", 10, 40);
        slate.drawString(". (dot)", 10, 50);
        slate.drawString("<up>", 10, 60);
        slate.drawString("<down>", 10, 70);
        slate.drawString("<left>", 10, 80);
        slate.drawString("<right>", 10, 90);
        slate.drawString("+ or = / -", 10, 110);
        slate.drawString("right-click", 10, 120);
        
        slate.drawString("Forward", 80, 40);
        slate.drawString("Backward", 80, 50);
        slate.drawString("Up", 80, 60);
        slate.drawString("Down", 80, 70);
        slate.drawString("Left", 80, 80);
        slate.drawString("Right", 80, 90);
        slate.drawString("Increase / Decrease thrust", 80, 110);
        slate.drawString("Navigate by mouse", 80, 120);

        slate.drawString("Roll left", 150, 40);
        slate.drawString("Roll right", 150, 50);
        slate.drawString("Pitch up", 150, 60);
        slate.drawString("Pitch down", 150, 70);
        slate.drawString("Yaw left", 150, 80);
        slate.drawString("Yaw right", 150, 90);

        slate.drawString("Click object to toggle its info", 10, 140);
        return slate;
    }
    
    /**
     * Removes just the PopUoInfo for this object.
     */
    public void releaseInfo()
    {
        World space = getWorld();
        if (m_info != null) space.removeObject(m_info);
    }
    
    /**
     * Computes new axis angle and displays it on an indicator.
     * @param axisInd Index of the axis of rotation affected.
     * @param angle The current angle to udpate in degrees.
     * @param update The angle update value in degrees.
     * @return the new angle in degrees normalized from 0 to 360.
     */
    private double setAxisDisplay(int axisInd, double angle, int update)
    {
        angle += update; 
        angle = normalize360(angle);
        m_axis3Ind[axisInd].setValue(angle);
        getImage().drawImage(m_axis3Ind[axisInd].getImage(), m_axis3Ind[axisInd].x, m_axis3Ind[axisInd].y);
        return angle;
    }
    
    /**
     * Computes new axis displacement and displays it on an indicator.
     * @param axisInd Index of the axis of displacement affected.
     * @param pos The current position to udpate.
     * @param update The position update value.
     * @return the new displacement.
     */
    private double setPosDisplay(int axisInd, double pos, double update)
    {
        pos += update; 
        // put limit code here if space has boundaries.
        m_posInd[axisInd].setValue(pos);
        getImage().drawImage(m_posInd[axisInd].getImage(), m_posInd[axisInd].x, m_posInd[axisInd].y);
        return pos;
    }
    
    /**
     * Normalizes an angle between 0 and 360.
     * @param angle the raw angle in degrees.
     * @return the normalized angle in degrees.
     */
    public static double normalize360(double angle)
    {
        while (angle < 0)   angle += 360;
        while (angle > 360) angle -= 360;
        return angle;
    }

}
