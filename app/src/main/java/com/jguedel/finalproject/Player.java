package com.jguedel.finalproject;



public class Player {
    public float scaleX;
    public float scaleY;
    public int posX;
    public int posY;

    public Player(){
        scaleX=5;
        scaleY=5;
        posX=500;
        posY=1200;
    }

    public int setX(int pos){
        posX = pos;
        return posX;
    }
}
