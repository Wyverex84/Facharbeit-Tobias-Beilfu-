package packages.camera;
import greenfoot.*;
/**
 * This is the Camera class. It is the central part of the camera
 * package as it runs the background world and displays the
 * things on screen. It extends the World class.
 * 
 * Camera is currently in development. Splitscreen support
 * is planned and you will find some parts of code  of that
 * but it does not do anything yet.
 * 
 * @author RcCookie
 * @version 0.2.1
 */
public class Camera extends World
{
    private int x, y, lx, ly, n;
    private double s;
    private World w;
    private Actor[] h;
    private boolean fixedToWorld;

    /**
     * Constructs a new World of type Camera that displayes an area
     * of 600 time 400 pixels of view range of the given world
     * following the given actor
     * 
     * @param world The world the camera should display
     * @param host The actor the camera should follow
     */
    public Camera(World world, Actor host){
        super(600/world.getCellSize(), 400/world.getCellSize(), world.getCellSize(), false);
        x = 600;
        y = 400;
        n = 1;
        w = world;
        h = new Actor[1];
        h[0] = host;
        s = 1;
        fixedToWorld = false;
        setGhosts();
        setBackground();
    }

    /**
     * Constructs a new World of type Camera that displayes an area
     * of 600 time 400 pixels centered onto the given coordinates
     * 
     * @param world The world the camera should display
     * @param locX The x position of the world the camera should look at
     * @param locY The y position of the world the camera should look at
     */
    public Camera(World world, int locX, int locY){
        super(600/world.getCellSize(), 400/world.getCellSize(), world.getCellSize(), false);
        x = 600;
        y = 400;
        n = 1;
        w = world;
        s = 1;
        lx = locX;
        ly = locY;
        fixedToWorld = true;
        setGhosts();
        setBackground();
    }

    /**
     * Constructs a new World of type Camera that displayes an area
     * of the given size centered onto the given coordinates
     * 
     * @param width The cameras screen width in pixels
     * @param height The cameras screen height in pixels
     * @param camNum WIP The number of cameras for splitscreen (Does nothing!)
     * @param scale WIP the zoom that the camera shows objects with
     * @param world The world the camera should display
     * @param locX The x position of the world the camera should look at
     * @param locY The y position of the world the camera should look at
     */
    public Camera(int width, int height, int camNum, double scale, World world, int[] locX, int[] locY){
        super(width/world.getCellSize(), height/world.getCellSize(), world.getCellSize(), false);
        x = width;
        y = height;
        n = camNum;
        w = world;
        s = scale;
        lx = locX[0];
        ly = locY[0];
        fixedToWorld = true;
        setGhosts();
        setBackground();
    }

    /**
     * Constructs a new World of type Camera that displayes an area
     * of the given size following the given actor.
     * 
     * @param width The cameras screen width
     * @param height The cameras screen height
     * @param camNum WIP The number of cameras for splitscreen (Does nothing!)
     * @param scale WIP the zoom that the camera shows objects with
     * @param world The world the camera should display
     * @param host WIP The actors each camera should follow (Only first is used!)
     */
    public Camera(int width, int height, int camNum, double scale, World world, Actor[] host){
        super(width/world.getCellSize(), height/world.getCellSize(), world.getCellSize(), false);
        x = width;
        y = height;
        n = camNum;
        w = world;
        h = host;
        s = scale;
        fixedToWorld = false;
        setGhosts();
        setBackground();
    }

    /**
     * Runs the main world without showing it and displays the actors at
     * their relative positions. You do NOT haave to run it manually.
     */
    public void act(){
        actWorld();
        setGhosts();
        setBackground();
    }

    /**
     * Removes all ghost objects and spawns new ones for each main world actor
     */
    private void setGhosts(){
        removeObjects(getObjects(Ghost.class));
        if(fixedToWorld){
            for(Object o : w.getObjects(null)){
                addObject(new Ghost((Actor)o, s), ((Actor)o).getX()-lx+x/2, ((Actor)o).getY()-ly+y/2);
            }
        }
        else{
            addObject(new Ghost(h[0], s), x/2, y/2);
            for(Object o : w.getObjects(null)){
                if(o!=h[0]){
                    addObject(new Ghost((Actor)o, s), ((Actor)o).getX()-h[0].getX()+x/2, ((Actor)o).getY()-h[0].getY()+y/2);
                }
            }
        }
    }

    /**
     * Draws the main worlds background image as background at the offset location.
     */
    private void setBackground(){
        setBackground(w.getBackground());
        GreenfootImage temp = new GreenfootImage(getBackground().getWidth(), getBackground().getWidth());
        temp.setColor(greenfoot.Color.DARK_GRAY);
        temp.fill();
        if(fixedToWorld){
            temp.drawImage(w.getBackground(), x/2-lx, y/2-ly);
        }
        else{
            temp.drawImage(w.getBackground(), x/2-h[0].getX(), y/2-h[0].getY());
        }
        setBackground(temp);
    }

    /**
     * Runs the main world in the background
     */
    private void actWorld(){
        for(Object o : w.getObjects(null)){
            ((Actor)o).act();
        }
        if(!fixedToWorld)w.act();
    }

    /**
     * Activates and returns a new Camera that looks at the 
     * given actor based on old settings.
     * 
     * @param a The actor to look at
     * @return The new Camera
     */
    public Camera lookAt(Actor a){
        Actor[] temp = {a};
        Camera next = new Camera(x, y, 1, s, w, temp);
        Greenfoot.setWorld(next);
        return next;
    }

    /**
     * Activates and return a new Camera that looks at the
     * given actors in splitscreen with the old amount
     * of screens and old settings.
     * 
     * @param a The actors to look at
     * @return The new Camera
     */
    public Camera lookAt(Actor[] a){
        Camera next = new Camera(x, y, n, s, w, a);
        Greenfoot.setWorld(next);
        return next;
    }

    /**
     * Activates and returns a new Camera that looks at the
     * given coordinates based on old settings.
     * 
     * @param locX The x-coordinate to look at
     * @param locY The y-coordinate to look at
     * @return The new camera
     */
    public Camera lookAt(int locX, int locY){
        int[] temp = {locX};
        int[] temp1= {locY};
        Camera next = new Camera(x, y, 1, s, w, temp, temp1);
        Greenfoot.setWorld(next);
        return next;
    }

    /**
     * Activates and returns a new Camera that looks at the
     * given coordinates with the old amount of screens
     * and based on old settings.
     * 
     * @param locX The x-coordinates to look at
     * @param locY The y-coordinates to look at
     * @return The new camera
     */
    public Camera lookAt(int[] locX, int[] locY){
        Camera next = new Camera(x, y, 1, s, w, locX, locY);
        Greenfoot.setWorld(next);
        return next;
    }






    /**
     * This is the Ghost class. It is part of the camera package and
     * is used to display actors on the camera screen.
     * 
     * @author RcCookie
     * @version 1.0
     */
    private class Ghost extends Actor
    {
        /**
         * Constructs a new ghost by copying the given actors
         * image and rotation. Scales the image of the actor to the
         * given value. Has to be positioned by constructing
         * class.
         * 
         * @param h The actor the ghost should copy
         */
        public Ghost(Actor h, double scale){
            GreenfootImage temp = h.getImage();
            temp.scale((int)((double)temp.getWidth()*scale), (int)((double)temp.getHeight()*scale));
            setImage(temp);
            setRotation(h.getRotation());
        }
    }
}