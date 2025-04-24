import greenfoot.*;

/**
 * PROJECT: Speed<br>
 * AUTHOR: danpost (greenfoot.org username)<br>
 * VERSION: January 13, 2014<br>
 * <br>
 * DESCRIPION: Shows the number of frames per second compared to the speed setting of a scenario.<br>
 * <br>
 * INSTRUCTION: Click on green arrows to adjust the speed setting.  The frames per second will reset.<br> 
 */
public class Speed extends World
{
    // actors that show text
    Actor speedDisplay = new Actor(){}, fpsDisplay = new Actor(){};
    // arrow actors to be clicked on
    Actor arrowLeft = new Actor(){}, arrowRight = new Actor(){};
    // control fields
    boolean hasSet, hasBegun;
    // setting and counter fields
    int speed = 50, frames;
    
    /**
     * Initialize the world (set up and add objects into the world)
     */
    public Speed()
    {
        super(400, 100, 1);
        Greenfoot.setSpeed(speed); // set the scenario speed to setting of field
        // initialize images of the four actors
        setArrowImages(); // create and set the images of the arrow actors
        updateSpeed(); // set initial image of speed text
        updateFPS(); // set initial image of fps text
        // add the four actors into the world
        addObject(arrowLeft, 75, 25);
        addObject(arrowRight, 325, 25);
        addObject(speedDisplay, 200, 25);
        addObject(fpsDisplay, 200, 75);
    }
    
    /**
     * Sets a new image to the actor displaying the current scenario speed
     */
    private void updateSpeed()
    {
        speedDisplay.setImage(new GreenfootImage("Speed: "+speed, 48, Color.BLACK, new Color(0, 0, 0, 0)));
    }
    
    /**
     * Sets a new image to the actor displaying the last frames per second result
     */
    private void updateFPS()
    {
        fpsDisplay.setImage(new GreenfootImage("FPS: "+frames, 48, Color.BLACK, new Color(0, 0, 0, 0)));
    }
    
    /**
     * Checks for mouse clicks on arrow actors and tracks frames per second of scenario
     */
    public void act()
    {
        // check for mouse clicks on arrow actors to change the scenario speed
        int d = 0; // field to hold any change in speed
        if (Greenfoot.mouseClicked(arrowLeft)) d--; // left arrow clicked sets change field to -1
        if (Greenfoot.mouseClicked(arrowRight)) d++;// right arrow clicked sets change field to 1
        if (d != 0 && speed+d > 0 && speed+d <= 100)
        { // change requested and speed will stay within range
            // reset frame counter and control fields
            frames = 0;
            hasSet = false;
            hasBegun = false;
            // change the speed
            speed += d;
            Greenfoot.setSpeed(speed);
            // update the display texts
            updateSpeed();
            updateFPS();
        }
        // get current fractional part of seconds of system time (0 to 999 milliseconds)
        int millis = (int)(System.currentTimeMillis()%1000);
        // code to begin the timing
        if (!hasSet && !hasBegun)
        { // time has not begun and we are not set to begin
            if (millis > 100) hasSet = true; // we are set to begin if past first 1/10 of a second
            return;
        }
        if (hasSet && !hasBegun)
        { // time has not begun, but we are set to begin
            if (millis < 100) { hasBegun = true; hasSet = false; } // zero tick, unset and begin time
            return;
        }
        // code to run the timing
        frames++; // count this frame
        if (!hasSet && hasBegun)
        { // must wait for 1/10 of a second before looking for first 1/10 of a second again
            if (millis > 100) hasSet = true; // reset after 1/10 of a second past last tick
            return;
        }
        if (hasSet && hasBegun)
        { // looking for next first 1/10 of a second
            if (millis < 100)
            { // next tick
                hasSet = false; // not looking for tick
                updateFPS(); // update text display of fps actor
                frames = 0; // reset the frames counter
            }
        }
    }

    /**
     * Creates and sets the images of the left and right arrows to the two arrow actors
     */
    private void setArrowImages()
    {
        // create left arrow image
        GreenfootImage leftArrow = new GreenfootImage(50, 50);
        int[] xsL = { 0, 49, 49 }, ysL = { 25, 0, 49 };
        leftArrow.setColor(Color.GREEN);
        leftArrow.fillPolygon(xsL, ysL, 3);
        leftArrow.setColor(Color.BLACK);
        leftArrow.drawPolygon(xsL, ysL, 3);
        arrowLeft.setImage(leftArrow); // sets the image to the arrowLeft actor
        // create right arrow image
        GreenfootImage rightArrow = new GreenfootImage(50, 50);
        int[] xsR = { 0, 49, 0 }, ysR = { 0, 25, 49 };
        rightArrow.setColor(Color.GREEN);
        rightArrow.fillPolygon(xsR, ysR, 3);
        rightArrow.setColor(Color.BLACK);
        rightArrow.drawPolygon(xsR, ysR, 3);
        arrowRight.setImage(rightArrow); // sets the image to the arrowRight actor
    }
}