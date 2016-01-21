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
public class BadEllipseComponent extends JComponent {

    private final double xCoord, yCoord, rectWidth, rectHeight;

    public BadEllipseComponent(double x, double y, double width, double height) {
        xCoord = x;
        yCoord = y;
        rectWidth = width;
        rectHeight = height;

        this.setLocation(0, 0);
        this.setSize(400, 400);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        //super.paintComponent(graphics);

        // really the Graphics object is a Graphics2D
        Graphics2D graphics2D = (Graphics2D) graphics;

        Ellipse2D.Double ellipse = new Ellipse2D.Double(
                xCoord, yCoord,
                rectWidth, rectHeight);

        graphics2D.draw(ellipse);
    }
}
