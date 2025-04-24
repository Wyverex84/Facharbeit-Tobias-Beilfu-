/**
 * CLASS:  ArcBar (subclass of Actor)
 * AUTHOR:  danpost (greenfoot.org username)
 * DATE:  MAY 17, 2014
 * REVISED:  OCTOBER 30, 2017 (updated for Greenfoot version 3.1.0)
 * DESCRIPTION:  An actor class whose objects display a quantitative measure in the form of an arc,
 *               or curved bar (like a health bar, progress bar, gauge, or meter).
 */
import greenfoot.*;

public class ArcBar extends Actor
{
    static final Color TRANS = new Color(0, 0, 0, 0);
    private static GreenfootImage barImage; // image of the curved colored portion of the arcbar
    static
    { // create the image for the 'barImage' field
        barImage = new GreenfootImage(300, 300);
        barImage.setColor(Color.GREEN);
        barImage.fillOval(0, 0, 300, 300); // create large green filled circle
        barImage.setColor(Color.BLACK);
        barImage.fillOval(20, 20, 260, 260); // create black filled inner circle (inside the green one)
        // cut bottom half of image off by drawing onto a half-sized image
        GreenfootImage image = new GreenfootImage(300, 150);
        image.drawImage(barImage, 0, 0);
        // remove all black pixels and replace with transparent
        int x = 20;
        Color trans = TRANS;
        for (int y=149; y>=20; y--)
        {
            while (!Color.BLACK.equals(image.getColorAt(x++, y)) && x<150);
            for (int n=(--x); n<=300-x; n++) image.setColorAt(n, y, trans);
        }
        // redraw the half circle arc onto full-sized image (bottom half is totally transparent)
        barImage.clear();
        barImage.drawImage(image, 0, 0);
    }
    
    private int angularRange = 120; // angular range of completely full arc (maximum value = 180)
    private int percentSized = 100; // size to scale images to when setting current image
    private String caption; // the caption for this arcbar actor
    private int value; // the current value for this arcbar actor
    private int maximumValue; // the maximum value for this arcbar actor
    
    /**
     * ArcBar Constructor: saves the specified state values and creates this actors initial image
     *
     * @param text the caption for this arcbar
     * @param initVal the initial value of this arcbar
     * @param maxVal the maximum value allowed for this arcbar
     */
    public ArcBar(String text, int initVal, int maxVal)
    {
        // save the state values
        caption = text;
        value = initVal;
        maximumValue = maxVal;
        updateImage(); // set initial image of this arcbar
    }
    
    /**
     * Method add: adjusts the value of this arcbar by the specified amount
     *
     * @param amount the adjustment amount for the value of this arcbar
     */
    public void add(int amount)
    {
        setValue(value+amount);
    }
    
    /**
     * Method getValue: returns the current value of this arcbar
     *
     * @return the current value of this arcbar
     */
    public int getValue()
    {
        return value;
    }
    
    /**
     * Method setValue: sets the current value of this arcbar to the specified value
     *
     * @param val the value at which the value of this arcbar is to be set
     */
    public void setValue(int val)
    {
        // limit range to max and min values
        if (val > maximumValue) val = maximumValue;
        if (val < 0) val = 0;
        value = val; // set value
        updateImage(); // update image
    }
    
    /**
     * Method getMaximumValue: returns the maximum limit of this arcbar
     *
     * @return the maximum limit of this arcbar
     */
    public int getMaximumValue()
    {
        return maximumValue;
    }
    
    /**
     * Method setPercentSized: sets the visual size of this arcbar to between 50 and 100 percent of full-sized
     *
     * @param pct the scaling percentage of the size of the image for this arcbar (full-sized is 300x150)
     */
    public void setPercentSized(int pct)
    {
        if (pct < 50) pct = 50;
        if (pct > 100) pct = 100;
        percentSized = pct;
        updateImage();
    }
    
    /**
     * Method updateImage: creates/updates the image of this arcbar
     */
    private void updateImage()
    {
        GreenfootImage image = new GreenfootImage(barImage); // get saved bar image (half circular arc on full image)
        image.rotate(angularRange-angularRange*(maximumValue-value)/maximumValue-180); // rotate as needed
        // cut button half of image off by drawing on half-sized image
        GreenfootImage bar = new GreenfootImage(300, 150);
        bar.drawImage(image, 0, 0);
        // re-draw on full-sized image
        image.clear();
        image.drawImage(bar, 0, 0);
        // rotate to set left edge (zero edge) at proper angle
        image.rotate((180-angularRange)/2);
        // re-draw final image on half-sized image
        bar.clear();
        bar.drawImage(image, 0, 0);
        // add text (caption and textual amount) to image
        GreenfootImage text = new GreenfootImage(""+value+"\n"+caption, 24, Color.BLACK, TRANS);
        bar.drawImage(text, 150-text.getWidth()/2, 65-text.getHeight()/2);
        bar.scale(300*percentSized/100, 150*percentSized/100);
        // set the image of this arcbar
        setImage(bar);
    }
}