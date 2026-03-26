package com.example.dino;

import javafx.scene.image.Image;

public class AnimationFrames {
    private Image img;
    private double frameW, frameH;

    public AnimationFrames(Image img, double frameW, double frameH) {
        this.img = img;
        this.frameW = frameW;
        this.frameH = frameH;
    }

    public double[] getFrame(int n) {
        double imgW = img.getWidth();
        double imgH = img.getHeight();
        int imgPerRow = (int) (imgW / frameW);
        int rowNumber = n / imgPerRow;
        int colNumber = n % imgPerRow;
        return new double[]{rowNumber*frameW,colNumber*frameH, frameW, frameH};
    }

    public Image getImg() {
        return img;
    }
}
