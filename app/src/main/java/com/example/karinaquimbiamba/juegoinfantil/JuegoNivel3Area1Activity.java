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
    ImageView imgCarta1, imgCarta2, imgCarta3, imgCarta4, imgCarta5, imgCarta6, imgCarta7, imgCarta8, imgCarta9, imgCarta10, imgCarta11, imgCarta12;
    Integer[] cartas={101,102,103,104,105,106,201,202,203,204,205,206};
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
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        txtPuntaje=findViewById(R.id.txtPuntaje);
        imgVidas=findViewById(R.id.imgVidas);
        imgCarta1=findViewById(R.id.imgCarta1);
        imgCarta2=findViewById(R.id.imgCarta2);
        imgCarta3=findViewById(R.id.imgCarta3);
        imgCarta4=findViewById(R.id.imgCarta4);
        imgCarta5=findViewById(R.id.imgCarta5);
        imgCarta6=findViewById(R.id.imgCarta6);
        imgCarta7=findViewById(R.id.imgCarta7);
        imgCarta8=findViewById(R.id.imgCarta8);
        imgCarta9=findViewById(R.id.imgCarta9);
        imgCarta10=findViewById(R.id.imgCarta10);
        imgCarta11=findViewById(R.id.imgCarta11);
        imgCarta12=findViewById(R.id.imgCarta12);

        imgCarta1.setTag("0");
        imgCarta2.setTag("1");
        imgCarta3.setTag("2");
        imgCarta4.setTag("3");
        imgCarta5.setTag("4");
        imgCarta6.setTag("5");
        imgCarta7.setTag("6");
        imgCarta8.setTag("7");
        imgCarta9.setTag("8");
        imgCarta10.setTag("9");
        imgCarta11.setTag("10");
        imgCarta12.setTag("11");
        cartaDerecho();
        Collections.shuffle(Arrays.asList(cartas));

        imgCarta1.setOnClickListener(this);
        imgCarta2.setOnClickListener(this);
        imgCarta3.setOnClickListener(this);
        imgCarta4.setOnClickListener(this);
        imgCarta5.setOnClickListener(this);
        imgCarta6.setOnClickListener(this);
        imgCarta7.setOnClickListener(this);
        imgCarta8.setOnClickListener(this);
        imgCarta9.setOnClickListener(this);
        imgCarta10.setOnClickListener(this);
        imgCarta11.setOnClickListener(this);
        imgCarta12.setOnClickListener(this);



    }

    private void cartaDerecho(){
        imagen1=R.drawable.b;
        imagen2=R.drawable.c;
        imagen3=R.drawable.d;
        imagen4=R.drawable.f;
        imagen5=R.drawable.g;
        imagen6=R.drawable.h;
        imagen7=R.drawable.b_copia;
        imagen8=R.drawable.c_copia;
        imagen9=R.drawable.d_copia;
        imagen10=R.drawable.f_copia;
        imagen11=R.drawable.g_copia;
        imagen12=R.drawable.h_copia;
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
        if(v==imgCarta5){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta5,carta);

        }
        if(v==imgCarta6){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta6,carta);
        }
        if(v==imgCarta7){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta7,carta);

        }
        if(v==imgCarta8){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta8,carta);

        }
        if(v==imgCarta9){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta9,carta);

        }
        if(v==imgCarta10){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta10,carta);

        }
        if(v==imgCarta11){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta11,carta);

        }
        if(v==imgCarta12){
            int carta= Integer.parseInt((String) v.getTag());
            doStuff(imgCarta12,carta);

        }

    }
    private void doStuff(ImageView v, int cart){
        if(cartas[cart]==101){
            v.setImageResource(imagen1);
        }else  if(cartas[cart]==102){
            v.setImageResource(imagen2);
        }else  if(cartas[cart]==103){
            v.setImageResource(imagen3);
        }else  if(cartas[cart]==104){
            v.setImageResource(imagen4);
        }else  if(cartas[cart]==105){
            v.setImageResource(imagen5);
        }else  if(cartas[cart]==106){
            v.setImageResource(imagen6);
        }else if(cartas[cart]==201){
            v.setImageResource(imagen7);
        }else  if(cartas[cart]==202){
            v.setImageResource(imagen8);
        }else  if(cartas[cart]==203){
            v.setImageResource(imagen9);
        }else  if(cartas[cart]==204){
            v.setImageResource(imagen10);
        }else  if(cartas[cart]==205){
            v.setImageResource(imagen11);
        } if(cartas[cart]==206){
            v.setImageResource(imagen12);
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
            imgCarta5.setEnabled(false);
            imgCarta6.setEnabled(false);
            imgCarta7.setEnabled(false);
            imgCarta8.setEnabled(false);
            imgCarta9.setEnabled(false);
            imgCarta10.setEnabled(false);
            imgCarta11.setEnabled(false);
            imgCarta12.setEnabled(false);

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
            }else  if(primerClic==4){
                imgCarta5.setVisibility(View.INVISIBLE);
            }else  if(primerClic==5){
                imgCarta6.setVisibility(View.INVISIBLE);
            }else  if(primerClic==6){
                imgCarta7.setVisibility(View.INVISIBLE);
            }else  if(primerClic==7){
                imgCarta8.setVisibility(View.INVISIBLE);
            }else  if(primerClic==8){
                imgCarta9.setVisibility(View.INVISIBLE);
            }else  if(primerClic==9){
                imgCarta10.setVisibility(View.INVISIBLE);
            }else  if(primerClic==10){
                imgCarta11.setVisibility(View.INVISIBLE);
            }else  if(primerClic==11){
                imgCarta12.setVisibility(View.INVISIBLE);
            }

            if(segundoClic==0){
                imgCarta1.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==1){
                imgCarta2.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==2){
                imgCarta3.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==3){
                imgCarta4.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==4){
                imgCarta5.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==5){
                imgCarta6.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==6){
                imgCarta7.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==7){
                imgCarta8.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==8){
                imgCarta9.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==9){
                imgCarta10.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==10){
                imgCarta11.setVisibility(View.INVISIBLE);
            }else  if(segundoClic==11){
                imgCarta12.setVisibility(View.INVISIBLE);
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
            imgCarta5.setImageResource(R.drawable.carta_reves);
            imgCarta6.setImageResource(R.drawable.carta_reves);
            imgCarta7.setImageResource(R.drawable.carta_reves);
            imgCarta8.setImageResource(R.drawable.carta_reves);
            imgCarta9.setImageResource(R.drawable.carta_reves);
            imgCarta10.setImageResource(R.drawable.carta_reves);
            imgCarta11.setImageResource(R.drawable.carta_reves);
            imgCarta12.setImageResource(R.drawable.carta_reves);
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
        imgCarta5.setEnabled(true);
        imgCarta6.setEnabled(true);
        imgCarta7.setEnabled(true);
        imgCarta8.setEnabled(true);
        imgCarta9.setEnabled(true);
        imgCarta10.setEnabled(true);
        imgCarta11.setEnabled(true);
        imgCarta12.setEnabled(true);


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
