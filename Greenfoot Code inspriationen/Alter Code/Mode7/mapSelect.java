import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class mapSelect  extends Actor
{
    int map=1;
    mapSpin m = new mapSpin();
    arrow a1 = new arrow(this,1),
          a2 = new arrow(this,2),
          a3 = new arrow(this,3),
          a4 = new arrow(this,4),
          a5 = new arrow(this,5);
    next n = new next(this);
    
    protected void addedToWorld(World world){
        getWorld().addObject(a1,155,140);
        getWorld().addObject(a2,155,163);
        getWorld().addObject(a3,155,186);
        //getWorld().addObject(a4,155,209);
        getWorld().addObject(a5,155,232);
        getWorld().addObject(m,64,164);
        getWorld().addObject(n,284,278);
    }
    
    public void act() 
    {}    
    
    public void click (int t){
        m.set(t);
        ((worldness)getWorld()).map = t;
    }
    
    public void end(){
        getWorld().removeObject(a1);
        getWorld().removeObject(a2);
        getWorld().removeObject(a3);
        getWorld().removeObject(a4);
        getWorld().removeObject(a5);
        getWorld().removeObject(m);
        getWorld().removeObject(n);
        getWorld().addObject(new carSelect(),150,150);
        getWorld().removeObject(this);
    }
}
