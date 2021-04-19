package com.jguedel.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.lang.InterruptedException;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SET ELEMENTS
        alien = new Alien();
        player = new Player();
        bullet = new Bullet();
        startBtn = findViewById(R.id.startBtn);

        //SET LISTENER EVENT
        startBtn.setOnClickListener(startGame);
        createBullet();
    }

    private final View.OnClickListener startGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calculateThread = new Thread(calculateAction);
            setContentView(R.layout.game);
            Left = findViewById(R.id.leftBtn);
            Right = findViewById(R.id.rightBtn);
            //SET INFLATER
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myLayout = (ConstraintLayout) findViewById(R.id.game);
            //ALIEN
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

            //Player
            playerIcon = (ImageView) layoutInflater.inflate(R.layout.ship, null);
            playerIcon.setX(player.posX);
            playerIcon.setY(player.posY);
            playerIcon.setScaleY(player.scaleY);
            playerIcon.setScaleX(player.scaleX);
            myLayout.addView(playerIcon, 0);

            //BULLET


            //MOVING BULLET


            //SET LISTENER EVENTS
            Left.setOnClickListener(moveLeft);
            Right.setOnClickListener(moveRight);
        }
    };
    public void createBullet(){
        bulletIcon = (ImageView) layoutInflater.inflate(R.layout.bullet, null);

        myLayout.addView(bulletIcon,1);
    }
    @Override
    protected void onResume() {
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
             while(bullet.onScreen == true) {
                 bullet.move(bullet.posY);
                 threadHandler.sendEmptyMessage(0);
             }
         }
    };

    //*****************Handler****************************
    public Handler threadHandler = new Handler() {
        public void handleMessage(android.os.Message msg){
            //bulletIcon.setX(player.posX);
            bulletIcon.setY(bullet.posY);
        }
    };

}