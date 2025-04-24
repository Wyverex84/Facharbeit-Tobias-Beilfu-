/*
 * Scores.java
 */
import greenfoot.Greenfoot;
import greenfoot.UserInfo;
import greenfoot.World;
import greenfoot.actions.PauseSimulationAction;
import greenfoot.actions.ResetWorldAction;
import greenfoot.core.Simulation;
import greenfoot.core.WorldHandler;
import greenfoot.gui.WorldCanvas;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class displays all the users and their scores nicely. The way to do it is just to
 * set the active world as this one, and set a return world.
 *
 * @author Yehuda
 * @version 4/21/2017 - MM/DD/YYYY
 */
public class Scores extends World {

    private WorldCanvas worldPanel;
    private JProgressBar loadingProgressBar = new JProgressBar();
    private SwingWorker<Void, Void> task;
    private DefaultListModel<String> usersListModel = new DefaultListModel<>();
    private JList<String> usersJList = new JList<>(usersListModel);
    private JScrollPane usersScrollPane = new JScrollPane(usersJList);
    private JButton backButton = new JButton("Back"),
            refreshButton = new JButton("Refresh"),
            findMeButton = new JButton("Find me");
    private JTextField searchTextField = new JTextField(15);
    private final World homeWorld;
    private List<UserInfo> usersList = UserInfo.getTop(0);
    private int simulationSpeed;

    /**
     * Constructor for objects of class Scores. Sets the back button to go to a new blank
     * world of 600x400. Cannot be called from other classes.
     *
     */
    private Scores() {
        this(new World(600, 400, 1) {
        });
    }

    /**
     * Constructor for objects of class Scores. Sets the back button to go to the
     * specified world, and does not go to the current user in the list.
     *
     * @param homeWorld the world to go to when the "Back" button is pressed
     */
    public Scores(World homeWorld) {
        this(homeWorld, false);
    }

    /**
     * Constructor for objects of class Scores. Sets the "Back" button to go to the
     * specified world, and goes to the current user in the list if {@code findMe} is
     * true.
     *
     * @param homeWorld the world to go to when the "Back" button is pressed
     * @param findMe    if true, then the list will go straight to the current user
     */
    public Scores(World homeWorld, boolean findMe) {
        super(WorldHandler.getInstance().getWorld().getWidth(),
                WorldHandler.getInstance().getWorld().getHeight(), 1);
        simulationSpeed = Simulation.getInstance().getSpeed();
        Greenfoot.setSpeed(100);

        worldPanel = WorldHandler.getInstance().getWorldCanvas();
        worldPanel.removeAll();
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }
        SwingUtilities.updateComponentTreeUI(worldPanel);
        worldPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.weightx = 1.0;
        gridConstraints.weighty = 1.0;
        gridConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        worldPanel.add(backButton, gridConstraints);
        SwingUtilities.updateComponentTreeUI(backButton);
        backButton.addActionListener((ActionEvent e) -> {
            backButtonActionPerformed(e);
        });

        loadingProgressBar.setString("Loading...");
        loadingProgressBar.setStringPainted(true);
        gridConstraints = new GridBagConstraints();
        gridConstraints.weightx = 1.0;
        gridConstraints.weighty = 1.0;
        gridConstraints.anchor = GridBagConstraints.LAST_LINE_START;
        gridConstraints.gridx = 1;
        gridConstraints.gridwidth = 3;
        gridConstraints.gridy = 1;
        int width = (int) (getWidth() * .75 / 2 - loadingProgressBar.getWidth() / 2);
        int height = (int) (getHeight() * .75 / 2 - loadingProgressBar.getHeight() / 2);
        gridConstraints.insets = new Insets(height, width, height, width);
        worldPanel.add(loadingProgressBar, gridConstraints);
        SwingUtilities.updateComponentTreeUI(usersScrollPane);

