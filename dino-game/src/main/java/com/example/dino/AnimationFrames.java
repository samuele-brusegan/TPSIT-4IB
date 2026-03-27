package com.example.dino;

import javafx.scene.image.Image;

public class AnimationFrames {
    private Image img;
    private double frameW, frameH;
    private int imgPerRow;
    private int numberOfFrames;

    public AnimationFrames(Image img, double frameW, double frameH) {
        this.img = img;
        this.frameW = frameW;
        this.frameH = frameH;

        double imgW = img.getWidth();
        double imgH = img.getHeight();

        this.imgPerRow = (int) (imgW / frameW);
        System.out.println("imgPerRow = " + imgPerRow);
        this.numberOfFrames = ((int) (imgH / frameH))*imgPerRow;
    }

    public double[] getFrame(int n) {
        int rowNumber = n / imgPerRow;
        int colNumber = n % imgPerRow;
        return new double[]{colNumber*frameH,rowNumber*frameW, frameW, frameH};
    }

    public Image getImg() {
        return img;
    }

    public int getNumberOfFrames(){
        return numberOfFrames;
    }
}
