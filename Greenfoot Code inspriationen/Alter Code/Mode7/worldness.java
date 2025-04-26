import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;


public class worldness  extends World
{
    static GreenfootImage c1 = new GreenfootImage("0001.png");
    static GreenfootImage c2 = new GreenfootImage("0002.png");
    static GreenfootImage c3 = new GreenfootImage("0003.png");
    static GreenfootImage c4 = new GreenfootImage("0004.png");
    static GreenfootImage c5 = new GreenfootImage("0005.png");
    static GreenfootImage c6 = new GreenfootImage("0006.png");
    static GreenfootImage c7 = new GreenfootImage("0007.png");
    static GreenfootImage c8 = new GreenfootImage("0008.png");
    static GreenfootImage c9 = new GreenfootImage("0009.png");
    static GreenfootImage c10 = new GreenfootImage("0010.png");
    static GreenfootImage c11 = new GreenfootImage("0011.png");
    static GreenfootImage c12 = new GreenfootImage("0012.png");
    static GreenfootImage c13 = new GreenfootImage("0013.png");
    static GreenfootImage buffer = new GreenfootImage(300,300);
    static int control=3,power=3,speed=3,map=1;
    
    public worldness()
    {    
        super(300,300,1); 
        setPaintOrder(wheel.class,needle.class,driver.class,message.class,box.class,raceTrack.class,FPS.class);//.,message.class);
        setActOrder(next.class,arrow.class,mapSelect.class,carSelect.class);
        //addObject(new raceTrack(),150,150);
        //addObject(new FPS(),150,100);
        //addObject(new driver(),150,249);
        addObject(new mapSelect(),150,150);
    }
    
    public void act(){
        List boxes = getObjects(box.class);
        for(int i=0;i<120;i++){
            for(int f=0;f<boxes.size();f++){
                ((box)boxes.get(f)).simulate(1);
            }
            for(int f=0;f<boxes.size();f++){
                ((box)boxes.get(f)).act();
            }
        }
        getBackground().setColor(Color.cyan);
        getBackground().fillRect(0,0,300,150);
        getBackground().setColor(Color.black);
        for(int f=0;f<boxes.size();f++){
            if(((box)boxes.get(f)).tied)
                getBackground().drawLine(150,0,(int)(((box)boxes.get(f)).x[0]),(int)(((box)boxes.get(f)).y[0]));
        }
    }
    
    public GreenfootImage getCar(int it){
        switch(it){
            case 0:return c1;
            case 1:return c2;
            case 2:return c3;
            case 3:return c4;
            case 4:return c5;
            case 5:return c6;
            case 6:return c7;
            case 7:return c8;
            case 8:return c9;
            case 9:return c10;
            case 10:return c11;
            case 11:return c12;
            case 12:return c13;
        }
        return null;
    }
    
    public GreenfootImage getRoad(int r){
        return new GreenfootImage("r"+r+".gif");
    }
    
    public GreenfootImage getMap(int m){
        return new GreenfootImage("m"+m+".gif");
    }
    
}
