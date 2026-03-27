package com.example.dino;

/**
 * Interfaccia per la gestione degli eventi di collisione.
 * <p>
 * Implementa il pattern Observer: le classi che implementano questa
 * interfaccia vengono notificate quando si verifica una collisione
 * tra due hitbox nel gioco.
 * </p>
 *
 * @see CollisionDetector
 */
public interface CollisionListener {
    /**
     * Called when a collision is detected by the {@link CollisionDetector}.
     */
    void onCollisionDetected();
}
