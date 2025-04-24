import greenfoot.*;
/**
 * PROJECT: BGMusic Actor Class Demo with a preview of ArcBar Actor Class
 * AUTHOR: danpost (greenfoot.org username)
 * DATE: MAY 17, 2014
 * REVISED: OCTOBER 30, 2017 (updated to Greenfoot version 3.1.0)
 * 
 * Demo of the BGMusic class; an Actor class to control background music.  Previews a demo of the ArcBar
 * class; an Actor class for a curved bar or meter (a visual display of a quantitative measure -- like
 * for a health or progress bar, or for a gauge or meter).
 */

public class Background extends World
{
    static final Color TRANS = new Color(0, 0, 0, 0);
    
    Actor btnBackground, btnAudio_1A, btnAudio_2A, btnAudio_3A, btnBGM, btnArcBar; // source viewing buttons
    Actor mouseOn; // mouse hover button
    String className; // the world class name
    ArcBar volumeBar, sizeBar; // the meters for the volume and the default size of the BGMusic class image
    int sizeTimer, volumeTimer; // timer controls for changing values of meter fields

    public Background()
    {
        super(800, 600, 1);
        // gets class name as string
        className = getClass().getName();
        // paint background
        GreenfootImage bg = getBackground();
        bg.setColor(new Color(240, 255, 192));
        bg.fill();
        // add title to background image of world
        GreenfootImage title = new GreenfootImage(className, 64, Color.BLACK, TRANS); // create title image
        bg.drawImage(title, 400-title.getWidth()/2, 50-title.getHeight()/2); // draw title onto background
        // add the volume and BGMusic class default image size bars
        addObject(volumeBar = new ArcBar("Volume", BGMusic.getVolume(), 100), 200, 160);
        addObject(sizeBar = new ArcBar("BGMusic default\nimage size", BGMusic.getImageSize(), 100), 600, 160);
        // add instruction lines to background image of world
        GreenfootImage info = new GreenfootImage("adjust with 'up' and 'down' arrows", 20, Color.BLACK, TRANS);
        bg.drawImage(info, 200-info.getWidth()/2, 200);
        info = new GreenfootImage("adjust with 'left' and right' arrows", 20, Color.BLACK, TRANS);
        bg.drawImage(info, 600-info.getWidth()/2, 200);
        String text = "Click on BGMusic actor to toggle music on and off.\n\n";
        text += "Click on the world background to spawn a new BGMusic actor.\n\n";
        text += "Click on ArcBar actor to change its size (randomly chosen).\n\n";
        text += "Click on buttons at right to view actor class codes.\n\n";
        text += "Click on buttons at left to view world class codes\n";
        text += "and then proceed to those worlds.\n\n";
        text += "Press 'space' to change the music once per world.\n\n";
        text += "In text viewing mode, use 'up' and 'down' arrow keys\n";
        text += "or drag the scrollbar handle to scroll text.\n\n";
        text += "In text viewing mode, press 'x' or 'escape' to proceed to next world.";
        info = new GreenfootImage(text, 20, Color.BLACK, TRANS);
        bg.drawImage(info, 400-info.getWidth()/2, 250);
        // add button headers to background image of world
        info = new GreenfootImage("Worlds", 32, Color.BLACK, TRANS);
        bg.drawImage(info, 75-info.getWidth()/2, 320);
        info = new GreenfootImage("Actors", 32, Color.BLACK, TRANS);
        bg.drawImage(info, 725-info.getWidth()/2, 320);
        // set button fields and add buttons into the world
        addObject(btnBackground = getNewButton("Background"), 75, 380);
        addObject(btnAudio_1A = getNewButton("Audio_1A"), 75, 440);
        addObject(btnAudio_2A = getNewButton("Audio_2A"), 75, 500);
        addObject(btnAudio_3A = getNewButton("Audio_3A"), 75, 560);
        addObject(btnBGM = getNewButton("BGMusic"), 725, 440);
        addObject(btnArcBar = getNewButton("ArcBar"), 725, 560);
        // add the background music button into the world
        if (!"Audio_3A".equals(className))addObject(new BGMusic(className+".mp3"), 40, 40);
    }

