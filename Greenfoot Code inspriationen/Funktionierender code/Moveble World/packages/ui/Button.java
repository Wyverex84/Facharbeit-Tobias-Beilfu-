package packages.ui;

import greenfoot.*;
/**
 * This is a Button class. Use it for interfaces. The Button works completly
 * automaticly, you do not have to feed it any information after the
 * construction. It offers selveral methods to get information about
 * the users interactions with it. It may also go to a certain world when clicked
 * to move through menus.
 * 
 * @author RcCookie
 * @version 1.2
 */
public class Button extends Actor
{
    /**
     * The number of times this button has been clicked.
     */
    protected int clickCount;

    /**
     * The number of frames this button was held down.
     */
    protected int pressTime;

    /**
     * Weather the button is touched by the mouse right now.
     */
    protected boolean touched;

    /**
     * Weather the button is pressed down by the mouse right now.
     */
    protected boolean pressed;

    /**
     * The text on the button.
     */
    protected String name;

    /**
     * The image given as input, or null if a color was chosen.
     */
    protected GreenfootImage inputImage;

    protected Text inputText;

    /**
     * The x and y size of the button.
     */
    protected int x, y;

    /**
     * The color used to fill the background if inputImage is null.
     */
    protected Color color;

    /**
     * The font size for the buttons text.
     */
    protected int fontSize;

    /**
     * Weather there should be an outline around the button.
     */
    protected boolean drawFrame;

    /**
     * The world to switch to after the button was clicked, or null.
     */
    protected World onClick;

    /**
     * The standart image of the button.
     */
    protected GreenfootImage image;

    /**
     * The buttons image when hovered over it with the mouse.
     */
    protected GreenfootImage hoveredImage;

    /**
     * The buttons image when it is being clicked.
     */
    protected GreenfootImage clickedImage;
    

    /**
     * Constructs a new grey Button with the given title and a default size
     * of at least 90 times 35 pixels.
     * 
     * @param title The text printed onto the button
     */
    public Button(String title){
        inputText = new Text(title, 25, Color.BLACK, Color.LIGHT_GRAY);
        x = 90;
        y = 35;
        drawFrame = true;
        onClick = null;
        setup();
    }

    /**
     * Constructs a new grey Button with the given title and a default size
     * of at least 90 times 35 pixels. It will switch to the given world when clicked.
     * 
     * @param title The text printed onto the button
     * @param onClick The world to switch to when clicked
     */
    public Button(String title, World onClick){
        inputText = new Text(title, 25, Color.BLACK, Color.LIGHT_GRAY);
        x = 90;
        y = 35;
        drawFrame = true;
        this.onClick = onClick;
        setup();
    }
    
    /**
     * Constructs a new grey Button with the given title and a the given width
     * and height.
     * 
     * @param x The width of the button
     * @param y The height of the button
     * @param title The text printed onto the button
     */
    public Button(int x, int y, String title){
        name = title;
        inputImage = null;
        this.x = x;
        this.y = y;
        color = Color.LIGHT_GRAY;
        fontSize = 25;
        drawFrame = true;
        onClick = null;
        setup();
    }

    /**
     * Constructs a new grey Button with the given title and a the given width
     * and height. It will switch to the given world when clicked.
     * 
     * @param x The width of the button
     * @param y The height of the button
     * @param title The text printed onto the button
     * @param onClick The world to switch to when clicked
     */
    public Button(int x, int y, String title, World onClick){
        name = title;
        inputImage = null;
        this.x = x;
        this.y = y;
        color = Color.LIGHT_GRAY;
        fontSize = 25;
        drawFrame = true;
        this.onClick = onClick;
        setup();
    }
    
    /**
     * Constructs a new Button with the given title in the given size and
     * the given width and height using the given background colour. Unless
     * onClick is null if will switch to that world when clicked on.
     * 
     * @param x The width of the button
     * @param y The height of the button
     * @param title The text printed onto the button
     * @param fontSize The font size for the text on the button
     * @param colour The background colour of the button
     * @param onClick The world to switch to when clicked
     */
    public Button(int x, int y, String title, int fontSize, Color colour, World onClick){
        name = title;
        inputImage = null;
        this.x = x;
        this.y = y;
        color = colour;
        this.fontSize = fontSize;
        drawFrame = true;
        this.onClick = onClick;
        setup();
    }
    
