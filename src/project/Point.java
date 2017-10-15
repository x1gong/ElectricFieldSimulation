package project;

public class Point {
    
    private static final double PRECISION = 0.0001;
    private static final double STEP_LENGTH = 1;
    
    private double x;
    private double y;
    private Charge sourceCharge;
    
    private Test test;

    public Point(double x, double y, Charge sourceCharge, Test test) {
        this.x = x;
        this.y = y;
        this.sourceCharge = sourceCharge;
        this.test = test;
    }
    
    public double getDistance(Point p) {
        return Math.sqrt(Math.pow(p.x - x, 2.0) + Math.pow(p.y - y, 2.0));
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public boolean hasNextPoint() {
        if (x > MainFrame.WIDTH || x < 0 || y < 20 || y > MainFrame.HEIGHT) {
            return false;
        }
        
        for (Charge q : test.charges) {
            if (q == this.sourceCharge) continue;
            if (q.contains(this)) {
                return false;
            }
        }
        
        return Math.abs(this.totalForce().getValue()) > PRECISION;
    }
    
    public Point nextPoint(double dirX, double dirY) {
        double dirZ = Math.sqrt(Math.pow(dirX, 2) + Math.pow(dirY, 2));
        double deltaX = STEP_LENGTH * dirX / dirZ;
        double deltaY = STEP_LENGTH * dirY / dirZ;
        if (! this.sourceCharge.isPositive()) {
            deltaX *= -1;
            deltaY *= -1;
        }
        return new Point(this.x + deltaX, this.y + deltaY, this.sourceCharge, 
                test);
    }
    
    public Force totalForce() {
        Force totalForce = new Force(0,0,0);
        for (Charge q : test.charges) {
            double d = this.getDistance(q.getCenter());
            double forceVal = q.getMagnitude() / Math.pow(d, 2);
            double dirX = this.x - q.getX();
            double dirY = this.y - q.getY();
            if (! q.isPositive()) {
                dirX *= -1;
                dirY *= -1;
            }
            Force force = new Force(forceVal, dirX, dirY);
            totalForce = totalForce.add(force);
        }
        return totalForce;
    }

    public Charge getSourceCharge() {
        return sourceCharge;
    }    
}
