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
            bulletIcon = (ImageView) layoutInflater.inflate(R.layout.bullet, null);
            bulletIcon.setX(player.posX);
            bulletIcon.setY(player.posY);
            myLayout.addView(bulletIcon,1);

            //MOVING BULLET
            for (int i = 0; i<=20; i++) {
                if (bullet.onScreen == true) {
                    bulletIcon.setY(bullet.move(bulletIcon.getY()));
                }
            }

            //SET LISTENER EVENTS
            Left.setOnClickListener(moveLeft);
            Right.setOnClickListener(moveRight);
        }
    };

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


}