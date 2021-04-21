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
    private ImageView alienIcon0;
    private ImageView alienIcon1;
    private Thread calculateThread;
    private ArrayList<Alien> alienArr;
    private List list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        //SET ELEMENTS
        player = new Player();
        bullet = new Bullet();
        //LAYOUT INFLATER
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myLayout = (ConstraintLayout) findViewById(R.id.game);

        //CREATING ALIEN ARRAY TO
        alienArr = new ArrayList();
        int name1 = 0;
        alienArr.add(new Alien(100,100, name1));
        int name2 = 1;
        alienArr.add(new Alien(300,200, name2));

        alienIcon0 = (ImageView) findViewById(R.id.alienIcon0);
        alienIcon1 = (ImageView) findViewById(R.id.alienIcon1);

        alienIcon0.setX(alienArr.get(0).posX);
        alienIcon0.setY(alienArr.get(0).posY);
        alienIcon1.setX(alienArr.get(1).posX);
        alienIcon1.setY(alienArr.get(1).posY);
        //VARIBALES TO FOR LEFT AND RIGHT BUTTON VIEWS
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
    }

    public void createBullet(){
        bulletIcon = (ImageView) layoutInflater.inflate(R.layout.bullet, null);
        myLayout.addView(bulletIcon,1);
    }

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
            Log.d("handler", "handleMessage: we in son");
            bulletIcon.setY(bullet.posY);
            if (bullet.onScreen == false){
                bulletIcon.setX(bullet.posX);
                bullet.setOnScreen(true);
            }
            for(Alien alien: alienArr){
                if(bullet.posX <= alien.posX+50 && bullet.posX >= alien.posX){
                    if(bullet.posY <= alien.posX+50 && bullet.posX >= alien.posX){
                        if(alien.id == 0){
                            alienIcon0.setX(-200);
                        }else{
                            alienIcon1.setX(-200);
                        }
                    }
                }
            }
        }
    };

}