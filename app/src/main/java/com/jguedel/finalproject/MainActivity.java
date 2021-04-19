package com.jguedel.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SET ELEMENTS
        //alien = new Alien();
        player = new Player();
        bullet = new Bullet();
        startBtn = findViewById(R.id.startBtn);

        //SET LISTENER EVENT
        startBtn.setOnClickListener(startGame);
    }

    private final View.OnClickListener startGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.game);
            Left = findViewById(R.id.leftBtn);
            Right = findViewById(R.id.rightBtn);
            //SET INFLATER
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myLayout = (ConstraintLayout) findViewById(R.id.game);
            //ALIEN
            createAliens();
            /*
            //TOP ROW
            for (int i = 1; i<=6; i++) {
                ImageView alienIcon = (ImageView) layoutInflater.inflate(R.layout.alien, null);
                alienIcon.setScaleY(alien.scaleY);
                alienIcon.setScaleX(alien.scaleX);
                alienIcon.setX(alien.getposX(i));
                alienIcon.setY(alien.posY);
                myLayout.addView(alienIcon, 0);
            }
            //BOTTOM ROW
            for (int i = 1; i<=6; i++) {
                ImageView alienIcon = (ImageView) layoutInflater.inflate(R.layout.alien, null);
                alienIcon.setScaleY(alien.scaleY);
                alienIcon.setScaleX(alien.scaleX);
                alienIcon.setX(alien.getposX(i));
                alienIcon.setY(alien.posY*2);
                myLayout.addView(alienIcon, 0);
            }
             */

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
        for (int i = 1; i<=6; i++) {
            alienArr.add(new Alien(100*i,100));
            alienArr.add(new Alien(100*i,200));
        }
        for (Alien alien: alienArr) {
            Log.d("TAG", "createAliens: " + alien.posX);
        }
    }

    public void createBullet(){
        bulletIcon = (ImageView) layoutInflater.inflate(R.layout.bullet, null);
        myLayout.addView(bulletIcon,1);
    }

    protected void start() {
        calculateThread.start();
        super.onResume();
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
         private static final int DELAY = 100;

         public void run() {
             try {
                 while(true) {
                     bullet.move(bullet.posY);
                     Thread.sleep(10);
                     threadHandler.sendEmptyMessage(0);
                 }
             }
             catch (InterruptedException e){
                 e.printStackTrace();
             }
         }
    };

    //*****************Handler****************************
    public Handler threadHandler = new Handler() {
        public void handleMessage(android.os.Message msg){
            bulletIcon.setY(bullet.posY);
            if (bullet.onScreen == false){
                bulletIcon.setX(bullet.posX);
                bullet.setOnScreen(true);
            }
        }
    };

}