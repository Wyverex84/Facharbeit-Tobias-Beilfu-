import java.awt.Color;


public class Object3D  
{
    //POINT DATA
    double[] x;
    double[] y;
    double[] z;
    int[][] shapes;
    int[][] points;
    double[] distance;
    Color[][] colors;
    boolean renderable=true;
    boolean[] back;
    
    //TEMPORARY VARIABLES
    double xTemp1;
    double yTemp1;
    double zTemp1;
    double xTemp2;
    double yTemp2;
    double zTemp2;
    double xTemp3;
    double yTemp3;
    double zTemp3;
    double[] nx;
    double[] ny;
    double[] nz;
    int tr1,tr2,tr3,tr4;
    
    //OBJECT DATA
    double X=0,Y=0,Z=0,xa=0,ya=0,za=0,scale=1;
    double a1=1,a2=0,a3=1,a4=0,a5=1,a6=0;
    double dist=0,cx,cy,cz;
    boolean backface,shadeless;
    int lowPolly;
    double lit;                      
    
    
    public Object3D(double[] xp,double[] yp,double[] zp,int[][] sp,Color[][] c,boolean bk,int low,boolean shade) 
    {
        x=xp;
        y=yp;
        z=zp;
        shapes=sp;
        points = new int[2][x.length];
        distance = new double[x.length];
        back = new boolean[x.length];
        colors = c;
        backface = bk;
        nx = new double[x.length];
        ny = new double[x.length];
        nz = new double[x.length];
        lowPolly = low;
        shadeless = shade;
    }
    
    public void getPoints(double dx, double dy, double dz, double b1,double b2, double b3, double b4){//, boolean trig){    
        
        renderable=true;
        dist = 100000+Math.pow(dx-X,2)+Math.pow(dy-Y,2)+Math.pow(dz-Z,2);
        cx = dx;
        cy = dy;
        cz = dz;
        
        for(int i=0;i<x.length;i++){ 
            xTemp1 = scale*x[i]*a1 - scale*y[i]*a2;
            yTemp1 = scale*y[i]*a1 + scale*x[i]*a2;
            
            zTemp1 = scale*z[i]*a3 - yTemp1*a4;
            yTemp2 = yTemp1*a3 + scale*z[i]*a4;
            
            xTemp2 = xTemp1*a5 - zTemp1*a6;
            zTemp2 = zTemp1*a5 + xTemp1*a6;
            
            nx[i] = xTemp2;
            ny[i] = yTemp2;
            nz[i] = zTemp2;     
            
            xTemp1 = xTemp2 + X - dx;
            yTemp1 = yTemp2 + Y - dy;
            zTemp1 = zTemp2 + Z - dz;
            
            xTemp2 = xTemp1*b1 - zTemp1*b2;
            zTemp1 = zTemp1*b1 + xTemp1*b2;
            
            yTemp2 = yTemp1*b3 - zTemp1*b4;
            zTemp2 = zTemp1*b3 + yTemp1*b4;
             
            if(zTemp2<0)renderable=false;
            
            distance[i] = Math.pow(xTemp2,2)+Math.pow(yTemp2,2)+Math.pow(zTemp2+9,2); 
            
            
            
            points[0][i] = 200+(int)(350*(xTemp2/zTemp2));                        
            points[1][i] = 200+(int)(350*(yTemp2/zTemp2));
        }
        
    }
    
    public int[][] getPolygon(int num){     
        tr1 = shapes[num][0];           
        tr2 = shapes[num][1];           
        tr3 = shapes[num][2];          
        if(shapes[num].length==4){         
            tr4 = shapes[num][3];
            int[][] triangle ={{points[0][tr1],points[0][tr2],points[0][tr3],points[0][tr4]},
                               {points[1][tr1],points[1][tr2],points[1][tr3],points[1][tr4]}};              
            return triangle;
        }else{
            int[][] triangle ={{points[0][tr1],points[0][tr2],points[0][tr3]},
                               {points[1][tr1],points[1][tr2],points[1][tr3]}};
            return triangle;
        }
    }
    
