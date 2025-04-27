import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)
import greenfoot.Font;
/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse Titelschrift.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Titelschrift extends Actor
{

    public void act() 
    {
        TextActor();
    }
    public void TextActor() 
    {
        GreenfootImage textBild = new GreenfootImage(450, 60);
        Font meineSchrift = new Font("Comic Sans MS", true, false, 50);
        textBild.setFont(meineSchrift);
        textBild.setColor(Color.WHITE);
        textBild.drawString("Odysseus Landung", 10, 45);
        setImage(textBild);
    }    
}