
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.UserInfo;
import greenfoot.World;
import greenfoot.core.WorldHandler;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This is where all the displaying happens.
 *
 * @author Yehuda
 * @version Modified: 1/30/2017
 * @version Modified: 5/3/2017
 */
public class MyWorld extends World {

    private static final Logger LOG = Logger.getLogger(MyWorld.class.getName());
    private final Actor message = new Actor() {
    };

    /**
     * Constructor for objects of class MyWorld.
     *
     */
    public MyWorld() {
        super(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2, 1);

        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }
        JPanel worldPanel = WorldHandler.getInstance().getWorldCanvas();
        worldPanel.removeAll();
        worldPanel.setLayout(new GridBagLayout());
        JButton scoreButton = new JButton("Scores");
        worldPanel.add(scoreButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        SwingUtilities.updateComponentTreeUI(worldPanel);
        SwingUtilities.updateComponentTreeUI(scoreButton);
        scoreButton.addActionListener((ActionEvent e) -> {
            highScore();
        });

        Fade text = new Fade(new GreenfootImage("Not Logged In", 72, Color.BLACK, new Color(0, 0, 0, 0)), 2);
        Resize userImage = new Resize(new GreenfootImage("no_avatar_thumb-bb727f2070cc2dd170ed8594ae644d97.jpg"), 150);
        GreenfootImage scoreText = new GreenfootImage("Score: " + "Not Available", 36, Color.BLACK, new Color(0, 0, 0, 0));
        UserInfo myInfo = UserInfo.getMyInfo();
        if (myInfo != null && UserInfo.isStorageAvailable()) {
            text = new Fade(new GreenfootImage(myInfo.getUserName(), 72, Color.BLACK, new Color(0, 0, 0, 0)), 2);
            userImage = new Resize(myInfo.getUserImage(), 150);
            setScore(myInfo);
            scoreText = new GreenfootImage("Score: " + myInfo.getScore(), 36, Color.BLACK, new Color(0, 0, 0, 0));
        }
        if (text.getImage().getWidth() > getWidth()) {
            text = new Fade(new GreenfootImage(myInfo.getUserName(), 36, Color.BLACK, new Color(0, 0, 0, 0)), 2);
        }
        addObject(userImage, getWidth() / 2, getHeight() / 2);
        addObject(text, getWidth() / 2, text.getImage().getHeight() / 2);
        getBackground().drawImage(scoreText, (getWidth() - scoreText.getWidth()) / 2, getHeight() - scoreText.getHeight());

        showText("Press \'Run\'\n to start", 100, getWidth() / 2, getHeight() / 2);
    }

    /**
     * Act - do whatever the MyWorld wants to do. This method is called whenever the 'Act'
     * or 'Run' button gets pressed in the environment.
     */
    @Override
    public void act() {
        showText(null, 100, getWidth() / 2, getHeight() / 2);
    }

    /**
     * This method displays the score board.
     */
    private void highScore() {
        if (UserInfo.isStorageAvailable()) {
            Scores scores = new Scores(this, true) {
                @Override
                public void worldChanged() {
                    try {
                        // Set System L&F
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        // handle exception
                    }
                    JPanel worldPanel = WorldHandler.getInstance().getWorldCanvas();
                    worldPanel.removeAll();
                    worldPanel.setLayout(new GridBagLayout());
                    JButton scoreButton = new JButton("Scores");
                    worldPanel.add(scoreButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                    SwingUtilities.updateComponentTreeUI(worldPanel);
                    SwingUtilities.updateComponentTreeUI(scoreButton);
                    scoreButton.addActionListener((ActionEvent e) -> {
                        highScore();
                    });
                }
            };
            Greenfoot.setWorld(scores);
        }
    }

    /**
     * This method sets the new score for the user. It (will) also sets the date and time
     * that the high score was achieved.
     *
     * @param myInfo the user info to edit the score
     */
    private void setScore(UserInfo myInfo) {
        myInfo.setScore(myInfo.getUserName().equals("Yehuda") ? 2471 : Greenfoot.getRandomNumber(2500) + 1);
        myInfo.store();
    }

    /**
     * This method is my own showText method in which you can also choose the size.
     *
     * @param text the text to be displayed
     * @param size the size of the text
     * @param x    the x-coordinate that the text should be placed in the World
     * @param y    the y-coordinate that the text should be placed in the World
     */
    public void showText(String text, int size, int x, int y) {
        GreenfootImage textImage = null;
        if (text != null && !"".equals(text)) {
            textImage = new GreenfootImage(text, size, Color.WHITE, new Color(0, 0, 0, 0), Color.BLACK);
        }
        message.setImage(textImage);
        addObject(message, x, y);
    }

    /**
     * This method automatically gets called when the 'Pause' button gets pressed which it
     * then displays "Press 'Run'".
     */
    @Override
    public void stopped() {
        showText("Press \'Run\'\nto resume", 100, getWidth() / 2, getHeight() / 2);
    }

    /**
     * This method automatically gets called when the 'Run' button gets pressed which it
     * then removes the text "Press 'Run'".
     */
    @Override
    public void started() {
        showText(null, 100, getWidth() / 2, getHeight() / 2);
    }
}