    /**
     * Method act: runs timers and performs mouse and keyboard triggered actions
     */
    public void act()
    {
        // changing volume
        if (volumeTimer == 0)
        {
            // check keys
            int dVol = 0;
            if (Greenfoot.isKeyDown("up")) dVol++;
            if (Greenfoot.isKeyDown("down")) dVol--;
            if (dVol != 0) // change requested
            {
                BGMusic.adjustVolume(dVol); // adjust volume
                volumeBar.setValue(BGMusic.getVolume()); // set volume bar value
                volumeTimer = 8; // start timer
            }
        }
        else volumeTimer--; // run timer
        // changing BGMusic class default image size
        if (sizeTimer == 0)
        {
            // check keys
            int dSize = 0;
            if (Greenfoot.isKeyDown("right")) dSize++;
            if (Greenfoot.isKeyDown("left")) dSize--;
            if (dSize != 0) // change requested
            {
                BGMusic.setImageSize(BGMusic.getImageSize()+dSize); // adjust default size
                sizeBar.setValue(BGMusic.getImageSize()); // set image size bar value
                sizeTimer = 8; // start timer
            }
        }
        else sizeTimer--; // run timer
        // change the music in the current world
        if ("space".equals(Greenfoot.getKey()))
        {
            if ("Audio_B.mp3".equals(BGMusic.getName())) addObject(new BGMusic(), 40, 40);
            else addObject(new BGMusic("Audio_B.mp3"), 40, 40);
            className = "";
        }
        // viewing class codes
        if (Greenfoot.mouseClicked(btnBGM)) Greenfoot.setWorld(new TextFileViewer("BGMusic.txt", this));
        if (Greenfoot.mouseClicked(btnArcBar)) Greenfoot.setWorld(new TextFileViewer("ArcBar.txt", this));
        if (Greenfoot.mouseClicked(btnBackground))
        {
            if ("Background".equals(className)) Greenfoot.setWorld(new TextFileViewer("Background.txt", this));
            else Greenfoot.setWorld(new TextFileViewer("Background.txt", new Background()));
        }
        if (Greenfoot.mouseClicked(btnAudio_1A))
        {
            if ("Audio_1A".equals(className)) Greenfoot.setWorld(new TextFileViewer("Audio_1A.txt", this));
            else Greenfoot.setWorld(new TextFileViewer("Audio_1A.txt", new Audio_1A()));
        }
        if (Greenfoot.mouseClicked(btnAudio_2A))
        {
            if ("Audio_2A".equals(className)) Greenfoot.setWorld(new TextFileViewer("Audio_2A.txt", this));
            else Greenfoot.setWorld(new TextFileViewer("Audio_2A.txt", new Audio_2A()));
        }
        if (Greenfoot.mouseClicked(btnAudio_3A))
        {
            if ("Audio_3A".equals(className)) Greenfoot.setWorld(new TextFileViewer("Audio_3A.txt", this));
            else Greenfoot.setWorld(new TextFileViewer("Audio_3A.txt", new Audio_3A()));
        }
        // changing size of arcbars
        if (Greenfoot.mouseClicked(volumeBar))
            volumeBar.setPercentSized(50+Greenfoot.getRandomNumber(51));
        if (Greenfoot.mouseClicked(sizeBar))
            sizeBar.setPercentSized(50+Greenfoot.getRandomNumber(51));
        // adding a new BGMusic actor into the world at mouse click location
        if (Greenfoot.mouseClicked(this))
        {
            MouseInfo mouse = Greenfoot.getMouseInfo(); // get info on mouse (if available)
            if (mouse != null) addObject(new BGMusic(), mouse.getX(), mouse.getY());
        }
    }

    /**
     * Method getNewButton: creates an Actor object with a button image with the caption given; the
     * image of the button is given the ability to change intensity as the mouse moves on and off it.
     *
     * @param caption the text to display on the button
     * @return the button Actor object created
     */
    protected Actor getNewButton(String caption)
    {
        // create the image for the actor not yet created
        GreenfootImage base = new GreenfootImage(120, 30); // create the base image for the button
        base.fill(); // fill with black (default drawing color) to be used for the frame
        base.setColor(new Color(192, 192, 255)); // set drawing color to a light blue
        base.fillRect(3, 3, 114, 24); // fill background of button leaving the outer frame
        GreenfootImage text = new GreenfootImage(caption, 24, Color.BLACK, TRANS); // create text image
        base.drawImage(text, 60-text.getWidth()/2, 15-text.getHeight()/2); // draw text image onto base image
        base.setTransparency(128); // adjust the intensity of the image to 'mouse not over' state
        Actor button = new Actor() // create the Actor
        {
            /**
             * Method act (for button Actor): changes intensity of image for mouse hovering action
             */
            public void act()
            {
                // gaining mouse hover
                if (mouseOn == null && Greenfoot.mouseMoved(this))
                {
                    mouseOn = this;
                    getImage().setTransparency(255);
                }
                // losing mouse hover
                if (mouseOn == this && Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this))
                {
                    mouseOn = null;
                    getImage().setTransparency(128);
                }
            }
        };
        button.setImage(base); // give button its image
        return button; // return the button Actor object
    }
}