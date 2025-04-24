/**
 * CLASS:  BGMusic (subclass of Actor)
 * AUTHOR:  danpost (greenfoot.org username)
 * DATE:  MAY 17, 2014
 * REVISED:  OCTOBER 30, 2017 (updated for Greenfoot version 3.1.0)
 * DESCRIPTION:  A class that does all the dirty work when dealing with background music.
 * 
 * Play all your background music through this class.
 * Creating an object of this class will allow the code to manipulate the state of the background music.
 * Adding the object into the world will allow the user to start and pause the background music.
 * To change the music, whether from the same world or a different one, just create a new BGMusic object
 * specifying the new audio to control in the form of its String filename. Using 'null' will cancel any
 * background music.
 */
import greenfoot.*;

public class BGMusic extends Actor
{
    private static String name; // the filename of the currently set audio
    private static GreenfootSound bgm; // the currently set background music audio
    private static boolean toggledOn; // the currently set active state of the music
    private static int imageSize = 30; // the image size (width and height) for actors of this class
    private static int volume = 25; // the volume level for the background music
    
    private GreenfootImage defaultImage = getImage();
    private GreenfootImage imageOn, imageOff; // the images this actor uses
    private int size = imageSize; // the size of the images of this actor (width and height)
    
    /**
     * BGMusic Constructor: creates an actor with default-sized image to control the currently set audio
     */
    public BGMusic()
    {
        this(imageSize);
    }
    
    /**
     * BGMusic Constructor: creates an actor of specified-sized image to control the currently set audio
     *
     * @param howBig the size (width and height in pixels) to use for the images of this actor
     */
    public BGMusic(int howBig)
    {
        setSize(howBig); // saves the image size
        createImages(); // creates and saves the images
        if (bgm != null && bgm.isPlaying())
            setImage(imageOn); else setImage(imageOff); // sets the appropriate current image for this actor
    }
    
    /**
     * BGMusic Constructor: creates an actor of default-sized image to control the newly specified audio
     *
     * @param bgMusic the filename of the audio that this class will now be assigned to control
     */
    public BGMusic(String bgMusic)
    {
        this(bgMusic, imageSize);
    }
    
    /**
     * BGMusic Constructor: creates an actor of specified-sized image to control the newly specified audio
     *
     * @param bgMusic the filename of the aduio that this class will now be assigned to control
     * @param howBig the size (width and height in pixels) to use for the images of this actor
     */
    public BGMusic(String bgMusic, int howBig)
    {
        setSize(howBig); // saves the image size
        createImages(); // creates and saves the images
        setBackgroundMusic(bgMusic); // assigns to this class the newly specified audio
    }
    
    /**
     * Method addedToWorld: removes any other BGMusic actors that are currently in the world
     * 
     * NOTE: this method is called automatically when an actor of this class is added into a world
     *
     * @param world the world this new BGMusic actor is being added into
     */
    public void addedToWorld(World world)
    { // for all BGMusic actors in the world: if it is not this one, remove it
        for (Object obj : world.getObjects(BGMusic.class)) if (obj != this) world.removeObject((Actor)obj);
    }
    
    /**
     * Method createImages: creates the images that this actor is to use
     * 
     * NOTE: this method is only called once per actor, during its construction
     */
    private void createImages()
    {
        imageOn = new GreenfootImage(defaultImage); // sets 'on' image to default image
        imageOn.scale(size, size); // scales it to size
        imageOff = new GreenfootImage(imageOn); // sets 'off' image to copy of 'on' image
        imageOff.setColor(Color.RED); // sets off' image drawing color to red
        imageOff.drawLine(0, 0, size-1, size-1); // draws diagonal line through image from top-left to bottom-right
        imageOff.drawLine(0, size-1, size-1, 0); // draws diagonal line through image from top-right to bottom-left
        // set the appropriate image to actor for the current active state of the music
        if (bgm != null && bgm.isPlaying()) setImage(imageOn); else setImage(imageOff);
    }
    
