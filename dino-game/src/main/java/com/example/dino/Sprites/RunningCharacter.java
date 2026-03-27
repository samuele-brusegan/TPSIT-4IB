package com.example.dino.Sprites;

import com.example.dino.AnimationFrames;
import com.example.dino.Position;
import com.example.dino.Speed;

public class RunningCharacter extends Sprite {

    private long frameAccumulator = 0;

    public RunningCharacter(Position pos, Speed speed, double screenWidth, double screenHeight, AnimationFrames aframes, int frameNumber) {
        super(pos, speed, screenWidth, screenHeight, aframes, frameNumber);
    }
    public RunningCharacter(Position pos, Speed speed, double screenWidth, double screenHeight, AnimationFrames aframes, int frameNumber, double scale) {
        super(pos, speed, screenWidth, screenHeight, aframes, frameNumber, scale);
    }

    public void update(long timeSinceLastFrame){
        if (timeSinceLastFrame>500_000) cycleFrames();
    }
}