    /**
     * Constructs a new Button with the given title in the given size and
     * the given width and height using the given background colour. Unless
     * onClick is null if will switch to that world when clicked on.
     * 
     * @param x The width of the button
     * @param y The height of the button
     * @param title The text printed onto the button
     * @param fontSize The font size for the text on the button
     * @param colour The background colour of the button
     * @param drawFrame if a black outline should be drawed around the image
     * @param onClick The world to switch to when clicked
     */
    public Button(int x, int y, String title, int fontSize, Color colour, boolean drawFrame, World onClick){
        name = title;
        inputImage = null;
        this.x = x;
        this.y = y;
        color = colour;
        this.fontSize = fontSize;
        this.drawFrame = drawFrame;
        this.onClick = onClick;
        setup();
    }

    /**
     * Constructs a new Button with the given image as background and
     * optional with a title and a black outline. Unless
     * onClick is null if will switch to that world when clicked on.
     * 
     * @param theImage the background image
     * @param title the title written centered onto the image, or null
     * @param fontSize the font size of the title. Ignore if there is no title
     * @param drawFrame if a black outline should be drawed around the image
     * @param onClick The world to switch to when clicked
     */
    public Button(GreenfootImage theImage, String title, int fontSize, boolean drawFrame, World onClick){
        name = title;
        inputImage = theImage;
        x = theImage.getWidth();
        y = theImage.getHeight();
        color = Color.LIGHT_GRAY;
        this.fontSize = fontSize;
        this.drawFrame = drawFrame;
        this.onClick = onClick;
        setup();
    }

    /**
     * Constructs a new button from the given text. Content and size
     * are updated every frame.
     * 
     * @param text The text object the button should be based on
     */
    public Button(Text text){
        inputText = text;
        x = y = 10;
        drawFrame = true;
        onClick = null;
        setup();
    }
    
    /**
     * Constructs a new button from the given text that is at least
     * as big as inputed. Content and size are updated every frame.
     * 
     * @param text The text object the button should be based on
     * @param minX The minimum width of the button
     * @param minY The minimum height of the button
     */
    public Button(Text text, int minX, int minY){
        inputText = text;
        if(minX < inputText.getImage().getWidth()) minX = inputText.getImage().getWidth();
        if(minY < inputText.getImage().getHeight()) minY = inputText.getImage().getHeight();
        x = minX;
        y = minY;
        drawFrame = true;
        onClick = null;
        setup();
    }
    
    /**
     * Constructs a new button from the given text that is at least
     * as big as inputed. It will switch to the given world unless it
     * is null. Content and size are updated every frame.
     * 
     * @param text The text object the button should be based on
     * @param minX The minimum width of the button
     * @param minY The minimum height of the button
     * @param drawFrame If a black outline should be drawed around the button
     * @param onClick The world to switch to when clicked
     */
    public Button(Text text, int minX, int minY, boolean drawFrame, World onClick){
        inputText = text;
        x = minX;
        y = minY;
        this.drawFrame = drawFrame;
        this.onClick = onClick;
        setup();
    }


