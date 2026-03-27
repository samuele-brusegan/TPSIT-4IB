package com.example.dino.Sprites;

import com.example.dino.AnimationFrames;
import com.example.dino.Position;
import com.example.dino.Speed;
import javafx.scene.shape.Rectangle;

/**
 * Il personaggio giocabile (Gino) con fisica di salto e gravità.
 * <p>
 * Estende {@link Sprite} aggiungendo:
 * </p>
 * <ul>
 *   <li>Gestione dello stato "a terra"/"in aria"</li>
 *   <li>Gravità che accelera verso il basso</li>
 *   <li>Salto impartito con impulso verticale iniziale</li>
 *   <li>Atterraggio automatico quando si raggiunge il terreno</li>
 * </ul>
 * <p>
 * Il movimento verticale è indipendente dalla velocità orizzontale.
 * La posizione del terreno (groundY) è impostata al momento della creazione.
 * </p>
 *
 * @see Sprite
 */
public class Gino extends Sprite {

    private boolean onGround;
    private double groundY;
    private final double gravity      = 2000;     // pixel/s^2
    private final double jumpVelocity = -750; // pixel/s

    /**
     * Costruttore per Gino senza scala personalizzata.
     *
     * @param pos posizione iniziale (x, y)
     * @param speed velocità iniziale
     * @param screenWidth larghezza dello schermo
     * @param screenHeight altezza dello schermo
     * @param aframes manager per i frame dell'animazione
     * @param frameNumber frame iniziale
     * @param groundY coordinata Y del terreno (dove Gino si ferma)
     */
    public Gino(Position pos, Speed speed, double screenWidth, double screenHeight, AnimationFrames aframes, int frameNumber, double groundY) {
        super(pos, speed, screenWidth, screenHeight, aframes, frameNumber);
        this.groundY = groundY;
        this.onGround = true;
    }

    /**
     * Costruttore per Gino con scala personalizzata.
     *
     * @param pos posizione iniziale (x, y)
     * @param speed velocità iniziale
     * @param screenWidth larghezza dello schermo
     * @param screenHeight altezza dello schermo
     * @param aframes manager per i frame dell'animazione
     * @param frameNumber frame iniziale
     * @param scale fattore di scala dello sprite
     * @param groundY coordinata Y del terreno (dove Gino si ferma)
     */
    public Gino(Position pos, Speed speed, double screenWidth, double screenHeight, AnimationFrames aframes, int frameNumber, double scale, double groundY) {
        super(pos, speed, screenWidth, screenHeight, aframes, frameNumber, scale);
        this.groundY = groundY;
        this.onGround = true;
    }

    /**
     * Fa saltare Gino.
     * <p>
     * L'impulso viene applicato solo se Gino si trova sul terreno
     * ({@code onGround == true}). La velocità verticale viene impostata
     * a {@code jumpVelocity} (valore negativo per salire).
     * </p>
     */
    public void jump() {
        if (onGround) {
            getSpeed().setVy(jumpVelocity);
            onGround = false;
        }
    }

    /**
     * Aggiorna lo stato di Gino applicando gravità e gestendo l'atterraggio.
     * <p>
     * Se in aria, aggiunge {@code gravity * dt} alla velocità verticale.
     * Se la posizione y supera {@code groundY}, riporta Gino a terra e
     * resetta la velocità verticale.
     * </p>
     *
     * @param deltaTime tempo trascorso dall'ultimo frame, in millisecondi
     */
    @Override
    public void update(long deltaTime) {
        // Applica gravità se in aria
        if (!onGround) {
            double dt = deltaTime / 1000.0; // secondi
            Speed speed = getSpeed();
            speed.setVy(speed.getVy() + gravity * dt);
        }

        super.update(deltaTime);

        // Controllo atterraggio
        Position pos = getPos();
        if (pos.getY() >= groundY) {
            pos.setY(groundY);
            getSpeed().setVy(0);
            onGround = true;
        }
    }
}
