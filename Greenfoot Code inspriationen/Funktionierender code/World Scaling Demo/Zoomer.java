import greenfoot.World;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

/**
 * CLASS: Zoomer (subclass of World)<br>
 * AUTHOR: danpost (greenfoot.org username)<br>
 * DATE: March 6, 2015<br>
 * <br><br>
 * DESCRIPTION: Zoomer is a World subclass that creates a world object that gives a project
 * the ability for its worlds to change size.  It is to be used in conjunction with the PIP
 * Actor class by danpost and the MouseInfo Object class by danpost.  In order to display an
 * accurate visual of the inactive world, the order that the actors are to be painted in the
 * world (if there is one) must be supplied to the Zoomer world.<br>
 * <br><br>
 * STEPS FOR USE:<br>
 * NOTE:  'initial world' refers to the class that the first Zoomer object is created in.<br>
 * (1) add this class, the MouseInfo Object class and the PIP Actor class to your project;<br>
 * (the MouseInfo Object and PIP Actor classes are both authored by danpost)
 * (2) change all 'Greenfoot.setWorld' statements in your project to 'Zoomer.setWorld'
 * statements; see the documentation on the method for its use;<br>
 * (3) change all 'Greenfoot.getMouseInfo' statements in your project to 'Zoomer.getMouseInfo'
 * statements;<br>
 * (4) change all 'Greenfoot.mouseMoved', 'Greenfoot.mousePressed', 'Greenfoot.mouseClicked'
 * 'Greenfoot.mouseDragged' and 'Greenfoot.mouseDragEnded' statements in your project to
 * 'Zoomer.mouseMoved', 'Zoomer.mousePressed', 'Zoomer.mouseClicked', 'Zoomer.mouseDragged'
 * and 'Zoomer.mouseDragEnded' statements;<br>
 * (5) as the last statement in your initial world constructor, create a new Zoomer object;
 * the following shows the format of the line:<br>
 * <pre>
        new Zoomer(this, [ comma delimited list of classes in top-down paint order ] );
 * </pre>
 * refer to the documentation on the constructor for its use.<br>
 * (6) if the class of your initial world is re-instantiated within your project, please read
 * the following section.
 * <br><br>
 * YOUR INITIAL WORLD CONSTRUCTOR:<br>
 * <br>
 * If there is a time in your project where a new instance of your initial world type
 * is created (that is, if you have the following:
 * <pre>
 *       Zoomer.setWorld(new InitialWorldClassNameHere() [, classes ] );
 * </pre>
 * anywhere within your project), you will have to add a second constructor to that initial world
 * class with a dummy argument (a parameter of any type) and use a call to that constructor using
 * something like this (which uses a dummy String argument):
 * <pre>
 *       Zoomer.setWorld(new InitialWorldClassNameHere("not initial instance") [, classes ]);
 * </pre>
 * The easiest way to alter your constructors is to go from something like this:
 * <pre>
 *       public InitialWorldClassNameHere()
 *       {
 *           super(800, 600, 1);
 *           // construction code here
 *           new Zoomer(this);
 *       }
 * </pre>
 * to this:
 * <pre>
 *       public InitialWorldClassNameHere()
 *       {
 *           this("");
 *           new Zoomer(this);
 *       }
 *       
 *       public InitialWorldClassNameHere(String dummyValue)
 *       {
 *           super(800, 600, 1);
 *           // construction code here
 *       }
 * </pre>
 * After doing so, any 'Zoomer.setWorld(new InitialWorldClassNameHere()...)' statements in your
 * project should be change to 'Zoomer.setWorld(new InitialWorldClassNameHere("dummy text")...)'
 * statements.
 * <br><br>
 * COMMA DELIMITED LIST OF CLASSES:
 * For the 'Zoomer' constructor and the 'setWorld' method, the second parameter requires a
 * list of classes in top-down paint order seperated by commas.<br>
 * Example:<br>
 * <pre>
 *      Zoomer.setWorld(new ZombieWorld(), Player.class, Zombie.class, Blood.class);
 * </pre>
 * If no paint order is to be given, just do not supply any classes.<br><br>
 * Example 1:<br>
 * <pre>
 *      new Zoomer(this);
 * </pre>
 * Example 2:<br>
 * <pre>
 *      Zoomer.setWorld(new AnyWorldClassNameHere());
 * </pre>
 * <h3>NOTE:  DO NOT MODIFY THIS CLASS</h3>
 */
public class Zoomer extends World
{
    private static String ZOOM_OUT = "=";
    private static String ZOOM_IN = "-";
    private static PIP pip;
    private static int zoom;
    private static Object mouseObj;
    private static MouseInfo mouseInfo;
    
    private int timer;

    /**
     * creates a world that can show the visual display of another in varying sizes
     * @param world the world to display
     * @param classes a comma delimited list of classes for the paint order of the given world<br>
     * (refer to the section on COMMA DELIMITED LIST OF CLASSES in the class description)
     */
    public Zoomer(World world, Class... classes)
    {
        super(1000, 600, 1);
        zoom = 100;
        pip = new PIP(world, classes);
        Greenfoot.setWorld(new Zoomer());
    }
    
    // internal constructor for a new running instance of this class (for changing sizes of worlds)
    private Zoomer()
    {
        super(pip.getImage().getWidth()*zoom/100, pip.getImage().getHeight()*zoom/100, 1);
        //dragging = false;
        update();
    }
    
    /**
     * controls the running and displaying of the displayed world and checks for and processes
     * requests for changing its display size
     */
    public void act()
    {
        getMoused();
        pip.step();
        update();
        if (timer < 10) timer++; else checkZoom();
    }
    
