import greenfoot.*;

/**
 * Class BarWorld: a collection of sub-classes for my Bar class<br>
 * <br>
 * Author: danpost<br>
 * Version: 2.0.1<br>
 */
public class BarWorld extends World
{
    MseActBar mab = new MseActBar("Frames", " acts/set", 50, 100);
    SwitchBar sb = new SwitchBar("Switch", true);
    TimerBar atb = new TimerBar("Time", "seconds", 20, 1000, true, false);
    TimerBar fcb = new TimerBar("Act Sets", "sets", 20, 50, false, true);
    
    /**
     * a world for demonstrating my bar sub-classes
     */
    public BarWorld()
    {    
        super(600, 400, 1);
    }
    
    /**
     * clears background image and prepares the world
     */
    public void started()
    {
        getBackground().setColor(Color.WHITE);
        getBackground().fill();
        addDescriptions();
        addBars();
    }
    
    private void addDescriptions()
    {
        String[] line = { "BAR SUBCLASSES", "",
            "MseActBar extends Bar:",
            "    Gives the user the ability to change the value of the bar by way of mouse clicks.",
            "", "",
            "SwitchBar extends Bar:",
            "    Gives the user the ability to switch between two value by way of mouse clicks.",
            "    The values can be interpreted as 'ON/OFF', 'TRUE/FALSE', 'YES/NO', etc.",
            "", "",
            "TimerBar extends Bar (actual time):",
            "    Starts counting down, or up, on command.  The switch above will start and stop it.",
            "    This one counts down 20 sets of 1000 milliseconds (or 20 seconds).",
            "", "",
            "TimerBar extends Bar (frame counter):",
            "    Starts counting up, or down, on command.",
            "    This one counts up 20 sets of initially 50 frames.  The first bar above adjusts this."
        };
        GreenfootImage bg = getBackground();
        for (int i = 0; i < line.length; i++)
        {
            GreenfootImage image = new GreenfootImage(line[i], 16, Color.BLACK, new Color(0, 0, 0, 0));
            bg.drawImage(image, (i == 0 ? 300 - image.getWidth() / 2 : 50), 30 + i * 18);
        }
    }
    
    private void addBars()
    {
        // prepare the mouse adjustable bar and add it to the world
        mab.setBreakPercent(0);
        mab.setSafeColor(Color.GRAY);
        mab.setAltBackgroundColor(Color.YELLOW);
        addObject(mab, 450, 73);
        // prepare the switch bar and add it to the world
        String[] sbTexts = { "Timer OFF", "Timer ON " };
        sb.setValueText(sbTexts);
        addObject(sb, 450, 145);
        // add the 'actual time' timer bar to the world and start it
        addObject(atb, 450, 235);
        atb.begin();
        // prepare the 'frame count' timer bar and add it to the world, started
        fcb.setBreakLow(false);
        fcb.setBreakPercent(80);
        addObject(fcb, 450, 325);
        fcb.begin();
    }
    
    /**
     * performs actions determined by the changes in the bar objects
     */
    public void act()
    {
        if (mab.isChanged())
        {
            // code to perform when the value of the bar changes
            // I have it controlling the number of frames per set in the 'frame count' timer bar
            fcb.stop();
            fcb.setDelayCount(mab.getValue());
            fcb.begin();
        }
        if (sb.isChanged())
        {
            // code to perform when the value of the switch changes
            // I have it controlling the running state of the 'actual time' timer bar
            if (sb.getState()) atb.restart(); else atb.stop();
        }
        // I have the timer bars repeating their counts
        if (atb.isExpired()) atb.begin();
        if (fcb.isExpired()) fcb.begin();
    }    
}