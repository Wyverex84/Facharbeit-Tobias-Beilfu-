import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.List;




public class raster extends World
{
    //RASTER DATA
    Object3D[] stack = new Object3D[4000];   
    int top=0,next;                   
    double[] dist = new double[8024];  
    double[] lit = new double[8024];
    int[][][] points = new int[8024][4][4];
    Color[][] colors = new Color[8024][2];
    int[] counter = new int[8024];
    boolean fastFramerate = true;
    
    //CAMERA DATA
    double camera=0,fader;
    double x=0,y=0,z=-9,ya=0,xa=0;
    int render=3;
    boolean quality=false,blur=true;
    double temp1,temp2,temp3,temp4;
    Color alpha = new Color(0,0,0,128);
    
    //LIGHT DATA
    double lx=0,ly=0,lz=0;
    double brightness=1;
    
    //STARS DATA
    int[] xStars = new int[90];
    int[] yStars = new int[90];
    boolean stars=false;
    
    //COLOR DATA
    Color bGray = new Color(180,180,180), dGray = new Color(90,90,90),bdGray = new Color(30,30,30);
    
    //STATION-SMALL DATA
    double[] s1 = {0,.707,1,.707,0,-.707,-1,-.707,0,.707,1,.707,0,-.707,-1,-.707,0,0};
    double[] s2 = {-.3,-.3,-.3,-.3,-.3,-.3,-.3,-.3,.3,.3,.3,.3,.3,.3,.3,.3,-.6,.6};
    double[] s3 = {1,.707,0,-.707,-1,-.707,0,.707,1,.707,0,-.707,-1,-.707,0,.707,0,0};
    int[][] s4 = {{1,0,8,9},
                  {2,1,9,10},
                  {3,2,10,11},
                  {4,3,11,12},
                  {5,4,12,13},
                  {6,5,13,14},
                  {7,6,14,15},
                  {0,7,15,8},
                  {0,1,16},
                  {1,2,16},
                  {2,3,16},
                  {3,4,16},
                  {4,5,16},
                  {5,6,16},
                  {6,7,16},
                  {7,0,16},
                  {8,9,17},
                  {9,10,17},
                  {10,11,17},
                  {11,12,17},
                  {12,13,17},
                  {13,14,17},
                  {14,15,17},
                  {15,8,17}};
    Color dRed = new Color(120,0,0);
    Color[][] s5 = {{dGray,dRed},
                    {bdGray,dRed},
                    {dGray,dRed},
                    {bdGray,dRed},
                    {dGray,dRed},
                    {bdGray,dRed},
                    {dGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed},
                    {bdGray,dRed}};
    Object3D StationSmall = new Object3D(s1,s2,s3,s4,s5,true,8,false);
    
