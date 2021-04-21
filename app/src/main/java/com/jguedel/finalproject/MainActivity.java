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
    private ImageView alienIcon2;
    private ImageView alienIcon3;
    private ImageView alienIcon4;
    private ImageView alienIcon5;
    private ImageView alienIcon6;
    private ImageView alienIcon7;
    private ImageView alienIcon8;
    private ImageView alienIcon9;
    private ImageView alienIcon10;
    private ImageView alienIcon11;
    private Thread calculateThread;
    private ArrayList<Alien> alienArr;
    private ArrayList<View> alienList;
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
        alienArr.add(new Alien(100,200, name2));
        int name3 = 2;
        alienArr.add(new Alien(200,100, name3));
        int name4 = 3;
        alienArr.add(new Alien(200,200, name4));
        int name5 = 4;
        alienArr.add(new Alien(300,100, name5));
        int name6 = 5;
        alienArr.add(new Alien(300,200, name6));
        int name7 = 6;
        alienArr.add(new Alien(400,100, name7));
        int name8 = 7;
        alienArr.add(new Alien(400,200, name8));
        int name9 = 8;
        alienArr.add(new Alien(500,100, name9));
        int name10 = 9;
        alienArr.add(new Alien(500,200, name10));
        int name11 = 10;
        alienArr.add(new Alien(600,100, name11));
        int name12 = 11;
        alienArr.add(new Alien(600,200, name12));

        alienIcon0 = (ImageView) findViewById(R.id.alienIcon0);
        alienIcon1 = (ImageView) findViewById(R.id.alienIcon1);
        alienIcon2 = (ImageView) findViewById(R.id.alienIcon2);
        alienIcon3 = (ImageView) findViewById(R.id.alienIcon3);
        alienIcon4 = (ImageView) findViewById(R.id.alienIcon4);
        alienIcon5 = (ImageView) findViewById(R.id.alienIcon5);
        alienIcon6 = (ImageView) findViewById(R.id.alienIcon6);
        alienIcon7 = (ImageView) findViewById(R.id.alienIcon7);
        alienIcon8 = (ImageView) findViewById(R.id.alienIcon8);
        alienIcon9 = (ImageView) findViewById(R.id.alienIcon9);
        alienIcon10 = (ImageView) findViewById(R.id.alienIcon10);
        alienIcon11 = (ImageView) findViewById(R.id.alienIcon11);



        alienList = new ArrayList();
        alienIcon0.setX(alienArr.get(0).posX);
        alienIcon0.setY(alienArr.get(0).posY);
        alienList.add(alienIcon0);
        alienIcon1.setX(alienArr.get(1).posX);
        alienIcon1.setY(alienArr.get(1).posY);
        alienList.add(alienIcon1);
        alienIcon2.setX(alienArr.get(2).posX);
        alienIcon2.setY(alienArr.get(2).posY);
        alienList.add(alienIcon2);
        alienIcon3.setX(alienArr.get(3).posX);
        alienIcon3.setY(alienArr.get(3).posY);
        alienList.add(alienIcon3);
        alienIcon4.setX(alienArr.get(4).posX);
        alienIcon4.setY(alienArr.get(4).posY);
        alienList.add(alienIcon4);
        alienIcon5.setX(alienArr.get(5).posX);
        alienIcon5.setY(alienArr.get(5).posY);
        alienList.add(alienIcon5);
        alienIcon6.setX(alienArr.get(6).posX);
        alienIcon6.setY(alienArr.get(6).posY);
        alienList.add(alienIcon6);
        alienIcon7.setX(alienArr.get(7).posX);
        alienIcon7.setY(alienArr.get(7).posY);
        alienList.add(alienIcon7);
        alienIcon8.setX(alienArr.get(8).posX);
        alienIcon8.setY(alienArr.get(8).posY);
        alienList.add(alienIcon8);
        alienIcon9.setX(alienArr.get(9).posX);
        alienIcon9.setY(alienArr.get(9).posY);
        alienList.add(alienIcon9);
        alienIcon10.setX(alienArr.get(10).posX);
        alienIcon10.setY(alienArr.get(10).posY);
        alienList.add(alienIcon10);
        alienIcon11.setX(alienArr.get(11).posX);
        alienIcon11.setY(alienArr.get(11).posY);
        alienList.add(alienIcon11);

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
                        alienList.get(alien.id).setX(-200);
                    }
                }
            }
        }
    };

}