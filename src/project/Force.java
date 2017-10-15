package project;

public class Force {

    private double magnitude;
    private double x;
    private double y;

    public Force(double magnitude, double dirX, double dirY) {
        this.magnitude = magnitude;
        double dirZ = Math.sqrt(Math.pow(dirX, 2) + Math.pow(dirY, 2));
        if (dirZ != 0) {
            this.x = magnitude * dirX / dirZ;
            this.y = magnitude * dirY / dirZ;
        } else {
            this.x = 0;
            this.y = 0;
        }
    }

    public Force add(Force f) {
        double xx = this.x + f.x;
        double yy = this.y + f.y;
        double zz = Math.sqrt(Math.pow(xx, 2) + Math.pow(yy, 2));
        return new Force(zz, xx, yy);
    }

    public double getValue() {
        return magnitude;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
