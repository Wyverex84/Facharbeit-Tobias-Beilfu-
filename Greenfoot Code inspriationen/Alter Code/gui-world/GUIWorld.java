import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Dimension;


/**
 * Tests for GUI components in various configurations.
 *
 * @author Ed Parrish
 * @version 1.0 1/4/2011
 */
public class GUIWorld extends World implements ActionListener {
    public static final Font LARGE_FONT = new Font("SansSerif", Font.BOLD, 20);
    public static final Font MED_FONT = new Font("SansSerif", Font.ITALIC, 14);
    public static final Font SMALL_FONT = new Font("SansSerif", Font.PLAIN, 10);

    private Label defaultLabel;
    private Label biggerLabel;
    private Label colorfulLabel;
    private Label fixedLabel;
    private Label multilineLabel;
    private Button defaultBtn;
    private Button biggerBtn;
    private Button colorfulBtn;
    private Button fixedSizeBtn;
    private Button multiLineBtn;
    private Button rectBtn;
    private Button roundBtn;
    private Button ovalBtn;
    private Button squareBtn;
    private Button multiBtn;
    private TextField defaultTF;
    private TextField biggerTF;
    private TextField colorTF;
    private TextField fixedSizeTF;
    private TextField longTF;

    private Button onOffBtn;
    private boolean enabled = true;

    private Border redBorder = new LineBorder(Color.RED, 3);
    private Border blueBorder = new LineBorder(Color.BLUE, 3);

    /**
     * Constructor for objects of class GUIWorld.
     */
    public GUIWorld() {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 430, 1);

        // Test labels
        // Following use of GreenfootImage works with version 2.0.1 or later
//        Label label = new Label(new GreenfootImage("Various labels", 16,
//            Color.BLACK, new Color(0, 0, 0, 0)));
        Label label = new Label("Various labels");
        addObject(label, 65, 30);
        defaultLabel = new Label("Default");
        addObject(defaultLabel, 65, 83);
        biggerLabel = new Label("Bigger", LARGE_FONT);
        biggerLabel.setFocusable(true);
        addObject(biggerLabel, 65, 132);
        colorfulLabel = new Label("Colorful", LARGE_FONT, Color.BLUE, Color.YELLOW);
        addObject(colorfulLabel, 65, 200);
        fixedLabel = new Label("Fixed size label is word wrapped automatically");
        fixedLabel.setSize(new Dimension(100, 50));
        fixedLabel.setBackground(Color.WHITE);
        addObject(fixedLabel, 65, 275);
        multilineLabel =
            new Label("Label with lines\nwrapped manually\nusing \\n");
        multilineLabel.setBackground(Color.CYAN);
        multilineLabel.setBorder(new LineBorder(Color.BLUE, 2));
        addObject(multilineLabel, 65, 350);

        // Text buttons
        Label stdLabel = new Label("Text Buttons");
        addObject(stdLabel, 190, 30);
        defaultBtn = new Button("Default", 1001);
        defaultBtn.addActionListener(this);
        addObject(defaultBtn, 190, 83);
        biggerBtn  = new Button("Bigger\u2082", 1002);
        biggerBtn.setFont(LARGE_FONT);
        biggerBtn.addActionListener(this);
        addObject(biggerBtn, 190, 132);
        colorfulBtn  = new Button("In Color", LARGE_FONT, Color.BLUE,
            new Color(0xCC, 0xFF, 0xFF));
        colorfulBtn.setID(1003);
        colorfulBtn.setBorder(blueBorder);
        colorfulBtn.setBackgroundPressed(new Color(0xFF, 0xFF, 0x99));
        colorfulBtn.setBackgroundHover(new Color(0xFF, 0xFF, 0x00));
        colorfulBtn.addActionListener(this);
        addObject(colorfulBtn, 190, 200);
        fixedSizeBtn = new Button("Fixed size button is word wrapped automatically");
        fixedSizeBtn.setID(1004);
        fixedSizeBtn.setBorder(new LineBorder(Color.BLUE, 2));
        fixedSizeBtn.setSize(new Dimension(100, 50));
        fixedSizeBtn.addActionListener(this);
        addObject(fixedSizeBtn, 190, 275);
        multiLineBtn = new Button("Button with lines\nwrapped manually\nusing \\n");
        multiLineBtn.setID(1005);
        multiLineBtn.setBackground(new Color(204, 204, 204, 128));
        multiLineBtn.addActionListener(this);
        addObject(multiLineBtn, 190, 350);

