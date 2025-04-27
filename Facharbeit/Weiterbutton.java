import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse Weiterbutton.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Weiterbutton extends Actor
{
    public void act()
    {
        grafik();
    }
    private void grafik()
    {
        // Methode um die Grafik zu laden
        GreenfootImage image = new GreenfootImage("Weiterbutton.png");
        image.scale(200, 50);
        setImage(image); 
    }    
}