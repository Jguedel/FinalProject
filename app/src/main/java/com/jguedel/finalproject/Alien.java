package com.jguedel.finalproject;

public class Alien {
    public float scaleX;
    public float scaleY;
    public int posX;
    public int posY;
    public boolean alive;
    public int id;

    public Alien(int x, int y, int id) {
        this.alive = true;
        this.scaleX=4;
        this.scaleY=4;
        this.posX = x;
        this.posY = y;
        this.id = id;
    }

    /*
    public void setPosX(int x){
        this.posX = x;
    }
    public void  setPosY(int y){
        this.posY = y;
    }
     */
    public float getposX(int i) {
        float X = posX * i;
        return X;
    }

}
