package project;

import java.awt.*;
import java.util.ArrayList;

public class Charge {
    
    private static final int DIAMETER = 16;
    private static final int RADIUS = 8;
    private static final int DENSITY_UNIT = 12;
    private static final int CHARGE_DENSITY = 50;
    
    private int lineDensity;
    
    private int x;
    private int y;
    private Point center;
    private int magnitude;
    private boolean positive;
    private ArrayList<Point> initialPoints;
    private Test test;
    
    public Charge(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.center = new Point(x, y, this, test);
        this.positive = value > 0;
        this.magnitude = Math.abs(value);
        this.initialPoints = new ArrayList<>();
        this.lineDensity = this.magnitude * DENSITY_UNIT;
        this.magnitude *= CHARGE_DENSITY;
        this.setInitialPoints();
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.drawOval(x - RADIUS, y - RADIUS, DIAMETER, DIAMETER);
        g.drawLine(x - RADIUS + 2, y, x + RADIUS - 2, y);
        if (this.positive) {
            g.drawLine(x, y - RADIUS + 2, x, y + RADIUS - 2);
        }
        g.setColor(c);
    }
    
    public boolean contains(Point p) {
        double d = Math.sqrt(Math.pow(p.getX() - x, 2) 
                + Math.pow(p.getY() - y, 2));
        return d < RADIUS;
    }
    
    private void setInitialPoints() {
        double unitAngle = 2 * Math.PI / lineDensity;
        for (int i = 0; i < lineDensity; i ++) {
            double theta = unitAngle * i;
            double deltaX = RADIUS * Math.cos(theta);
            double deltaY = RADIUS * Math.sin(theta);
            Point p = new Point(x + deltaX, y + deltaY, this, test);
            this.initialPoints.add(p);
        } 
    }

    public ArrayList<Point> getInitialPoints() {
        return initialPoints;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public project.Point getCenter() {
        return center;
    }

    public boolean isPositive() {
        return positive;
    }
}
