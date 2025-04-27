import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/** 
 * <style>
 *   table, th, td {
 * border: 2px solid black;
 * border-collapse: collapse;
 * text-align:center;
 * vertical-align: text-top;
 *}
 *</style>
 * @author (Tobias Beilfuß) <br>
 * @version (0.1.4) <br>
 * <b style="font-size:120%;">Dokumentation:</b> <br>
 * <table style="width:100%;">
 * <tr>
 * <th>Datum</th>
 * <th>Änderung</th>
 * </tr>
 * 
 * <tr>
 * <th><b style="font-size:80%;">18.04.2025:</b></th>
 * <td>welt unterklasse Startscreen erstellt</td>
 * </tr>
 * 
 * <tr>
 * <th rowspan="4"><b style="font-size:80%;">20.04.2025:</b></th>
 * <td> Welt größe geändert</td>
 * </tr>
 * 
 * <tr>
 * <td>Startbutton erstellt</td>
 * </tr>
 * 
 * <tr>
 * <td> Title erstellt</td>
 * </tr>
 * 
 * <tr>
 * <td>Startbutton anklickbar gemacht</td>
 * </tr>
 * 
 * <tr>
 * <th><b style="font-size:80%;">22.04.2025:</b></th>
 * <td>Hintergrund erstellt (noch kein hintergrundbild vorhanden)</td>
 * </tr>
 * 
 * <tr>
 * <th rowspan="2"><b style="font-size:80%;">25.04.2025:</b></th>
 * <td>Commen.Hintergrundbild Entfernt</td>
 * </tr>
 * 
 * <tr>
 * <td>Hintergrundbild methode hinzugefügt</td>
 * </tr>
 * </table>
 */
public class Startscreen extends World
{
    // Deklaration der Variablen
    private Startbutton startbutton;
    private Stopbutton stopbutton;
    private Titelschrift titel;
    /**
     * Constructor for objects of class Startscreen.
     * 
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
    void start()
    {
        startbutton = new Startbutton();
        stopbutton = new Stopbutton();
        addObject(startbutton, 600, 540);
        addObject(stopbutton, 600, 600);
    }
    void isbuttonclicked()
    {
        //prüft ob der button geklickt wurde
        if (Greenfoot.mouseClicked(startbutton))
        {
            Greenfoot.setWorld(new Game());
        }
        if (Greenfoot.mouseClicked(stopbutton))
        {
            Greenfoot.stop();
        }
    }
    void Title()
    {
        //fügt den Text hinzu
        //showText("Odysseus Landung", 600, 200);
        titel = new Titelschrift();
        addObject(titel, 600, 200);
    }
    private void Hintergrund()
    {
        //fügt den Hintergrund hinzu
        GreenfootImage hintergrund = new GreenfootImage("Startscreen_Hintergrund.png");
        hintergrund.scale(1200, 800);
        setBackground(hintergrund);
    }
}