import greenfoot.*;

/** a simple class that creates button objects */
public class Button extends Actor
{
    private boolean mouseOn; // tracks current mouse state of button
    private String text; // holds the caption currently assigned to the button
    
    /** creates a blank button object */
    public Button()
    {
        this("");
    }
    
    /** creates a button object with text */
    public Button(String caption)
    {
        setText(caption);
    }

    /** controls intensity of image on mouse hover events */
    public void act()
    {
        // gaining mouse hover
        if (!mouseOn && Zoomer.mouseMoved(this))
        {
            mouseOn = true;
            int x = getX(), y = getY();
            World w = getWorld();
            w.removeObject(this);
            w.addObject(this, x, y);
            getImage().setTransparency(255);
        }
        // losing mouse hover
        if (mouseOn && Greenfoot.mouseMoved(null) && !Zoomer.mouseMoved(this))
        {
            mouseOn = false;
            getImage().setTransparency(128);
        }
    }
    
    /**
     * creates an image of a button with the given string text
     * @param caption the text the button is to display
     */
    public void setText(String caption)
    {
        text = caption;
        mouseOn = false;
        GreenfootImage base = new GreenfootImage(180, 30); // create the base image for the button
        base.fill(); // fill with black (default drawing color) to be used for the frame
        base.setColor(new Color(192, 192, 255)); // set drawing color to a light blue
        base.fillRect(3, 3, 174, 24); // fill background of button leaving the outer frame
        GreenfootImage text = new GreenfootImage(caption, 24, Color.BLACK, new Color(0, 0, 0, 0)); // create text image
        while (base.getHeight() < text.getHeight()+12) base.scale(base.getWidth(), base.getHeight()+1);
        base.drawImage(text, 90-text.getWidth()/2, base.getHeight()/2-text.getHeight()/2); // draw text image onto base image
        base.setTransparency(128); // adjust the intensity of the image to 'mouse not over' state
        setImage(base);
    }
    
    /**
     * returns the text captioned on the button
     * @return the current button text
     */
    public String getText()
    {
        return text;
    }
}