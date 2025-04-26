import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class arrow extends Actor
{
    GreenfootImage point = new GreenfootImage("arrowSmall.gif");
    int x=0,y=0,type,extend=0;
    carSelect call1;
    mapSelect call2;
    
    public arrow(boolean flip, carSelect c,int t){
        if(flip)point.mirrorHorizontally();
        call1 = c;
        type=t;
    } 
    
    public arrow(mapSelect c,int t){
        call2 = c;
        type=t;
        extend=60;
    } 
    
    public void act() 
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse!=null){
            x=mouse.getX();
            y=mouse.getY();
        }
        getImage().clear();
        if(x<getX()+8+extend && x>getX()-8 && y>getY()-8 && y<getY()+8){
            if(Greenfoot.mousePressed(null)){
                if(call2==null)call1.click(type);
                if(call1==null)call2.click(type);
            }
            getImage().drawImage(point,0,0);
        }
    }    
}
