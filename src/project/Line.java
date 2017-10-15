package project;

import java.util.ArrayList;
import java.awt.*;

public class Line {
    
    ArrayList<Point> points;
    
    private boolean directionDrown;
    private Charge sourceCharge;
    
    public Line(Point p) {
        this.points = new ArrayList<>();
        points.add(p);
        directionDrown = false;
        this.sourceCharge = p.getSourceCharge();
    }
    
    public void addPoint(Point p) {
        this.points.add(p);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
    
    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLUE);
        for (int i = 0; i < points.size(); i ++) {
            Point p = points.get(i);            
            int x = (int)Math.round(p.getX());
            int y = (int)Math.round(p.getY());

            if (i > points.size() / 3
                    && ! this.directionDrown) {
                double dirX = p.totalForce().getX();
                double dirY = p.totalForce().getY();
                Arrow arrow = new Arrow(x, y, dirX, dirY);
                arrow.draw(g);
                this.directionDrown = true;
            }
            g.fillOval(x, y, 2, 2);
            
        }
        g.setColor(c);
    }
    
    private class Arrow {
        
        private static final double LENGTH = 18;
        private static final double ALPHA = Math.PI / 12;
        
        private int x;
        private int y;
        private double dirX;
        private double dirY;
        private double beta;
        private boolean vertical;
        
        
        public Arrow(int x, int y, double dirX, double dirY) {
            this.x = x;
            this.y = y;
            this.dirX = dirX;
            this.dirY = dirY;
            if (dirX != 0) {
                this.beta = Math.atan(dirY / dirX);
                if (dirX < 0 && dirY >= 0) {
                    beta = Math.PI + beta;
                } else if (dirX < 0 && dirY <= 0) {
                    beta = Math.PI + beta;
                }
                this.vertical = false;
            } else {
                this.vertical = true;
            }
        }

        public void draw(Graphics g) {
            Color c = g.getColor();
            
            g.setColor(Color.BLUE);
            
            double theta = 0;
            double x1 = 0;
            double y1 = 0;
            double x2 = 0;
            double y2 = 0;
            
            if (this.vertical) {
                x1 = x - LENGTH * Math.tan(ALPHA);
                x2 = x + LENGTH * Math.tan(ALPHA);
                if (dirY > 0) {
                    y1 = y - LENGTH;
                    y2 = y - LENGTH;
                } else {
                    y1 = y + LENGTH;
                    y2 = y + LENGTH;
                }
            } else {
                theta = beta - ALPHA;
                double deltaX = LENGTH * Math.cos(theta);
                double deltaY = LENGTH * Math.sin(theta);
                x1 = x - deltaX;
                y1 = y - deltaY;
                
                theta = beta + ALPHA;
                deltaX = LENGTH * Math.cos(theta);
                deltaY = LENGTH * Math.sin(theta);
                x2 = x - deltaX;
                y2 = y - deltaY;
            }
            
            int[] xPoints = {(int)Math.round(x1), (int)Math.round(x2), x};
            int[] yPoints = {(int)Math.round(y1), (int)Math.round(y2), y};
            g.fillPolygon(xPoints, yPoints, 3);
            
            g.setColor(c);
        }
    }
}
