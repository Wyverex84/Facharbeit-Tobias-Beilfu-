import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button  extends Actor
{
    public static final float FONT_SIZE = 18.0f;
    private static final float CHARACTER_WIDTH = 9;
    
    private boolean pressed, displayCount;
    private int count;
    private GreenfootImage currentImage, pressedImage, unpressedImage;
    private int imageOffset;
    private String text;
    private Slider slider;

    public Button(String message, Slider slider){
        text = message;
        this.slider = slider;
        createImages(message);
        updateImage();
    }

    /**
     * Act - do whatever the Button wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        boolean clicked = Greenfoot.mouseClicked(this);
        if(clicked){
            pressed = !pressed;
            buttonPressed(pressed);
            //rollover = false;
        }
        /*boolean moved = Greenfoot.mouseMoved(this);
        if(moved){
            rollover = true;
        }else if(rollover && Greenfoot.mouseMoved(null)){
            rollover = false;
            moved = true;
        }*/
        if(clicked){// || moved){
            updateImage();
        }
    }   
    
    public void setPressed(boolean pressed){
        this.pressed = pressed;
        updateImage();
    }
    
    public boolean getPressed(){
        return pressed;
    }
    
    public void setCount(int count){
        this.count = count;
    }
    
    public int getCount(){
        return count;
    }
    
    public void setDisplayCount(boolean display){
        displayCount = display;
        updateImage();
    }
    
    public boolean isDisplayCount(){
        return displayCount;
    }
    
    public void increment(){
        count++;
        updateImage();
    }
    
    public void decrement(){
        count--;
        updateImage();
    }
    
    public void reset(){
        count = 0;
        updateImage();
    }
    
    public void buttonPressed(boolean pressed){
        if(text.equals("setValue(0)")){
            slider.setValue(0);
        }else if(text.equals("setValue(1)")){
            slider.setValue(1);
        }else if(text.equals("setValue(100)")){
            slider.setValue(100);
        }else if(text.equals("setMaximumValue(1)")){
            slider.setMaximumValue(1);
        }else if(text.equals("setMaximumValue(100)")){
            slider.setMaximumValue(100);
        }else if(text.equals("showValue(true)")){
            slider.showValue(true);
        }else if(text.equals("showValue(false)")){
            slider.showValue(false);
        }else if(text.equals("showPercentage(true)")){
            slider.showPercentage(true);
        }else if(text.equals("showPercentage(false)")){
            slider.showPercentage(false);
        }else if(text.equals("setMajorSections(0)")){
            slider.setMajorSections(0);
        }else if(text.equals("setMajorSections(1)")){
            slider.setMajorSections(1);
        }else if(text.equals("setMajorSections(2)")){
            slider.setMajorSections(2);
        }else if(text.equals("setMinorSections(0)")){
            slider.setMinorSections(0);
        }else if(text.equals("setMinorSections(2)")){
            slider.setMinorSections(2);
        }
    }
    
    private void createImages(String message){
        GreenfootImage orig = getImage();
        orig.scale(20, 20);
        java.awt.Font font = orig.getFont();
        font = font.deriveFont(FONT_SIZE);
        imageOffset = orig.getWidth();
        int width = (int)(imageOffset+message.length()*CHARACTER_WIDTH);
        int height = (int)Math.max(orig.getHeight(), FONT_SIZE*2);
        unpressedImage = new GreenfootImage(width, height);
        unpressedImage.drawImage(orig, 0, 0);
        int y = (int)(orig.getHeight()+FONT_SIZE)/2;
        unpressedImage.drawString(message, orig.getWidth(), y);
        setImage(unpressedImage);
        
        /*orig = new GreenfootImage("button-green.png");
        orig.rotate(180);
        orig.scale(20, 20);
        width = width;
        height = (int)Math.max(orig.getHeight(), FONT_SIZE*2);
        pressedImage = new GreenfootImage(width, height);
        pressedImage.drawImage(orig, 0, 0);
        y = (int)(orig.getHeight()+FONT_SIZE)/2;
        pressedImage.drawString(message, orig.getWidth(), y);*/
    }
    
    private void updateImage(){
        /*if(pressed){
            currentImage = pressedImage;
        }else{
            currentImage = unpressedImage;
        }
        GreenfootImage image = currentImage;
        if(displayCount){
            image = new GreenfootImage(image);
            String c = String.valueOf(count);
            //int x = (int)((image.getWidth()-imageOffset-c.length()*CHARACTER_WIDTH)/2)+imageOffset;
            image.drawString(c, imageOffset, image.getHeight());
        }
        setImage(image);*/
    }
}
