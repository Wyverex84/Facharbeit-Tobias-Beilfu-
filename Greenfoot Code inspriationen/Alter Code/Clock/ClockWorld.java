
import greenfoot.World;
import greenfoot.core.WorldHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Write a description of class ClockWorld here.
 *
 * @author Yehuda
 * @version 4/19/2017
 */
public class ClockWorld extends World {

    static final String[] dayString = new String[]{"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    static final String[] monthString = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", "Undecimber"};
    static final String[] ampmString = new String[]{"AM", "PM"};
    private static final Logger LOG = Logger.getLogger(ClockWorld.class.getName());

    private JPanel worldPanel = WorldHandler.getInstance().getWorldCanvas();

    private JTabbedPane clockPane = new JTabbedPane();
    private JPanel clockPanel = new JPanel();
    private JPanel stopwatchPanel = new JPanel();
    private JPanel timerPanel = new JPanel();

    private JLabel clockLabel = new JLabel();
    private JLabel dayLabel = new JLabel();
    private JLabel dateLabel = new JLabel();
    private final Timer clockTimer;

    private JButton startButton = new JButton();
    private JButton lapButton = new JButton();
    private JLabel stopwatchElapsedLabel = new JLabel();
    private final Timer stopwatchTimer;
    private long stopwatchStartTime;
    private long stopwatchPausedTime;
    private int stopwatchElapsedTime;
    private int stopwatchHours;
    private int stopwatchMinutes;
    private int stopwatchSeconds;
    private int stopwatchHundreds;
    private JScrollPane lapScrollPane = new JScrollPane();
    private JList lapList = new JList();
    private DefaultListModel lapListModel = new DefaultListModel();

    private JLabel hourLabel = new JLabel();
    private JLabel minuteLabel = new JLabel();
    private JSpinner hourSpinner = new JSpinner();
    private JSpinner minuteSpinner = new JSpinner();
    private int hourValue = 0;
    private int minuteValue = 0;
    private JButton pauseButton = new JButton();
    private JButton cancelButton = new JButton();
    private JLabel timerLabel = new JLabel();
    private Timer timerTimer;
    private long timerStartTime = 0;
    private long timerPausedTime = 0;
    private int timerElapsedTime = 00;
    private int timerHours = 00;
    private int timerMinutes = 00;
    private int timerSeconds = 00;
    private int timerHundreds = 00;

    private DecimalFormat numberFormat = new DecimalFormat("00");
    private Font dateTimeFont = new Font("Calibri", Font.BOLD, 22);

    /**
     * Constructor for objects of class ClockWorld.
     *
     */
    public ClockWorld() {
        // Create a new world with 215x265 cells with a cell size of 1x1 pixels.
        super(215, 265, 1);

        worldPanel.removeAll();
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }
        SwingUtilities.updateComponentTreeUI(worldPanel);

        GridBagConstraints gridConstraints = new GridBagConstraints();
        clockPane.setPreferredSize(new Dimension(200, 250));
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        worldPanel.add(clockPane, gridConstraints);
        SwingUtilities.updateComponentTreeUI(clockPane);

        // clock panel
        clockPanel.setLayout(new GridBagLayout());
        clockPanel.setBackground(Color.BLACK);

        dayLabel.setText("");
        dayLabel.setFont(dateTimeFont);
        dayLabel.setForeground(Color.WHITE);
        dayLabel.setBackground(Color.BLACK);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.insets = new Insets(0, 0, 5, 0);
        clockPanel.add(dayLabel, gridConstraints);
        SwingUtilities.updateComponentTreeUI(dayLabel);

        dateLabel.setText("");
        dateLabel.setFont(dateTimeFont);
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setBackground(Color.BLACK);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.insets = new Insets(5, 0, 5, 0);
        clockPanel.add(dateLabel, gridConstraints);
        SwingUtilities.updateComponentTreeUI(dateLabel);

        clockLabel.setText("");
        clockLabel.setFont(dateTimeFont);
        clockLabel.setForeground(Color.WHITE);
        clockLabel.setBackground(Color.BLACK);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        gridConstraints.insets = new Insets(5, 0, 0, 0);
        clockPanel.add(clockLabel, gridConstraints);
        SwingUtilities.updateComponentTreeUI(clockLabel);
        clockTimerActionPerformed(null);

        clockPane.add("Date - Time", clockPanel);
        SwingUtilities.updateComponentTreeUI(clockPanel);

        // stopwatch panel
        stopwatchPanel.setLayout(new GridBagLayout());

        stopwatchElapsedLabel.setText("00:00:00.00");
        stopwatchElapsedLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(0, 0, 5, 0);
        stopwatchPanel.add(stopwatchElapsedLabel, gridConstraints);
        SwingUtilities.updateComponentTreeUI(stopwatchElapsedLabel);

