/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author behnish
 */
public abstract class Event implements Serializable {

    private static final long serialVersionUID = -7435622685898555817L;
    double ax;
    double ay;
    double bx;
    double by;
    private double x;
    private double y;
    private int step;
    double ux;
    double uy;
    protected boolean frozen = false;


    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setPointAndT(Point a, Point b, int step)
    {
        setTrajectory(a, b, step);
    }
            
    /**
     *
     * @param a Source point in x and y
     * @param b Destination point in x and y
     * @param step how many steps it should take
     */
    public final void setTrajectory(Point a, Point b, int step) {
        if (step == 0) {
            this.step = 1;
        } else {
            this.step = step;
        }
        this.ax = a.getX();
        this.ay = a.getY();
        this.bx = b.getX();
        this.by = b.getY();

        this.x = this.ax;
        this.y = this.ay;

        //double distance = 1000.0;//a.distance(b);
        double distance = a.distance(b);
        
        this.ux = ((this.bx - this.ax) / distance);
        this.uy = ((this.by - this.ay) / distance);


    }

    public Point currentLocation() {
        Point p = new Point();
        p.setLocation(this.x, this.y);
        return p;
    }

    public void freeze() {
        this.frozen = true;
    }

    public void moveForward() {
        this.frozen = false;

        this.x += this.step * this.ux;
        this.y += this.step * this.uy;        
    }


    public void moveBackward() {
        this.frozen = false;
        this.x -= this.step * this.ux;
        this.y -= this.step * this.uy;
    }

    public boolean isIntoBounds() {
        return (this.x - this.ax) * (this.x - this.bx) + (this.y - this.ay) * (this.y - this.by) <= 0.0D;
    }

    public void reset() {
        this.x = this.ax;
        this.y = this.ay;
    }

    public void end() {
        this.x = this.bx;
        this.y = this.by;
    }

    public abstract void draw(Graphics2D g);
}