    /**
     * Method act: checks for mouse clicks to toggle the active state of the music
     */
    public void act() 
    {
        if (bgm != null && Greenfoot.mouseClicked(this)) // if valid click
        {
            if (toggledOn = !toggledOn) play(); else pause(); // toggle active state of music
        }
    }
    
    /**
     * Method setBackgroundMusic: changes the audio used by this class to the one in the specified file
     *
     * @param bgMusic the audio file this class is to now use (or null, for none)
     */
    public void setBackgroundMusic(String bgMusic)
    {
        boolean wasPlaying = false; // to hold current state of music
        if (bgm != null && bgm.isPlaying()) // if music playing
        {
            bgm.stop(); // stop the music
            wasPlaying = true; // record state
        }
        if (bgMusic == null) bgm = null;
        else
        {
            (bgm = new GreenfootSound(bgMusic)).setVolume(volume); // sets the specified audio
            name = bgMusic;
        }
        if (bgm != null && wasPlaying) bgm.playLoop(); else setImage(imageOff); // resets the active state
    }
    
    /**
     * Method pause: pauses the currently playing audio (if playing)
     */
    public void pause()
    {
        if (bgm != null && bgm.isPlaying()) // if music playing
        {
            bgm.pause(); // pause music
            setImage(imageOff); // update image
        }
    }
    
    /**
     * Method play: starts the audio (if not yet playing)
     */
    public void play()
    {
        if (bgm != null && !bgm.isPlaying()) // if music is not playing
        {
            bgm.playLoop(); // start/resume music
            setImage(imageOn); // update image
        }
    }
    
    /**
     * Method stop: stops the currently playing music (if playing)
     */
    public void stop()
    {
        if (bgm != null && bgm.isPlaying()) // if music playing
        {
            bgm.stop(); // stop the music
            setImage(imageOff); // update image
        }
    }
    
    /**
     * Method isPlaying: returns the active state of the currently set audio
     *
     * @return a true/false value for the active state of the audio
     */
    public static boolean isPlaying()
    {
        return toggledOn; // return the currently active state of the audio
    }
    
    /**
     * Method setVolume: sets the volume level for the audio of this class
     *
     * @param vol the level at which to set the volume for the audio of this class
     */
    public static void setVolume(int vol)
    { // if audio exists and legal value specified, set the new volume level for the audio
        if (vol >= 0 && vol <= 100)
        {
            volume = vol;
            if (bgm != null) bgm.setVolume(volume);
        }
    }
    
    /**
     * Method adjustVolume: adjust the volume level for the audio of this class
     *
     * @param adjustment A parameter
     */
    public static void adjustVolume(int adjustment)
    {
        if (volume+adjustment >= 0 && volume+adjustment <= 100)
        {
            volume += adjustment;
            if (bgm != null) bgm.setVolume(volume);
        }
    }
    
    /**
     * Method getVolume: returns the currently set volume level of this class
     *
     * @return the currently set volume level of this class
     */
    public static int getVolume()
    {
        return volume;
    }
    
    /**
     * Method setSize: sets the size for the image used for this actor
     *
     * @param howBig the specified size for the image used for this actor
     */
    private void setSize(int howBig)
    {
        if (howBig >= 20 && howBig <= 100) size = howBig;
    }
    
    /**
     * Method setImageSize: sets the default size for the default image used for this class
     *
     * @param howBig the specified default size for the default image used for this class
     */
    public static void setImageSize(int howBig)
    {
        if (howBig >=20 && howBig <= 100) imageSize = howBig;
    }
    
    /**
     * Method getImageSize: returns the currently set default size used for the default images of this class
     *
     * @return the default size used for the default images of this class
     */
    public static int getImageSize()
    {
        return imageSize;
    }
    
    /**
     * Method getName: returns the String filename of the currently set background music (or 'null', if none)
     *
     * @return the String name of the currently set background music file (or 'null')
     */
    public static String getName()
    {
        if (bgm == null) return null; else return name;
    }
}