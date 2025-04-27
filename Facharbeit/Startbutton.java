import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Die Klasse Startbutton repräsentiert einen Knopf, der in der Greenfoot-Umgebung verwendet wird.
 * Sie lädt eine Grafik für den Startbutton und skaliert diese auf die gewünschte Größe.
 * 
 * <p>Version: 0.1.1</p>
 * 
 * <p>Autor: Tobias Beilfuß</p>
 * 
 * Methoden:
 * <ul>
 *   <li><b>act()</b>: Wird in jedem Frame aufgerufen und sorgt dafür, dass die Grafik geladen wird.</li>
 *   <li><b>grafik()</b>: Private Methode, die die Grafik des Startbuttons lädt und skaliert.</li>
 * </ul>
 */

/**
 * 
 * @author (Tobias Beilfuß) 
 * @version (0.1.1)
 */
public class Startbutton extends Startscreen_Menu
{
    public void act()
    {
        grafik();
    }
    private void grafik()
    {
        // Methode um die Grafik zu laden
        GreenfootImage image = new GreenfootImage("Startbutton.png");
        image.scale(200, 50);
        setImage(image); 
    }
}
