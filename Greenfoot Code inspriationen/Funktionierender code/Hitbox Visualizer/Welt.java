import greenfoot.*;

public class Welt extends World
{
    /** use any actor image less than 320x320 */
    private  GreenfootImage actorImage = new GreenfootImage("rocket.png");
    
    /** *********     DO NOT MODIFY BEYOND THIS POINT     ************* */
    /** main actor */
    private SimpleActor saActor = new SimpleActor(); // visual
    private SimpleActor txtActor = new SimpleActor(); // descriptive text
    
    /** getIntersectingObjects */
    private SimpleActor saRectangle = new SimpleActor(); // visual
    private SimpleActor txtRectangle = new SimpleActor(); // text
    private SimpleActor ckbRectangle = new SimpleActor(); // checkbox
    
    /** getObjectsInRange */
    private SimpleActor saCircle = new SimpleActor(); // visual
    private SimpleActor txtCircle = new SimpleActor(); // text
    private SimpleActor ckbCircle = new SimpleActor(); // checkbox
    
    /** getNeighbours:false */
    private SimpleActor saDiamond = new SimpleActor(); // visual
    private SimpleActor txtDiamond = new SimpleActor(); // text
    private SimpleActor ckbDiamond = new SimpleActor(); // checkbox
    
    /** getNeighbours:true */
    private SimpleActor saSquare = new SimpleActor(); // visual
    private SimpleActor txtSquare = new SimpleActor(); // text
    private SimpleActor ckbSquare = new SimpleActor(); // checkbox
    
    /** conjoiners */
    private SimpleActor saJoiner = new SimpleActor(); // visual
    private SimpleActor saOring = new SimpleActor(); // 'OR' button
    private SimpleActor saAnding = new SimpleActor(); // 'AND' button
    
    /** consolidating */
    private SimpleActor[] saAreas = new SimpleActor[] { saRectangle, saCircle, saDiamond, saSquare }; // areas
    private SimpleActor[] saTexts = new SimpleActor[] { txtRectangle, txtCircle, txtDiamond, txtSquare }; // texts
    private SimpleActor[] saBoxes = new SimpleActor[] { ckbRectangle, ckbCircle, ckbDiamond, ckbSquare }; // checkboxes
    
    /** colors */
    private Color[] colors = new Color[] { Color.MAGENTA, Color.GREEN, Color.CYAN, Color.YELLOW };
    
    /** checkbox values */
    private boolean[] selected = { false, false, false, false };
    
    /** focused method (text box) */
    private int focusOn = 0;
    
    public Welt()
    {
        super(640, 400, 1);
        Greenfoot.setSpeed(45);
        
        /** the background */
        // text area (left)
        GreenfootImage bg = getBackground();
        GreenfootImage image = new GreenfootImage(340, 400);
        image.setColor(new Color(48, 48, 48, 96));
        image.fill();
        image.setTransparency(80);
        bg.drawImage(image, 0, 0);
        // area separator
        image = new GreenfootImage(2, 400);
        image.fill();
        bg.drawImage(image, 339, 0);
        // title (top-right)
        image = new GreenfootImage(340, 40);
        image.setColor(new Color(0, 0, 255, 40));
        image.fill();
        bg.drawImage(image, 341, 0);
        image = new GreenfootImage(340, 2);
        image.fill();
        bg.drawImage(image, 340, 39);
        image = new GreenfootImage("HITBOX VISUALIZER", 36, Color.BLUE, new Color(0, 0, 0, 0));
        bg.drawImage(image, 490-image.getWidth()/2, 20-image.getHeight()/2);
        
        /** the actors */
        // the main actor
        saActor.setImage(new GreenfootImage(actorImage));
        addObject(saActor, 490, 200);
        // actor text
        updateActorText();
        addObject(txtActor, 170, 50);
        // localize actor dimensions
        int w = saActor.getImage().getWidth();
        int h = saActor.getImage().getHeight();
        // method actors
        for (int i=0; i<4; i++)
        {
            if (i > 0) { if (h < w) h = w; else w = h; }
            // area of method
            saAreas[i].setImage(new GreenfootImage(w, h));
            addObject(saAreas[i], 490, 200);
            // text of method
            updateText(i);
            addObject(saTexts[i], 170, 144+70*i);
            // checkbox of method
            updateCheckbox(i);
            addObject(saBoxes[i], 45, 154+70*i);
        }
        // buttons
        SimpleActor[] joiners = new SimpleActor[] { saOring, saAnding }; // consolidate
        String[] symbols = new String[] { "   OR   ", "  AND  " }; // button texts
        for (int i=0; i<2; i++)
        {
            image = new GreenfootImage(symbols[i], 32, Color.BLACK, Color.LIGHT_GRAY);
            image.drawRect(0, 0, image.getWidth()-1, image.getHeight()-1);
            joiners[i].setImage(image);
            addObject(joiners[i], 440+100*i, 380);
        }
    }
    
