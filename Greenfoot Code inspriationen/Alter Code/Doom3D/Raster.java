import java.util.*;
import greenfoot.*;  


public class Raster  
{
    static int[] counter = new int[2000];
    static int[][][] points = new int[2000][4][4];
    //static int top=0,next; 
    static double[] dist = new double[2000]; 
    static int[] renderable = new int[2000];
    static Color[] colors = new Color[8024];
    static Object3D now;
    static int start=0,end=0;
    static int Lx1,Ly1,Lx2,Ly2;
    static int Kx1,Ky1,Kx2,Ky2;
    static double camX,camY,camZ;
    static double d1,d2,d3,d4;
    static double lense;
    static Camera camera;
    
    //SPECIAL RENDERING VARIABLES
    static double[] x1 = new double[4];
    static double[] y1 = new double[4];
    static double[] z1 = new double[4];
    static double[] x2 = new double[4];
    static double[] y2 = new double[4];
    static double[] z2 = new double[4];
    static double[] zs1 = new double[4];
    static double[] zs2 = new double[4];
    static double[] tempX = new double[4];
    static double[] tempY = new double[4];
    static double[] tempZ = new double[4];
    static int rx,ry;
    static double xTemp1,yTemp1,zTemp1,xTemp2,yTemp2,zTemp2;

    
    public static void clear(){
        start = 0;
        end = 0;
    }
    
    public static void setCamera(Camera c){
        camera = c;
        updateCamera();
    }
    
    public static void updateCamera(){
        camX = camera.getX();
        camY = camera.getY();
        camZ = camera.getZ();
        d1 = camera.d1;
        d2 = camera.d2;
        d3 = camera.d3;
        d4 = camera.d4;
        lense = camera.getLense();
    }
    
    public static void drawObject(GreenfootImage image,List Objects){
        updateCamera();
        drawObject(image,Objects,camX,camY,camZ,d1,d2,d3,d4);
    }
    
    public static void drawObject(GreenfootImage image,Object3D toDraw){
        updateCamera();
        drawObject(image,toDraw,camX,camY,camZ,d1,d2,d3,d4);
    }
    
    public static void drawObject(GreenfootImage image,List Objects,double x,double y, double z,double temp1,double temp2,double temp3,double temp4){
        start = end;
        
        for(int s=0;s<Objects.size();s++){         
            now = (Object3D) Objects.get(s);     
            now.getPoints(x,y,z,temp1,temp2,temp3,temp4);
            for(int i=0;i<now.numPoly();i++){
                points[end] = now.getPolygon(i);
                renderable[end] = now.renderable(i);
                if(renderable[end]!=0){       
                    if(renderable[end]==2){
                        points[end] = now.getSpecialPolygon(i,temp1,temp2,temp3,temp4,x,y,z);
                    }
                    dist[end] = now.distance(i);
                    colors[end] = now.getColor(i);
                    counter[end] = end;
                    end++;
                }
                
            }
        }
        
        
        double gap=end;
        int swaps=1;
        while(swaps>0 || gap>1){
            if(gap>1){
                gap=gap/1.3;
                if(gap==9 || gap==10)
                    gap=11;
            }
            swaps=0;
            int i=start,sa;
            double s;
            while(i+gap+1<end){
                if(dist[i] < dist[i+(int)(gap+1)]){
                    swaps++;
                    s = dist[i];
                    dist[i] = dist[i+(int)(gap+1)];
                    dist[i+(int)(gap+1)] = s;
                    
                    sa = counter[i];
                    counter[i] = counter[i+(int)(gap+1)];
                    counter[i+(int)(gap+1)] = sa;
                    
                    //s = lit[i];
                    //lit[i] = lit[i+(int)(gap+1)];
                    //lit[i+(int)(gap+1)] = s;
                }
                i++;
            }
        }
        int index;
        for(int i = start;i<end;i++){
            index = counter[i];
            image.setColor(colors[index]);
            image.fillPolygon(points[index][0],points[index][1],points[index][0].length);
            //image.setColor(Color.black);
        }
    }
    
    
    public static void drawObject(GreenfootImage image,Object3D toRender,double x,double y, double z,double temp1,double temp2,double temp3,double temp4){
        start = end;
                
        now = toRender;
        now.getPoints(x,y,z,temp1,temp2,temp3,temp4);
        for(int i=0;i<now.numPoly();i++){
            points[end] = now.getPolygon(i);
            renderable[end] = now.renderable(i);
            
            if(renderable[end]!=0){     
                colors[end] = now.getColor(i);
                if(renderable[end]==2){
                    points[end] = now.getSpecialPolygon(i,temp1,temp2,temp3,temp4,x,y,z);
                    //colors[end] = Color.blue;
                }
                dist[end] = now.distance(i);
                
                counter[end] = end;
                if(points[end]!=null){
                    end++;
                }
            }
            
        } 
        
        double gap=end;
        int swaps=1;
        while(swaps>0 || gap>1){
            if(gap>1){
                gap=gap/1.3;
                if(gap==9 || gap==10)
                    gap=11;
            }
            swaps=0;
            int i=start,sa;
            double s;
            while(i+gap+1<end){
                if(dist[i] < dist[i+(int)(gap+1)]){
                    swaps++;
                    s = dist[i];
                    dist[i] = dist[i+(int)(gap+1)];
                    dist[i+(int)(gap+1)] = s;
                    
                    sa = counter[i];
                    counter[i] = counter[i+(int)(gap+1)];
                    counter[i+(int)(gap+1)] = sa;
                    
                    //s = lit[i];
                    //lit[i] = lit[i+(int)(gap+1)];
                    //lit[i+(int)(gap+1)] = s;
                }
                i++;
            }
        }
        int index;
        for(int i = start;i<end;i++){
            index = counter[i];
            //if(renderable[index]!=2){
                image.setColor(colors[index]);
                image.fillPolygon(points[index][0],points[index][1],points[index][0].length);
                //image.setColor(Color.black);
                image.drawPolygon(points[index][0],points[index][1],points[index][0].length);
            //}
        } 
    }
    
