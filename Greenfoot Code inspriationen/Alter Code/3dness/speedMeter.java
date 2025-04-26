import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;


public class speedMeter  extends Actor
{
    GreenfootImage screen = new GreenfootImage(20,350);
    Color alpha = new Color(0,0,0,20);
    int en=0,enLast=0;
    
    public speedMeter(){
        screen.setColor(new Color(128,128,128));
        screen.drawLine(7,0,7,350);
        screen.drawLine(13,0,13,350);
        screen.drawLine(0,0,20,0);
        screen.drawLine(0,349,20,349);
        for(int i=0;i<350;i=i+25){
            screen.drawLine(5,i,7,i);
            screen.drawLine(13,i,15,i);
        }
        setImage(screen);
    }
        
    public void act() 
    {
        screen.setColor(Color.black);
        screen.drawLine(8,0,8,350);
        screen.drawLine(9,0,9,350);
        screen.drawLine(10,0,10,350);
        screen.drawLine(11,0,11,350);
        screen.drawLine(12,0,12,350);
        screen.setColor(new Color(enLast,0,0));
        screen.fillRect(8,en,5,3);
        if(enLast>0)enLast--;
    }   
    
    public void setX(int speed){
        screen.setColor(Color.green);
        screen.fillRect(8,350-speed,5,3);
    }
    
    public void setEn(int speed){
        en=350-speed;
        enLast=255;
    }
    
    
    
}
