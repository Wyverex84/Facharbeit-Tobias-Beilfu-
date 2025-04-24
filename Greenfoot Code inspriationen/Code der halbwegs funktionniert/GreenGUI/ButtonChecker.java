import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Checks if a button has been pressed in the world.
 *
 * @author Ed Parrish
 * @version 1 on 11/25/2017
 */
public class ButtonChecker extends Actor
{
    // Color shading tints
    private static final Color RED_TINT = new Color(255, 0, 0, 32);
    private static final Color GREEN_TINT = new Color(0, 255, 0, 32);

    /**
     * Detect and react to button clicks.
     */
    public void act()
    {
        List<Button> buttons = getWorld().getObjects(Button.class);
        for (Button button : buttons)
        {
            if (button.isPressed())
            {
                String answer = Greenfoot.ask("Why did you click me?");
                button.setBackgroundColor(RED_TINT);
                if (button.getText().equals("Button"))
                {
                    button.setText(answer);
                }
                List<Label> labels = getWorld().getObjects(Label.class);
                Label label2 = labels.get(1);
                label2.setText(answer);
                label2.setBackgroundColor(GREEN_TINT);
                for (Label label : labels)
                {
                    if (label.getText().equals("Label"))
                    {
                        label.setText(answer);
                    }
                }

            }
        }
    }
}
