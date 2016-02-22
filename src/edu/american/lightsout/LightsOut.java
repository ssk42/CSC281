package edu.american.lightsout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple game to demonstrate concepts in Swing
 *
 * @author knappa
 * @version 1.0
 */
public class LightsOut extends JPanel {

    public static int SIZE = 10;
    private final GridElement[][] gridElements;

    public LightsOut() {

        this.setLayout(new BorderLayout());

        // set up the Randomize button
        JButton randomizeButton = new JButton("Randomize");
        this.add(randomizeButton, BorderLayout.SOUTH);
        randomizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 0; i < SIZE; i++)
                    for (int j = 0; j < SIZE; j++) {
                        gridElements[i][j].randomize();
                    }
            }
        });


        // create a container JPanel for the GridElements
        JPanel buttonGrid = new JPanel();
        this.add(buttonGrid, BorderLayout.CENTER);
        buttonGrid.setLayout(new GridLayout(SIZE, SIZE));

        gridElements = new GridElement[SIZE][SIZE];

        // initialize the GridElements and add them to the buttonGrid
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gridElements[i][j] = new GridElement();
                buttonGrid.add(gridElements[i][j]);
            }
        }

        // connect the GridElements
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i > 0) gridElements[i][j].setNorth(gridElements[i - 1][j]);
                if (i < SIZE - 1) gridElements[i][j].setSouth(gridElements[i + 1][j]);
                if (j > 0) gridElements[i][j].setWest(gridElements[i][j - 1]);
                if (j < SIZE - 1) gridElements[i][j].setEast(gridElements[i][j + 1]);
            }
        }

    }

    /**
     * @param args ignored
     */
    public static void main(String[] args) {

        // set up the "Nimbus" look and feel, if available. Code taken from:
        // https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/nimbus.html
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }


        JFrame frame = new JFrame();
        frame.setTitle("Lights Out!");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new LightsOut());

        // set size based on calls to child components
        frame.pack();

        frame.setVisible(true);

    }

    /**
     * @ /** Class to encapsulate the buttons and their location
     */
    private class GridElement extends JPanel {

        private final String onString = " ", offString = " ";
        private final Color offColor = Color.BLACK, onColor = Color.PINK;
        private JButton button;
        private boolean lightOn;
        private GridElement north, south, east, west;

        /**
         * Default Constructor
         */
        public GridElement() {
            button = new JButton(offString);
            this.add(button);

            lightOn = false;
            updateButton();

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    flipSwitch();
                    if (north != null) north.flipSwitch();
                    if (south != null) south.flipSwitch();
                    if (east != null) east.flipSwitch();
                    if (west != null) west.flipSwitch();
                }
            });

        }

        /**
         * Changes the state of the button to be appropriate for the current on/off state
         */
        private void updateButton() {
            if (lightOn) {
                button.setText(onString);
                button.setBackground(onColor);
            } else {
                button.setText(offString);
                button.setBackground(offColor);
            }
        }

        /**
         * toggle this light (and only this light)
         */
        public void flipSwitch() {
            lightOn = !lightOn;
            updateButton();
        }

        /**
         * @param gridElement The <code>GridElement</code> to the North in the grid
         */
        public void setNorth(GridElement gridElement) {
            this.north = gridElement;
        }

        /**
         * @param gridElement The <code>GridElement</code> to the South in the grid
         */
        public void setSouth(GridElement gridElement) {
            this.south = gridElement;
        }

        /**
         * @param gridElement The <code>GridElement</code> to the East in the grid
         */
        public void setEast(GridElement gridElement) {
            this.east = gridElement;
        }

        /**
         * @param gridElement The <code>GridElement</code> to the West in the grid
         */
        public void setWest(GridElement gridElement) {
            this.west = gridElement;
        }

        /**
         * Change to the opposite on/off value with 50% prob.
         */
        public void randomize() {
            if (Math.random() >= 0.5) this.flipSwitch();
        }
    }

}