    /** updates text of main actor */
    private void updateActorText()
    {
        String text = "   actor width: "+saActor.getImage().getWidth()+"   \n";
        text += "   actor height: "+saActor.getImage().getHeight()+"   \n";
        text += "   actor rotation: "+saActor.getRotation()+"   ";
        GreenfootImage image = new GreenfootImage(text, 24, Color.BLACK, new Color(0, 0, 0, 0));
        image.setColor(Color.DARK_GRAY);
        image.drawRect(-1, 0, image.getWidth()+1, image.getHeight()-1);
        txtActor.setImage(image);
    }
    
    /** updates image of a checkbox */
    private void updateCheckbox(int num)
    {
        GreenfootImage image = new GreenfootImage(20, 20);
        if (selected[num])
        {
            image.setColor(colors[num]);
            image.fill();
        }
        image.setColor(Color.BLACK);
        image.drawRect(0, 0, 19, 19);
        saBoxes[num].setImage(image);
    }
    
    /** updates text of a method */
    private void updateText(int num)
    {
        Color bgColor = (focusOn == num && selected[num]) ? Color.LIGHT_GRAY : new Color(0, 0, 0, 0);
        String text = "";
        switch(num)
        {
            case 0:
                text = " getIntersectingObjects(null)             \n ";
                break;
            case 1:
                text = " getObjectsInRange(range, null)       \n";
                text += "range: "+(saCircle.getImage().getWidth()/2);
                break;
            case 2:
                text = " getNeighbours(distance, false, null) \n";
                text += "distance: "+(saDiamond.getImage().getWidth()/2);
                break;
            case 3:
                text = " getNeighbours(distance, true, null)  \n";
                text += "distance: "+(saSquare.getImage().getWidth()/2);
                break;
        }
        GreenfootImage image = new GreenfootImage(text, 24, Color.BLACK, bgColor);
        image.setColor(Color.DARK_GRAY);
        image.drawRect(0, 0, image.getWidth()-1, image.getHeight()-1);
        saTexts[num].setImage(image);
    }
    
    public void act()
    {
        int dr = 0;
        if (Greenfoot.isKeyDown("a")) dr--;
        if (Greenfoot.isKeyDown("d")) dr++;
        if (Greenfoot.isKeyDown("r")) dr+= 5;
        if (dr != 0)
        {
            saActor.turn(dr);
            saRectangle.turn(dr);
            updateActorText();
            Greenfoot.delay(5);
        }
        if (saJoiner.getWorld() != null && (Greenfoot.mouseClicked(null) || Greenfoot.mouseDragEnded(null)))
        {
            removeObject(saJoiner);
            return;
        }
        if (Greenfoot.mousePressed(saOring)) showUnion();
        if (Greenfoot.mousePressed(saAnding)) showIntersection();
        for (int i=0; i<4; i++)
        {
            if (Greenfoot.mouseClicked(saBoxes[i]))
            {
                int last = focusOn;
                selected[i] = !selected[i];
                if (selected[i]) focusOn = i; else if (focusOn == i) focusOn = 0;
                if (last >= 0) updateText(last);
                updateCheckbox(i);
                updateText(i);
                updateArea(i);
            }
            if (Greenfoot.mouseClicked(saTexts[i]) && selected[i])
            {
                int last = focusOn;
                focusOn = i;
                if (last >= 0) updateText(last);
                updateText(i);
            }
        }
        int dx = 0;
        int dy = 0;
        if (Greenfoot.isKeyDown("right")) dx++;
        if (Greenfoot.isKeyDown("left")) dx--;
        if (Greenfoot.isKeyDown("up")) dy++;
        if (Greenfoot.isKeyDown("down")) dy--;
        if (dy == 0 && (dx == 0 || focusOn != 0)) return;
        if (focusOn != 0) dx = dy;
        GreenfootImage image = saAreas[focusOn].getImage();
        int w = image.getWidth();
        int h = image.getHeight();
        if (w+dx < 5 || w+dx > 250) dx = 0;
        if (h+dy < 5 || h+dy > 250) dy = 0;
        if (dx != 0 || dy != 0)
        {
            image.scale(w+dx, h+dy);
            updateArea(focusOn);
            updateText(focusOn);
            if (focusOn == 0)
            {
                saActor.setImage(new GreenfootImage(actorImage));
                saActor.getImage().scale(w+dx, h+dy);
                updateActorText();
            }
        }
        Greenfoot.delay(5);
    }
    
