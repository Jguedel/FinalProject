package com.jguedel.finalproject;

public class Alien {
    public float scaleX;
    public float scaleY;
    public int posX;
    public int posY;
    public boolean alive;

    public Alien() {
        alive = true;
        scaleX=4;
        scaleY=4;
        posX=100;
        posY=100;
    }
    public float getposX(int i) {
        float X = posX * i;
        return X;
    }

}
