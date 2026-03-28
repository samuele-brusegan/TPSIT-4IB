package com.example.dino;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Rileva collisioni tra hitbox e notifica i listener usando il pattern Observer.
 * <p>
 * Questa classe controlla se due {@link BoundingBox} si intersecano e,
 * in caso di collisione, notifica tutti i {@link CollisionListener}
 * registrati. Supporta multi-listener.
 * </p>
 *
 * @see CollisionListener
 */
public class CollisionDetector {
    private final List<CollisionListener> listeners = new ArrayList<>();

    /**
     * Aggiunge un listener per le notifiche di collisione.
     *
     * @param cl il listener da registrare
     */
    public void addListener(CollisionListener cl) { listeners.add(cl); }

    /**
     * Verifica se due hitbox si intersecano e notifica i listener in caso positivo.
     *
     * @param dinoHitbox hitbox del giocatore (Gino)
     * @param cactusHitbox hitbox dell'ostacolo (roccia)
     */
    public void check(BoundingBox dinoHitbox, BoundingBox cactusHitbox) {
        if (dinoHitbox.intersects(cactusHitbox)) {
            notifyListeners();
        }
    }

    private void notifyListeners() {
        for (CollisionListener l : listeners) {
            l.onCollisionDetected();
        }
    }
}