    /** updates image of hitbox area of a method */
    private void updateArea(int num)
    {
        if (!selected[num])
        {
            saAreas[num].getImage().clear();
            return;
        }
        GreenfootImage image = saAreas[num].getImage();
        image.clear();
        int w = image.getWidth();
        int h = image.getHeight();
        Color color = new Color(colors[num].getRed(), colors[num].getGreen(), colors[num].getBlue(), 80);
        image.setColor(color);
        switch(num)
        {
            case 0:
            case 3:
                image.fill();
                image.setColor(Color.BLACK);
                image.drawRect(0, 0, w-1, h-1);
                break;
            case 1:
                image.fillOval(0, 0, w, h);
                image.setColor(Color.BLACK);
                image.drawOval(0, 0, w, h);
                break;
            case 2:
                int[] xs = { w/2, w, w/2, 0 };
                int[] ys = { 0, h/2, h, h/2 };
                image.fillPolygon(xs, ys, 4);
                image.setColor(Color.BLACK);
                image.drawPolygon(xs, ys, 4);
        }
    }
    
    /** shows OR combined hitbox area */
    public void showIntersection()
    {
        GreenfootImage actorArea = null;
        int w = 300;
        int h = 300;
        for (int n=0; n<4; n++)
        {
            if (!selected[n]) continue;
            GreenfootImage area = saAreas[n].getImage();
            if (n == 0) area = actorArea = getActorArea();
            if (area.getWidth() < w) w = area.getWidth();
            if (area.getHeight() < h) h = area.getHeight();
        }
        if (w == 300) return;
        GreenfootImage image = new GreenfootImage(w, h);
        image.fill();
        Color trans = new Color(0, 0, 0, 0);
        for (int y=0; y<h; y++) for (int x=0; x<w; x++)
        {
            for (int n=0; n<4; n++)
            {
                if (!selected[n]) continue;
                GreenfootImage area = saAreas[n].getImage();
                if (n == 0) area = actorArea;
                int aw = area.getWidth();
                int ah = area.getHeight();
                int dx = (aw-w)/2;
                int dy = (ah-h)/2;
                if (area.getColorAt(x+dx, y+dy).getAlpha() == 0)
                {
                    image.setColorAt(x, y, trans);
                    break;
                }
            }
        }
        saJoiner.setImage(image);
        addObject(saJoiner, 490, 200);
    }
    
    /** shows AND combined hitbox area */
    public void showUnion()
    {
        GreenfootImage actorArea = null;
        int w = 0;
        int h = 0;
        for (int n=0; n<4; n++)
        {
            if (!selected[n]) continue;
            GreenfootImage area = saAreas[n].getImage();
            if (n == 0) area = actorArea = getActorArea();
            if (area.getWidth() > w) w = area.getWidth();
            if (area.getHeight() > h) h = area.getHeight();
        }
        if (w == 0) return;
        GreenfootImage image = new GreenfootImage(w, h);
        for (int y=0; y<h; y++) for (int x=0; x<w; x++)
        {
            for (int n=0; n<4; n++)
            {
                if (!selected[n]) continue;
                GreenfootImage area = saAreas[n].getImage();
                if (n == 0) area = actorArea;
                int aw = area.getWidth();
                int ah = area.getHeight();
                int dx = (w-aw)/2; //-((aw-w+1)%2)*(w%2);
                int dy = (h-ah)/2; //-((ah-h+1)%2)*(h%2);
                if (x-dx >= 0 && x-dx < aw && y-dy >= 0 && y-dy < ah &&
                    area.getColorAt(x-dx, y-dy).getAlpha() != 0)
                {
                    image.setColorAt(x, y, Color.BLACK);
                    break;
                }
            }
        }
        saJoiner.setImage(image);
        addObject(saJoiner, 490, 200);
    }
    
    /** returs image of rotated rectangular hitbox area */
    private GreenfootImage getActorArea()
    {
        int w = saActor.getImage().getWidth()*3/2;
        int h = saActor.getImage().getHeight()*3/2;
        int max = (int)Math.max(w, h);
        GreenfootImage area = new GreenfootImage(max, max);
        int x = (max-saRectangle.getImage().getWidth())/2;
        int y = (max-saRectangle.getImage().getHeight())/2;
        area.drawImage(saRectangle.getImage(), x, y);
        area.rotate(saRectangle.getRotation());
        return area;
    }
}