import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class carSpin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class carSpin  extends Actor
{
    GreenfootImage buffer = new GreenfootImage(300,300);
    int it;
    double counter=0;
    boolean flip=false;
    Color color;
    static int type=0;
    
    public carSpin(){
        setImage(buffer);
    } 
    
    public void act() 
    {
        flip=false;
        it=(int)counter;
        if(it>12){
            it = 24-it;
            flip=true;
        }
        counter=counter+.25;
        if(counter>=24)counter=0;
        if(Greenfoot.mousePressed(this)){
            type++;
            if(type==7)type=0;
        }
        buffer = new GreenfootImage(((worldness)getWorld()).getCar(it));
        if(flip)buffer.mirrorHorizontally();
        buffer.scale(100,50);
        for(int x=0;x<100;x++){
            for(int y=0;y<50;y++){
                color = buffer.getColorAt(x,y);
                if(color.getRed()!=0 || color.getGreen()!=0 || color.getBlue()!=0){
                    if(type==0)buffer.setColorAt(x,y,new Color(color.getRed(),color.getGreen(),color.getGreen()));
                    if(type==1)buffer.setColorAt(x,y,new Color(color.getGreen(),color.getRed(),color.getGreen()));
                    if(type==2)buffer.setColorAt(x,y,new Color(color.getGreen(),color.getGreen(),color.getRed()));
                    if(type==3)buffer.setColorAt(x,y,new Color(color.getRed(),color.getRed(),color.getGreen()));
                    if(type==4)buffer.setColorAt(x,y,new Color(color.getGreen(),color.getRed(),color.getRed()));
                    if(type==5)buffer.setColorAt(x,y,new Color(color.getRed(),color.getGreen(),color.getRed()));
                    if(type==6)buffer.setColorAt(x,y,new Color(color.getRed(),color.getRed(),color.getRed()));
                }
            }
        }
        setImage(buffer);
    }    
}
