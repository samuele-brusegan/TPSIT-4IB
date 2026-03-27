package com.example.dino;

/**
 * Rappresenta una velocità 2D con componenti vx (orizzontale) e vy (verticale).
 * <p>
 * La velocità è espressa in pixel/secondo per coerenza con il sistema
 * di aggiornamento basato su deltaTime.
 * </p>
 *
 * @see Position
 */
public class Speed {
    private double vx;
    private double vy;

    /**
     * Costruttore che inizializza le componenti di velocità.
     *
     * @param vx velocità orizzontale in pixel/secondo
     * @param vy velocità verticale in pixel/secondo
     */
    public Speed(double vx, double vy) {
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * Restituisce la velocità orizzontale (vx).
     *
     * @return velocità in pixel/secondo
     */
    public double getVx() {
        return vx;
    }

    /**
     * Imposta la velocità orizzontale (vx).
     *
     * @param vx la nuova velocità orizzontale in pixel/secondo
     */
    public void setVx(double vx) {
        this.vx = vx;
    }

    /**
     * Restituisce la velocità verticale (vy).
     *
     * @return velocità in pixel/secondo
     */
    public double getVy() {
        return vy;
    }

    /**
     * Imposta la verticale (vy).
     *
     * @param vy la nuova velocità verticale in pixel/secondo
     */
    public void setVy(double vy) {
        this.vy = vy;
    }

    /**
     * Incrementa la velocità orizzontale di un delta.
     *
     * @param deltaVx l'incremento da aggiungere a vx
     */
    public void updateVx(double deltaVx) {
        this.vx+=deltaVx;
    }

    /**
     * Incrementa la velocità verticale di un delta.
     *
     * @param deltaVy l'incremento da aggiungere a vy
     */
    public void updateVy(double deltaVy) {
        this.vy+=deltaVy;
    }
}
