package com.example.dino;

import javafx.scene.image.Image;

/**
 * Gestisce l'estrazione di frame da uno sprite sheet.
 * <p>
 * Un sprite sheet è un'immagine contenente tutti i frame di un'animazione
 * disposti in una griglia. Questa classe calcola le coordinate (x, y, w, h)
 * per estrarre un singolo frame dato il suo indice (partendo da 1).
 * </p>
 * <p>
 * I frame sono numerati da 1 a {@link #getNumberOfFrames()}.
 * La numerazione procede per righe: i primi {@code imgPerRow} frame
 * sono sulla prima riga, poi la seconda, ecc.
 * </p>
 *
 * @see Sprite
 */
public class AnimationFrames {
    private Image img;
    private double frameW, frameH;
    private int imgPerRow;
    private int numberOfFrames;

    /**
     * Costruttore che inizializza il manager con l'immagine e le dimensioni dei frame.
     * <p>
     * Calcola automaticamente:
     * </p>
     * <ul>
     *   <li>{@code imgPerRow} = (int)(img.getWidth() / frameW)</li>
     *   <li>{@code numberOfFrames} = ((int)(img.getHeight() / frameH)) * imgPerRow</li>
     * </ul>
     *
     * @param img l'immagine dello sprite sheet
     * @param frameW larghezza di un singolo frame
     * @param frameH altezza di un singolo frame
     */
    public AnimationFrames(Image img, double frameW, double frameH) {
        this.img = img;
        this.frameW = frameW;
        this.frameH = frameH;

        double imgW = img.getWidth();
        double imgH = img.getHeight();

        this.imgPerRow = (int) (imgW / frameW);
        this.numberOfFrames = ((int) (imgH / frameH))*imgPerRow;
    }

    /**
     * Restituisce le coordinate di un frame nello sprite sheet.
     * <p>
     * La numerazione parte da 1. Il frame viene posizionato nella griglia
     * per righe: i primi {@code imgPerRow} frame sono sulla riga 0,
     * i successivi sulla riga 1, ecc.
     * </p>
     *
     * @param n numero del frame (1-based)
     * @return array {@code [x, y, width, height]} del frame nello sprite sheet
     */
    public double[] getFrame(int n) {
        int rowNumber = n / imgPerRow;
        int colNumber = n % imgPerRow;
        return new double[]{colNumber*frameH,rowNumber*frameW, frameW, frameH};
    }

    /**
     * Restituisce l'immagine dello sprite sheet.
     *
     * @return l'immagine
     */
    public Image getImg() {
        return img;
    }

    /**
     * Restituisce il numero totale di frame disponibili.
     *
     * @return numero di frame
     */
    public int getNumberOfFrames(){
        return numberOfFrames;
    }

    /**
     * Restituisce la larghezza di un singolo frame.
     *
     * @return larghezza in pixel
     */
    public double getFrameW() {
        return frameW;
    }
    /**
     * Restituisce l'altezza di un singolo frame.
     *
     * @return altezza in pixel
     */
    public double getFrameH() {
        return frameH;
    }
}