        usersScrollPane.setVisible(false);
        usersScrollPane.setPreferredSize(new Dimension((int) (getWidth() * .75), (int) (getHeight() * .75)));
        usersJList.setFont(new Font("Times New Roman", Font.BOLD, 18));
        usersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersJList.setCellRenderer(new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                return usersJListGetListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        gridConstraints = new GridBagConstraints();
        gridConstraints.weightx = 1.0;
        gridConstraints.weighty = 1.0;
        gridConstraints.anchor = GridBagConstraints.LAST_LINE_START;
        gridConstraints.gridx = 1;
        gridConstraints.gridwidth = 3;
        gridConstraints.gridy = 1;
        worldPanel.add(usersScrollPane, gridConstraints);
        SwingUtilities.updateComponentTreeUI(usersScrollPane);

        gridConstraints = new GridBagConstraints();
        gridConstraints.weighty = 1.0;
        gridConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 2;
        worldPanel.add(refreshButton, gridConstraints);
        SwingUtilities.updateComponentTreeUI(refreshButton);
        refreshButton.addActionListener((ActionEvent e) -> {
            refreshButtonActionPerformed(e);
        });

        gridConstraints = new GridBagConstraints();
        gridConstraints.weighty = 1.0;
        gridConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 2;
        worldPanel.add(findMeButton, gridConstraints);
        SwingUtilities.updateComponentTreeUI(findMeButton);
        findMeButton.addActionListener((ActionEvent e) -> {
            findMeButtonActionPerformed(e);
        });

        searchTextField.setToolTipText("Search...");
        gridConstraints = new GridBagConstraints();
        gridConstraints.weighty = 1.0;
        gridConstraints.anchor = GridBagConstraints.PAGE_START;
        gridConstraints.gridx = 3;
        gridConstraints.gridy = 2;
        worldPanel.add(searchTextField, gridConstraints);
        SwingUtilities.updateComponentTreeUI(searchTextField);
        searchTextField.addActionListener((ActionEvent e) -> {
            searchTextFieldUpdate(null);
        });
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                // Gives notification that an attribute or set of attributes changed.
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                searchTextFieldUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchTextFieldUpdate(e);
            }

        });
        PauseSimulationAction.getInstance().setEnabled(false);
        ResetWorldAction.getInstance().setEnabled(false);
        this.homeWorld = homeWorld;
        refreshButton.doClick();
        if (findMe) {
            findMeButton.doClick();
        }
    }

    /**
     * This method gets called automatically when the backButton gets pressed. It goes to
     * the specified world.
     *
     * @param e the ActionEvent that called the method
     */
    private void backButtonActionPerformed(ActionEvent e) {
        if (homeWorld instanceof Scores) {
            JOptionPane.showMessageDialog(null, "The world that you're trying to"
                    + " go to,\nis the one that you find yourself in.", "No Outlet", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        worldPanel.removeAll();
        PauseSimulationAction.getInstance().setEnabled(true);
        ResetWorldAction.getInstance().setEnabled(true);
        Greenfoot.setSpeed(simulationSpeed);
        worldChanged();
        Greenfoot.setWorld(homeWorld);
    }

    /**
     * Return a component that has been configured to display the specified value. That
     * component's <code>paint</code> method is then called to "render" the cell. If it is
     * necessary to compute the dimensions of a list because the list cells do not have a
     * fixed size, this method is called to generate a component on which
     * <code>getPreferredSize</code> can be invoked.
     *
     * @param list         The JList we're painting.
     * @param value        The value returned by list.getModel().getElementAt(index).
     * @param index        The cells index.
     * @param isSelected   True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     *
     * @return A component whose paint() method will render the specified value.
     *
     * @see JList
     * @see ListSelectionModel
     * @see ListModel
     */
    private Component usersJListGetListCellRendererComponent(JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        JLabel theLabel = new JLabel();
        theLabel.setText(value.toString());
        theLabel.setIcon(new ImageIcon(usersList.get(index).getUserImage().getAwtImage()));
        if (isSelected) {
            theLabel.setBackground(list.getSelectionBackground());
            theLabel.setForeground(list.getSelectionForeground());
        } else {
            theLabel.setBackground(list.getBackground());
            theLabel.setForeground(list.getForeground());
        }
        theLabel.setEnabled(list.isEnabled());
        theLabel.setFont(list.getFont());
        theLabel.setOpaque(true);
        return theLabel;
    }

    /**
     * This method gets called automatically when the refreshButton gets clicked. It
     * reloads the contents of the list.
     *
     * @param e the ActionEvent that called this method
     */
    private void refreshButtonActionPerformed(ActionEvent e) {
        worldPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        usersListModel.clear();
        usersList.clear();
        List<UserInfo> newList = UserInfo.getTop(0);
        refreshButton.setEnabled(false);
        findMeButton.setEnabled(false);
        searchTextField.setEnabled(false);
        usersScrollPane.setVisible(false);
        loadingProgressBar.setVisible(true);
        task = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                int progress = 0;
                setProgress(0);
                int increment = 100 / newList.size();
                for (int i = 0; i < newList.size(); i++) {
                    usersList.add(newList.get(i));
                    usersListModel.addElement(newList.get(i).getRank() + "."
                            + "    ".substring((int) Math.log10(newList.get(i).getRank()))
                            + newList.get(i).getScore() + "  " + newList.get(i).getUserName());
                    progress += i == newList.size() - 1 ? 100 - progress : increment;
                    setProgress(progress);
                }
                return null;
            }

            @Override
            protected void done() {
                super.done();
                worldPanel.setCursor(null);
                loadingProgressBar.setVisible(false);
                usersScrollPane.setVisible(true);
                refreshButton.setEnabled(true);
                findMeButton.setEnabled(true);
                searchTextField.setEnabled(true);
            }
        };
        task.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if ("progress" == e.getPropertyName()) {
                    int progress = (Integer) e.getNewValue();
                    loadingProgressBar.setValue(progress);
                }
            }
        });
        task.execute();
    }

    /**
     * This method gets called automatically when the findMeButton is clicked. It sets the
     * selected item in the list as the current user's.
     *
     * @param e the ActionEvent that called this method
     */
    private void findMeButtonActionPerformed(ActionEvent e) {
        usersJList.setSelectedValue(UserInfo.getMyInfo().getRank() + "."
                + "    ".substring((int) Math.log10(UserInfo.getMyInfo().getRank()))
                + UserInfo.getMyInfo().getScore() + "  " + UserInfo.getMyInfo().getUserName(), true);
    }

    /**
     * This method is called automatically when a change has been made the the
     * searchTextField. It makes the list only show items that the name of that user
     * starts with the the text in the field.
     *
     * @param e the DocumentEvent that called this method
     */
    private void searchTextFieldUpdate(DocumentEvent e) {
        usersListModel.clear();
        usersList.clear();
        for (Object obj : UserInfo.getTop(0)) {
            if (((UserInfo) obj).getUserName().length() >= searchTextField.getText().length()) {
                if (((UserInfo) obj).getUserName().substring(0, searchTextField.getText().length()).compareToIgnoreCase(searchTextField.getText()) == 0) {
                    usersList.add(((UserInfo) obj));
                    usersListModel.addElement(((UserInfo) obj).getRank() + "."
                            + "    ".substring((int) Math.log10(((UserInfo) obj).getRank()))
                            + ((UserInfo) obj).getScore() + "  " + ((UserInfo) obj).getUserName());
                }
            }
        }
    }

    /**
     * This method gets called when the world is about to get switched to the one
     * specified for the "Back" button. By default it's empty, and is meant to be
     * overridden.
     */
    public void worldChanged() {
    }
}
