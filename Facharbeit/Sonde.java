import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Die Klasse Sonde repräsentiert ein Objekt, das sich in einer Greenfoot-Welt bewegen kann.
 * Sie simuliert eine Sonde mit Schwerkraft, Beschleunigung und Steuerung durch Benutzereingaben.
 * 
 * <p>Funktionen der Klasse:</p>
 * <ul>
 *   <li>Bewegung der Sonde basierend auf Benutzereingaben (Pfeiltasten oder WASD).</li>
 *   <li>Simulation von Schwerkraft, die die Sonde nach unten zieht.</li>
 *   <li>Begrenzung der Bewegung der Sonde innerhalb der Weltgrenzen.</li>
 *   <li>Automatische Anpassung des Bildes der Sonde.</li>
 * </ul>
 * 
 * <p>Attribute:</p>
 * <ul>
 *   <li><b>velocityX:</b> Geschwindigkeit der Sonde in X-Richtung.</li>
 *   <li><b>velocityY:</b> Geschwindigkeit der Sonde in Y-Richtung.</li>
 *   <li><b>acceleration:</b> Beschleunigung, die durch Benutzereingaben ausgelöst wird.</li>
 *   <li><b>gravity:</b> Konstante Schwerkraft, die auf die Sonde wirkt.</li>
 * </ul>
 * 
 * <p>Methoden:</p>
 * <ul>
 *   <li><b>act:</b> Hauptmethode, die in jedem Frame aufgerufen wird. Sie steuert die Eingabe, 
 *       Schwerkraft, Bewegung und Bildaktualisierung.</li>
 *   <li><b>handleInput:</b> Verarbeitet Benutzereingaben und passt die Geschwindigkeit entsprechend an.</li>
 *   <li><b>applyGravity:</b> Fügt die Schwerkraft zur vertikalen Geschwindigkeit hinzu.</li>
 *   <li><b>moveSonde:</b> Bewegt die Sonde basierend auf ihrer Geschwindigkeit und begrenzt sie an den Weltgrenzen.</li>
 *   <li><b>Img:</b> Lädt und skaliert das Bild der Sonde.</li>
 * </ul>
 * 
 * @author (Tobias Beilfuß)
 * @version (1.2)
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
