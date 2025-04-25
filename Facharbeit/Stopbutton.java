import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse stopbutton.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Stopbutton extends Actor
{
    /**
     * Act - tut, was auch immer stopbutton tun will. Diese Methode wird aufgerufen, 
     * sobald der 'Act' oder 'Run' Button in der Umgebung angeklickt werden. 
     */
    public void act()
    {
        grafik();
    }
    void grafik()
    {
        // Methode um die Grafik zu laden
        GreenfootImage image = new GreenfootImage("stopbutton.png");
        setImage(image);
    }
} 
