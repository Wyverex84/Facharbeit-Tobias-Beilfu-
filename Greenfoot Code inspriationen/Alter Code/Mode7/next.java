import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class next extends Actor
{
    static GreenfootImage point = new GreenfootImage("next.gif");
    int x=0,y=0,type;
    carSelect call1;
    mapSelect call2;
    
    public next(carSelect c){
        call1 = c;
    } 
    
    public next(mapSelect c){
        call2 = c;
    } 
    
    public void act() 
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse!=null){
            x=mouse.getX();
            y=mouse.getY();
        }
        getImage().clear();
        if(x<getX()+14 && x>getX()-62 && y>getY()-14 && y<getY()+14){
            if(Greenfoot.mousePressed(null)){
                if(call2==null)call1.end();
                if(call1==null)call2.end();
            }
            getImage().drawImage(point,0,0);
        }
    }    
}

