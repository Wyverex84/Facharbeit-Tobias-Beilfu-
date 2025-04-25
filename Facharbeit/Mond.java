import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse Mond.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Mond extends Actor
{
    // Deklaration der Variablen
    private Color mondgrau = new Color(150, 150, 150); // Grauton für den Mond
    public void act() 
    {
        // Ergänzen Sie Ihren Quelltext hier...
    }    

    protected void addedToWorld(World Game)
    {
        GreenfootImage mondImage = new GreenfootImage(400, 195);
        mondImage.setColor(mondgrau);
        mondImage.fillRect(0, 0, 402, 100); // Rechteck ohne Rundungen
        setImage(mondImage);
    }
}