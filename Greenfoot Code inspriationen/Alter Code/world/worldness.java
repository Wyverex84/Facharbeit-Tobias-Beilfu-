import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class world here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class worldness extends World
{

    int offset=0;
    sphere s = new sphere();
    wall w = new wall();
    int pressed=0;
    
    public worldness()
    {    
        // Create a new world with 20x20 cells with a cell size of 10x10 pixels.
        super(300, 300, 1); 
        addObject(new FPS(),50,20);
        addObject(s,150,150);
    }
    
    public void act(){
        String str = Greenfoot.getKey();
        if(Greenfoot.isKeyDown("o")){
            if(pressed<0)
                offset = 1-offset;
            pressed=5;  
        }else{
            pressed--;
        }
        if(Greenfoot.isKeyDown("3")){
            addObject(s,150,150);
            removeObject(w);
        }
        if(Greenfoot.isKeyDown("4")){
            addObject(w,150,150);
            removeObject(s);
        }
        
        
        
        //b1.setLocation(150+offset,150);
        //b2.setLocation(150,150+offset);
        //b3.setLocation(150+offset,150+offset);
        
    }
 
}
