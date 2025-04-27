import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)
/**
 * Die Klasse Schrift1 ist eine Unterklasse von Actor und dient dazu,
 * einen Text auf dem Bildschirm anzuzeigen. Der Text wird mit einer 
 * benutzerdefinierten Schriftart und Farbe dargestellt.
 * 
 * Methoden:
 * - act(): Wird in jedem Aktionszyklus aufgerufen und ruft die Methode 
 *   TextActor() auf, um den Text zu erstellen.
 * - TextActor(): Erstellt ein GreenfootImage mit einem Text, der mit 
 *   einer bestimmten Schriftart, Größe und Farbe gezeichnet wird, und 
 *   setzt dieses Bild als das Bild des Objekts.
 * 
 * Verwendete Klassen und Methoden:
 * - GreenfootImage: Zum Erstellen und Bearbeiten von Bildern.
 * - Font: Zum Festlegen der Schriftart, Schriftgröße und Schriftstil.
 * - Color: Zum Festlegen der Textfarbe.
 * - setImage(): Zum Setzen des Bildes für das Actor-Objekt.
 * 
 * Hinweis: Die Schriftart "Comic Sans MS" wird verwendet, und der Text 
 * "Odysseus Landung" wird in weißer Farbe dargestellt.
 *
 * ⬆️ Text Wurde von VSCode Copilot generiert. ⬆️
 * @author (Tobias Beilfuß) 
 * @version (1.5)
 */
public class Schrift1 extends Startscreen_Menu
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