        // Image-based buttons
        Label imgLabel = new Label("Image Buttons");
        addObject(imgLabel, 320, 30);
        GreenfootImage rectBtnUp = new GreenfootImage("rectbtn-up.jpg");
        GreenfootImage rectBtnDown = new GreenfootImage("rectbtn-down.jpg");
        GreenfootImage rectBtnHover = new GreenfootImage("rectbtn-hover.jpg");
        rectBtn = new Button(rectBtnUp, rectBtnDown, rectBtnHover);
        rectBtn.setID(1006);
        rectBtn.addActionListener(this);
        addObject(rectBtn, 320, 83);
        roundBtn = new Button(new GreenfootImage("roundbtn-up.png"),
            new GreenfootImage("roundbtn-down.png"),
            new GreenfootImage("roundbtn-hover.png"));
        roundBtn.setText("Round");
        roundBtn.setFont(MED_FONT);
        roundBtn.setID(1007);
        roundBtn.addActionListener(this);
        addObject(roundBtn, 320, 140);
        ovalBtn = new Button(new GreenfootImage("roundedbtn-up.png"),
            new GreenfootImage("roundedbtn-down.png"),
            new GreenfootImage("roundedbtn-hover.png"));
        ovalBtn.setForeground(Color.BLUE);
        ovalBtn.setText("Rounded Rectangle");
        ovalBtn.setID(1008);
        ovalBtn.addActionListener(this);
        addObject(ovalBtn, 320, 200);
        squareBtn = new Button(rectBtnUp, rectBtnDown, rectBtnHover);
        squareBtn.setSize(new Dimension(100, 50));
        squareBtn.setText("Fixed size button is word wrapped automatically");
        squareBtn.setID(1009);
        squareBtn.addActionListener(this);
        addObject(squareBtn, 320, 275);
        multiBtn = new Button(rectBtnUp, rectBtnDown, rectBtnHover);
        multiBtn.setText("Button with lines\nwrapped manually\nusing \\n");
        multiBtn.setID(1009);
        multiBtn.addActionListener(this);
        addObject(multiBtn, 320, 350);

        // Text fields
        Label textLabel = new Label("Text Fields\n(press Enter key\nfor action)");
        addObject(textLabel, 475, 30);
        defaultTF = new TextField("Default 8", 8);
        defaultTF.addActionListener(this);
        defaultTF.requestFocus();
        addObject(defaultTF, 475, 83);
        biggerTF = new TextField("Biggy", 6);
        biggerTF.setFont(LARGE_FONT);
        biggerTF.addActionListener(this);
        addObject(biggerTF, 475, 132);
        colorTF = new TextField("Colorful", 7, LARGE_FONT, Color.BLUE,
            Color.YELLOW);
        colorTF.setBorder(blueBorder);
        colorTF.addActionListener(this);
        addObject(colorTF, 475, 200);
        fixedSizeTF = new TextField("Fixed size component");
        fixedSizeTF.setSize(new Dimension(100, 50));
        fixedSizeTF.setFont(MED_FONT);
        fixedSizeTF.addActionListener(this);
        addObject(fixedSizeTF, 475, 275);
        longTF = new TextField("Text field with too many characters", 10);
        longTF.setFont(LARGE_FONT);
        longTF.addActionListener(this);
        addObject(longTF, 475, 350);

        // Test disable/enable operation
        onOffBtn = new Button("Disable\nComponents");
        onOffBtn.addActionListener(this);
        addObject(onOffBtn, 190, 400);
    }

    // Test the isPressed() method of Button
    public void act() {
        defaultLabel.setText("isPressed: "+defaultBtn.isPressed());

        // Put red border around text field when selected
        Border b = colorTF.getBorder();
        if (colorTF.isFocusOwner() && b != redBorder) {
            colorTF.setBorder(redBorder);
        } else if (!colorTF.isFocusOwner() && b != blueBorder) {
            colorTF.setBorder(blueBorder);
        }
    }

    // Test the actionPerformed() callback
    public void actionPerformed(GUIComponent c) {
        if (c.getID() == 1002) {
            biggerLabel.setText("action on\nbutton\n# " + c.getID());
        }
        if (c != onOffBtn) {
            c.setText("Action \nPerformed");
        } else {
            enabled = !enabled;
            defaultLabel.setEnabled(enabled);
            biggerLabel.setEnabled(enabled);
            colorfulLabel.setEnabled(enabled);
            fixedLabel.setEnabled(enabled);
            multilineLabel.setEnabled(enabled);
            defaultBtn.setEnabled(enabled);
            biggerBtn.setEnabled(enabled);
            colorfulBtn.setEnabled(enabled);
            fixedSizeBtn.setEnabled(enabled);
            multiLineBtn.setEnabled(enabled);
            rectBtn.setEnabled(enabled);
            roundBtn.setEnabled(enabled);
            ovalBtn.setEnabled(enabled);
            squareBtn.setEnabled(enabled);
            multiBtn.setEnabled(enabled);
            defaultTF.setEnabled(enabled);
            biggerTF.setEnabled(enabled);
            colorTF.setEnabled(enabled);
            fixedSizeTF.setEnabled(enabled);
            longTF.setEnabled(enabled);
            if (enabled) {
                c.setText("Disable\nComponents");
            } else {
                c.setText("Enable\nComponents");
            }
        }
    }
}
