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
 * Write a description the of class here.
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
 * <th><b style="font-size:80%;">18.04.2025:</b></th>
 * <td>welt unterklasse Endscreen erstellt</td>
 * </tr>
 * <tr>
 * <th rowspan="2"><b style="font-size:80%;">20.04.2025:</b></th>
 * <td> Welt größe geändert</td>
 * </tr>
 * <tr>
 * <th><b style="font-size:80%;">22.04.2025:</b></th>
 * <td>Methode zum erstellen eines hintergrundes hinzugefügt</td>
 * </tr>
 * </table>
 */
public class Endscreen extends World
{

    /**
     * Constructor for objects of class Endscreen.
     * 
     */
    public Endscreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(500, 600, 1); 
    }
    void Hintergrund(String filename, int width, int height, World world)
    {
        Commen.Hintergrund("weltall.png",500,600,this);
    }
}
