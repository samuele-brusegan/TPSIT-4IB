package com.example.dino;

public class Score {
    private int score;

    public Score(int score) {
        this.score = score;
    }
    public int get() {
        return score;
    }
    public void increment(int score) {
        this.score += score;
    }
    public void set(int score) {
        this.score = score;
    }

}
