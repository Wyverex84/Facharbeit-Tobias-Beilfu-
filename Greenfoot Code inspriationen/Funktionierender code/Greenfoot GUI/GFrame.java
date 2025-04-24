import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * This is part of PiRocks' Greenfoot GUI program.
 * It is a window witha title that can can contain other GComponents.
 * 
 * 
 * @author Michael (modified significantly by PiRocks)
 * @version 1.1
 */
public class GFrame extends GContainer
{

    /* Frame params */
    private String message;
    private String title;
    private int width = 200, height = 50;
    private int titleHeight = 20;
    
    /* Input params */
    private boolean isdragged = false;
    
        
    /**
     * Create a new GFrame with a message, default title ("Greenfoot"),default dimensions(200x50), and default title height (20).
     */    
    public GFrame(String message)
    {
        this(message,"Greenfoot",200,50,20);
    }
        
    /**
     * Create a new GFrame with a message, title, default dimensions(200x50), and default title height (20).
     */    
    public GFrame(String message,String title)
    {
        this(message,title,200,50,20);
    }
          
    /**
     * Create a new GFramw with a message, title, dimensions, and default title height (20).
     */    
    public GFrame(String message,String title,int width,int height)
    {
        this(message,title,width,height,20);
    }
    
    /**
     * Create a new GFrame with a message, title, dimensions, and dtitle height.
     */ 
    public GFrame(String message, String title, int width, int height, int titleHeight)
    {
        this.message = message;
        this.title = title;
        this.width = width;
        this.height = height;
        this.titleHeight = titleHeight;
        makeFrame();
    }
    
    /**
     * Build the window's initial image using the params specified in the constructors.
     */
    private void makeFrame()
    {
        GreenfootImage frame = new GreenfootImage(width, height+titleHeight);
        GreenfootImage title = new GreenfootImage(width, titleHeight);
        GreenfootImage body = new GreenfootImage(width, height);
        
        //Now fill in the colours
        title.setColor(greenfoot.Color.BLUE);
        body.setColor(greenfoot.Color.GRAY);
        title.fill();
        body.fill();
        
        //Now write the text onto the frame
        body.setColor(greenfoot.Color.WHITE);
        title.setColor(greenfoot.Color.WHITE);
        title.drawString(this.title, 0, titleHeight/2+5);
        body.drawString(message, 10, body.getHeight()/2);
        
        //Now add the parts of the frame onto the frame
        frame.drawImage(title, 0, 0);
        frame.drawImage(body, 0, titleHeight);
        
        getDrawPic().scale(frame.getWidth(),frame.getHeight());
        getDrawPic().drawImage(frame,0,0);
    }
    
    /**
     * Update this frame's picture by calling all of the active contained elements' update method,
     * and then drawing their newly generated images. 
     */
    public void update() {
        if(elementCount()<1) return;
        GComponent draw = getElements()[0];
        draw.update();
        
        GreenfootImage pic = draw.getDrawPic();
        pic.scale(width - 10, height - 10);
        getDrawPic().setColor(greenfoot.Color.GRAY);
        getDrawPic().fillRect(0, titleHeight,width,height+titleHeight);
        getDrawPic().drawImage(pic, 5,titleHeight + 5);
    }
    
    /**
     * Handles mouse input. For the moment this just transfers the data over to
     * all of the active contained elements' handleInput method.
     * 
     * This method also sees if the window should be moved. Window movement code is <em>very</em>
     * buggy. Working on making more reliable.
     * 
     * In a future version, code to close/resize this window will be added.
     */
    public void handleInput(MouseInfo m, int realX,int realY) {
        //Handle frame events.
        if(m!=null) {//Check sure a mouse event has occured.
            
            if(!Greenfoot.mouseDragged(this)) isdragged = false;
            else {//Handle movement.
                if(isdragged == false) {
                    int x = m.getX();
                    int y = m.getY();
                    y-=(getDrawPic().getHeight()/2);
                    if(y > 0 && y < titleHeight) {//check to see if titlebar was dragged...
                        x-=(getDrawPic().getWidth()/2);
                        if(x > 0 && x < width) {//ok title bar was dragged.
                            isdragged = true;
                        }
                    }
                } else {
                    setLocation(m.getX(),m.getY() + ((getDrawPic().getHeight()/2) - 5));
                }
            }
            
        }
        
        if(elementCount()<1) return;
        
        int y = realY + (titleHeight/2);
        
        GComponent draw = getElements()[0];
        draw.handleInput(m,realX,realY);
    }
}
