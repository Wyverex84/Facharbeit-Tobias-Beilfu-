import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)


/**
 * Displays the number of frames per second.
 * 
 * @author Michael Berry
 * @version 12/09/08
 */
public class FPS extends Actor
{
    private static final Color textColor = new Color(255, 0, 50);
    private static final String text = "FPS: ";
    private static final int updateFreq = 40;
    private double counter=0;
    private double prevTime;
    private double fps;
    World world;
    int i=0;

    /**
     * Create a new FPS object
     */
    public FPS()
    {
        counter = 0;
        setImage(new GreenfootImage(100, 16));
        GreenfootImage image = getImage();
        image.setColor(textColor);
        updateImage();
    }
    
    protected void addedToWorld(World w){
        world = ((world)getWorld());
    }
    
    /**
     * Set the value of the frames per second
     */
    private void setFPS(String val)
    {
        updateImage();
    }
    
    public void act()
    {
        counter++;
        double gap = System.currentTimeMillis()/1000.0 - prevTime;
        if(counter>=updateFreq) {
            prevTime = System.currentTimeMillis()/1000.0;
            fps = counter/gap;
            
            counter=0;
            updateImage();
        }
        //if(fps>50){
        //    getWorld().addObject(new asteroid(0,0,500),0,0);
        //    i++;
        //    System.out.println(i);
        //}
        
    }

    /**
     * Make the image
     */
    private void updateImage()
    {
        GreenfootImage image = getImage();
        image.clear();
        image.drawString(text + "" + fps, 1, 12);
    }
}