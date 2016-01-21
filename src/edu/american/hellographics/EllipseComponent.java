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

    public EllipseComponent(double x, double y, double width, double height) {
        ellipse = new Ellipse2D.Double(x, y, width, height);

        this.setLocation(0, 0);
        this.setSize(400, 400);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // really the Graphics object is a Graphics2D
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.draw(ellipse);
    }
}