        startButton.setText("Start");
        startButton.setOpaque(true);
        startButton.setFocusPainted(false);
        startButton.setForeground(new Color(0, 200, 0, 255));
        startButton.setBackground(new Color(0, 128, 0, 255));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.insets = new Insets(5, 5, 0, 0);
        stopwatchPanel.add(startButton, gridConstraints);
        SwingUtilities.updateComponentTreeUI(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButtonActionPerformed(e);
            }
        });

        lapButton.setText("Lap");
        lapButton.setEnabled(false);
        lapButton.setFocusPainted(false);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.insets = new Insets(5, 0, 0, 5);
        stopwatchPanel.add(lapButton, gridConstraints);
        SwingUtilities.updateComponentTreeUI(lapButton);
        lapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lapButtonActionPerformed(e);
            }
        });

        lapScrollPane.setPreferredSize(new Dimension(150, 100));
        lapScrollPane.setViewportView(lapList);
        lapList.setFont(new Font("Arial", Font.PLAIN, 12));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(10, 0, 0, 0);
        stopwatchPanel.add(lapScrollPane, gridConstraints);
        SwingUtilities.updateComponentTreeUI(lapScrollPane);

        clockPane.add("Stopwatch", stopwatchPanel);
        SwingUtilities.updateComponentTreeUI(stopwatchPanel);

        // timer panel
        timerPanel.setLayout(new GridBagLayout());

        timerLabel.setText("00:00.00");
        timerLabel.setVisible(false);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 4;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.insets = new Insets(0, 0, 5, 0);
        timerPanel.add(timerLabel, gridConstraints);
        SwingUtilities.updateComponentTreeUI(timerLabel);

        hourSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        hourSpinner.setVisible(true);
        ((JSpinner.DefaultEditor) hourSpinner.getEditor()).getTextField().setHorizontalAlignment(SwingConstants.CENTER);
        ((JSpinner.DefaultEditor) hourSpinner.getEditor()).getTextField().setEditable(false);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.insets = new Insets(0, 0, 5, 5);
        timerPanel.add(hourSpinner, gridConstraints);
        SwingUtilities.updateComponentTreeUI(hourSpinner);
        hourSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                spinnerStateChanged(e);
            }
        });

        hourLabel.setText("hours");
        hourLabel.setVisible(true);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.insets = new Insets(0, 0, 5, 5);
        timerPanel.add(hourLabel, gridConstraints);
        SwingUtilities.updateComponentTreeUI(hourLabel);

        minuteSpinner.setModel(new SpinnerNumberModel(1, -1, 60, 1));
        minuteSpinner.setVisible(true);
        ((JSpinner.DefaultEditor) minuteSpinner.getEditor()).getTextField().setHorizontalAlignment(SwingConstants.CENTER);
        ((JSpinner.DefaultEditor) minuteSpinner.getEditor()).getTextField().setEditable(false);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 0;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.insets = new Insets(0, 5, 5, 5);
        timerPanel.add(minuteSpinner, gridConstraints);
        SwingUtilities.updateComponentTreeUI(minuteSpinner);
        minuteSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                spinnerStateChanged(e);
            }
        });

        minuteLabel.setText("min");
        minuteLabel.setVisible(true);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 3;
        gridConstraints.gridy = 0;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.insets = new Insets(0, 0, 5, 0);
        timerPanel.add(minuteLabel, gridConstraints);
        SwingUtilities.updateComponentTreeUI(minuteLabel);

        cancelButton.setText("Cancel");
        cancelButton.setEnabled(false);
        cancelButton.setFocusPainted(false);
        //cancelButton.setUI(UIManager.);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(5, 0, 0, 5);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        timerPanel.add(cancelButton, gridConstraints);
        SwingUtilities.updateComponentTreeUI(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelButtonActionPerformed(e);
            }
        });

        pauseButton.setText("Start");
        pauseButton.setForeground(new Color(0, 200, 0, 255));
        pauseButton.setBackground(new Color(0, 128, 0, 255));
        pauseButton.setOpaque(true);
        pauseButton.setFocusPainted(false);
        gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(5, 5, 0, 0);
        gridConstraints.anchor = GridBagConstraints.CENTER;
        timerPanel.add(pauseButton, gridConstraints);
        SwingUtilities.updateComponentTreeUI(pauseButton);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseButtonActionPerformed(e);
            }
        });

        clockPane.add("Timer", timerPanel);
        SwingUtilities.updateComponentTreeUI(timerPanel);

        clockTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clockTimerActionPerformed(e);
            }
        });

        stopwatchTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopwatchTimerActionPerformed(e);
            }
        });

        timerTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerTimerActionPerformed(e);
            }
        });

        clockTimer.start();

        lapList.setModel(lapListModel);
    }

    /**
     * This method gets called automatically by the clockTimer at every interval
     * that it's initialized to.
     *
     * @param e the ActionEvent that called the method
     */
    private void clockTimerActionPerformed(ActionEvent e) {
        Calendar rightNow = Calendar.getInstance();
        int day = rightNow.get(Calendar.DAY_OF_WEEK);
        int month = rightNow.get(Calendar.MONTH);
        int date = rightNow.get(Calendar.DATE);
        int year = rightNow.get(Calendar.YEAR);

        int ampm = rightNow.get(Calendar.AM_PM);
        int hour = rightNow.get(Calendar.HOUR);
        int minute = rightNow.get(Calendar.MINUTE);
        int second = rightNow.get(Calendar.SECOND);

        dayLabel.setText(dayString[day] + ",");
        dateLabel.setText(monthString[month] + " " + date + ", " + year);
        clockLabel.setText(hour + ":" + numberFormat.format(minute) + ":" + numberFormat.format(second) + " " + ampmString[ampm]);
    }

    /**
     * This method gets called automatically when the startButton is pressed.
     *
     * @param e the ActionEvent that called the method
     */
    private void startButtonActionPerformed(ActionEvent e) {
        if (startButton.getText().equals("Start")) {
            if (stopwatchElapsedLabel.getText().equals("00:00:00.00")) {
                lapButton.setEnabled(true);
                lapListModel.addElement("Lap 1  00:00:00.00");
                stopwatchStartTime = System.currentTimeMillis();
            } else {
                stopwatchStartTime += (System.currentTimeMillis() - stopwatchPausedTime);
            }
            stopwatchTimer.start();
            startButton.setText("Stop");
            startButton.setForeground(new Color(255, 0, 0, 255));
            startButton.setBackground(new Color(128, 0, 0, 255));
            lapButton.setText("Lap");
        } else if (startButton.getText().equals("Stop")) {
            stopwatchPausedTime = System.currentTimeMillis();
            stopwatchTimer.stop();
            startButton.setText("Start");
            startButton.setForeground(new Color(0, 200, 0, 255));
            startButton.setBackground(new Color(0, 128, 0, 255));
            lapButton.setText("Reset");
        }
    }

    /**
     * This method gets called automatically when the lapButton is pressed.
     *
     * @param e the ActionEvent that called the method
     */
    private void lapButtonActionPerformed(ActionEvent e) {
        if (lapButton.getText().equals("Reset")) {
            stopwatchTimer.stop();
            stopwatchElapsedLabel.setText("00:00:00.00");
            startButton.setText("Start");
            lapButton.setText("Lap");
            lapButton.setEnabled(false);
            startButton.setForeground(new Color(0, 200, 0, 255));
            startButton.setBackground(new Color(0, 128, 0, 255));
            stopwatchElapsedTime = 00;
            stopwatchHundreds = 00;
            stopwatchSeconds = 00;
            stopwatchMinutes = 00;
            stopwatchHours = 00;
            lapListModel.clear();
        } else if (lapButton.getText().equals("Lap")) {
            lapListModel.add(0, "Lap " + (lapListModel.getSize() + 1) + " " + stopwatchElapsedLabel.getText());
        }

    }

    /**
     * This method gets called automatically by the stopwatcTimer at the
     * intervals that it's initialized to.
     *
     * @param e the ActionEvent that called the method
     */
    private void stopwatchTimerActionPerformed(ActionEvent e) {
        stopwatchElapsedTime = (int) (System.currentTimeMillis() - stopwatchStartTime);
        stopwatchHours = stopwatchElapsedTime / 3600000;
        stopwatchElapsedTime -= stopwatchHours * 3600000;
        stopwatchMinutes = stopwatchElapsedTime / 60000;
        stopwatchElapsedTime -= stopwatchMinutes * 60000;
        stopwatchSeconds = stopwatchElapsedTime / 1000;
        stopwatchElapsedTime -= stopwatchSeconds * 1000;
        stopwatchHundreds = stopwatchElapsedTime / 10;
        stopwatchElapsedLabel.setText(numberFormat.format(stopwatchHours) + ":" + numberFormat.format(stopwatchMinutes) + ":" + numberFormat.format(stopwatchSeconds) + "." + numberFormat.format(stopwatchHundreds));
        lapListModel.set(0, "Lap " + lapListModel.getSize() + "  " + stopwatchElapsedLabel.getText());
    }

    /**
     * This method gets called automatically when the cancelButton gets clicked.
     *
     * @param e the ActionEvent that called the method
     */
    private void cancelButtonActionPerformed(ActionEvent e) {
        pauseButton.setText("Start");
        pauseButton.setForeground(new Color(0, 200, 0, 255));
        pauseButton.setBackground(new Color(0, 128, 0, 255));
        cancelButton.setEnabled(false);
        timerLabel.setVisible(false);
        hourSpinner.setVisible(true);
        hourLabel.setVisible(true);
        minuteSpinner.setVisible(true);
        minuteLabel.setVisible(true);
        timerLabel.setText("00:00.00");
        timerTimer.stop();
        timerTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerTimerActionPerformed(e);
            }
        });
    }

    /**
     * This method gets called automatically when the pauseButton gets clicked.
     *
     * @param e the ActionEvent that called the method
     */
    private void pauseButtonActionPerformed(ActionEvent e) {
        if (pauseButton.getText().equals("Start")) {
            pauseButton.setText("Pause");
            pauseButton.setForeground(new Color(255, 128, 0));
            pauseButton.setBackground(new Color(200, 100, 0));
            cancelButton.setEnabled(true);
            hourSpinner.setVisible(false);
            hourLabel.setVisible(false);
            minuteSpinner.setVisible(false);
            minuteLabel.setVisible(false);
            timerLabel.setVisible(true);
            if (timerLabel.getText().equals("00:00.00")) {
                timerStartTime = System.currentTimeMillis();
                hourValue = Integer.parseInt(hourSpinner.getValue().toString());
                minuteValue = Integer.parseInt(minuteSpinner.getValue().toString());
            } else {
                timerStartTime += (System.currentTimeMillis() - timerPausedTime);
            }
            timerTimer.start();
        } else if (pauseButton.getText().equals("Pause")) {
            pauseButton.setText("Start");
            pauseButton.setForeground(new Color(0, 200, 0, 255));
            pauseButton.setBackground(new Color(0, 128, 0, 255));
            timerPausedTime = System.currentTimeMillis();
            timerTimer.stop();
        }
    }

    /**
     * This method gets called automatically every time the state of the two
     * timer spinners get changed.
     *
     * @param e the ActionEvent that called the method
     */
    private void spinnerStateChanged(ChangeEvent e) {
        hourValue = Integer.parseInt(hourSpinner.getValue().toString());
        minuteValue = Integer.parseInt(minuteSpinner.getValue().toString());
        if (minuteValue == 60 && hourValue != 0) {
            minuteSpinner.setValue(0);
        } else if (minuteValue == -1) {
            minuteSpinner.setValue(59);
        }
        if (hourValue == 0 && minuteValue == 0) {
            minuteSpinner.setValue(59);
        } else if (hourValue == 0 && minuteValue == 60) {
            minuteSpinner.setValue(1);
        }

        if (hourValue == 1) {
            hourLabel.setText("hour");
        } else {
            hourLabel.setText("hours");
        }
        hourValue = Integer.parseInt(hourSpinner.getValue().toString());
        minuteValue = Integer.parseInt(minuteSpinner.getValue().toString());
    }

    /**
     * This method gets called automatically at the intervals that the
     * timerTimer is set to.
     *
     * @param e the ActionEvent that called the method
     */
    private void timerTimerActionPerformed(ActionEvent e) {
        timerElapsedTime = (int) (System.currentTimeMillis() - timerStartTime);
        timerElapsedTime = ((hourValue * 3600000) + (minuteValue * 60000)) - timerElapsedTime;
        boolean timerComplete = timerElapsedTime <= 0;
        timerHours = timerElapsedTime / 3600000;
        timerElapsedTime -= timerHours * 3600000;
        timerMinutes = timerElapsedTime / 60000;
        timerElapsedTime -= timerMinutes * 60000;
        timerSeconds = timerElapsedTime / 1000;
        timerElapsedTime -= timerSeconds * 1000;
        timerHundreds = timerElapsedTime / 10;
        if (timerMinutes <= 0 && timerHours <= 0) {
            timerLabel.setText(numberFormat.format(timerSeconds) + "." + numberFormat.format(timerHundreds));
        } else if (timerHours <= 0) {
            timerLabel.setText(numberFormat.format(timerMinutes) + ":" + numberFormat.format(timerSeconds) + "." + numberFormat.format(timerHundreds));
        } else if (timerHours > 0) {
            timerLabel.setText(numberFormat.format(timerHours) + ":" + numberFormat.format(timerMinutes) + ":" + numberFormat.format(timerSeconds) + "." + numberFormat.format(timerHundreds));
        }
        if (timerComplete) {
            cancelButton.doClick();
            Toolkit.getDefaultToolkit().beep();
            Timer beepTimer = new Timer(3500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Toolkit.getDefaultToolkit().beep();
                }
            });
            beepTimer.start();
            JOptionPane.showMessageDialog(null, "Timer Done", "Timer", JOptionPane.PLAIN_MESSAGE);
            beepTimer.stop();
        }
    }
}
