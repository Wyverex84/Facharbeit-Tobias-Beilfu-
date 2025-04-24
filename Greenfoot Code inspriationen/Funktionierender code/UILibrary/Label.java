import greenfoot.*;

/**
 * This is a generic, reusable Label for a user interface. This label is part of the "MSG" 
 * Greenfoot GUI toolkit.
 * 
 * Note that positioning places the label's top left corner at the target location.
 * 
 * @author Michael Kölling 
 * @version 1.2
 */
public class Label extends Actor
{
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    
    // colours and other user definable attributes with default values
    private Color textColor = Color.BLACK;
    private Color backgroundColor = null;
    private Color borderColor = null;
    private int alignment = CENTER;
    
    // spacing, with default values
    private final int INSET = 8;  // space between text and edge
    
    private String text;
    private int fontSize = 14;
    private int textX;      // the x-coordinate of the left edge of the text
    private int width = -1;
    private int height = -1;
    
    /**
     * Create a label with a default text.
     */
    public Label()
    {
        this("Label");
    }
    
    /**
     * Create a label with a given text.
     */
    public Label(String text)
    {
        this.text = text;
        createImage();
    }
    
    /** 
     * Set the text to be displayed.
     */
    public void setText(String text)
    {
        this.text = text;
        createImage();
    }
    
    /**
     * Return this label's text.
     */
    public String getText()
    {
        return text;
    }
    
    /** 
     * Set the font size for the label text.
     */
    public void setFontSize(int size)
    {
        fontSize = size;
        createImage();
    }
    
    /**
     * Return this label's font size.
     */
    public int getFontSize()
    {
        return fontSize;
    }
    
    /** 
     * Set the background color.
     */
    public void setBackground(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
        createImage();
    }
    
    /** 
     * Set the foreground color.
     */
    public void setForeground(Color foregroundColor)
    {
        this.textColor = foregroundColor;
        createImage();
    }
    
    /** 
     * Set the border color.
     */
    public void setBorder(Color borderColor)
    {
        this.borderColor = borderColor;
        createImage();
    }
    
    /** 
     * Set the text alignment. The value should be one of the alignment constants defined in this 
     * class (LEFT, CENTER, RIGHT).
     */
    public void setAlignment(int alignment)
    {
        this.alignment = alignment;
        createImage();
    }
    
    /** 
     * Set the width of this label to a fixed width.
     */
    public void setWidth(int width)
    {
        this.width = width;
        createImage();
    }
    
    /** 
     * Set the height of this label to a fixed height.
     */
    public void setHeight(int height)
    {
        this.height = height;
        createImage();
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
     * Paint the label's background.
     */
    private void paintBackground()
    {
        if (backgroundColor != null) {
            GreenfootImage img = getImage();
            img.setColor(backgroundColor);
            img.fill();
        }
    }
    
    /**
     * Paint the label's border.
     */
    private void paintBorder()
    {
        if (borderColor != null) {
            GreenfootImage img = getImage();
            img.setColor(borderColor);
            img.drawRect(0, 0, img.getWidth()-1, img.getHeight()-1);
        }
    }

    /**
     * Paint the label's text.
     */
    private void paintText(GreenfootImage textImage)
    {
        GreenfootImage img = getImage();
        img.drawImage(textImage, textX, (img.getHeight()-textImage.getHeight())/2);
    }
    
    /**
     * Paint the label image, including the button text and decorations.
     */
    private void repaint(GreenfootImage textImage)
    {
        paintBackground();
        paintBorder();
        paintText(textImage);
    }

    /**
     * Create the actor's image at the right size. If the size has been set explicitly, that size 
     * is used. If not, the size is calculated  to fit the label text. The size calculation takes 
     * the text on the label and the current font into account.
     */
    private void createImage()
    {
        int imgWidth = width;
        int imgHeight = height;
        
        GreenfootImage textImage = new GreenfootImage(text, fontSize, textColor, null);
        
        int textWidth = textImage.getWidth();
        if (imgWidth <= 0) {
            imgWidth = textWidth + 2 * INSET;
        }
        if (imgHeight <= 0) {
            imgHeight = textImage.getHeight() + 2 * INSET;
        }
        
        if (alignment == LEFT) {
            textX = INSET;
        }
        else if (alignment == RIGHT) {
            textX = imgWidth - INSET - textWidth;
        }
        else { // center
            textX = (imgWidth/2) - (textWidth/2);
        }
        GreenfootImage img = new GreenfootImage(imgWidth, imgHeight);
        setImage(img);
        repaint(textImage);
    }
}
