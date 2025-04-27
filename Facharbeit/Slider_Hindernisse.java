import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)
/**
 * Die Klasse Slider_Hindernisse implementiert einen Schieberegler (Slider) mit einer 
 * grafischen Benutzeroberfläche. Der Slider kann verwendet werden, um Werte innerhalb 
 * eines bestimmten Bereichs auszuwählen. Die Klasse enthält Funktionen zur Anzeige 
 * des aktuellen Werts, zur Anpassung des Werts und zur Aktivierung/Deaktivierung des Sliders.
 * 
 * Eigenschaften:
 * - Der Slider hat einen minimalen und maximalen Wert, die beim Erstellen festgelegt werden.
 * - Eine grafische Hand (SliderHand) kann entlang des Sliders bewegt werden, um den Wert zu ändern.
 * - Der aktuelle Wert wird als Text angezeigt.
 * - Ein optionaler Beschriftungstext (Label) kann unterhalb des Sliders angezeigt werden.
 * 
 * Konstruktoren:
 * - `Slider_Hindernisse()`: Erstellt einen Standard-Slider mit einem Bereich von 0 bis 100 und ohne Beschriftung.
 * - `Slider_Hindernisse(String labelText, int min, int max)`: Erstellt einen Slider mit einem angegebenen Bereich und einer Beschriftung.
 * 
 * Methoden:
 * - `addedToWorld(World world)`: Fügt die Hilfsobjekte (Hand, Wertanzeige und Beschriftung) zur Welt hinzu.
 * - `getValue()`: Gibt den aktuellen Wert des Sliders zurück.
 * - `setValue(int val)`: Setzt den Wert des Sliders auf den angegebenen Wert, sofern dieser im Bereich liegt.
 * - `setEnabled(boolean enable)`: Aktiviert oder deaktiviert den Slider.
 * - `isEnabled()`: Gibt zurück, ob der Slider aktiviert ist.
 * - `setLocation(int x, int y)`: Überschreibt die Position des Sliders und verschiebt auch die zugehörigen Objekte (Hand, Wertanzeige, Beschriftung).
 * 
 * Innere Klassen:
 * - `SliderHand`: Implementiert die bewegliche Hand des Sliders. Sie reagiert auf Mausbewegungen und passt den Wert entsprechend an.
 * - `Text`: Stellt ein Textobjekt dar, das zur Anzeige von Werten und Beschriftungen verwendet wird.
 * 
 * Hinweise:
 * - Die Klasse verwendet die Greenfoot-Bibliothek für die grafische Darstellung und Interaktion.
 * - Die Slider-Hand kann nur innerhalb des definierten Bereichs bewegt werden.
 * - Wenn ein ungültiger Wert gesetzt wird, wird eine Fehlermeldung ausgegeben.
 * 
 * ⬆️ Text Wurde von VSCode Copilot generiert. ⬆️
 * @author: (Tobias Beilfuß)
 * @version: (1.5)
 * 
 */

public class Slider_Hindernisse extends Actor
{
    private static final int MIN_X = -82;   // min and max offset in pixels on the slider image
    private static final int MAX_X = 81;
    private static final double RANGE_X = MAX_X - MIN_X;

    private SliderHand hand;                // the object representing the hand
    private Text value;                     // an object for the numeric value display
    private Text label;                     // an object for displaying the label

    private boolean enabled;
    private int val;
    private int min;
    private int max;
    private int range;

    /**
     * Create a default slider (range [0..100], no label).
     */
    public Slider_Hindernisse()
    {
        this(" ", 0, 100);
    }

    /**
     * Create a slider with the specified minimum and maximum values. The labelText will
     * be shown under the slider.
     */
    public Slider_Hindernisse(String labelText, int min, int max)
    {
        this.min = min;
        this.max = max;
        range = max - min;
        enabled = true;
        label = new Text(labelText);
        value = new Text(Integer.toString((min+max)/2));
    }

    /**
     * Add the helper objects (hand and labels).
     */
    public void addedToWorld(World world)
    {
        hand = new SliderHand();
        getWorld().addObject (hand, getX(), getY()-4);
        getWorld().addObject (value, getX(), getY()-30);
        getWorld().addObject (label, getX(), getY()+30);
    }

    /**
     * Return the currect value of the slider.
     */
    public int getValue()
    {
        return val;
    }

    /**
     * Set the value of the slider.
     */
    public void setValue(int val)
    {
        if (val < min || val > max) {
            System.err.println("Value for slider out of range (" + val + ") - ignored");
            return;
        }

        int x = MIN_X + (int) ( (val - min) * (RANGE_X / range) );
        hand.setLocation (getX() + x, hand.getY());

        this.val = val;
        value.setText (""+val);
    }

    public void setEnabled(boolean enable) 
    {
        hand.setEnabled(enable);
        enabled = enable;
    }

    public boolean isEnabled() 
    {
        return enabled;
    }

    /**
     * Overide setLocation to also move child objects (hand and labels).
     */
    public void setLocation(int x, int y) 
    {
        x = x + getImage().getWidth()/2;
        y = y + getImage().getHeight()/2;
        super.setLocation (x, y);
        if (hand != null) {
            hand.setLocation(x, y-4);
            value.setLocation(x, y-30);
            label.setLocation(x, y+30);
        }
    }

    /**
     * Set the value of the slider from a global X co-ordinate. This is used by the slider
     * hand.
     */
    private void setValueFromX(int x) 
    {
        x -= getX();   // convert from absolute to relative offset

        val = min + (int) ( (x - MIN_X) * ( range / RANGE_X) );
        value.setText (""+val);
    }

    /**
     * Get the minimum X offset (in pixels) for the slider hand.
     */
    private int getMinX()
    {
        return getX() + MIN_X;
    }

    /**
     * Get the maximum X offset (in pixels) for the slider hand.
     */
    private int getMaxX()
    {
        return getX() + MAX_X;
    }

    /**
     * The class implementing the handle that moves on the slider.
     */
    class SliderHand extends Actor
    {        
        public SliderHand()
        {
            setImage("slider-hand.png");
        }

        public void act() 
        {
            if (enabled && Greenfoot.mouseDragged(this)) {
                int oldX = getX();

                MouseInfo mouse = Greenfoot.getMouseInfo();
                int x = mouse.getX();

                if (x < getMinX())
                    x = getMinX();

                if (x > getMaxX())
                    x = getMaxX();

                if (x != oldX) {
                    setLocation(x, getY());
                    setValueFromX(x);
                }
            }
        }    

        public void setEnabled(boolean enable) 
        {
            if (enable)
                setImage("slider-hand.png");
            else
                setImage("slider-hand-grey.png");
        }
    }

    /**
     * This class provides objects that just show a bit of text on the screen.
     */
    public class Text extends Actor
    {
        public Text(String text)
        {
            setText(text);
        }

        public void setText(String text)
        {
            GreenfootImage img = new GreenfootImage(text.length() * 12, 16);
            int textWidth = img.getAwtImage().getGraphics().getFontMetrics().stringWidth(text);
            Font font1 = new Font("Comic Sans MS", false, false, 12);
            img.setFont(font1);
            img.drawString(text, (img.getWidth() - textWidth) / 2, 12);
            setImage(img);
        }
    }

}