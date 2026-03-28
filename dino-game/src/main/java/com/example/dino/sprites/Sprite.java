package com.example.dino.sprites;

import com.example.dino.FrameManager;
import com.example.dino.Position;
import com.example.dino.Speed;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Classe base per tutti gli sprite del gioco.
 * <p>
 * Gestisce posizione, velocità, animazione e hitbox degli oggetti di gioco.
 * Supporta hitbox predefinite (calcolate dalle dimensioni del frame con padding)
 * o hitbox personalizzate impostate dinamicamente.
 * </p>
 * <p>
 * Il movimento è basato su tempo reale: la posizione viene aggiornata
 * come {@code posizione += velocità * deltaTime} per essere indipendente dal framerate.
 * </p>
 *
 * @see Gino
 * @see com.example.dino.CollisionDetector
 * @see FrameManager
 */
public class Sprite {
    private Position pos;
    private long counter = 0;

    private final double screenWidth;
    private final double screenHeight;

    private Speed speed;

    private final FrameManager aframes;
    private int frameNumber;

    private double scale = 1;

    private Double customHitboxWidth = null;
    private Double customHitboxHeight = null;
    private Double customHitboxOffsetX = 0.0;
    private Double customHitboxOffsetY = 0.0;

    /**
     * Costruttore completo con scala personalizzata.
     *
     * @param pos posizione iniziale
     * @param speed velocità iniziale
     * @param screenWidth larghezza dello schermo
     * @param screenHeight altezza dello schermo
     * @param aframes manager per i frame dell'animazione
     * @param frameNumber frame iniziale da visualizzare
     * @param scale fattore di scala per lo sprite
     */
    public Sprite(Position pos, Speed speed, double screenWidth, double screenHeight, FrameManager aframes, int frameNumber, double scale) {
        this.pos = pos;
        this.speed = speed;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.aframes = aframes;
        this.frameNumber = frameNumber;
        this.scale = scale;
    }

    /**
     * Costruttore senza scala (scale = 1.0).
     *
     * @param pos posizione iniziale
     * @param speed velocità iniziale
     * @param screenWidth larghezza dello schermo
     * @param screenHeight altezza dello schermo
     * @param fm manager per i frame dell'animazione
     * @param frameNumber frame iniziale da visualizzare
     */
    public Sprite(Position pos, Speed speed, double screenWidth, double screenHeight, FrameManager fm, int frameNumber) {
        this.pos = pos;
        this.speed = speed;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.aframes = fm;
        this.frameNumber = frameNumber;
    }

    /**
     * Restituisce la velocità corrente dello sprite.
     *
     * @return l'oggetto Speed contenente le componenti vx e vy
     */
    public Speed getSpeed() {
        return speed;
    }

    /**
     * Imposta una nuova velocità per lo sprite.
     *
     * @param speed l'oggetto Speed con le nuove componenti di velocità
     */
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    /**
     * Restituisce la posizione corrente dello sprite.
     *
     * @return l'oggetto Position contenente x e y
     */
    public Position getPos() {
        return pos;
    }

    /**
     * Imposta una nuova posizione per lo sprite.
     *
     * @param pos la nuova posizione
     */
    public void setPos(Position pos) {
        this.pos = pos;
    }

    /**
     * Aggiorna lo stato dello sprite in base al tempo trascorso.
     * <p>
     * Incrementa il contatore interno e sposta la posizione usando
     * la formula: {@code posizione += velocità * (deltaTime / 1000)}.
     * </p>
     *
     * @param deltaTime il tempo trascorso dall'ultimo frame, in millisecondi
     */
    public void update(long deltaTime) {
        counter++;
        double dt = deltaTime / 1000.0; // Converti in secondi
        pos.setX(pos.getX() + speed.getVx() * dt);
        pos.setY(pos.getY() + speed.getVy() * dt);
    }

    /**
     * Avanza al frame successivo dell'animazione.
     * <p>
     * Se si raggiunge il numero totale di frame, si ritorna al frame 1.
     * </p>
     */
    public void cycleFrames(){
        int nof = aframes.getNumberOfFrames();

        frameNumber++;
        if (frameNumber >= nof) frameNumber = 1;
    }

