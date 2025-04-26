import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class carSelect  extends Actor
{
    static int total=1,control=3,speed=3,power=3;
    arrow a1 = new arrow(false,this,1),
          a2 = new arrow(false,this,2),
          a3 = new arrow(false,this,3),
          a4 = new arrow(true,this,-1),
          a5 = new arrow(true,this,-2),
          a6 = new arrow(true,this,-3);
    carSpin cs = new carSpin();
    next n = new next(this);
    
    protected void addedToWorld(World world){
        getWorld().addObject(a1,225,159);
        getWorld().addObject(a2,225,190);
        getWorld().addObject(a3,225,223);
        getWorld().addObject(a4,155,159);
        getWorld().addObject(a5,155,190);
        getWorld().addObject(a6,155,223);
        getWorld().addObject(cs,64,177);
        getWorld().addObject(n,284,278);
    }
    
    public void act() 
    {
        getImage().setColor(new Color(150,150,150));
        for(int f=169;f<169+100;f=f+10){
            getImage().fillRect(f,103,8,8);
        }
        for(int f=166;f<166+50;f=f+10){
            getImage().fillRect(f,155,8,8);
        }
        for(int f=166;f<166+50;f=f+10){
            getImage().fillRect(f,186,8,8);
        }
        for(int f=166;f<166+50;f=f+10){
            getImage().fillRect(f,219,8,8);
        }
        
        getImage().setColor(Color.blue);
        for(int f=169;f<169+10*total;f=f+10){
            getImage().fillRect(f,103,8,8);
        }
        for(int f=166;f<166+10*control;f=f+10){
            getImage().fillRect(f,155,8,8);
        }
        for(int f=166;f<166+10*power;f=f+10){
            getImage().fillRect(f,186,8,8);
        }
        for(int f=166;f<166+10*speed;f=f+10){
            getImage().fillRect(f,219,8,8);
        }
    }    
    
    public void click (int t){
        int dir = t/Math.abs(t);
        if(Math.abs(t)==1 && control+dir!=6 && control+dir!=-1 && total-dir!=-1){
            control=control+dir;
            total=total-dir;
        }
        if(Math.abs(t)==2 && power+dir!=6 && power+dir!=-1 && total-dir!=-1){
            power=power+dir;
            total=total-dir;
        }
        if(Math.abs(t)==3 && speed+dir!=6 && speed+dir!=-1 && total-dir!=-1){
            speed=speed+dir;
            total=total-dir;
        }
        ((worldness)getWorld()).control = control;
        ((worldness)getWorld()).power = power;
        ((worldness)getWorld()).speed = speed;
    }
    
    public void end(){
        getWorld().removeObject(a1);
        getWorld().removeObject(a2);
        getWorld().removeObject(a3);
        getWorld().removeObject(a4);
        getWorld().removeObject(a5);
        getWorld().removeObject(a6);
        getWorld().removeObject(cs);
        getWorld().removeObject(n);
        getWorld().addObject(new raceTrack(),150,150);
        getWorld().removeObject(this);
    }
}
