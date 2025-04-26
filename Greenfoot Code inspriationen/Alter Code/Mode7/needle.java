import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class needle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class needle  extends Actor
{
    double rot=0,prev=0;
    int counter=77;
    int type=0;
    int wait=0;
    
    
    public void act() 
    {
    }    
    
    public void set(double f){
        setRotation((int)(f*50-120));
        counter++;
        if(f<1.5 && prev>1.5){
            type=3;
            counter=78;
        }
        if(f>1.5 && prev<1.5){
            type=1;
            counter=78;
        }
        if(wait>0)wait--;
        if(wait<0)wait++;
        if(f-prev>.01 && wait<=0){
            Greenfoot.playSound("upSlow.wav");
            wait=30;
        }
        if(f-prev<-.02 && wait>=0){
            Greenfoot.playSound("downSlow.wav");
            wait=-30;
        }
        if(counter==78){
            if(type==0)Greenfoot.playSound("low.wav");
            if(type==1)Greenfoot.playSound("down.wav");
            if(type==2)Greenfoot.playSound("high.wav");
            if(type==3)Greenfoot.playSound("up.wav");
            if(type==3)type=0;
            if(type==1)type=2;
            counter=0;
        }
        prev=f;
    }
    
    public void set2(double f){
        f=(int)f;
        rot = rot + .1*(f-rot);
        setRotation((int)(rot*70-120));
    }
        
}
