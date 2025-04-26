import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.List;
import java.util.ArrayList;
import java.awt.AWTException; //needed to use the Robot class
import java.awt.Robot; //needed to move the mouse back to the screen center once moved
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class world extends World
{
    //RASTER DATA
    boolean fastFramerate = true;
    double b1,b2,b3,b4;
    int skip = 1; 
    int right,left;
    int renderMode = 1;
    
    //RENDER DATA
    /*
    double d,c;
    int[] xp = {0,0,1,1};
    int[] zp = {0,1,1,0};  
    int[] scx = new int[4];
    int[] scy = new int[4];
    int[] scx2 = new int[4];
    int[] scy2 = new int[4];
    double cx;
    double cy;
    double cz;
    double[] zScore = new double[4];
    double[] zScoreBack = new double[4];
    boolean special = false;
    double[] tempX = new double[4];
    double[] tempY = new double[4];
    double[] tempZ = new double[4];
    double h;
    */
    
    //ROBOT DATA
    boolean calibrated=false;//calibration flag--the mouse has to be calibrated before it can be used
    int offX,offY;
    boolean isRobot = false;
    boolean hasStarted = false;
    
    //RENDERED POINT DATA
    int rx,ry;
    double xTemp1,yTemp1,zTemp1,xTemp2,yTemp2,zTemp2;
    boolean renderable = false;
    boolean notRenderable;

    //PLAYER DATA
    double myx=7,myy=1,myz=7;
    double ya=0,xa=0,tya=0,txa=0;
    double temp1,temp2,temp3,temp4;
    boolean crouching = false;
    double speed = .4;
    double height = 1;
    
    double xvel,yvel,zvel;
    boolean falling = false;
    double up = .1;
    int mx,my;
    MouseInfo mouse;
    boolean spaceWasPressed = false;
    
    //double enx=4.5,eny=2.5,enz=1;
    boolean rendered = false;
    double vel = .05;
    double delta=0;

    
    Map3D myMap3D;
    double[][] mapFloor;
    double[][] mapRoof;   
    
    //GUN DATA
    //Object3D gun = new Object3D("Weapon_Pistol");
    //Object3D hand = new Object3D("Hand1");
    //Object3D clip = new Object3D("Clip1");
    ArrayList gunz = new ArrayList();
    double straf = 0;
    double recoil = 0;
    double sway = 0;
    int reloading = 0;
    
    ArrayList<GameObject> objectsList = new ArrayList<GameObject>();
    Camera camera = new Camera();
    
    double rise = 3;

    public world()
    {  
        super(700,400,1);
        
        addObject(new FPS(),54,8);
        //gunz.add(gun);
        
        //addObject(l,0,0);
        //objectsList.add(l);
        
        
        myMap3D = new Map3D(1,getBackground(),objectsList,this,camera);
        mapFloor = Map3D_Data.getFloor(1);
        mapRoof = Map3D_Data.getRoof(1);
        Raster.setCamera(camera);
        
        addObject(new Laser(Math.PI/2,1.5),12.5,1,19.5);
        addObject(new Laser(Math.PI/2,1.5),12.5,2,19.5);
        addObject(new Switch(Math.PI/2),11.5,1.4,23.5);
        
        addObject(new PottedPlant(),7.5,.31,1.5);
        addObject(new Computer(),26.5,.71,10.5);
        addObject(new Computer(),3.5,.8,12.5);
        
        addObject(new Computer(3.1415),22.5,.8,16.5);
        addObject(new Papers(),20.5,.8,16.5);
        addObject(new Papers(),22.5,.8,15.5);
        addObject(new PottedPlant(),22.5,.31,12.5);
        addObject(new PottedPlant(),18.5,.31,16.5);
        
        addObject(new Papers(),4.5,.8,12.5);
        addObject(new Papers(),3.5,.8,13.5);
        addObject(new Papers(),27.5,.71,10.5);
    }
    
    //*************************************************************
    //ACT
    //*************************************************************
   
    public void act(){
        Raster.clear();
        
        
        if(!hasStarted){
            isRobot = true;
            hasStarted = true;
            getBackground().setColor(Color.red);
            getBackground().drawString("Press Start to begin playing without Mouse Control",0,40);
        }
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse!=null){
            mx = mouse.getX();
            my = mouse.getY();
            if(mouse.getButton()==3){
                Greenfoot.stop();
            }
        }
        
        if(!calibrated && isRobot){
            try {
                Robot robot = new Robot();
            }catch (AWTException e) {}
            calibrate();
            getBackground().setColor(Color.white);
            getBackground().fill();
            getBackground().setColor(Color.red);
            getBackground().drawString("Click in the center of the screen, and then drag the mouse to the top left",0,40);
            getBackground().drawString("corner of your computer screen, then let go of the mouse to calbrate.",0,60);
            getBackground().drawString("If you do not want Mouse control, pause the scenario, and then resume",0,90);
            return;
        }

        if(isRobot){
            tya -= (300-mx)/400.0;
            txa += (200-my)/400.0;
            if(txa>Math.PI/2-.1){
                txa = Math.PI/2-.1;
            }
            if(txa<-Math.PI/2+.1){
                txa = -Math.PI/2+.1;
            }
            try {
                Robot robot = new Robot();
                robot.mouseMove(300+offX,200+offY);//shift the mouse back to the center of the screen
            }catch (AWTException e) {}
        }else{
            txa = (200-my)/200.0;
        }
        
        String key = Greenfoot.getKey();
        if(skip!=1 && key!=null && key.equals("3")){
            skip--;
        }
        if(skip!=10 && key!=null && key.equals("4")){
            skip++;
        }
        
        ya += (tya-ya)/1.3;
        xa += (txa-xa)/1.3;
        
        
        //map[14][15] = rise;
        //map[14][15] = rise;
        
        //display();
        myMap3D.display();
        
        speed = .04;
        height = 1.1;
        if(Greenfoot.isKeyDown("c")){
            crouching = true;
        }
        if(!Greenfoot.isKeyDown("c") && mapRoof[(int)myx][(int)myz]-.7>myy){
            crouching = false;
        }
        if(crouching){
            speed = .02;
            height = .5;
        }

        
        
        for(int sk=0;sk<skip;sk++){
            calculatePhysics();
        }
        camera.setLocation(myx,myy,myz);
        camera.setRotation(xa,ya);
        
        double b1 = Math.cos(ya);
        double b2 = Math.sin(ya);
        double b3 = Math.cos(xa);
        double b4 = Math.sin(xa);
        //Raster.drawObject(getBackground(),objectsList,myx,myy,myz,b1,b2,b3,b4);
        displayWeapon();
        

        if(!Greenfoot.isKeyDown("space")){
            spaceWasPressed = false;
        }
        if(Greenfoot.isKeyDown("escape"))
            Greenfoot.stop();
        //if(brightness>0)brightness -= .02;
        getBackground().setColor(Color.red);
        getBackground().drawString("Press Esc to stop the scenario",0,55);
        getBackground().drawString("Render Mode: " + renderMode,0,25);
        getBackground().drawString("Frame Skip: " + skip,0,40);
        if(Greenfoot.isKeyDown("1")){
            renderMode = 1;
            myMap3D.setRenderMode(renderMode);
        }
        if(Greenfoot.isKeyDown("2")){
            renderMode = 2;
            myMap3D.setRenderMode(renderMode);
        }
        //if(canSee(objectsList.get(0).getPosition(),myx,myy,myz)){
        //    getBackground().drawString("I See YOU!!",0,70);
        //}
        getBackground().fillRect(600,0,700,700);
    }
    
    //*************************************************************
    //DISPLAY WEAPON PISTOL
    //*************************************************************
    
    public void displayWeapon(){
        /*
        sway += .03;
        if(straf>Math.PI*2){
            straf -= Math.PI*2;
        }
        recoil -= recoil/10;
        if(Greenfoot.mousePressed(null) && reloading<=60){
            if(reloading!=0){
                gun.setLocation(0,0,0);
                reloading = 0;
            }
            recoil = .6;
            straf = 4;
            double[] tempPos;
            if(isRobot){
                tempPos = castRay(myx,myy,myz,xa,ya,.6);
            }else{
                tempPos = castRay(myx,myy,myz,xa+(200-my)/470.0,ya-(300-mx)/350.0,.6);
            }
            Object3D temp3D = new Object3D("Ball");
            while(tempPos[1]-.5 < mapFloor[(int)tempPos[0]][(int)tempPos[2]]){
                tempPos[1] += .1;
            }
            while(tempPos[1]+.5 > mapRoof[(int)tempPos[0]][(int)tempPos[2]]){
                tempPos[1] -= .1;
            }
            
            Object3D temp1 = new Object3D("Ball");
            temp1.setLocation(tempPos[0],tempPos[1],tempPos[2]);
            if(objectsList.size() > 10){
                objectsList.remove(0);
            }
            //objectsList.add(temp1); 
        }
        for(int i=0;i<objectsList.size();i++){
            //objectsList.get(i).rotate(.01,0,0);
        }
        
        if(Greenfoot.isKeyDown("r") && reloading==0){
            reloading = 100;
        }
        if(reloading!=0){
            reloading--;
            if(reloading>75){
                gun.moveTo(.5,.5,.5,.4,-1.5,0,10);
            }
            if(reloading<65){
                if(isRobot){
                    gun.moveTo(0,0,0,0,0,0,8);
                }else{
                    gun.moveTo(0,0,0,(200-my)/470.0,0,(300-mx)/350.0,8);
                }
            }
            if(reloading>20){
                Raster.drawObject(getBackground(),hand,1.35*Math.sin(reloading/15.0),.3*Math.sin(reloading/15.0)+.59,-1.1,1,0,1,0);
            }
            if(reloading>70){
                Raster.drawObject(getBackground(),clip,1.35*Math.sin(reloading/15.0),.3*Math.sin(reloading/15.0)+.52,-1.1,1,0,1,0);
            }
            if(reloading==30){
                reloading = 0;
            }
        }else{
            if(isRobot){
                gun.setRotation(recoil,0,0);
            }else{
                gun.setRotation(recoil + (200-my)/470.0,0,(300-mx)/350.0);
            }
        }
        */
        //Raster.drawObject(getBackground(),gun,-.5+.01*Math.cos(sway),.7+.01*Math.sin(sway*.6)+.04*Math.cos(straf),-.8,1,0,1,0);
    }

    //*************************************************************
    //CALCULATE PHYSICS
    //*************************************************************
    
    public void calculatePhysics(){
        xvel = xvel*.7;
        zvel = zvel*.7;
        
        delta += .06;

        if(isRobot){
            if(Greenfoot.isKeyDown("d")){
                xvel += speed*Math.sin(ya+Math.PI/2);
                zvel += speed*Math.cos(ya+Math.PI/2);
            }
            if(Greenfoot.isKeyDown("a")){
                xvel += speed*Math.sin(ya-Math.PI/2);
                zvel += speed*Math.cos(ya-Math.PI/2);
            }
        }else{
            if(Greenfoot.isKeyDown("d")){
                tya += .05;
            }
            if(Greenfoot.isKeyDown("a")){
                tya -= .05;
            }
        }
      
        if(Greenfoot.isKeyDown("w")){
            xvel += speed*Math.sin(ya);
            zvel += speed*Math.cos(ya);
        }
        if(Greenfoot.isKeyDown("s")){
            xvel += -speed*Math.sin(ya);
            zvel += -speed*Math.cos(ya);
        }
        
        if(falling){
            yvel -= .02;
            if(mapFloor[(int)myx][(int)myz]+height > myy  && yvel < 0){
                falling = false;
            }
            if(mapRoof[(int)myx][(int)myz]-.2 < myy && yvel>0){
                yvel = -.1*yvel;
                myy -= .1;
            }
        }
        
        myy += .2*yvel;
        myx += xvel;
        myz += zvel;
        
        straf += 6*(xvel*xvel + zvel*zvel);
        if(6*(xvel*xvel + zvel*zvel) < .01 && !(straf<.1 || straf>6.1)){
            if(straf > Math.PI){
                straf += .08;
            }else{
                straf -= .08;
            }
        }
        
        if(hit(.2,0)){
            myx -= xvel;
        }
        if(hit(-.2,0)){
            myx -= xvel;
        }
        if(hit(0,.2)){
            myz -= zvel;
        }
        if(hit(0,-.2)){
            myz -= zvel;
        }
            
        if(hit(.2,0)){
            xvel -= .2;
        }
        if(hit(-.2,0)){
            xvel += .2;
        }
        if(hit(0,.2)){
            zvel -= .2;
        }
        if(hit(0,-.2)){
            zvel += .2;
        }
            
        double diff1 = mapFloor[(int)(myx+.2)][(int)myz]+height  - myy;
        double diff2 = mapFloor[(int)(myx-.2)][(int)myz]+height  - myy;
        double diff3 = mapFloor[(int)myx][(int)(myz+.2)]+height  - myy;
        double diff4 = mapFloor[(int)myx][(int)(myz-.2)]+height  - myy;
            
        double diff = mapFloor[(int)myx][(int)myz]+height  - myy;
        
        if(diff1<-.5 && diff2<-.5 && diff3<-.5 && diff4<-.5){
            yvel -= .04;
            falling = true;
        }
        if(diff>-.5 && !falling){
            yvel = diff;
        }
        
        if(!crouching && Greenfoot.isKeyDown("space") && !falling && !spaceWasPressed){
            falling = true;
            yvel = .7;
            xvel *= 1.6;
            zvel *= 1.6;
            //myy = map[(int)myx][(int)myz]+1;
            spaceWasPressed = true;
        } 
    }
    
    //*************************************************************
    //CALIBRATE
    //*************************************************************
    
    public void calibrate(){
        if(Greenfoot.mouseDragEnded(null)){
            offX=-mx;
            offY=-my;
            calibrated = true;
        }
        my = 200;
    }
    
    public boolean hit(double a, double b){
        return mapFloor[(int)(myx+a)][(int)(myz+b)]+height  - myy > .5   || mapRoof[(int)(myx+a)][(int)(myz+b)]-.2  < myy;   
    }
    
    public boolean hit(double a, double b,double c){
        return mapFloor[(int)a][(int)c] > b  || mapRoof[(int)a][(int)c] < b;   
    }
    
    public boolean hit(double x, double y,double z,double size){
        return hit(x+size,y+size,z+size) || hit(x+size,y+size,z-size) || hit(x+size,y-size,z+size) || hit(x+size,y-size,z-size) || hit(x-size,y+size,z+size) || hit(x-size,y+size,z-size) || hit(x-size,y-size,z+size) || hit(x-size,y-size,z-size);
    }
    
    public double[] castRay(double x,double y,double z,double dx,double dy,double dz,double size){
        int counter = 0;
        while(!hit(x,y,z)){
            x += dx/10;
            y += dy/10;
            z += dz/10;
            counter++;
        }
        while(hit(x+size,y+size,z+size) || hit(x+size,y+size,z-size) || hit(x+size,y-size,z+size) || hit(x+size,y-size,z-size) || hit(x-size,y+size,z+size) || hit(x-size,y+size,z-size) || hit(x-size,y-size,z+size) || hit(x-size,y-size,z-size)){
            x -= dx/10;
            y -= dy/10;
            z -= dz/10;
            counter--;
            if(counter==-1){
                break;
            }
        }
        double[] ret = {x,y,z};
        return ret;
    }
    
    
    public double[] castRay(double x,double y,double z,double angX,double angY,double size){
        double dy = Math.sin(angX);
        double dx = Math.sin(angY)*Math.cos(angX);
        double dz = Math.cos(angY)*Math.cos(angX);
        return castRay(x,y,z,dx,dy,dz,size);
    }
    
    public boolean canSee(double x1,double y1,double z1,double x2,double y2,double z2){
        int counter = 0;
        double dx = x2-x1;
        double dy = y2-y1;
        double dz = z2-z1;
        double mag = Math.sqrt(dx*dx + dy*dy + dz*dz)*2;
        dx /= mag;
        dy /= mag;
        dz /= mag;
  
        while(!hit(x1,y1,z1)){
            x1 += dx;
            y1 += dy;
            z1 += dz;
            counter++;
            if(counter>mag){
                return true;
            }
        }
        return false;
    }
    
    public boolean canSee(Object3D o1,Object3D o2){
        return canSee(o1.getX(),o1.getY(),o1.getZ(),o2.getX(),o2.getY(),o2.getZ());
    }
    
    public boolean canSee(Object3D o1,double x,double y,double z){
        return canSee(o1.getX(),o1.getY(),o1.getZ(),x,y,z);
    }
    
    public boolean canSee(Object3D o1){
        return canSee(o1.getX(),o1.getY(),o1.getZ(),myx,myy,myz);
    }
    
    public boolean canSee(double x,double y,double z){
        return canSee(x,y,z,myz,myy,myz);
    }
    
    public boolean canSee(double[] pos,double x,double y,double z){
        return canSee(x,y,z,pos[0],pos[1],pos[2]);
    }
    
    public boolean canSee(double[] pos){
        return canSee(myz,myy,myz,pos[0],pos[1],pos[2]);
    }
    
    public boolean canSee(double[] pos1,double[] pos2){
        return canSee(pos1[0],pos1[1],pos1[2],pos2[0],pos2[1],pos2[2]);
    }
    
    public void stopped(){
        isRobot = false;
    }
    
    public void addObject(GameObject a,double x,double y,double z){
        addObject(a,0,0);
        a.setLocation(x,y,z);
        objectsList.add(a);
    }
    
    public void removeObject(Actor a){
        objectsList.remove((GameObject)a);
        super.removeObject(a);
    }
    
    public boolean isNear(GameObject g){
        return isNear(g,1);
    }
    
    public boolean isNear(GameObject g,double radius){
        double r = Math.pow(g.getPosition()[0]-myx,2) + Math.pow(g.getPosition()[1]-myy,2) + Math.pow(g.getPosition()[2]-myz,2);
        return r<radius;
    }
    
    
}
