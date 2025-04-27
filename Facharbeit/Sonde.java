import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 *<style>
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
 * @version (0.1.2)
 * <b style="font-size:120%;">Dokumentation:</b> <br>
 * <table style="width:100%;">
 * <tr>
 * <th>Datum</th>
 * <th>Änderung</th>
 * </tr>
 * <tr>
 * <th><b style="font-size:80%;">18.04.2025:</b></th>
 * <td>Sonde erstellt</td>
 * </tr>
 * <tr>
 * <th rowspan="5"><b style="font-size:80%;">20.04.2025:</b></th>
 * <td>Welt größe geändert</td>
 * </tr>
 * <tr>
 * <td>methode zu steuerung der Sonde hinzugefügt</td>
 * </tr>
 * <tr>
 * <td>Methode für die Schwerkraft hinzugefügt</td>
 * </tr>
 * <tr>
 * <td>Methode für die Bewegung hinzugefügt</td>
 * </tr>
 * </table>
 */
public class Sonde extends Actor {
    private double velocityX = 0; // Geschwindigkeit in X-Richtung
    private double velocityY = 0; // Geschwindigkeit in Y-Richtung
    private double acceleration = 0.1; // Beschleunigung
    private double gravity = 0.05; // Schwerkraft

    public void act() {
        handleInput();
        applyGravity();
        moveSonde();
        Img();
    }

    private void handleInput() 
    {
        if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"))
        {
            velocityX -= acceleration; // Nach links
        }
        if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right"))
        {
            velocityX += acceleration; // Nach rechts
        }
        if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up"))
        {
            velocityY -= acceleration; // Nach oben (Bremsen)
        }
        if (Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down"))
        {
            velocityY += acceleration; // Nach unten (Beschleunigen)
        }
    }

    private void applyGravity() 
    {
        velocityY += gravity; // Schwerkraft wirkt nach unten
    }

    private void moveSonde() 
    {
        setLocation((int) (getX() + velocityX), (int) (getY() + velocityY));

        // Begrenzung der Sonde am Rand der Welt
        if (getX() <= 0 || getX() >= getWorld().getWidth()) 
        {
            velocityX = 0; // Stoppe Bewegung in X-Richtung
        }
        if (getY() <= 0 || getY() >= getWorld().getHeight()) 
        {
            velocityY = 0; // Stoppe Bewegung in Y-Richtung
        }
    }
    private void Img()
    {
        GreenfootImage img = new GreenfootImage("sonde.png");
        img.scale(40, 60); // Größe der Sonde anpassen
        setImage(img);
    }
}
