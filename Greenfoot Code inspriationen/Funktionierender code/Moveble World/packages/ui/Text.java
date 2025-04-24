package packages.ui;

import greenfoot.*;

/**
 * The text class is used to store and display some text and to input
 * and modify the text in other objects.
 * 
 * @author RcCookie
 * @version 1.0
 */
public class Text extends Actor{

    /**
     * The text of the text.
     */
    protected String content = "";

    /**
     * The fontSize of the dext drawn.
     */
    private int fontSize = 20;

    /**
     * The color of the letters of the text.
     */
    private Color color = Color.BLACK;

    /**
     * The color of the background image of the text.
     */
    private Color background = null;
    

    /**
     * Constructs an empty text with default settings.
     */
    public Text(){}

    /**
     * Constructs a new text with the given content.
     * 
     * @param content The text of the text
     */
    public Text(String content){
        this.content = content;
        update();
    }

    /**
     * Constructs a new text with the given content written in the given
     * font size.
     * 
     * @param content The text of the text
     * @param fontSize The font size of the text
     */
    public Text(String content, int fontSize){
        this.content = content;
        this.fontSize = fontSize;
        update();
    }

    /**
     * Constructs a new text with the given content written in the given
     * color.
     * 
     * @param content The text of the text
     * @param color The color of the text
     */
    public Text(String content, Color color){
        this.content = content;
        this.color = color;
        update();
    }

    /**
     * Constructs a new text with the given content written in the given
     * color and font size.
     * 
     * @param content The text of the text
     * @param fontSize The font size of the text
     * @param color The color of the text
     */
    public Text(String content, int fontSize, Color color){
        this.content = content;
        this.fontSize = fontSize;
        this.color = color;
        update();
    }

    /**
     * Constructs a new text with the given content written in the given
     * color and font size. The background will be filled with the given
     * backgound color.
     * 
     * @param content The text of the text
     * @param fontSize The font size of the text
     * @param color The color of the text
     * @param background The color of the background
     */
    public Text(String content, int fontSize, Color color, Color background){
        this.content = content;
        this.fontSize = fontSize;
        this.color = color;
        this.background = background;
        update();
    }
    
    
    /**
     * Updates the image of the text according to the current settings.
     */
    protected void update(){
        setImage(new GreenfootImage(content, fontSize, color, background));
    }
    
    
    /**
     * Sets the text of the text to the given string.
     * 
     * @param content The new text
     */
    public void setContent(String content){
        this.content = content;
        update();
    }
    
    /**
     * Sets the font size of the text (also of the already written stuff) to
     * the given one.
     * 
     * @param fontSize The new font size
     */
    public void setFontSize(int fontSize){
        this.fontSize = fontSize;
        update();
    }
    
    /**
     * Sets the color of the text (also of the already written stuff) to the
     * given one.
     * 
     * @param color The new text color
     */
    public void setColor(Color color){
        this.color = color;
        update();
    }
    
    /**
     * Sets the background color to the given one.
     * 
     * @param background The new background color
     */
    public void setBackgroundColor(Color background){
        this.background = background;
        update();
    }
    
    
    /**
     * returns the text of the text.
     * 
     * @return The text of the text
     */
    public String getContent(){
        return content;
    }

    /**
     * returns the color of the text.
     * 
     * @return The color of the text
     */
    public Color getColor(){
        return color;
    }

    /**
     * Returns the background color of the text.
     * 
     * @return The background color of the text
     */
    public Color getBackgroundColor(){
        return background;
    }

    /**
     * Returns the font size of the text.
     * 
     * @return The font size of the text
     */
    public int getFontSize(){
        return fontSize;
    }
}