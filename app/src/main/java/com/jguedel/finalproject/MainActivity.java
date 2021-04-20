package com.jguedel.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LayoutInflater layoutInflater;
    private ConstraintLayout myLayout;
    private Alien alien;
    private Player player;
    private Bullet bullet;
    private Button startBtn;
    private Button Left;
    private Button Right;
    private ImageView playerIcon;
    private ImageView bulletIcon;
    private Thread calculateThread;
    private ArrayList<Alien> alienArr;
    private List list;
    public ImageView alienIcon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        //SET ELEMENTS
        player = new Player();
        bullet = new Bullet();
        Left = findViewById(R.id.leftBtn);
        Right = findViewById(R.id.rightBtn);
        //startBtn = findViewById(R.id.startBtn);
        //SET INFLATER
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myLayout = findViewById(R.id.game);
        Log.d("TAG", "onCreate: " + myLayout);

        alienArr = new ArrayList();
        //TOP ROW
        int j = 6;
        for (int i = 1; i<=6; i++) {
            String name1 = Integer.toString(i);
            alienArr.add(new Alien(100*i,100, name1, true));
            String name2 = Integer.toString(j);
            alienArr.add(new Alien(100*i,200,name2, true));
            j++;
        }
        for (Alien alien: alienArr) {
            ImageView alienIcon = (ImageView) layoutInflater.inflate(R.layout.alienship, null);
            alienIcon.setScaleY(alien.scaleY);
            alienIcon.setScaleX(alien.scaleX);
            alienIcon.setX(alien.posX);
            alienIcon.setY(alien.posY);
            myLayout.addView(alienIcon, 0);
            Log.d("TAG", "createAliens: " + alien.posX);
        }

        //Player
        playerIcon = (ImageView) layoutInflater.inflate(R.layout.ship, null);
        playerIcon.setX(player.posX);
        playerIcon.setY(player.posY);
        playerIcon.setScaleY(player.scaleY);
        playerIcon.setScaleX(player.scaleX);
        myLayout.addView(playerIcon, 0);

        //BULLET
        createBullet();

        //SET LISTENER EVENTS
        Left.setOnClickListener(moveLeft);
        Right.setOnClickListener(moveRight);
        calculateThread = new Thread(calculateAction);

        //SET LISTENER EVENT
        //startBtn.setOnClickListener(startGame);
    }
/*
    private final View.OnClickListener startGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.game);
            Left = findViewById(R.id.leftBtn);
            Right = findViewById(R.id.rightBtn);

            //Player
            playerIcon = (ImageView) layoutInflater.inflate(R.layout.ship, null);
            playerIcon.setX(player.posX);
            playerIcon.setY(player.posY);
            playerIcon.setScaleY(player.scaleY);
            playerIcon.setScaleX(player.scaleX);
            myLayout.addView(playerIcon, 0);

            //BULLET
            createBullet();

            //SET LISTENER EVENTS
            Left.setOnClickListener(moveLeft);
            Right.setOnClickListener(moveRight);
            calculateThread = new Thread(calculateAction);
            start();
        }
    };

    private void createAliens(){
        alienArr = new ArrayList();
        //TOP ROW
        int j = 6;
        for (int i = 1; i<=6; i++) {
            String name1 = Integer.toString(i);
            alienArr.add(new Alien(100*i,100, name1, true));
            String name2 = Integer.toString(j);
            alienArr.add(new Alien(100*i,200,name2, true));
            j++;
        }
        for (Alien alien: alienArr) {
            //String name = "alienIcon" + Integer.toString(z);
            ImageView alienIcon = (ImageView) layoutInflater.inflate(R.layout.alien, null);
            alienIcon.setScaleY(alien.scaleY);
            alienIcon.setScaleX(alien.scaleX);
            alienIcon.setX(alien.posX);
            alienIcon.setY(alien.posY);
            myLayout.addView(alienIcon, 0);
            Log.d("TAG", "createAliens: " + alien.posX);
        }
    }
*/

    public void createBullet(){
        bulletIcon = (ImageView) layoutInflater.inflate(R.layout.bullet, null);
        bulletIcon.setX(player.posX);
        bulletIcon.setY(player.posY);
        myLayout.addView(bulletIcon,1);
    }

    protected void onResume() {
        calculateThread.start();
        super.onResume();
    }
    protected void onPause(){
        finish();
        super.onPause();
    }
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

    //MOVE PLAYER LEFT
    private final View.OnClickListener moveLeft = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            player.move(-10);
            playerIcon.setX(player.posX);
            bullet.setPlayerX(player.posX);
        }
    };
    //MOVE PLAYER RIGHT
    private final View.OnClickListener moveRight = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            player.move(10);
            playerIcon.setX(player.posX);
            bullet.setPlayerX(player.posX);
        }
    };

    //**********************************Thread*********************************
    private Runnable calculateAction = new Runnable(){
         public void run() {
             try {
                 while(true) {
                     bullet.move(bullet.posY);
                     Thread.sleep(20);
                     threadHandler.sendEmptyMessage(0);
                 }
             }
             catch (InterruptedException e){
                 e.printStackTrace();
             }
         }
    };

    //*****************Handler****************************
    @SuppressLint("HandlerLeak")
    public Handler threadHandler = new Handler() {
        public void handleMessage(android.os.Message msg){
            bulletIcon.setY(bullet.posY);
            if (bullet.onScreen == false){
                bulletIcon.setX(bullet.posX);
                bullet.setOnScreen(true);
            }

            for(int i = 0; i<=alienArr.size()-1;i++) {
                if (bullet.posX <= alienArr.get(i).posX + 50 && bullet.posX >= alienArr.get(i).posX) {
                    if (bullet.posY <= alienArr.get(i).posY + 50 && bullet.posY >= alienArr.get(i).posY) {
                        Log.d("hit", "handleMessage: " + i);
                        alienArr.get(i).alive = false;
                    }
                }
            }
                myLayout.removeView(alienIcon);
        }
    };

}