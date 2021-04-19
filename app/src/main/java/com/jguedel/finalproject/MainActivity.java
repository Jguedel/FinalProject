package com.jguedel.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private LayoutInflater layoutInflater;
    private ConstraintLayout myLayout;
    private Alien alien;
    private Player player;
    private Button startBtn;
    private Button Left;
    private Button Right;
    private ImageView ship;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SET ELEMENTS
        alien = new Alien();
        player = new Player();
        startBtn = findViewById(R.id.startBtn);

        ship = findViewById(R.id.ship);

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
            //LAYOUT INFLATER
            //ALIEN
            ImageView alienIcon = (ImageView) layoutInflater.inflate(R.layout.alien, null);
            alienIcon.setX(alien.posX);
            alienIcon.setY(alien.posY);
            alienIcon.setScaleY(alien.scaleY);
            alienIcon.setScaleX(alien.scaleX);
            myLayout.addView(alienIcon, 0);
            Left.setOnClickListener(moveLeft);
           // Right.setOnClickListener(moveRight);
        }
    };

    private final View.OnClickListener moveLeft = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("TAG", "onClick: hit" );
            ship.setX(player.posX +10);
        }
    };
}