    public boolean renderable(int num){ 
        if(!renderable)return false;
        tr1 = shapes[num][0];       
        tr2 = shapes[num][1];
        tr3 = shapes[num][2];
        if(shapes[num].length==4){
            tr4 = shapes[num][3];
            int x1=points[0][tr1],x2=points[0][tr2],x3=points[0][tr3],x4=points[0][tr4];
            int y1=points[1][tr1],y2=points[1][tr2],y3=points[1][tr3],y4=points[1][tr4];              
            if((x1<0 && x2<0 && x3<0 && x4<0) || (y1<0 && y2<0 && y3<0 && y4<0) || (x1>400 && x2>400 && x3>400 && x4>400) || (y1>400 && y2>400 && y3>400 && y4>400))
                return false;
            
            xTemp1 = (ny[tr1]-ny[tr3])*(nz[tr2]-nz[tr3]) - (nz[tr1]-nz[tr3])*(ny[tr2]-ny[tr3]);
            yTemp1 = (nz[tr1]-nz[tr3])*(nx[tr2]-nx[tr3]) - (nx[tr1]-nx[tr3])*(nz[tr2]-nz[tr3]);
            zTemp1 = (nx[tr1]-nx[tr3])*(ny[tr2]-ny[tr3]) - (ny[tr1]-ny[tr3])*(nx[tr2]-nx[tr3]);
            if(xTemp1*nx[tr2] + yTemp1*ny[tr2] + zTemp1*nz[tr2] < 0){
                xTemp1 = -xTemp1;
                yTemp1 = -yTemp1;
                zTemp1 = -zTemp1;
            }

            return xTemp1*(nx[tr2]+X-cx) + yTemp1*(ny[tr2]+Y-cy) + zTemp1*(nz[tr2]+Z-cz) < 0 || !backface;
           
        }else{
            int x1=points[0][tr1],x2=points[0][tr2],x3=points[0][tr3];
            int y1=points[1][tr1],y2=points[1][tr2],y3=points[1][tr3];              
            if((x1<0 && x2<0 && x3<0) || (y1<0 && y2<0 && y3<0) || (x1>400 && x2>400 && x3>400) || (y1>400 && y2>400 && y3>400))
                return false;
                
            xTemp1 = (ny[tr1]-ny[tr3])*(nz[tr2]-nz[tr3]) - (nz[tr1]-nz[tr3])*(ny[tr2]-ny[tr3]);
            yTemp1 = (nz[tr1]-nz[tr3])*(nx[tr2]-nx[tr3]) - (nx[tr1]-nx[tr3])*(nz[tr2]-nz[tr3]);
            zTemp1 = (nx[tr1]-nx[tr3])*(ny[tr2]-ny[tr3]) - (ny[tr1]-ny[tr3])*(nx[tr2]-nx[tr3]);
            if(xTemp1*nx[tr2] + yTemp1*ny[tr2] + zTemp1*nz[tr2] < 0){
                xTemp1 = -xTemp1;
                yTemp1 = -yTemp1;
                zTemp1 = -zTemp1;
            }
            return xTemp1*(nx[tr2]+X-cx) + yTemp1*(ny[tr2]+Y-cy) + zTemp1*(nz[tr2]+Z-cz) < 0 || !backface;    

        }
    }
    
    public double lit(double lx, double ly, double lz){
        if(shadeless)return 0;
        
        lit = Math.sqrt(xTemp1*xTemp1+yTemp1*yTemp1+zTemp1*zTemp1);
        xTemp1 = xTemp1/lit;
        yTemp1 = yTemp1/lit;
        zTemp1 = zTemp1/lit;
        
        
        lx = nx[tr2]+X-lx;
        ly = ny[tr2]+Y-ly;
        lz = nz[tr2]+Z-lz;
        
        lit = Math.sqrt(lx*lx+ly*ly+lz*lz);
        lx = lx/lit;
        ly = ly/lit;
        lz = lz/lit;
        
        lit = (xTemp1*lx + yTemp1*ly + zTemp1*lz)/(lit/500.0+1);
        if(lit>0)lit=0;
        //System.out.println(lit);
        return -lit;
    }
            
            
    public int numPoly(){     
        if(dist>10000000)return 0;
        if(dist>5000000)return lowPolly;
        return shapes.length;
    }
    
    public int numPoints(int num){ 
        return shapes[num].length;
    }
    
    public double distance(int num){  
        int tr1 = shapes[num][0];
        int tr2 = shapes[num][1];      
        int tr3 = shapes[num][2];       
        if(shapes[num].length==4){
            int tr4 = shapes[num][3];  
            return (distance[tr1]+distance[tr2]+distance[tr3]+distance[tr4])/4.0;
        }else{
            return (distance[tr1]+distance[tr2]+distance[tr3])/3.0;
        }
    }
    
    
    
    public Color[] getColor(int num){
        return colors[num];            
    }
    
    public Object3D duplicate(){   
        Color[][] cn = new Color[colors.length][2];
        for(int i=0;i<colors.length;i++){
            cn[i][0] = new Color(colors[i][0].getRed(),colors[i][0].getBlue(),colors[i][0].getGreen(),colors[i][0].getAlpha());
            cn[i][1] = new Color(colors[i][1].getRGB());
        }
        return new Object3D(x,y,z,shapes,cn,backface,lowPolly,shadeless);
    }
    
    public void setLocation(double a, double b, double c){ 
        X=a;                                               
        Y=b;                                               
        Z=c;
    }
    
    public void move(double a, double b, double c){         
        X=X+a;                                            
        Y=Y+b;                                             
        Z=Z+c;
    }
    
    public void setRotation(double a, double b, double c){          
        xa=a;                                             
        ya=b;      
        za=c;
        a1 = Math.cos(ya);                         
        a2 = Math.sin(ya);
        a3 = Math.cos(xa);
        a4 = Math.sin(xa);  
        a5 = Math.cos(za);
        a6 = Math.sin(za);  
    }
    
    public void rotate(double a, double b,double c){                
        xa=xa+a;                                           
        ya=ya+b;  
        za=za+c;
        a1 = Math.cos(ya);                         
        a2 = Math.sin(ya);
        a3 = Math.cos(xa);
        a4 = Math.sin(xa);  
        a5 = Math.cos(za);
        a6 = Math.sin(za);  
    }
    
    public double[] getLocation(){         
        double[] pos = {X,Y,Z};
        return pos;
    }
    
    public double[] getRotation(){         
        double[] rot = {xa,ya,za};
        return rot;
    }
    
    public void setColor(Color c1,Color c2,int num){
        colors[num][0] = c1;
        colors[num][1] = c2;
    }
    
    public void setColor(Color c1, int num){
        setColor(c1,c1,num);
    }
    
    public void setColor(Color c1, Color c2){
        for(int i=0;i<colors.length;i++){
            setColor(c1,c2,i);
        }
    }
    
    public void setColor(Color c1){
        setColor(c1,c1);
    }
    
    public void scale(double s){
        scale = s;
    }

}
