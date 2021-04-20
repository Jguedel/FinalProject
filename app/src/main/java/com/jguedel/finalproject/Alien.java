package com.jguedel.finalproject;

public class Alien {
    public float scaleX;
    public float scaleY;
    public int posX;
    public int posY;
    public boolean alive;
    public String name;

    public Alien(int x, int y, String name, boolean life) {
        this.alive = life;
        this.scaleX=4;
        this.scaleY=4;
        this.posX = x;
        this.posY = y;
        this.name = name;
    }

    public void setAlive(boolean ans){
        this.alive = ans;
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
        float X = this.posX * i;
        return X;
    }

}
