package com.example.dino;

/**
 * Rappresenta una coordinata 2D (x, y) nello spazio di gioco.
 * <p>
 * Classe immutabile? No, Mutable: le coordinate possono essere modificate
 * tramite i metodi setter o {@link #update(Speed)}.
 * </p>
 *
 * @see Speed
 */
public class Position {
    private double x;
    private double y;

    /**
     * Costruttore che inizializza le coordinate.
     *
     * @param x coordinata orizzontale
     * @param y coordinata verticale
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Restituisce la coordinata x.
     *
     * @return la posizione orizzontale
     */
    public double getX() {
        return x;
    }

    /**
     * Imposta la coordinata x.
     *
     * @param x la nuova posizione orizzontale
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Restituisce la coordinata y.
     *
     * @return la posizione verticale
     */
    public double getY() {
        return y;
    }

    /**
     * Imposta la coordinata y.
     *
     * @param y la nuova posizione verticale
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Incrementa la coordinata x di un valore.
     *
     * @param deltaX incremento da aggiungere a x
     */
    public void updateX(double deltaX) {
        this.x+=deltaX;
    }

    /**
     * Incrementa la coordinata y di un valore.
     *
     * @param deltaY incremento da aggiungere a y
     */
    public void updateY(double deltaY) {
        this.y+=deltaY;
    }

    /**
     * Aggiorna la posizione aggiungendo le componenti di una velocità.
     * <p>
     * Equivalente a: {@code updateX(speed.getVx()); updateY(speed.getVy());}
     * </p>
     *
     * @param speed l'oggetto Speed da cui ottenere vx e vy
     * @see Speed
     */
    public void update(Speed speed) {
        updateX(speed.getVx());
        updateY(speed.getVy());
    }
}
