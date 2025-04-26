import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class text here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Text extends Actor
{
    /**
     * Act - do whatever the text wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Text(){
        GreenfootImage im=getImage();
        im.drawString("click \"Run\"  to display", 10, 120);
        Font font=new Font("Arial",Font.ITALIC,30);
        im.setFont(font);
        im.drawString("3D House", 0, 30);
        im.drawString("Demo", 20, 80);
    }
    
    public void act() 
    {
        // Add your action code here.
    }    
}