    //TURRET DATA
    double[] tu1 = {0,.707,1,.707,0,-.707,-1,-.707,0,.707,1,.707,0,-.707,-1,-.707,0,0};
    double[] tu2 = {-.3,-.3,-.3,-.3,-.3,-.3,-.3,-.3,.3,.3,.3,.3,.3,.3,.3,.3,-5,2};
    double[] tu3 = {1,.707,0,-.707,-1,-.707,0,.707,1,.707,0,-.707,-1,-.707,0,.707,0,0};
    int[][] tu4 = {{8,9,17},
                  {9,10,17},
                  {10,11,17},
                  {11,12,17},
                  {12,13,17},
                  {13,14,17},
                  {14,15,17},
                  {15,8,17},
                  
                  {1,0,8,9},
                  {2,1,9,10},
                  {3,2,10,11},
                  {4,3,11,12},
                  {5,4,12,13},
                  {6,5,13,14},
                  {7,6,14,15},
                  {0,7,15,8},
                  
                  {0,1,16},
                  {1,2,16},
                  {2,3,16},
                  {3,4,16},
                  {4,5,16},
                  {5,6,16},
                  {6,7,16},
                  {7,0,16}};
    Color[][] tu5 = {{bdGray,Color.red},
                    {bdGray,Color.red},
                    {bdGray,Color.red},
                    {bdGray,Color.red},
                    {bdGray,Color.red},
                    {bdGray,Color.red},
                    {bdGray,Color.red},
                    {bdGray,Color.red},
        
                    {dGray,bdGray},
                    {Color.red,bdGray},
                    {dGray,bdGray},
                    {Color.red,bdGray},
                    {dGray,bdGray},
                    {Color.red,bdGray},
                    {dGray,bdGray},
                    {Color.red,bdGray},
                    
                    {dGray,bdGray},
                    {dGray,bdGray},
                    {dGray,bdGray},
                    {dGray,bdGray},
                    {dGray,bdGray},
                    {dGray,bdGray},
                    {dGray,bdGray},
                    {dGray,bdGray}};
    Object3D Turret = new Object3D(tu1,tu2,tu3,tu4,tu5,true,8,false);
    
    
    //XWING DATA
    double[] x1 = {-.1,.1,-.4,.4,-.4,.4,-.4,.4,-.1,.1,-.4,.4,-.4,.4,0,0,-.4,.4,-3,3,-3,3,-3,3,-3,3,.6,.4,.4,.6,.6,.4,.4,.6,-.6,-.4,-.4,-.6,-.6,-.4,-.4,-.6,.6,.4,.4,.6,.6,.4,.4,.6,-.6,-.4,-.4,-.6,-.6,-.4,-.4,-.6};    //X coordinates of all points
    double[] x2 = {0,0,0,0,-.3,-.3,-.3,-.3,.1,.1,.2,.2,.2,.2,0,0,.2,.2,-.8,-.8,-.8,-.8,.7,.7,.7,.7,-.6,-.6,-.4,-.4,-.6,-.6,-.4,-.4,-.6,-.6,-.4,-.4,-.6,-.6,-.4,-.4,0,0,.2,.2,0,0,.2,.2,0,0,.2,.2,0,0,.2,.2};    //Y coordinates of all points
    double[] x3 = {2,2,0,0,-.3,-.3,-.6,-.6,2,2,0,0,-.6,-.6,0,0,-.3,-.3,-.3,-.3,-.6,-.6,-.3,-.3,-.6,-.6,-.4,-.4,-.4,-.4,-1.2,-1.2,-1.2,-1.2,-.4,-.4,-.4,-.4,-1.2,-1.2,-1.2,-1.2,-.4,-.4,-.4,-.4,-1.2,-1.2,-1.2,-1.2,-.4,-.4,-.4,-.4,-1.2,-1.2,-1.2,-1.2};    //Z coordinates of all points
    int[][] x4 = {{0,1,3,2},   
                  {0,8,10,2},
                  {9,1,3,11},
                  //{3,2,4,5},
                  {5,4,6,7},
                  {11,10,8,9},
                  {13,12,10,11},
                  {7,6,12,13},
                  {12,6,4,2},
                  {12,2,10},
                  {13,11,3},
                  {13,3,5,7},
                  {4,6,20,18},
                  {16,12,24,22},
                  {19,21,7,5},
                  {23,25,13,17},
                  {26,27,31,30},
                  {27,28,32,31},
                  {28,29,33,32},
                  {29,26,30,33}, 
                  {34,35,39,38},
                  {35,36,40,39},
                  {36,37,41,40},
                  {37,34,38,41},  
                  {42,43,47,46},
                  {43,44,48,47},
                  {44,45,49,48},
                  {45,42,46,49},
                  {50,51,55,54},
                  {51,52,56,55},
                  {52,53,57,56},
                  {53,50,54,57}};
    Color[][] x5={{dGray,dGray}, 
                  {dGray,dGray}, 
                  {dGray,dGray}, 
                  {bGray,Color.white}, 
                  {dGray,dGray}, 
                  {bGray,Color.white}, 
                  {bGray,Color.white}, 
                  {Color.black,Color.black},
                  {Color.white,Color.white},
                  {Color.white,Color.white},
                  {Color.black,Color.black},
                  {bGray,dGray}, 
                  {bGray,dGray}, 
                  {bGray,dGray}, 
                  {bGray,dGray}, 
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray}, 
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray},
                  {dGray,dGray}};           
    Object3D Xwing = new Object3D(x1,x2,x3,x4,x5,false,1,true);
    
    
    //TIE FIGHTER DATA
    double[] t1 = {0,0,.4,.4,0,-.4,-.4,0,1.2,-1.2,1,1,1,1,1,1,-1,-1,-1,-1,-1,-1};
    double[] t2 = {0,.6,.2,-.2,-.6,-.2,.2,0,0,0,.9,.9,0,-.9,-.9,0,.9,.9,0,-.9,-.9,0};
    double[] t3 = {.4,0,0,0,0,0,0,-.4,0,0,.5,-.5,-1,-.5,.5,1,.5,-.5,-1,-.5,.5,1};
    int[][] t4 = {{0,1,2},
                  {0,2,3},
                  {0,4,3},
                  {0,4,5},
                  {0,5,6},
                  {0,6,1},
                  
                  {7,1,2},
                  {7,2,3},
                  {7,4,3},
                  {7,4,5},
                  {7,5,6},
                  {7,6,1},
                  
                  {2,3,8},
                  {9,6,5},
                  
                  {8,10,11},
                  {8,11,12},
                  {8,12,13},
                  {8,13,14},
                  {8,14,15},
                  {8,15,10},
                  
                  {9,16,17},
                  {9,17,18},
                  {9,18,19},
                  {9,19,20},
                  {9,20,21},
                  {9,21,16}};
    Color[][] t5 = {{bGray,dGray},
                    {bGray,dGray},
                    {bGray,dGray},
                    {bGray,dGray},
                    {bGray,dGray},
                    {bGray,dGray},
                        
                    {bGray,dGray},
                    {bGray,dGray},
                    {bGray,dGray},
                    {bGray,dGray},
                    {bGray,dGray},
                    {bGray,dGray},
                    
                    {dGray,dGray},
                    {dGray,dGray},
                    
                    {Color.black,dGray},
                    {Color.black,dGray},
                    {Color.black,dGray},
                    {Color.black,dGray},
                    {Color.black,dGray},
                    {Color.black,dGray},
                    
                    {Color.black,dGray},
                    {Color.black,dGray},
                    {Color.black,dGray},
                    {Color.black,dGray},
                    {Color.black,dGray},
                    {Color.black,dGray}};
    Object3D TieFighter = new Object3D(t1,t2,t3,t4,t5,false,12,false);
    List fighters;
    
    
    //ASTEROID DATA
    double[] a1 = {0,1,1,-1,-1,1.4,0,-1.4,0,0};
    double[] a2 = {1.2,.6,.6,.6,.6,-.6,-.6,-.6,-.6,-1.2};
    double[] a3 = {0,-1,1,1,-1,0,1.4,0,-1.4,0};
    int[][] a4 = {{0,1,2},
                  {7,8,4},
                  {9,5,8},
                  {1,2,5},

                  {0,2,3},
                  {0,3,4},
                  {0,4,1},
                  {9,8,7},
                  {9,7,6},
                  {9,6,5},
                  {1,4,8},
                  {4,3,7},
                  {3,2,6},
                  {8,5,1},
                  {5,6,2},
                  {6,7,3}};
    Color[][] a5 = {{dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray},
                    {dGray,bGray}};
    Object3D Asteroid = new Object3D(a1,a2,a3,a4,a5,true,4,false);
    List asteroids;
    
    //RUBBLE DATA
    double[] r1 = {.5,.5,-.5,-.5};
    double[] r2 = {.5,-.5,-.5,.5};
    double[] r3 = {0,.1,0,-.1};
    int[][] r4 = {{0,1,2,3}};
    Color[][] r5 = {{Color.blue,Color.blue}};
    Object3D Rubble = new Object3D(r1,r2,r3,r4,r5,false,0,false);
    
    //BULLET DATA
    double[] b1 = {-.1,.1,0,0,0,0};
    double[] b2 = {0,0,0,.1,-.1,0};
    double[] b3 = {0,0,3,0,0,3};
    int[][] b4 = {{0,1,2},
                  {3,4,5}};
    Color[][] b5 = {{Color.yellow,Color.yellow},
                    {Color.yellow,Color.yellow}};
    Object3D Bullet = new Object3D(b1,b2,b3,b4,b5,false,1,false);
    
    //CROSSHAIR DATA
    double[] c1 = {0,.1,-.1,  .4,.8,.8,  0,.1,-.1,    -.4,-.8,-.8};
    double[] c2 = {.4,.8,.8,  0,.1,-.1,  -.4,-.8,-.8,  0,.1,-.1};
    double[] c3 = {0,0,0,0,0,0,0,0,0,0,0,0};
    int[][] c4 = {{0,1,2},
                  {3,4,5},
                  {6,7,8},
                  {9,10,11}};
    Color[][] c5 = {{Color.red,Color.red},
                    {Color.red,Color.red},
                    {Color.red,Color.red},
                    {Color.red,Color.red}};
    Object3D Crosshair = new Object3D(c1,c2,c3,c4,c5,false,1,true);
    
    
    Xwing fighter = new Xwing(0,0,0);

    public raster()
    {  
        super(400,400,1);
        addObject(new FPS(),54,8);
        addObject(fighter,0,0);
        addObject(new radar(),340,30);
        
        double[][] path = {{5000,0,0,5000},
                           {-20,-20,-20,-20},
                           {5000,5000,0,0}};
        for(int i=0;i<5;i++){
            addObject(new TieFighter(5000,i*10-20,5000,path,5),0,0);
            for(int j=0;j<4;j++){
                path[1][j] += 10;
            }
        }
        for(int i=0;i<500;i++){
            addObject(new asteroid(Greenfoot.getRandomNumber(5000),Greenfoot.getRandomNumber(100)-50,Greenfoot.getRandomNumber(5000)),0,0);
            //addObject(new asteroid(500,0,500),0,0);;
        }
        addObject(new StationSmall(2500,0,2500),0,0);
        addObject(new Turret(2600,0,2500),0,0);
        addObject(new Turret(2500,0,2600),0,0);
        addObject(new Turret(2400,0,2500),0,0);
        addObject(new Turret(2500,0,2400),0,0);
        addObject(new Turret(2500,-50,2500),0,0);
        loadStars();
        setActOrder(speedMeter.class,asteroid.class,Xwing.class,crosshair.class);
    }
   
    public void act(){

        display();
        if(Greenfoot.isKeyDown("escape"))
            Greenfoot.stop();
        asteroids = getObjects(asteroid.class);
        fighters = getObjects(Enemy.class);
        //if(brightness>0)brightness -= .02;
        
        Greenfoot.setSpeed(50);
        if(!fastFramerate){
            Greenfoot.setSpeed(45);
            List objects  = getObjects(Actor.class);
            Actor ac;
            for(int i=0;i<objects.size();i++){
                ac = (Actor) objects.get(i);
                ac.act();
            }
        }
    }
    
    public void display() 
    {
        getBackground().setColor(Color.black);
        if(blur)
            getBackground().setColor(alpha);
        getBackground().fillRect(0,0,400,400);
        if(stars)displayStars();
        String str = Greenfoot.getKey();
        if(str!=null && str.equals("s"))stars=!stars;
        if(str!=null && str.equals("q"))quality=!quality;
        if(str!=null && str.equals("m"))blur=!blur;
        if(str!=null && str.equals("f"))fastFramerate=!fastFramerate;
        
        ya = ya + (-fighter.ya-ya)/2.0;
        xa = .6*fighter.xa;
       
        x = fighter.x+9*Math.sin(fighter.ya)*Math.cos(fighter.xa+.1);
        y = fighter.y-9*Math.sin(fighter.xa+.1);
        z = fighter.z-9*Math.cos(fighter.ya)*Math.cos(fighter.xa+.1);
        
        lx = x+80*fighter.vx;
        ly = y+80*fighter.vy;
        lz = z+80*fighter.vz;
        
        temp1 = Math.cos(ya);
        temp2 = Math.sin(ya);
        temp3 = Math.cos(xa);
        temp4 = Math.sin(xa);
        
        int dim=0;       
        Object3D now;
        for(int s=0;s<top;s++){         
            now = stack[s];     
            now.getPoints(x,y,z,temp1,temp2,temp3,temp4);
            for(int i=0;i<now.numPoly();i++){
                points[dim] = now.getPolygon(i);
                if(now.renderable(i)){       
                    lit[dim] = now.lit(lx,ly,lz);
                    dist[dim] = now.distance(i);
                    colors[dim][0] = now.getColor(i)[0];
                    colors[dim][1] = now.getColor(i)[1];
                    counter[dim] = dim;
                    dim++;
                }
            }
        }
        
        
        double gap=dim;
        int swaps=1;
        while(swaps>0 || gap>1){
            if(gap>1){
                gap=gap/1.3;
                if(gap==9 || gap==10)
                    gap=11;
            }
            swaps=0;
            int i=0,sa;
            double s;
            while(i+gap+1<dim){
                if(dist[i] < dist[i+(int)(gap+1)]){
                    swaps++;
                    s = dist[i];
                    dist[i] = dist[i+(int)(gap+1)];
                    dist[i+(int)(gap+1)] = s;
                    
                    sa = counter[i];
                    counter[i] = counter[i+(int)(gap+1)];
                    counter[i+(int)(gap+1)] = sa;
                    
                    s = lit[i];
                    lit[i] = lit[i+(int)(gap+1)];
                    lit[i+(int)(gap+1)] = s;
                }
                i++;
            }
        }
        
        
       
        
        if(render==0){
            for(int f = 0;f<dim;f++){
                next = counter[f];
                getBackground().setColor(colors[next][1]);
                getBackground().drawPolygon(points[next][0],points[next][1],points[next][0].length);
            }
        }
        if(render==1){
            for(int f = 0;f<dim;f++){
                next = counter[f];
                getBackground().setColor(colors[next][0]);
                getBackground().fillPolygon(points[next][0],points[next][1],points[next][0].length);
            }
        }
        if(render==2){
            for(int f = 0;f<dim;f++){
                next = counter[f];
                getBackground().setColor(colors[next][0]);
                getBackground().fillPolygon(points[next][0],points[next][1],points[next][0].length);
                getBackground().setColor(colors[next][1]);
                getBackground().drawPolygon(points[next][0],points[next][1],points[next][0].length);
            }
        }
        int r,g,b;
        if(render==3){
            for(int f = 0;f<dim;f++){
                next = counter[f];
                fader = 1 - dist[f]/10000000.0;
                if(fader<0)fader=0;
                
                r = (int)(colors[next][0].getRed());
                g = (int)(colors[next][0].getGreen());
                b = (int)(colors[next][0].getBlue());
                
                r = (int)(fader*(r+brightness*lit[f]*(254-r)));
                g = (int)(fader*(g+brightness*lit[f]*(254-g)));
                b = (int)(fader*(b+brightness*lit[f]*(254-b)));
                
                getBackground().setColor(new Color(r,g,b));
                getBackground().fillPolygon(points[next][0],points[next][1],points[next][0].length);
                
                r = (int)(colors[next][1].getRed());
                g = (int)(colors[next][1].getGreen());
                b = (int)(colors[next][1].getBlue());
                
                r = (int)(fader*(r+brightness*lit[f]*(254-r)));
                g = (int)(fader*(g+brightness*lit[f]*(254-g)));
                b = (int)(fader*(b+brightness*lit[f]*(254-b)));
                
                getBackground().setColor(new Color(r,g,b));
                getBackground().drawPolygon(points[next][0],points[next][1],points[next][0].length);
            }
        }
        getBackground().setColor(Color.red);
        getBackground().drawString("Polygons:"+dim,3,32);
        getBackground().drawString("Quality:"+render,3,50);
        getBackground().drawString("Quality Control: "+ ((quality) ? "on" : "off"),3,68);
        getBackground().drawString("Motion Blur: "+ ((blur) ? "on" : "off"),3,86);
        getBackground().drawString("Framerate: "+ ((fastFramerate) ? "Fast" : "Slow"),3,104);
        
        if(Greenfoot.isKeyDown("1"))
            render=0;
        if(Greenfoot.isKeyDown("2"))
            render=1;
        if(Greenfoot.isKeyDown("3"))
            render=2;
        if(Greenfoot.isKeyDown("4"))
            render=3; 
    }
         
 
    public void addToRaster(Object3D s){  
        stack[top] = s;                
        top++;                          
        //System.out.println(s + " added to slot " + top);
    }
    
    public void removeFromRaster(Object s){ 
        int i=0;
        //System.out.println("Ship " + s + " removed from raster");
        for(int f=0;f<top;f++){
            if(stack[f]!=s){
                stack[i] = stack[f];
                i++;
            }  
        }
        top--;
    }
    
    public Object3D newXwing(){
        return Xwing.duplicate();
    }
    
    public Object3D newAsteroid(){
        return Asteroid.duplicate();
    }
    
    public Object3D newBullet(){
        return Bullet.duplicate();
    }
    
    public Object3D newRubble(){
        return Rubble.duplicate();
    }
    
    public Object3D newTieFighter(){
        return TieFighter.duplicate();
    }
    
    public Object3D newCrosshair(){
        return Crosshair.duplicate();
    }
    
    public Object3D newStationSmall(){
        return StationSmall.duplicate();
    }
    
    public Object3D newTurret(){
        return Turret.duplicate();
    }
    
    public List getAsteroids(){
        return asteroids;
    }
    
    public List getFighters(){
        return fighters;
    }
     

    public void loadStars(){
        for(int i=0;i<xStars.length;i++){
            xStars[i] = Greenfoot.getRandomNumber(2199);
            yStars[i] = Greenfoot.getRandomNumber(800)-200;
        }
    }
    
    public void displayStars(){
        getBackground().setColor(Color.white);
        double yas;
        for(int i=0;i<xStars.length;i++){
            yas = (xStars[i]-(int)(350*ya));
            while(yas<0)yas=yas+2119;
            while(yas>2119)yas=yas-2119;
            getBackground().drawLine((int)yas,yStars[i]-(int)(350*xa),(int)yas,yStars[i]-(int)(350*xa));
        }
    }
    
    public void lowerQuality(){
        if(render>0 && quality)render--;
    }
    
    public void explode(double a,double b, double c){
        //if(brightness>.8)return;
        //lx = a;
        //ly = b;
        //lz = c;
        //brightness = 1;
    }
    
}
