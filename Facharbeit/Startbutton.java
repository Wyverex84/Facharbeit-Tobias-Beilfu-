import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * <style>
 * table, th, td {
 * border: 2px solid black;
 * border-collapse: collapse;
 * text-align:center;
 * vertical-align: text-top;
 *}
 *</style>
 * Write a description the of class here.
 * 
 * @author (Tobias Beilfuß) 
 * @version (0.1.1)
 * <b style="font-size:120%;">Dokumentation:</b> <br>
 * <table style="width:100%;">
 * <tr>
 * <th>Datum</th>
 * <th>Änderung</th>
 * </tr>
 * <tr>
 * <th><b style="font-size:80%;">20.04.2025:</b></th>
 * <td>Actor Startbutton erstellt</td>
 * </tr>
 * 
 * <tr>
 * <th><b style="font-size:80%;">25.04.2025:</b></th>
 * <td>Grafik Methode hinzugefügt</td>
 * </tr>
 * </table>
 */
public class Startbutton extends Actor
{
    public void act()
    {
        grafik();
    }
    void grafik()
    {
        // Methode um die Grafik zu laden
        GreenfootImage image = new GreenfootImage("Startbutton.png");
        setImage(image);
    }
}
