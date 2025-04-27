import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Die Klasse Game repräsentiert die Hauptwelt des Spiels.
 * Sie initialisiert die Welt, fügt Objekte wie die Sonde, den Mond und die Benutzeroberfläche hinzu
 * und setzt den Hintergrund der Welt.
 * 
 * Funktionen der Klasse:
 * - Initialisierung der Welt mit einer Größe von 1200x800 Pixeln.
 * - Hinzufügen der Sonde in die Mitte der Welt.
 * - Hinzufügen des Mondes an eine feste Position.
 * - Erstellen und Hinzufügen von UI-Elementen wie Schieberegler.
 * - Setzen eines Hintergrundbildes für die Welt.
 * 
 * Variablen:
 * - `sonde`: Repräsentiert die Sonde, die in der Welt platziert wird.
 * - `mond`: Repräsentiert den Mond, der in der Welt platziert wird.
 * - `slider1`: Ein Schieberegler für die Einstellung der Bodenbeschaffenheit.
 * - `slider2`: Ein Schieberegler für die Einstellung der Hindernisse.
 * 
 * ⬆️ Text Wurde von VSCode Copilot generiert. ⬆️ 
 * die anderen Kommentare wurden von Tobias Beilfuß selber hinzugefügt
 * @author (Tobias Beilfuß) 
 * @version (1.5)
 */
public class Game extends World
{

    // Deklaration der Variablen
    private Sonde sonde;
    private Mond mond;


    public Game()
    {    
        // Erstellt eine neue Welt mit 1200x800 Zellen mit einer Zellgröße von 1x1 Pixel.
        super(1200, 800, 1); //dies ist die hauptwelt
        
        // fügt die Sonde und den Mond hinzu
        Mond();
        Sonde();
        // fügt den Hintergrund hinzu
        Hintergrund();
    }
    void Sonde() 
    {
        // Füge die Sonde in die Mitte der Welt ein
        sonde = new Sonde();
        addObject(sonde, 300, 10);
    }
    void Mond() 
    {
        mond = new Mond();
        addObject(mond, 600, 800);
    }

    private void Hintergrund()
    {
        GreenfootImage hintergrund = new GreenfootImage("Game_Hintergrund.png");
        hintergrund.scale(1200, 800);
        setBackground(hintergrund);  
    }
}
