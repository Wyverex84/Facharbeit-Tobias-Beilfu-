import greenfoot.*;

/**
 * CLASS: PIP (subclass of Actor)<br>
 * AUTHOR: danpost (greenfoot.org username)<br>
 * DATE: May 22, 2013 (created)<br>
 * APPENDED: March 7, 2015 ('getWorld' and 'getPaintOrder' methods added)<br>
 * <br>
 * DESCRIPTION:<br>
 * An actor object that captures the current visual state of a world object.  When added to a world, this actor becomes
 * a 'picture-in-picture' (PIP) type object.  Methods are provided to control the running state of the capture world.
 * <h3>NOTE: DO NOT MODIFY THIS CLASS</h3>
 */
public class PIP extends Actor
{
    private World minor; // the world to display in the image of this PIP object
    private Class[] paintOrder; // the paint order of the world to display
    private boolean activeState; // the current active state of this actor which runs the world that is displayed

    /**
     * creates a picture-in-picture object
     * @param minorWorld the world object to portray
     * @param classes the paint order of the world to portray
     */
    public PIP(World minorWorld, Class[] classes)
    {
        // save arguments to instance fields
        minor = minorWorld;
        paintOrder = classes;
        // create the base image of the PIP object
        int width = minor.getWidth()*minor.getCellSize();
        int height = minor.getHeight()*minor.getCellSize();
        GreenfootImage image = new GreenfootImage(width+12, height+12);
        // paint the background of the base image white
        image.setColor(Color.WHITE);
        image.fill();
        // create the picture frame
        image.setColor(Color.BLACK);
        image.drawRect(0, 0, width+11, height+11);
        image.drawRect(1, 1, width+9, height+9);
        image.drawRect(4, 4, width+3, height+3);
        // set the base image as the current image of this PIP object
        setImage(image);
        // update the picture of this PIP object (display the given world)
        updateImage();
    }
    
    /** controls the running state of the displayed world */
    public void act()
    {
        if (!activeState) return;
        for (Object obj : minor.getObjects(null))
        {
            Actor actor = (Actor)obj;
            if (actor.getWorld() != null) actor.act();
        }
        minor.act();
        updateImage();
    }        

    // updates the picture of the given world within the image of this PIP object
    private void updateImage()
    {
        GreenfootImage view = new GreenfootImage(minor.getBackground());
        for (Object obj : minor.getObjects(null))
        {
            Actor actor = (Actor)obj;
            if(!isPaintOrderActor(actor))
            {
                int x=actor.getX()*minor.getCellSize()+minor.getCellSize()/2;
                int y=actor.getY()*minor.getCellSize()+minor.getCellSize()/2;
                GreenfootImage img = getActorImage(actor);
                int w=actor.getImage().getWidth(), h=actor.getImage().getHeight();
                view.drawImage(img, x-img.getWidth()/2, y-img.getHeight()/2);
            }
        }
        for(int i=1; i<=paintOrder.length; i++) for(Object obj: minor.getObjects(paintOrder[paintOrder.length-i]))
        {
            Actor actor = (Actor)obj;
            int x=actor.getX()*minor.getCellSize()+minor.getCellSize()/2;
            int y=actor.getY()*minor.getCellSize()+minor.getCellSize()/2;
            GreenfootImage img = getActorImage(actor);
            int w=actor.getImage().getWidth(), h=actor.getImage().getHeight();
            view.drawImage(img, x-img.getWidth()/2, y-img.getHeight()/2);
        }
        getImage().drawImage(view, 6, 6);
    }
    
    // returns the image of the given actor within the displayed world
    private GreenfootImage getActorImage(Actor actor)
    {
        GreenfootImage actorImg = actor.getImage();
        int w = actorImg.getWidth();
        int h = actorImg.getHeight();
        int max = Math.max(w, h);
        GreenfootImage image = new GreenfootImage(max*2, max*2);
        image.drawImage(actorImg, max-actorImg.getWidth()/2, max-actorImg.getHeight()/2);
        image.rotate(actor.getRotation());
        return image;
    }
        
    // determines whether the given actor is an instance of one of the paint order class of the displayed world
    private boolean isPaintOrderActor(Actor actor)
    {
        for(int i=0; i<paintOrder.length; i++) if(actor.getClass().equals(paintOrder[i])) return true;
        return false;
    }

    /**
     * sets the active state of this PIP object (use this as an alternative to using the 'run' and 'pause' methods)
     * @param newActiveState the active state to set this PIP object to
     */
    public void setActiveState(boolean newActiveState)
    {
        activeState = newActiveState;
    }
    
    /**
     * gets the current active state of this PIP object
     * @return the current active state of this PIP object
     */
    public boolean getActiveState()
    {
        return activeState;
    }
    
    /** activates this PIP object, setting the displayed world into a running state */
    public void run()
    {
        setActiveState(true);
    }
    
    /** pauses this PIP object, setting the displayed world into a stopped state */
    public void pause()
    {
        setActiveState(false);
    }
    
    /** executes one act cycle of the displayed world */
    public void step()
    {
        if (activeState) return;
        activeState = true;
        act();
        activeState = false;
    }
    
    /**
     * returns the world currently displayed by this PIP object
     * @return the world this PIP object is currently displaying
     */
    public World getPIPWorld()
    {
        return minor;
    }
    
    /**
     * returns the paint order for the world this PIP object is displaying
     * @return an array of classes given as the paint order of the displayed world
     */
    public Class[] getPaintOrder()
    {
        return paintOrder;
    }
}