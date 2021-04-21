package com.jguedel.finalproject;

import android.util.Log;

public class Bullet {
    public float posX;
    public float posY;
    public boolean onScreen;
    public float playerX;
    public float playerY;

    public Bullet(){
        onScreen = true;
        playerY = 950;
        playerX = 350;

    }

    public void setPlayerX(float x){
        playerX = x;
    }

    public void setOnScreen(boolean ans) {
        onScreen = ans;
    }

    public float move(float y) {
        posY = y - 10;
        if (posY<=-200){
            posY = playerY;
            posX = playerX;
        }
        if(onScreen== false){
            posX =playerX;
            posY =playerY;
            onScreen = true;
        }
        return posY;
    }
}
