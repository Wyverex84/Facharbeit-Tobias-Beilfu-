import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse Zurückbutton.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Zurückbutton extends Pre_Game_Menu
{
    public void act()
    {
        grafik();
    }    
    private void grafik()
    {
        // Methode um die Grafik zu laden
        GreenfootImage image = new GreenfootImage("Zurückbutton.png");
        image.scale(200, 50);
        setImage(image);
    }
} 