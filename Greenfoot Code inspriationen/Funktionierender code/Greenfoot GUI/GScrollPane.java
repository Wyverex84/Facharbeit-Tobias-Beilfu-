import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * GScrollPane is part of PiRocks' Greenfoot GUI
 * 
 * When finished it will be a greenfoot version of a JScrollPane.
 * 
 * Please note that the greenfoot gui package will still be using parts of awt.
 * 
 * @author PiRocks
 * @version 1.2
 */
public class GScrollPane  extends GContainer
{
    /**Params*/
    private int w = 0, h = 0,gw = 0, gh =0,c = 0;
    private int xoff = 0, yoff = 0;
    private int contentHeight = 0;
    public static final int SCROLLBAR_HEIGHT = 40;
    public static final int SCROLLBAR_WIDTH = 15;
    private int scrollx = 0;
    
    /**
     * Creates a new GSCrollPane with the specified width height and columns.
     */
    public GScrollPane(int w, int h,int c) {
        if(w <SCROLLBAR_WIDTH+1) this.w = SCROLLBAR_WIDTH+1;
        if(h <SCROLLBAR_HEIGHT+1) h = SCROLLBAR_HEIGHT+1;
        this.w = w;
        this.h = h;
        this.c = c;
        getDrawPic().scale(this.w,this.h);
    }
    
    /**
     * Add the specified object into this ScrollPane.
     */
    public void add(GComponent g) {
        super.add(g);
        int pw = g.getDrawPic().getWidth();
        int ph = g.getDrawPic().getHeight();
        if(ph>gh) gh = ph;
        if(pw > gw) gw = pw;
    }
    
    /**
     * Removes the specified component from this ScrollPane
     */
    public void remove(GComponent g) {
        super.remove(g);
        GComponent[] comps = getElements();
        gh = 0;
        gw = 0;
        for(int i =0; i < comps.length;i++) {
            GComponent g2 = comps[i];
            int pw = g2.getDrawPic().getWidth();
            int ph = g2.getDrawPic().getHeight();
            if(ph>gh) gh = ph;
            if(pw > gw) gw = pw;
        }
    }
    
    /**
     * Updates this ScrollPanes picture.
     */
    public void update() 
    {
        calcOffset();
        GComponent[] draw = getElements();
        int gr = (int)Math.ceil(draw.length/c);
        int index = 0;
        for(int i =0;i <c;i++) {
            for(int j = 0; j < gr;j++) {
                int x = (i * gw)-xoff;
                int y = (j * gh)-yoff;
                try {
                    GreenfootImage pic = draw[index].getDrawPic();
                    pic.scale(gw,gh);
                    getDrawPic().drawImage(pic,x-xoff,y-yoff);
                } catch(Exception e) { }//If the elements don't comepletely fill up the last row, we will get a few ArrayIndexOutOfBoundsExceptions.
                index++;
            }
        }
        drawScrollBar();
    }
    
    /**
     * Handles drawing the scroll bar.
     */
    private void drawScrollBar() {
        GreenfootImage g = getDrawPic();
        g.setColor(greenfoot.Color.WHITE);
        g.fillRect(g.getWidth()-SCROLLBAR_WIDTH,0,g.getWidth(),g.getHeight());
        g.setColor(greenfoot.Color.BLACK);
        g.drawRect(g.getWidth()-SCROLLBAR_WIDTH,0,g.getWidth(),g.getHeight());
        g.fillRect(g.getWidth()-SCROLLBAR_WIDTH,scrollx,SCROLLBAR_WIDTH,SCROLLBAR_HEIGHT);
    }
    
    /**
     * Calculates how far to offset the content to match the scrollbar.
     */
    private void calcOffset() {
        double total = h - SCROLLBAR_HEIGHT;
        double percent = (double)scrollx/total;
        yoff = (int)(((int)Math.ceil(elementCount()/c))*percent);
    }
        
    /**
     * Handles mouse input - in this case the scrollbar.
     * <br />
     * <br />
     * Please note that any object in a GScrollPane will <em>not</em> receive mouse input
     * as of right now.
     */
    public void handleInput(MouseInfo m,int realX,int realY) {
        if(m==null) return;
        if(!Greenfoot.mouseDragged(null)&&!Greenfoot.mouseClicked(null)) return;
        int x = m.getX();
        int y = m.getY();
        x = x - realX;
        y = y - realY;
        //y = y - getY() - getDrawPic().getWidth();
       // y = y + (SCROLLBAR_HEIGHT/2);
        if(x > (getDrawPic().getWidth()/2) || x < ((getDrawPic().getWidth()/2)-SCROLLBAR_WIDTH))return;
        if(y > (getDrawPic().getHeight()/2) || y < 0 - (getDrawPic().getHeight()/2)) return;
        
        y+=(getDrawPic().getHeight()/2);
        scrollx = y - 10 - (SCROLLBAR_HEIGHT/2);
        if(scrollx < 0) scrollx = 0;
        if(scrollx > h - SCROLLBAR_HEIGHT) scrollx = h - SCROLLBAR_HEIGHT;
    }
}