    /**
     * Creates the image, hoveredImage and clickedImage for the button using
     * the latest settings and aplies the matching image to the button.
     */
    public void createImages(){
        if(inputText != null){
            name = inputText.getContent();
            int x = this.x;
            int y = this.y;
            if(x < inputText.getImage().getWidth() + 6) x = inputText.getImage().getWidth() + 6;
            if(y < inputText.getImage().getHeight()) y = inputText.getImage().getHeight();

            image = new GreenfootImage(x, y);
            if(inputText.getBackgroundColor() != null){
                image.setColor(inputText.getBackgroundColor());
                if(x > inputText.getImage().getWidth()){
                    int delta = x - inputText.getImage().getWidth();
                    image.fillRect(0, 0, delta / 2, image.getHeight());
                    image.fillRect(image.getWidth() - (delta - delta / 2), 0, delta / 2, image.getHeight());
                }
                if(y > inputText.getImage().getHeight()){
                    int deltaX = x - inputText.getImage().getWidth();
                    int deltaY = y - inputText.getImage().getHeight();
                    image.fillRect(deltaX / 2, 0, inputText.getImage().getWidth(), deltaY / 2);
                    image.fillRect(deltaX / 2, image.getHeight() - (deltaY - deltaY / 2), inputText.getImage().getWidth(), deltaY / 2);
                }
            }
            image.drawImage(inputText.getImage(), (x - inputText.getImage().getWidth()) / 2, (y - inputText.getImage().getHeight()) / 2);
            
        }
        else{
            if(inputImage != null){
                image = new GreenfootImage(inputImage);
            }
            else{
                image = new GreenfootImage(x, y);
                image.setColor(color);
                image.fill();
            }
            if(name!=null){
                GreenfootImage temp1 = new GreenfootImage(name, fontSize, Color.BLACK, null);
                image.drawImage(temp1, image.getWidth()/2-temp1.getWidth()/2, image.getHeight()/2-temp1.getHeight()/2);
            }
        }

        image.setColor(Color.BLACK);
        if(drawFrame)image.drawRect(0,0,image.getWidth()-1,image.getHeight()-1);

        hoveredImage = new GreenfootImage(image);
        hoveredImage.scale((int)(image.getWidth() * 1.1), (int)(image.getHeight() * 1.1));

        GreenfootImage temp = new GreenfootImage(image.getWidth(), image.getHeight());
        temp.setColor(Color.BLACK);
        temp.fill();
        temp.setTransparency(80);
        clickedImage = new GreenfootImage(image);
        clickedImage.drawImage(temp, 0, 0);

        if(touched){
            if(pressed) setImage(clickedImage);
            else setImage(hoveredImage);
        }
        else setImage(image);
    }


    /**
     * Applies default settings to the button
     */
    private void setup(){
        createImages();
        touched = false;
        pressed = false;
        clickCount = 0;
        pressTime = 0;
    }
    

    /**
     * Analyses the mouse interactions each frame. Do not remove
     * any code here! Use the run method instead!
     */
    public final void act()
    {
        touched = touching();
        if(touched&&Greenfoot.mousePressed(null)){
            pressed=true;
            onPress();
        }
        else if(Greenfoot.mouseClicked(null)){
            if(pressed&&touched){
                clickCount++;
                onClick();
                onRelease();
                pressed = false;
                if(onClick!=null)Greenfoot.setWorld(onClick);
            }
            else if(pressed){
                pressed = false;
                onRelease();

            }
        }

        
        if(inputText != null) createImages();
        else if(touched){
            if(pressed) setImage(clickedImage);
            else setImage(hoveredImage);
        }
        else setImage(image);
        
        if(pressed)pressTime++;
        run();
    }


    /**
     * Checkes weather the mouse is touching the button right now. Is rather
     * cpu-intense so don't use it more than once per frame.
     * 
     * @return Wheather the mouse is touching the button
     */
    public boolean touching(){
        boolean out = false;
        try{
            MouseInfo mouse = Greenfoot.getMouseInfo();
            Sensor sensor = new Sensor();
            getWorld().addObject(sensor, mouse.getX(), mouse.getY());
            out = sensor.touching(this);
        }catch(Exception e){return false;}
        return out;
    }


    /**
     * A simple class to test if it is touching the button.
     */
    class Sensor extends Actor{
        /**
         * Creates a new sensor with an transparent one pixel big image.
         */
        public Sensor(){
            setImage(new GreenfootImage(1, 1));
        }

        /**
         * Weather the sensor is touching the given button.
         * 
         * @param button The button to check for
         * @return Weather the given button is touched
         */
        public boolean touching(Button button){
            boolean out = getOneIntersectingObject(Button.class) == button;
            getWorld().removeObject(this);
            return out;
        }
    }


    /**
     * Executed once per frame as a replacement of the act method. Override
     * it to use.
     */
    public void run(){}

    /**
     * Executed whenever the mouse is first pressed onto the button
     */
    public void onPress(){}

    /**
     * Executed whenever the mouse is released after holding down the button,
     * also if is is not on it no more.
     */
    public void onRelease(){}

