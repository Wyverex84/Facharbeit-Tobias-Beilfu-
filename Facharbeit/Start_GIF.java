import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse Start_GIF.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Start_GIF extends Zwischensequenzen_actor
{
    GifImage gifImage = new GifImage("Mondsonde_Start.gif");

    public void act() 
    {
        setImage(gifImage.getCurrentImage());
        
    }
}