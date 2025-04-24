
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.util.logging.Logger;

/**
 * This class animates the image decreasing the size to 1 pixel and increasing
 * it to MAX_SIZE pixels changing 1 pixel each act cycle.
 *
 * @author Yehuda
 * @version 1/31/2017
 */
public class Resize extends Actor {

    private static final Logger LOG = Logger.getLogger(Resize.class.getName());

    private final int maxSize;
    private final GreenfootImage actorImage;
    private boolean increase = false;
    private int size;

    /**
     * Constructor for objects of class Resize.
     *
     * @param image the image to animate
     * @param maxSize the biggest size for the sides of the image
     */
    public Resize(GreenfootImage image, int maxSize) {
        this.maxSize = maxSize;
        actorImage = new GreenfootImage(image);
        setImage(image);
        size = getImage().getWidth();
    }

    /**
     * Act - do whatever the UserImage wants to do. This method is called
     * whenever the 'Act' or 'Run' button gets pressed in the environment.
     */
    @Override
    public void act() {
        // Add your action code here.
        updateImage();
    }

    /**
     * This is the method that sets the image to the image with the new
     * dimensions.
     */
    private void updateImage() {
        GreenfootImage scaledImage = new GreenfootImage(actorImage);
        if (getImage().getWidth() >= maxSize && getImage().getHeight() >= maxSize && increase == true) {
            increase = false;
        }
        if (getImage().getWidth() <= 1 || getImage().getHeight() <= 1 && increase == false) {
            increase = true;
        }
        if (increase == false) {
            size--;
        }
        if (increase == true) {
            size++;
        }
        scaledImage.scale(size, size);
        setImage(scaledImage);
    }
}
