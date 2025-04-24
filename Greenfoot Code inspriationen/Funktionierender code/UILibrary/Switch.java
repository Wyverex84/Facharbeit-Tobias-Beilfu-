import greenfoot.*;

/**
 * The switch is a UI element that can be in two possible states. It is part of the "MSG" 
 * Greenfoot GUI toolkit.
 * 
 * The switch has no callback (listener) mechanism. Instead, it is typically used by polling the switch
 * using the 'isOn()' method when the state of the switch is of interest.
 * 
 * Note that positioning places the switch's top left corner at the target location.
 * 
 * To use this class in your project, you also need to copy two images: 
 *      switch-up.png and switch-down.png
 * 
 * @author Michael Kölling 
 * @version 1.2
 */
public class Switch extends Actor
{
    private boolean up = true;
    
    private GreenfootImage upImage = new GreenfootImage("switch-up.png");
    private GreenfootImage downImage = new GreenfootImage("switch-down.png");

    /**
     * Create a switch without text labels. The switch is initially "on".
     */
    public Switch()
    {
        this("", "");
    }
    
    /**
     * Create a switch with two text labels at top and bottom. The switch is initially "on".
     * 
     * @param topText  The text label to appear at the top ("on") position of the switch.
     * @param bottomText  The text label to appear at the bottom ("off") position of the switch.
     */
    public Switch(String topText, String bottomText)
    {
        createImage(upImage, topText, bottomText);
        createImage(downImage, topText, bottomText);
        setImage(upImage);
    }
    
    /**
     * Check whether the switch was clicked and toggle if it was.
     */
    public void act() 
    {
        if (Greenfoot.mouseClicked(this)) {
            toggle();
        }
    }
    
    /**
     * Check whether the switch is currently on or off.
     * 
     * @returns True, if the switch is currently set to "on" (the 'up' position); false otherwise.
     */
    public boolean isOn()
    {
        return !up;
    }
    
    /**
     * Set this switch to on or off.
     * 
     * @param on  If true, set to on. Otherwise set to off.
     */
    public void setState(boolean on)
    {
        up = on;
    }

    /**
     * Toggle this switch's state.
     */
    public void toggle()
    {
        up = !up;
        if (up) {
            setImage(upImage);
        }
        else {
            setImage(downImage);
        }
    }
    
    /**
     * Override setLocation to position the label's top left corner at the given location.
     */
    public void setLocation(int x, int y)
    {
        super.setLocation (x + getImage().getWidth()/2, y + getImage().getHeight()/2);
    }
    
    // --------------------------- private methods ---------------------------------

    /**
     * Create the switch's image from the base image and the two given text strings.
     */
    private void createImage(GreenfootImage image, String text1, String text2)
    {
        GreenfootImage tmp1 = new GreenfootImage(text1, 12, null, null);
        GreenfootImage tmp2 = new GreenfootImage(text2, 12, null, null);
        
        image.drawString(text1, (image.getWidth() - tmp1.getWidth()) / 2, 12);
        image.drawString(text2, (image.getWidth() - tmp2.getWidth()) / 2, 86);
    }
}
