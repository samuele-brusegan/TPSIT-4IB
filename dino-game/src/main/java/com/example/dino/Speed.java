package com.example.dino;

public class Speed {
    private double vx;
    private double vy;
    public Speed(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void updateVx(double deltaVx) {
        this.vx+=deltaVx;
    }
    public void updateVy(double deltaVy) {
        this.vy+=deltaVy;
    }
}
