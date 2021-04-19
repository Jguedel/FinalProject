package com.jguedel.finalproject;


import android.util.Log;

public class Player {
    public float scaleX;
    public float scaleY;
    public int posX;
    public int posY;

    public Player(){
        scaleX=5;
        scaleY=5;
        posX=350;
        posY=1050;
    }

    public void move(int dest){
        posX += dest;
        Log.d("TAG", "posX: " + posX);
    }
}
