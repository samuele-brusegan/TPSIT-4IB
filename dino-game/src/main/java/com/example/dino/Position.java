package com.example.dino;

public class Position {
    private double x;
    private double y;
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void updateX(double deltaX) {
        this.x+=deltaX;
    }
    public void updateY(double deltaY) {
        this.y+=deltaY;
    }
    public void update(Speed speed) {
        updateX(speed.getVx());
        updateY(speed.getVy());
    }
}
