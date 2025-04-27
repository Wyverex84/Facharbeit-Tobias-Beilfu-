import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse Pre_Game.
 * 
 * @author (Tobias Beilfuß) 
 * @version (1.5)
 */
public class Pre_Game extends World
{
    private Slider_Bodenbeschafenheit slider1;
    private Slider_Hindernisse slider2;
    private Weiterbutton weiterbutton;
    private Zurückbutton zurückbutton;
    
    public Pre_Game()
    {    
        // Erstellt eine neue Welt mit 600x400 Zellen und einer Zell-Größe von 1x1 Pixeln.
        super(1200, 800, 1);
        // UI elemente werden hinzugefügt
        UI();
        Hintergrund();
    }
    public void act()
    {
        // ruft die Methode auf um zu prüfen ob der button geklickt wurde
        isbuttonclicked();
    }
    void isbuttonclicked()
    {
        //prüft ob der button geklickt wurde
        if (Greenfoot.mouseClicked(weiterbutton))
        {
            // wechselt zur Game-Welt
            Greenfoot.setWorld(new Start());
        }
        if (Greenfoot.mouseClicked(zurückbutton))
        {
            // wechselt zur Startscreen-Welt
            Greenfoot.setWorld(new Startscreen());
        }
    }
    void UI()
    {
        slider1 = new Slider_Bodenbeschafenheit("Bodenbeschafenheit", 1, 4);
        slider2 = new Slider_Hindernisse("Hindernisse", 1, 5);
        weiterbutton = new Weiterbutton();
        zurückbutton = new Zurückbutton();
        addObject(slider1, 500, 300);
        addObject(slider2, 500, 400);
        addObject(weiterbutton, 600, 600);
        addObject(zurückbutton, 600, 660);
    }
    private void Hintergrund()
    {
        GreenfootImage hintergrund = new GreenfootImage("Game_Hintergrund.png");
        hintergrund.scale(1200, 800);
        setBackground(hintergrund);  
    }
}