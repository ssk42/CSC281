package edu.american.hellographics;

import javax.swing.*;

/**
 * Show some of the basics of Swing
 *
 * @author knappa
 * @version 1.0
 */

public class HelloGraphics {

    /**
     * where it all begins
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame();

        frame.setSize(400, 400);

        frame.setTitle("I was framed!");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < 20; i++)
            //frame.add(new BadEllipseComponent(
            frame.add(new EllipseComponent(
                    (Math.random() * 200)+40,
                    (Math.random() * 200)+40,
                    (Math.random() * 100)+50,
                    (Math.random() * 100)+50));

        frame.setVisible(true);

    }

}
