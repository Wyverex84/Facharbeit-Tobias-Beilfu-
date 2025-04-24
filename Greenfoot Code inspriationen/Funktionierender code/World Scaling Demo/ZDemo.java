import greenfoot.*;

public class ZDemo extends World
{
    private Button spinButton, dragButton, moveButton;
    
    /** a simple world to demo the Zoomer class that can change the size of a world */
    public ZDemo()
    {
        super(600, 400, 1);
        /** building the background */
        GreenfootImage bg = getBackground();
        bg.setColor(new Color(240, 224, 192));;
        bg.fill();
        GreenfootImage tImg = new GreenfootImage("Pause me and try to drag my actors.", 42, Color.BLACK, new Color(0, 0, 0, 0));
        bg.drawImage(tImg, 300-tImg.getWidth()/2, 350);
        tImg = new GreenfootImage("Press\n'o' key\nto zoom\nOUT", 42, Color.BLACK, new Color(0, 0, 0, 0));
        bg.drawImage(tImg, 100-tImg.getWidth()/2, 175-tImg.getHeight()/2);
        tImg = new GreenfootImage("Press\n'i' key\nto zoom\nIN", 42, Color.BLACK, new Color(0, 0, 0, 0));
        bg.drawImage(tImg, 500-tImg.getWidth()/2, 175-tImg.getHeight()/2);
        /** adding the actors */
        addObject(spinButton = new Button("Click on me\n(detects clicks)"), 300, 160);
        addObject(dragButton = new Button("Drag me around\n(detects drags)"), 300, 75);
        addObject(moveButton = new Button("Use arrow keys to\nmove me around\n(detects keys)"), 300, 250);
        /** setting the zoomer */
        Zoomer.setKeys("o", "i");
    }
    
    public void started()
    {
        new Zoomer(this);
    }
    
    /** checks for button and keyboard actions */
    public void act()
    {
        // click
        if (spinButton.getRotation() > 0 || Zoomer.mouseClicked(spinButton)) spinButton.turn(-1);
        // drag
        if (Zoomer.mouseDragged(dragButton))
        {
            MouseInfo mouse = Zoomer.getMouseInfo();
            dragButton.setLocation(mouse.getX(), mouse.getY());
        }
        // keyboard
        int dx = 0, dy = 0;
        if (Greenfoot.isKeyDown("up")) dy--;
        if (Greenfoot.isKeyDown("down")) dy++;
        if (Greenfoot.isKeyDown("left")) dx--;
        if (Greenfoot.isKeyDown("right")) dx++;
        moveButton.setLocation(moveButton.getX()+dx, moveButton.getY()+dy);
    }
}