    /**
     * Executed ehenever the mouse is released after holding down the button,
     * but only when the mouse touched the button while being released.
     */
    public void onClick(){}
    

    /**
     * Resets the stats of the button.
     */
    public void reset(){
        clickCount = 0;
        pressTime = 0;
    }
    
    /**
     * Sets the count of the button to the given value.
     * 
     * @param n The new value
     */
    public void setCount(int n){
        clickCount = n;
    }
    
    /**
     * Returns the number of times the button was clicked since the last
     * reset.
     * 
     * The Button is considered to be clicked when the mouse was pressed on
     * the button and then released on it again. It still counts as a click
     * if the mouse was dragged outside of the button while being pressed
     * but is released on the button.
     * 
     * @return The number of clicks
     */
    public int getClickCount(){
        return clickCount;
    }
    
    /**
     * Returns true if the button was clicked since the last reset.
     * 
     * The Button is considered to be clicked when the mouse was pressed on
     * the button and then released on it again. It still counts as a click
     * if the mouse was dragged outside of the button while being pressed
     * but is released on the button.
     * 
     * @return A boolean if the button was ever clicked at least one time
     */
    public boolean clicked(){
        return clickCount>0;
    }
    
    /**
     * Returns if the button is being pressed right now.
     * 
     * The Button is considered to be pressed when the mouse was pressed on
     * the buttonand since than holded down. It still counts as pressed
     * if the mouse was dragged outside of the button while being pressed.
     * 
     * @return If the button is pressed right now
     */
    public boolean pressed(){
        return pressed;
    }
    
    /**
     * Returns if the button is being touched by the mouse right now.
     * 
     * @return If the button is touched right now
     */
    public boolean touched(){
        return touched;
    }
    
    /**
     * Returns the number of frames the button was pressed since the last
     * reset.
     * 
     * It is considered as pressed if the boolean pressed() is true.
     * 
     * @return the time the button was pressed
     */
    public int getTimePressed(){
        return pressTime;
    }
    
    /**
     * Returns the title written onto the button.
     * 
     * @return The buttons text
     */
    public String getTitle(){
        return name;
    }

    /**
     * Returns the text object used in this button
     * 
     * @return The text object of this button
     */
    public Text getText(){
        return inputText;
    }


    /**
     * Override the title of the button.
     * 
     * @param name The new title
     */
    public void setTitle(String name){
        this.name = name;
        createImages();
    }

    /**
     * Sets the image of the button to the given one. If image is null,
     * the latest color will be used.
     * 
     * @param image The new image
     */
    public void setBackgroundImage(GreenfootImage image){
        inputImage = image;
        createImages();
    }

    /**
     * Sets the background of the button to the given color.
     * 
     * @param color the new color
     */
    public void setColor(Color color){
        inputImage = null;
        this.color = color;
        createImages();
    }

    /**
     * Sets the images displayed to custom ones to allow more customisation.
     * 
     * @param image The image to be showm normally
     * @param hoveredImage The image to be showm when the mouse hovers over the button
     * @param clickedImage The image to be shown when the button is pressed down
     */
    public void setCustomImages(GreenfootImage image, GreenfootImage hoveredImage, GreenfootImage clickedImage){
        this.image = image;
        this.hoveredImage = hoveredImage;
        this.clickedImage = clickedImage;
        if(touched){
            if(pressed) setImage(this.clickedImage);
            else setImage(this.hoveredImage);
        }
        else setImage(this.image);

    }

    /**
     * Sets the text object the button is based on to the given one.
     * 
     * @param text The text object to set to
     */
    public void setText(Text text){
        inputText = text;
        createImages();
    }

    /**
     * Sets weather the outline should be drawn or not
     * 
     * @param drawFrame Weather the outline should be drawn
     */
    public void drawFrame(boolean drawFrame){
        this.drawFrame = drawFrame;
        createImages();
    }

    /**
     * Sets the world to switch to on click to the given one. If onClick is
     * null, the world will not be swiched.
     * 
     * @param onClick The world to switch to, or null
     */
    public void setNextWorld(World onClick){
        this.onClick = onClick;
    }
}