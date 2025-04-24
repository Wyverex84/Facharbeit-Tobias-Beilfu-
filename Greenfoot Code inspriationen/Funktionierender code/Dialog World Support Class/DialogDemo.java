import greenfoot.*;

public class DialogDemo extends World
{
    public Dialog currentDialog; // for dialog to call
    public SampleDialog dialog = new SampleDialog(this); // a sample dialog
    private boolean calledDialog; // flag to indicate dialog called
    private Actor btnDD, btnSD;
    
    public DialogDemo()
    {
        super(800, 600, 1);
        // scenario title
        GreenfootImage img = new GreenfootImage("DIALOG DEMO", 48, Color.BLUE, Color.WHITE);
        getBackground().drawImage(img, 400-img.getWidth()/2, 60);
        // prepare
        showSampleDialogData(); // to show initial values
        showText("Press 'escape' for dialog", 400, 500);
        btnDD = createButton("DialogDemo");
        addObject(btnDD, 260, 560);
        btnSD = createButton("SampleDialog");
        addObject(btnSD, 540, 560);
    }
    
    public void act()
    {
        // returning from dialog
        if (calledDialog)
        {
            updateData(); // show dialog values
            calledDialog = false; // clear flag
        }
        // going to dialog
        if ("escape".equals(Greenfoot.getKey()))
        {
            currentDialog = dialog; // assigning of dialog to call
            Greenfoot.setWorld(currentDialog); // call dialog
            calledDialog = true; // set flag
        }
        if (Greenfoot.mouseClicked(btnDD)) Greenfoot.setWorld(new TextFileViewer("DialogDemo.txt", this));
        if (Greenfoot.mouseClicked(btnSD)) Greenfoot.setWorld(new TextFileViewer("SampleDialog.txt", this));
    }
    
    // update with dialog values
    private void updateData()
    {
        if (currentDialog instanceof SampleDialog) showSampleDialogData();
    }
    
    // show sample dialog data
    private void showSampleDialogData()
    {
        String out = "Input box = \""+dialog.inputBox.getText()+"\"\n";
        out += "Listed text box = \""+dialog.listBox.getText()+"\"\n";
        out += "Check box is "+(dialog.checkBox.isChecked() ? "" : "un")+"checked\n";
        out += "Spinner = "+dialog.spinner.getValue()+"\n";
        out += "Text box = \""+dialog.textBox.getText()+"\"\n";
        showText(out, 400, 300);
    }
    
    private Actor createButton(String caption)
    {
        GreenfootImage img = new GreenfootImage(150, 36);
        img.setColor(Color.LIGHT_GRAY);
        img.fill();
        img.setColor(Color.BLACK);
        for (int i=0; i<3; i++) img.drawRect(i, i, 149-2*i, 35-2*i);
        GreenfootImage txt = new GreenfootImage(caption, 24, Color.BLACK, Color.LIGHT_GRAY);
        img.drawImage(txt, 75-txt.getWidth()/2, 18-txt.getHeight()/2);
        Actor button = new Actor() {};
        button.setImage(img);
        return button;
    }
}