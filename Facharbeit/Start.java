import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse Start.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Start extends Zwischensequenzen
{
    private Start_GIF start;
    private int counter; // Zeit bis zur Pre_Game-Welt
    
    public Start()
    {
        start = new Start_GIF();
        addObject(start, 600, 400);
        counter = 550;
    }     
    public void act()
    {
        counter--;
        if (Greenfoot.isKeyDown("enter") || Greenfoot.mouseClicked(start) || Greenfoot.isKeyDown("space"))
        {
            // wechselt zur Pre_Game-Welt
            Greenfoot.setWorld(new Game());
        }
        if (counter == 0) 
        {
            // wechselt zur Pre_Game-Welt
            Greenfoot.setWorld(new Game());
        }

    }  
}