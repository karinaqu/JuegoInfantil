package com.example.karinaquimbiamba.juegoinfantil;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;

public class JuegoNivel3Area1Activity extends AppCompatActivity implements View.OnClickListener{

    TextView txtPuntaje;
    private ImageView imgVidas;
    ImageView imgCarta1, imgCarta2, imgCarta3, imgCarta4;
    Integer[] cartas={101,102,201,202};
    int imagen1,imagen2,imagen3,imagen4, imagen5, imagen6, imagen7,imagen8,imagen9,imagen10,
            imagen11, imagen12;

    int primeraCarta, segundaCarta;
    int primerClic, segundoClic;
    int numeroCarta=1;


    int turno=1;
    int vidas=3;
    int contadorPuntaje=0, cpuPuntos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nivel3_area1);

        txtPuntaje=findViewById(R.id.txtPuntaje);
        imgVidas=findViewById(R.id.imgVidas);
        imgCarta1=findViewById(R.id.imgCarta1);
        imgCarta2=findViewById(R.id.imgCarta2);
        imgCarta3=findViewById(R.id.imgCarta3);
        imgCarta4=findViewById(R.id.imgCarta4);


        imgCarta1.setTag("0");
        imgCarta2.setTag("1");
        imgCarta3.setTag("2");
        imgCarta4.setTag("3");

        cartaDerecho();
        Collections.shuffle(Arrays.asList(cartas));

        imgCarta1.setOnClickListener(this);
        imgCarta2.setOnClickListener(this);
        imgCarta3.setOnClickListener(this);
        imgCarta4.setOnClickListener(this);



    }

    private void cartaDerecho(){
        imagen1=R.drawable.b;
        imagen2=R.drawable.c;
        imagen3=R.drawable.b_copia;
        imagen4=R.drawable.c_copia;

    }

    private void cartaDerecho2(){
        imagen1=R.drawable.d;
        imagen2=R.drawable.f;
        imagen3=R.drawable.d_copia;
        imagen4=R.drawable.f_copia;

    }

    @Override
    public void onClick(View v) {
        if(v==imgCarta1){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta1,carta);

        }
        if(v==imgCarta2){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta2,carta);

        }
        if(v==imgCarta3){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta3,carta);

        }
        if(v==imgCarta4){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta4,carta);

        }


    }
    private void doStuff(ImageView v, int cart){
        if(cartas[cart]==101){
            v.setImageResource(imagen1);
        }else  if(cartas[cart]==102){
            v.setImageResource(imagen2);
        }else  if(cartas[cart]==201){
            v.setImageResource(imagen3);
        }else  if(cartas[cart]==202){
            v.setImageResource(imagen4);
        }
        if (numeroCarta==1){
            primeraCarta=cartas[cart];
            if(primeraCarta>200){
                primeraCarta=primeraCarta-100;
            }
            numeroCarta=2;
            primerClic=cart;
            v.setEnabled(false);



        }else if(numeroCarta==2){
            segundaCarta=cartas[cart];
            if(segundaCarta>200){
                segundaCarta=segundaCarta-100;
            }
            numeroCarta=1;
            segundoClic=cart;

            imgCarta1.setEnabled(false);
            imgCarta2.setEnabled(false);
            imgCarta3.setEnabled(false);
            imgCarta4.setEnabled(false);


            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    calcular();
                }
            },1000);
        }

    }
    private void calcular(){

        if(primeraCarta==segundaCarta){
            if(primerClic==0){
                imgCarta1.setVisibility(View.INVISIBLE);
            }else  if(primerClic==1){
                imgCarta2.setVisibility(View.INVISIBLE);
            }else  if(primerClic==2){
                imgCarta3.setVisibility(View.INVISIBLE);
            }else  if(primerClic==3){
                imgCarta4.setVisibility(View.INVISIBLE);
            }

            if(segundoClic==0){
                imgCarta1.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==1){
                imgCarta2.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==2){
                imgCarta3.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==3){
                imgCarta4.setVisibility(View.INVISIBLE);
            }

            contadorPuntaje++;
            txtPuntaje.setText(""+contadorPuntaje);
            if(turno==1){


            }else if (turno==2){
                //contadorPuntaje++;
                //txtPuntaje.setText(""+contadorPuntaje);
            }

        }else {
            imgCarta1.setImageResource(R.drawable.carta_reves);
            imgCarta2.setImageResource(R.drawable.carta_reves);
            imgCarta3.setImageResource(R.drawable.carta_reves);
            imgCarta4.setImageResource(R.drawable.carta_reves);

            vidas--;

            if(turno==1){
                //contadorPuntaje++;
                //txtPuntaje.setText(""+contadorPuntaje);

            }else if (turno==2){
                //contadorPuntaje++;
                //txtPuntaje.setText(""+contadorPuntaje);

            }

        }
        imgCarta1.setEnabled(true);
        imgCarta2.setEnabled(true);
        imgCarta3.setEnabled(true);
        imgCarta4.setEnabled(true);
        Cambio();



    }
    private void Cambio(){
        cartaDerecho2();


    }
    private void perdidaVidas(){
        vidas--;
        switch (vidas){
            case 0:
                imgVidas.setImageResource(vidas);
                break;
            case 1:
                Toast.makeText(this, "Tienes una vida",Toast.LENGTH_LONG).show();
                imgVidas.setImageResource(R.drawable.unavida);
                break;
            case 2:
                Toast.makeText(this, "Tienes dos vidas",Toast.LENGTH_LONG).show();
                imgVidas.setImageResource(R.drawable.dosvidas);
                break;
            case 3:
                imgVidas.setImageResource(R.drawable.tresvidas);
                break;

        }
    }

}
