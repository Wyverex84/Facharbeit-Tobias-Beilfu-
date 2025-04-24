
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.util.logging.Logger;

/**
 * This class fades the specified text in and out.
 *
 * @author Yehuda
 * @version 12/18/2016
 */
public class Fade extends Actor {

    private static final Logger LOG = Logger.getLogger(Fade.class.getName());

    private final int fadeSpeed;
    private final GreenfootImage image;
    private boolean down;
    private boolean increase = false;

    /**
     * Constructor for objects of class Fade.
     *
     * @param image the image to fade in and out
     * @param fadeSpeed the amount to increment to transparency level of the
     * image
     */
    public Fade(GreenfootImage image, int fadeSpeed) {
        this.fadeSpeed = fadeSpeed;
        this.image = new GreenfootImage(image);
        setImage(image);
    }

    /**
     * Act - do whatever the Text wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    @Override
    public void act() {
        // Add your action code here.
        if (down != Greenfoot.isKeyDown("space")) {
            down = !down;
            if (down) {
                image.setTransparency(image.getTransparency() / 2);
                setImage(image);
            }
        }
        fade();
    }

    /**
     * This method changes the transparency up and down to make the text fade in
     * and out
     */
    private void fade() {
        if (image.getTransparency() > 255 - fadeSpeed && increase == true) {
            increase = false;
            image.setTransparency(255);
            return;
        }
        if (image.getTransparency() < fadeSpeed && increase == false) {
            increase = true;
            image.setTransparency(0);
            return;
        }
        if (increase == false) {
            image.setTransparency(image.getTransparency() - fadeSpeed);
        }
        if (increase == true) {
            image.setTransparency(image.getTransparency() + fadeSpeed);
        }
        setImage(image);
    }
}
