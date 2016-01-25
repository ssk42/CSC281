package edu.american.hellographics;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * a component which draws an ellipse
 *
 * @author knappa
 * @version 1.0
 */
public class EllipseComponent extends JComponent {

    private final Ellipse2D.Double ellipse;

    /**
     * Constructor for EllipseComponent-s
     * @param x top left x coord
     * @param y top left y coord
     * @param width width (x direction)
     * @param height height (y direction)
     */
    public EllipseComponent(double x, double y, double width, double height) {

        this.ellipse = new Ellipse2D.Double(x, y, width, height);

        this.setLocation(0, 0); // sets the location of the top left in the parent component
        this.setSize(400, 400); // sets size to the same as the JFrame we will be contained in

    }

    /**
     * default constructor
     */
    public EllipseComponent() {
        this.ellipse = new Ellipse2D.Double(0,0,400,400);

        this.setLocation(0, 0); // sets the location of the top left in the parent component
        this.setSize(400, 400); // sets size to the same as the JFrame we will be contained in
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // really the Graphics object is a Graphics2D
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.draw(this.ellipse);
    }

}
