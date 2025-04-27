/**
 * Die Klasse Stopbutton repräsentiert einen Button, der in der Greenfoot-Umgebung
 * verwendet werden kann, um eine Stopp-Funktionalität darzustellen.
 * 
 * Diese Klasse lädt eine Grafik für den Button und skaliert sie auf die gewünschte Größe.
 * 
 * Methoden:
 * - act(): Wird in jedem Frame aufgerufen und sorgt dafür, dass die Grafik geladen wird.
 * - grafik(): Private Methode, die die Grafik des Buttons lädt und skaliert.
 * 
 * Voraussetzungen:
 * - Eine Bilddatei mit dem Namen "stopbutton.png" muss im Projektverzeichnis vorhanden sein.
 * 
 * @author (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Stopbutton extends Actor
{
    public void act()
    {
        grafik();
    }    
    private void grafik()
    {
        // Methode um die Grafik zu laden
        GreenfootImage image = new GreenfootImage("stopbutton.png");
        image.scale(200, 50);
        setImage(image);
    }
} 