    /**
     * Renderizza lo sprite sul canvas.
     * <p>
     * Disegna l'immagine corrente dal sprite sheet e, se la modalità debug
     * è attiva (presenza di hitbox visualizzata), disegna anche il rettangolo
     * dell'hitbox in viola.
     * </p>
     *
     * @param gc il GraphicsContext del canvas su cui disegnare
     */
    public void render(GraphicsContext gc) {
        double[] vals = aframes.getFrame(frameNumber);
        Image img = aframes.getImg();
        gc.drawImage(img, vals[0], vals[1], vals[2], vals[3], pos.getX(), pos.getY(), vals[2]*scale, vals[3]*scale);

        gc.setStroke(Color.PURPLE); // Cambiamo colore per vederlo meglio
        Bounds hb = getHitbox();
        gc.strokeRect(hb.getMinX(), hb.getMinY(), hb.getWidth(), hb.getHeight());
    }

    /**
     * Verifica se lo sprite è uscito dai boundaries dello schermo.
     *
     * @return true se lo sprite è fuori dai bordi, false altrimenti
     */
    public boolean isOutOfBounds(){
        double x = pos.getX();
        double y = pos.getY();
        double tolleranza = 25;
        return (x<0-tolleranza || x>screenWidth+tolleranza || y<0-tolleranza || y>screenHeight+tolleranza);
    }
    protected long getCounter() {
        return counter;
    }
    protected FrameManager getAframes() {
        return aframes;
    }

    /**
     * Imposta una hitbox personalizzata con dimensioni fisse e offset (0,0).
     * <p>
     * La hitbox viene calcolata come {@code (pos.x, pos.y, width, height)}.
     * </p>
     *
     * @param width larghezza della hitbox in pixel
     * @param height altezza della hitbox in pixel
     * @see #setCustomHitbox(double, double, double, double)
     */
    public void setCustomHitbox(double width, double height) {
        this.customHitboxWidth = width;
        this.customHitboxHeight = height;
    }

    /**
     * Imposta una hitbox personalizzata con dimensioni e offset.
     * <p>
     * La hitbox viene calcolata come:
     * {@code (pos.x + offsetX, pos.y + offsetY, width, height)}.
     * </p>
     *
     * @param width larghezza della hitbox in pixel
     * @param height altezza della hitbox in pixel
     * @param offsetX offset orizzontale dalla posizione dello sprite
     * @param offsetY offset verticale dalla posizione dello sprite
     */
    public void setCustomHitbox(double width, double height, double offsetX, double offsetY) {
        this.customHitboxWidth = width;
        this.customHitboxHeight = height;
        this.customHitboxOffsetX = offsetX;
        this.customHitboxOffsetY = offsetY;
    }

    /**
     * Rimuove la hitbox personalizzata, ripristinando il calcolo automatico.
     */
    public void clearCustomHitbox() {
        this.customHitboxWidth = null;
        this.customHitboxHeight = null;
    }

    /**
     * Restituisce il rettangolo di collisione (hitbox) dello sprite.
     * <p>
     * Se è stata impostata una hitbox personalizzata tramite
     * {@link #setCustomHitbox(double, double)} o
     * {@link #setCustomHitbox(double, double, double, double)},
     * viene utilizzata quella. Altrimenti, la hitbox viene calcolata
     * automaticamente dal frame corrente applicando un padding per evitare
     * collisioni su pixel trasparenti.
     * </p>
     *
     * @return un {@link BoundingBox} rappresentante l'hitbox
     */
    public BoundingBox getHitbox() {
        double width, height, offsetX, offsetY;

        // Se è stata impostata una hitbox personalizzata, usala
        if (customHitboxWidth != null && customHitboxHeight != null) {
            width = customHitboxWidth;
            height = customHitboxHeight;
            offsetX = customHitboxOffsetX;
            offsetY = customHitboxOffsetY;
        } else {
            // Altrimenti calcola la hitbox dal frame con padding
            FrameManager af = getAframes();
            double w = aframes.getFrameW() * scale;
            double h = aframes.getFrameH() * scale;
            double paddingX = w * 0.15;  // 15% di spazio vuoto a dx e sx
            double paddingY = h * 0.10; // 10% di spazio vuoto sopra e sotto
            width = w - (2 * paddingX);
            height = h - (2 * paddingY);
            offsetX = paddingX;
            offsetY = paddingY;
        }

        return new BoundingBox(
                pos.getX() + offsetX,
                pos.getY() + offsetY,
                width,
                height
        );
    }

    /**
     * Restituisce il fattore di scala corrente dello sprite.
     *
     * @return il valore di scala
     */
    public double getScale() {
        return scale;
    }

    /**
     * Restituisce la larghezza corrente dell'hitbox.
     *
     * @return larghezza in pixel
     */
    public double getHitboxWidth() {
        return getHitbox().getWidth();
    }

    /**
     * Restituisce l'altezza corrente dell'hitbox.
     *
     * @return altezza in pixel
     */
    public double getHitboxHeight() {
        return getHitbox().getHeight();
    }
}
