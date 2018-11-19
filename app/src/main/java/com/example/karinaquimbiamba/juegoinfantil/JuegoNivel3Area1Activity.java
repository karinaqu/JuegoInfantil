package com.example.karinaquimbiamba.juegoinfantil;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;

public class JuegoNivel3Area1Activity extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean correr;
    private long pausarOffSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nivel3_area1);
        chronometer= findViewById(R.id.chCronometro);
        //chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        if(!correr){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            correr=true;

        }
    }
    public void startCronometro(View v){
        if(!correr){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            correr=true;

        }


    }
    public void pausarCronometro(View v){
        if(correr){
            chronometer.stop();
            pausarOffSet= SystemClock.elapsedRealtime()- chronometer.getBase();
            correr=false;
        }



    }
    public void riniciarCronometro(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());

    }
}