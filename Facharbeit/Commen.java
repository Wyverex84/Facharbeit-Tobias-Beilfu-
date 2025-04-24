import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * <style>
 * table, th, td {
 * border: 2px solid black;
 * border-collapse: collapse;
 * text-align:center;
 * vertical-align: text-top;
 * }
 * </style>
 * In dieser klasse sind, Methoden die regelmäsig benutzt Werden.
 * 
 * @author (Tobias Beilfuß) 
 * @version (0.1.0)
 * <b style="font-size:120%;">Dokumentation:</b> <br>
 * <table style="width:100%;">
 * <tr>
 * <th>Datum</th>
 * <th>Änderung</th>
 * </tr>
 * <tr>
 * <th rowspan="2"><b style="font-size:80%;">22.04.2025:</b></th>
 * <td>Klasse Commen erstellt</td>
 * </tr>
 * <tr>
 * <td>Hintergrund Methode erstellt</td>
 * </tr>
 * </table>
 */
public class Commen  
{
    /**
     * Setzt den Hintergrund der Welt auf ein Bild.
     * @param img Das Bild, das als Hintergrund verwendet werden soll.
     * @param world Die Welt, deren Hintergrund gesetzt werden soll.
     */
    public static void Hintergrund(String filename, int width, int height, World world) 
    {
        // Erstellen Sie ein neues GreenfootImage-Objekt mit dem angegebenen Dateinamen.
        GreenfootImage img = new GreenfootImage(filename);
        // Skalieren Sie das Bild auf die angegebenen Breite und Höhe.
        img.scale(width, height);
        // Setzen Sie das Hintergrundbild der Welt auf das skalierte Bild.
        world.setBackground(img);
    }
}