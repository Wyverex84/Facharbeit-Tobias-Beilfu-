package packages.twoD;

import greenfoot.*;
/**
 * A utility class for Raycast.
 */
public class Sensor extends Actor{
    double x, y;
    public Sensor(int rotation, int width){
        setImage(new GreenfootImage(1, width));
        setRotation(rotation);
    }
    protected void addedToWorld(World w){
        x = getX();
        y = getY();
    }
    public Actor getOneIntersectingObject(Class<?> cls){
        return super.getOneIntersectingObject(cls);
    }
    public void move(double distance){
        double radians = Math.toRadians(getRotation());
        x += Math.cos(radians) * distance;
        y += Math.sin(radians) * distance;
        setLocation((int)(x + 0.5), (int)(y + 0.5));
    }
}