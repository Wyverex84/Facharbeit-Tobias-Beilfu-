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
 * @version (0.1.1)
 * <b style="font-size:120%;">Dokumentation:</b> <br>
 * <table style="width:100%;">
 * <tr>
 * <th>Datum</th>
 * <th>Änderung</th>
 * </tr>
 * <tr>
 * <th><b style="font-size:80%;">18.04.2025:</b></th>
 * <td>Welt unterklasse Game erstellt</td>
 * </tr>
 * <tr>
 * <th rowspan="2"><b style="font-size:80%;">20.04.2025:</b></th>
 * <td>Welt größe geändert</td>
 * </tr>
 * <tr>
 * <td>methode zum erstellen der Sonde hinzugefügt</td>
 * </tr>
 * </table>
 */
public class Game extends World
{

    // Deklaration der Variablen
    private Sonde sonde;
    private Mond mond;

    public Game()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        Sonde();
        Hintergrund();
    }
    
    private void Sonde() 
    {
        // Füge die Sonde in die Mitte der Welt ein
        sonde = new Sonde();
        addObject(sonde, 300, 10);
    }
    
    private void Hintergrund()
    {
        GreenfootImage hintergrund = new GreenfootImage("Game_Hintergrund.png");
        hintergrund.scale(600, 400);
        setBackground(hintergrund);  
    }
}
