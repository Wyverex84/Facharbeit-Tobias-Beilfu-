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
 * </table>
 */
public class Startscreen extends World
{
    // Deklaration der Variablen
    private Startbutton startbutton;
    /**
     * Constructor for objects of class Startscreen.
     * 
     */
    public Startscreen()
    {    
        // Erstellt eine neue welt mit500x600 Zellen mit einer Zellgröße von 1x1 Pixel.
        super(500, 600, 1);
        start();
        Title();
    }
    public void act()
    {
        //wenn auf Playbutton geklickt wird wird die weld Auf MyWorld gesetzt
        if (Greenfoot.mouseClicked(startbutton))
        {
            Greenfoot.setWorld(new Game());
        }
    }
    void start()
    {
        startbutton = new Startbutton();
        addObject(startbutton, 250, 300);
    }
    void Title()
    {
        //fügt den Text hinzu
        showText("Odysseus Landung", 250, 100);
    }
    void Hintergrund(String filename, int width, int height, World world)
    {
        Commen.Hintergrund("weltall.png",500,600,this);
    }
}
