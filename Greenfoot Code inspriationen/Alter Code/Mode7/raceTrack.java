import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class raceTrack extends Actor
{
    GreenfootImage screen = new GreenfootImage(300,300);
    GreenfootImage floor;// = new GreenfootImage("m4.gif");
    static GreenfootImage mask;// = new GreenfootImage("r1.gif");
    GreenfootImage buffer = new GreenfootImage(300,150);
    double[] controlA = {.97,.92,.89,.8,.7,.5},
             speedA   = {3,3.5,4,4.5,5,5.5},
             powerA   = {1.009,1.007,1.0001,1,.99,.97};
    
    needle n = new needle();
    needle n1 = new needle();
    wheel w = new wheel();
    driver cr = new driver();
    box bx1 = new box(1);
    box bx2 = new box(2);
    
    boolean b1,b2,auto=false,flip;
    static double quality = 1/50.0;
    double x,y,a=1,b=0,tan,j,i;
    double t1,t2,t3,t4,na;//test points
    double X=55.5,Y=700;
    double mx=0,my=0,dx=0,dy=0,v=0,va=0,da=-Math.PI/2.0,vn,vt,vda;
    int backAfter=0;
    
    Color me;
    
    double[] render = new double[50];
    Color color;
    int it,width=20,crash=0,comp=1,lap=0,skid=0;
    int ecomp=1,elap=0;
    int counter=230;
    
    double dist,ex=404,ey=159,enang;
    double emx=0,emy=0,edx=0,edy=0,ev=0,eva=0,eda=-Math.PI/2.0,evn,evt,evda;
    
    double max=5.5,min=1,accel=.99,handle=.2; // Car constants
    double add,minConst,maxConst;
    
    double emax=4,emin=1,eaccel=.99,ehandle=.8; // Car constants
    double eadd,eminConst,emaxConst;
    
    public raceTrack(){
        //System.out.println(((worldness)getWorld()).map);
        int[] Xar = {151, 151, 55, 1, 306};
        int[] Yar = {618, 618, 700, 1, 338};
        int[] Xbr = {104, 104, 115, 1, 227};
        int[] Ybr = {600, 600, 709, 1, 324};
        double[] Abr = {-1.5,-1.5,-1.5,-1.5,-1.18};
        X = Xar[((worldness)getWorld()).map-1];
        Y = Yar[((worldness)getWorld()).map-1];
        ex = Xbr[((worldness)getWorld()).map-1];
        ey = Ybr[((worldness)getWorld()).map-1];
        da = Abr[((worldness)getWorld()).map-1];
        eda = da;
        
        handle = controlA[((worldness)getWorld()).control];
        accel = powerA[((worldness)getWorld()).power];
        max = speedA[((worldness)getWorld()).speed];  
        
        maxConst = accel;
        add = (max - (.99*accel*max))/(.99*accel);
        accel = .93;
        minConst = 1.0/((.99*add)/min + .99);     
        
        emaxConst = eaccel;
        eadd = (emax - (.99*eaccel*emax))/(.99*eaccel);
        eaccel = .93;
        eminConst = 1.0/((.99*eadd)/emin + .99);
               
        setImage(screen);
        for(int f=128;f<178;f++){
            render[f-128] = Math.tan(Math.PI*(.5*f/180.0));
        }  
    }
    
    public void addedToWorld(World world){
        getWorld().addObject(n,63,267);
        getWorld().addObject(n1,125,267);
        getWorld().addObject(w,96,299);
        getWorld().addObject(cr,150,249);
        mask = ((worldness)getWorld()).getRoad(((worldness)getWorld()).map);
        floor = ((worldness)getWorld()).getMap(((worldness)getWorld()).map);
        world.addObject(bx1,130,26);
        world.addObject(bx2,170,26);
    }

 
    public void act() 
    {
        
        if(Greenfoot.isKeyDown("1"))quality=1/75.0;
        if(Greenfoot.isKeyDown("2"))quality=1/50.0;
        if(Greenfoot.isKeyDown("3"))quality=1/30.0;
        if(Greenfoot.isKeyDown("4"))quality=1/20.0;
        screen.clear(); 
        display();
        
        n.set(v); 
        n1.set2(v);
        
        if(counter>0){
            counter--;
            light();
            return;
        }
        
        physics();
        lapCounter();
        displayEnemy();       
        if(auto)backAfter++;
        if(backAfter==200){
            getWorld().addObject(new mapSelect(),150,150);
            getWorld().removeObject(n);
            getWorld().removeObject(n1);
            getWorld().removeObject(w);
            getWorld().removeObject(cr);
            getWorld().removeObject(bx1);
            getWorld().removeObject(bx2);
            getWorld().removeObject(this);
        }
    }    
    
//***************************************************************************************************************************************************    
//***************************************************************************************************************************************************  
  
    public boolean ok(int x2,int y2){
        return x2<1000 && x2>0 && y2<1000 && y2>0 && (!mask.getColorAt(x2,y2).equals(Color.black) && !mask.getColorAt(x2,y2).equals(Color.red));
    }
    
    public boolean ok2(int x2,int y2){
        return x2<1000 && x2>0 && y2<1000 && y2>0 && (!mask.getColorAt(x2,y2).equals(Color.black) && !mask.getColorAt(x2,y2).equals(Color.cyan) && !mask.getColorAt(x2,y2).equals(Color.red));
    }
    
//***************************************************************************************************************************************************    
//***************************************************************************************************************************************************
    
    public void light(){
        if(counter%75 == 0)
            getWorld().addObject(new message(3-counter/75),150,75);
        if(counter==225 || counter==150 || counter==75)
            Greenfoot.playSound("red.wav");
        if(counter==0)
            Greenfoot.playSound("green.wav");
    }
    
//***************************************************************************************************************************************************    
//***************************************************************************************************************************************************  
  
    public void display(){
        a=Math.cos(da);
        b=Math.sin(da);
        j=260;
        for(int f=128;f<178;f++){
            tan = render[f-128];
            x = a*tan -tan*b;
            y = b*tan + tan*a;
            t1=(int)(x*5+X);
            t2=(int)(y*5+Y);
            t3=(int)((x+2*tan*b)*5 + X);
            t4=(int)((y-2*tan*a)*5 + Y);
            i=300-150*quality;
            for(double g=-tan;g<tan;g=g+quality*tan){
                t1 = (int)(5*x+X);
                t2 = (int)(5*y+Y);
                if(t1>0 && t2>0 && t1<1000 && t2<1000){
                    screen.setColor(floor.getColorAt((int)(5*x+X),(int)(5*y+Y)));
                    screen.fillRect((int)i,(int)j-10,10,2);
                }
                x=x+quality*tan*b;
                y=y-quality*tan*a;
                i=i-quality*150;
            }
            j=j-2;
        }    
    }
    
//***************************************************************************************************************************************************    
//***************************************************************************************************************************************************    
    
    public void physics(){
        //b1 = ok2((int)(X+90*a-width*b),(int)(Y+width*a+90*b));
        //b2 = ok2((int)(X+90*a+width*b),(int)(Y-width*a+90*b));
        //dist=90;
        //while(!(b1||b2) && dist>0){
        //    b1 = ok2((int)(X+dist*a-width*b),(int)(Y+width*a+dist*b));
        //    b2 = ok2((int)(X+dist*a+width*b),(int)(Y-width*a+dist*b));
        //    dist=dist-10;
        //}
        if(Greenfoot.isKeyDown("right"))// || (b1 && (Greenfoot.isKeyDown("a")||auto)))
            vda=vda+.01;
        if(Greenfoot.isKeyDown("left"))// || (b2 && (Greenfoot.isKeyDown("a")||auto)))
            vda=vda-.01;
        w.set(vda);
        vda=vda*.85;
        da=da+vda;
        vn = v*Math.sin(va-da);
        vt = v*Math.cos(va-da);
        if(skid>0)skid--;
        if(Math.abs(vn)>.75){
            if(skid==0)Greenfoot.playSound("skid.wav");
            skid=50;
        }
        vn = vn*handle;
        
        if(Greenfoot.isKeyDown("space")){    // || ((ok2((int)(X+70*a),(int)(Y+70*b)) ||  ok2((int)X,(int)Y))&& (Greenfoot.isKeyDown("a")||auto) && dist>40)){
            vt = vt+add;
            vt = maxConst*vt;
        }
        if(Greenfoot.isKeyDown("down"))     // || ((!ok2((int)(X+20*a),(int)(Y+20*b)) || dist<70) && (Greenfoot.isKeyDown("a")||auto)))
            vt = vt*minConst;
        if(ok((int)X,(int)Y))
            vt = .99*vt;
        else
            vt = minConst*vt;
            
        v = Math.hypot(vn,vt);
        va = Math.atan2(vn,vt)+da;
        mx = v*Math.cos(va);
        my = v*Math.sin(va);
        X=X+mx;
        Y=Y+my;
        me = mask.getColorAt((int)X,(int)Y);
        if(me.equals(Color.red)){
            X=X-mx;
            Y=Y-my;
            mx=0;
            my=0;
            v=0;
        }
        if(da>3.14159)da=da-6.2831853;
        if(da<-3.14159)da=da+6.2831853;

        
        
        a=Math.cos(eda);
        b=Math.sin(eda);
        b1 = ok2((int)(ex+70*a-width*b),(int)(ey+width*a+70*b));
        b2 = ok2((int)(ex+70*a+width*b),(int)(ey-width*a+70*b));
        dist=70;
        while(!(b1||b2) && dist>0){
            b1 = ok2((int)(ex+dist*a-width*b),(int)(ey+width*a+dist*b));
            b2 = ok2((int)(ex+dist*a+width*b),(int)(ey-width*a+dist*b));
            dist=dist-10;
        }
        if(b1)
            evda=evda+.01;
        if(b2)
            evda=evda-.01;
        evda=evda*.85;
        eda=eda+evda;
        evn = ev*Math.sin(eva-eda);
        evt = ev*Math.cos(eva-eda);
        if(Math.abs(evn)>.6){
            floor.fillRect((int)(ex-13*b),(int)(ey+13*a),4,4);
            floor.fillRect((int)(ex+13*b),(int)(ey-13*a),4,4);
        }
        evn = evn*.7;
        if((ok2((int)(ex+70*a),(int)(ey+70*b)) ||  ok2((int)ex,(int)ey)) && dist>40){
            evt = evt+eadd;
            evt = emaxConst*evt;
        }
        if(dist<70 || !ok2((int)(ex+10*a),(int)(ey+10*b)))
            evt = evt-.02;
        if(ok((int)ex,(int)ey))
            evt = .99*evt;
        else
            evt = eminConst*evt;
        ev = Math.hypot(evn,evt);
        eva = Math.atan2(evn,evt)+eda;
        emx = ev*Math.cos(eva);
        emy = ev*Math.sin(eva);
        ex=ex+emx;
        ey=ey+emy;
        if(eda>3.14159)eda=eda-6.2831853;
        if(eda<-3.14159)eda=eda+6.2831853; 
    }
    
//***************************************************************************************************************************************************    
//***************************************************************************************************************************************************  
  
    public void lapCounter(){
        if(me.equals(Color.blue)){
            if(comp==1)
                comp=2;
            if(comp==3){
                getWorld().addObject(new message(4),150,75);
                comp=4;
            }
        }
        if(me.equals(Color.yellow)){
            if(comp==1){
                comp=4;
                getWorld().addObject(new message(4),150,75);
            }
            if(comp==2)
                comp=3;
        }
        if(me.equals(Color.green)){
            if(comp==3){
                lap++;
                if(lap==4){
                    auto=true;
                    if(4>elap)
                         getWorld().addObject(new message(8),150,75);
                    else
                         getWorld().addObject(new message(9),150,75);
                }else{
                    if(lap<4)getWorld().addObject(new message(lap+4),150,75);
                }
            }
            comp=1;
        }      
        me = mask.getColorAt((int)ex,(int)ey);
        if(me.equals(Color.blue))
            ecomp=2;
        if(me.equals(Color.green) && ecomp==2){
            ecomp=1;
            elap++;
        }  
    }  
    
//***************************************************************************************************************************************************    
//***************************************************************************************************************************************************   

    public void displayEnemy(){
        dist = 1000*Math.atan(1/Math.hypot(X-ex,Y-ey));
        enang = Math.atan2(Y-ey,X-ex)+3.14159 - da;
        if(enang>3.14159)enang=enang-6.2831853;
        if(enang<-3.14159)enang=enang+6.2831853;
        enang = 150+190*enang;
        buffer.clear();
        it = (int)((180.0*((Math.atan2(X-ex,Y-ey)+eda)/3.14159)+90)/15.0);
        if(it<0)it=it+24;
        if(it>23)it=it-24;
        flip=false;
        if(it>12){
            it = 24-it;
            flip=true;
        }
        buffer.drawImage(((worldness)getWorld()).getCar(it),0,0);
        if(crash>0)crash--;
        if(dist>60 && crash==0){
            bx1.tied=false;
            bx2.tied=false;
            Greenfoot.playSound("crash.wav");
            crash=20;
            t1 = v;
            t2 = va;
            t3 = da;
            v = .7*ev;
            ev = t1;
            eva = t2;
            if(Math.abs(eda-da)<1){
                da = eda;
                eda = t3;
            }else{
                va = eva;
                eva = t2;
            }
        }
        if(dist>78){
            Greenfoot.playSound("crash.wav");
            na = Math.atan2(ey-Y,ex-X);
            t1 = v+.5;
            v = ev+.5;
            ev = t1;
            va = na+Math.PI;
            eva=na;
        }
        buffer.scale((int)(8*dist),(int)(4*dist));
        if(flip)buffer.mirrorHorizontally();
        screen.drawImage(buffer,(int)(enang-4*dist),-(int)(2*dist)+150); 
        buffer = new GreenfootImage(300,150);
    }
    
//***************************************************************************************************************************************************    
//***************************************************************************************************************************************************    
}