    /*
    public static void displayClippedObject(GreenfootImage image){
        int next;
        for(int f = 0;f<end;f++){
            next = counter[f];
            image.setColor(colors[next]);
            int[][] tempPoints = clip(points[next],Lx1,Ly1,Lx2,Ly2);
            tempPoints = clip(tempPoints,Kx2,Ky2,Kx1,Ky1);
            image.fillPolygon(tempPoints[0],tempPoints[1],tempPoints[0].length);
            //image.setColor(colors[next][1]);
            //image.drawPolygon(points[next][0],points[next][1],points[next][0].length);
        }
    }
    */
    
   /*
    public static void displayObjects(GreenfootImage image,ArrayList<Object3D> list){
        for(int i = 0;i<list.size();i++){
            list.get(i).getRenderedImage().drawImage(image);
        }
    }
    */
    
    /*
    public static void displayClippedObject(GreenfootImage image,RenderedImage rImage){
        int next;
        for(int f = rImage.start;f<rImage.end;f++){
            next = counter[f];
            image.setColor(colors[next]);
            int[][] tempPoints = clip(points[next],Lx1,Ly1,Lx2,Ly2);
            tempPoints = clip(tempPoints,Kx2,Ky2,Kx1,Ky1);
            image.fillPolygon(tempPoints[0],tempPoints[1],tempPoints[0].length);
            image.setColor(Color.black);
            image.drawPolygon(tempPoints[0],tempPoints[1],tempPoints[0].length);
        }
    }
    */
    
   /*
    public static void displayClippedObject(GreenfootImage image,ArrayList<Object3D> objects){
        for(int i = 0;i<objects.size();i++){
            displayClippedObject(image,objects.get(i).getRenderedImage());
        }
    }
    */
    
    public static int[][] clip(int[][] points, int xv1,int yv1,int xv2,int yv2){
        int l = points[0].length;
        
        double[] x1 = new double[l];
        double[] y1 = new double[l];
        double[] x2 = new double[l];
        double[] y2 = new double[l];
        boolean[] zs1 = new boolean[l];
        boolean[] zs2 = new boolean[l];
        
        double slope;
        if(xv1==xv2){
            xv1++;
        }
        slope = ((double)yv1-yv2)/(xv1-xv2);
        double yint = yv1 - slope*xv1;
            
        int[] wrap = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        wrap[l] = 0;
        
        
        //Separate
        
        for(int i=0;i<l;i++){
            x1[i] = points[0][wrap[i]];
            x2[i] = points[0][wrap[i+1]];
            y1[i] = points[1][wrap[i]];
            y2[i] = points[1][wrap[i+1]];
            zs1[i] = right(x1[i],y1[i],xv1,yv1,xv2,yv2);
            zs2[i] = right(x2[i],y1[i],xv1,yv1,xv2,yv2);
        }
        //Clip
        
       double[] px = new double[15];
        double[] py = new double[15];
        int index = 0;
        
        for(int i=0;i<l;i++){
            if(zs1[i]){
                px[index] = x1[i];
                py[index] = y1[i];
                index++;
            }
            if(zs1[i] != zs2[i]){
                double a = x1[i];
                double b = y1[i];
                //double d = zs1[i];
                double e = x2[i];
                double f = y2[i];
                //double h = zs2[i];
                if(a==e){
                    a++;
                }
                double slopeT = (b-f)/(a-e);
                double yT = b - slopeT*a;
                
                double fx = intersectX(slope,yint,slopeT,yT);
                double fy = intersectY(slope,yint,fx);
                
                px[index] = fx;
                py[index] = fy;
                index++;    
            }
        }
             
        int[][] pointsOut = new int[2][index];
        
        for(int i=0;i<index;i++){
            //renderPoint(,,pz[i]);
            
            pointsOut[0][i] = (int)px[i];
            pointsOut[1][i] = (int)py[i];
        }
        return pointsOut;
        //image.fillPolygon(scrx,scry,scrx.length);
        //if(outLined){
            //getBackground().setColor(Color.black);
        //}
        //image.drawPolygon(scrx,scry,scrx.length);
        //getBackground().setColor(Color.white);
    }
    
    public static boolean right(double px,double py,double vx1,double vy1,double vx2,double vy2){
        double Ux = vx2 - vx1;
        double Uy = vy2 - vy1;
        double Vx = px - vx1;
        double Vy = py - vy1;
        return Ux*Vy - Uy*Vx < 0;
    }
    
    public static double intersectX(double m,double a,double n,double b){
        return (b-a)/(m-n);
    }
    
    public static double intersectY(double m,double a,double x){
        return m*x + a; 
    }
    
    public static void setLine1(int a,int b,int c,int d){
        Lx1 = a;
        Ly1 = b;
        Lx2 = c;
        Ly2 = d;
    }
    
    public static void setLine2(int a,int b,int c,int d){
        Kx1 = a;
        Ky1 = b;
        Kx2 = c;
        Ky2 = d;
    }
    
   
    
   
}
