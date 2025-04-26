import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)



public class crosshair extends Actor
{
    Object3D myship;
    
    protected void addedToWorld(World world){
        myship = ((raster)getWorld()).newCrosshair();
        ((raster)getWorld()).addToRaster(myship);
    }
    
    public void act(){}
    
}