    // checks and processes requests to change world size
    private void checkZoom()
    {
        int dz = 0;
        if (Greenfoot.isKeyDown(ZOOM_IN) && Math.min(getWidth(), getHeight()) > 112) dz--;
        if (Greenfoot.isKeyDown(ZOOM_OUT) && getWidth() < 1012 && getHeight() < 612) dz++;
        if (dz != 0)
        {
            zoom += dz;
            Greenfoot.setWorld(new Zoomer());
        }
    }
    
    // updates the background image to display the current visual state of the PIP world
    private void update()
    {
        GreenfootImage img = new GreenfootImage(pip.getImage());
        img.scale(img.getWidth()*zoom/100, img.getHeight()*zoom/100);
        setBackground(img);
    }
    
    /**
     * allows changing of worlds during project execution
     * @param world the new world to display
     * @param classes a comma delimited list of classes in top-down paint order for the world given<br>
     * (refer to the section on COMMA DELIMITED LIST OF CLASSES in the class description)
     */
    public static void setWorld(World world, Class... classes)
    {
        pip = new PIP(world, classes);
        Greenfoot.setWorld(new Zoomer());
    }
    
    /**
     * allows changing of the trigger keys for zooming in and out (changing size of world);<br>
     * the default keys are "=" (equal sign -- key with "+" sign) for larger and "-" for smaller
     * @param expandKey the string representation of the key to expand the world
     * @param shrinkKey the string representation of the key to shrink the world
     */
    public static void setKeys(String expandKey, String shrinkKey)
    {
        ZOOM_OUT = expandKey;
        ZOOM_IN = shrinkKey;
    }
    
    /**
     * replaces 'Greenfoot.mouseClicked(Object)' for the world portrayed
     * @param obj the object that the mouse click is checked to be on (or 'null' for any mouse click, anywhere)
     */
    public static boolean mouseClicked(Object obj)
    {
        if (!Greenfoot.mouseClicked(null)) return false; else if (obj == null) return true;
        return mouseObj == obj;
    }
    
    /**
     * replaces 'Greenfoot.mousePressed(Object)' for the world portrayed
     * @param obj the object that the mouse press is checked to be on (or 'null' for any mouse press, anywhere)
     */
    public static boolean mousePressed(Object obj)
    {
        if (!Greenfoot.mousePressed(null)) return false; else if (obj == null) return true;
        return mouseObj == obj;
    }
    
    /**
     * replaces 'Greenfoot.mouseMoved(Object)' for the world portrayed
     * @param obj the object that the mouse move is checked to end on (or 'null' for any mouse move, anywhere)
     */
    public static boolean mouseMoved(Object obj)
    {
        if (!Greenfoot.mouseMoved(null)) return false; else if (obj == null) return true;
        return mouseObj == obj;
    }
    
    /**
     * replaces 'Grreenfoot.mouseDragged(Object) for the world portrayed
     * @param obj the object that the mouse drag is checked to have started on (or 'null' for any mouse drag)
     */
    public static boolean mouseDragged(Object obj)
    {
        if (!Greenfoot.mouseDragged(null)) return false; else if (obj == null) return true;
        return mouseObj == obj;
    }
    
    /**
     * replaces 'Grreenfoot.mouseDragEnded(Object) for the world portrayed
     * @param obj the object that the mouse drag is checked to have started on (or 'null' for any mouse drag ending)
     */
    public static boolean mouseDragEnded(Object obj)
    {
        if (!Greenfoot.mouseDragEnded(null)) return false; else if (obj == null) return true;
        return mouseObj == obj;
    }
        
    // creates a new MouseInfo object
    private static void getMoused()
    {
        if (!Greenfoot.mouseMoved(null) &&
            !Greenfoot.mouseDragged(null) &&
            !Greenfoot.mouseDragEnded(null) &&
            !Greenfoot.mousePressed(null) &&
            !Greenfoot.mouseClicked(null)) return;
        mouseInfo = new MouseInfo();
        int mseX = mouseInfo.getX()*100/zoom, mseY = mouseInfo.getY()*100/zoom;
        mouseInfo.setCoordinates(mseX, mseY);
        if (Greenfoot.mouseDragged(null))
        {
            if (mouseObj instanceof Actor) mouseInfo.setActor((Actor)mouseObj);
            else mouseInfo.setActor(null);
            return;
        }
        if (mseX < 0 || mseX >= pip.getImage().getWidth() || mseY < 0 || mseY >= pip.getImage().getHeight())
        {
            mouseObj = null;
            mouseInfo.setActor(null);
            return;
        }
        Class[] po = pip.getPaintOrder();
        java.util.List<Object> mObjs = pip.getPIPWorld().getObjectsAt(mseX, mseY, null);
        if (mObjs.isEmpty())
        {
            mouseObj = pip.getPIPWorld();
            mouseInfo.setActor(null);
            return;
        }
        java.util.List<Object> wObjs = pip.getPIPWorld().getObjects(null);
        int index = 0;
        while (index < wObjs.size()) if (!mObjs.contains(wObjs.get(index))) wObjs.remove(index); else index++;
        java.util.Collections.reverse(wObjs);
        for (int i=0; i<po.length; i++) for (Object ob : wObjs)
        {
            if (pip.getPIPWorld().getObjectsAt(mseX, mseY, po[i]).contains(ob))
            {
                mouseObj = ob;
                mouseInfo.setActor((Actor)mouseObj);
                return;
            }
        }
        mouseObj = wObjs.get(0);
        mouseInfo.setActor((Actor)mouseObj);
    }
    
    /**
     * replaces 'Greenfoot.getMouseInfo' for the world portrayed and returns the current MouseInfo object
     * @return the latest MouseInfo object created
     */
    public static MouseInfo getMouseInfo()
    {
        return mouseInfo;
    }
}