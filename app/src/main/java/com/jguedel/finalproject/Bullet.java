package com.jguedel.finalproject;

public class Bullet {
    public int posX;
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
        }
        return posY;
    }
}
