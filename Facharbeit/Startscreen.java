import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/** 
 * Die Klasse Startscreen repräsentiert den Startbildschirm des Spiels.
 * Sie erbt von der Klasse World und initialisiert die Benutzeroberfläche 
 * mit einem Start- und Stop-Button, einem Titel und einem Hintergrundbild.
 * 
 * Funktionen der Klasse:
 * - Startet das Spiel automatisch beim Erstellen des Startbildschirms.
 * - Fügt interaktive Buttons (Start und Stop) hinzu.
 * - Zeigt einen Titeltext an.
 * - Setzt ein Hintergrundbild.
 * - Reagiert auf Mausklicks auf die Buttons, um das Spiel zu starten oder zu stoppen.
 * 
 * Methodenübersicht:
 * - Startscreen(): Konstruktor, der die Welt initialisiert und die Elemente hinzufügt.
 * - act(): Wird in jedem Frame aufgerufen, um auf Benutzerinteraktionen zu reagieren.
 * - start(): Fügt die Buttons (Start und Stop) zur Welt hinzu.
 * - isbuttonclicked(): Überprüft, ob ein Button geklickt wurde, und führt entsprechende Aktionen aus.
 * - Title(): Fügt den Titeltext zur Welt hinzu.
 * - Hintergrund(): Setzt das Hintergrundbild der Welt.
 * 
 * Verwendung:
 * Diese Klasse wird verwendet, um den Startbildschirm des Spiels zu erstellen 
 * und die Navigation zum Hauptspiel oder das Beenden des Spiels zu ermöglichen.
 * ⬆️ Text Wurde von VSCode Copilot generiert. ⬆️
 * @author (Tobias Beilfuß)
 * @version (1.5)
 */
public class Startscreen extends World
{
    // Deklaration der Variablen
    private Startbutton startbutton;
    private Stopbutton stopbutton;
    private Schrift1 titel;

    /**
     * Konstruktor für die Klasse Startscreen.
     * Erstellt eine neue Welt mit 1200x800 Zellen und einer Zellgröße von 1x1 Pixel.
     * Fügt den Start- und Stop-Button sowie den Titel und den Hintergrund hinzu.
     */
    public Startscreen()
    {   
        // Erstellt eine neue welt mit400x600 Zellen mit einer Zellgröße von 1x1 Pixel.
        super(1200, 800, 1);
        // startet das Spiel automatisch
        Greenfoot.start(); 
        // fügt den Startbutton und den Stopbutton hinzu
        start();
        // fügt den Titel hizu
        Title();
        // fügt den Hintergrund hinzu
        Hintergrund();
    }

    public void act()
    {
        // ruft die Methode auf um zu prüfen ob der button geklickt wurde
        isbuttonclicked();
    }

    void start() //Fügt die Buttons zum Programm Start hinzu
    {
        // fügt die Buttons hinzu
        startbutton = new Startbutton();
        stopbutton = new Stopbutton();
        // fügt die Buttons in die Welt ein
        addObject(startbutton, 600, 540);
        addObject(stopbutton, 600, 600);
    }

    void isbuttonclicked()
    
    {
        //prüft ob der button geklickt wurde
        if (Greenfoot.mouseClicked(startbutton))
        {
            // wechselt zur Game-Welt
            Greenfoot.setWorld(new Pre_Game());
        }
        if (Greenfoot.mouseClicked(stopbutton))
        {
            // stoppt das Spiel
            Greenfoot.stop();
        }
    }

    void Title()
    {
        // fügt den Titel hinzu
        titel = new Schrift1();
        addObject(titel, 600, 200);
    }

    private void Hintergrund()
    {
        //fügt den Hintergrund hinzu
        GreenfootImage hintergrund = new GreenfootImage("Startscreen_Hintergrund.png"); // Startscreen_Hintergrundbild.png ist KI-generiert
        hintergrund.scale(1200, 800);
        setBackground(hintergrund);
    }
}