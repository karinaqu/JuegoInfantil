package com.example.karinaquimbiamba.juegoinfantil;

import android.content.ClipData;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Puntaje;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class JuegoOrdenarVocales extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{

    //Variables definidas para la realización del menú al finalizar el juego
    private RelativeLayout rlMenu;

    private TextView txtResultado;
    private Chronometer chronometer;
    private boolean empezarCronometro;
    private TextView txtPuntaje;
    private ImageView imgFlechaAtras;
    private long pausarOffSet;
    LinearLayout lyVocal1, lyVocal2, lyVocal3, lyVocal4, lyVocal5;
    ImageView imgLetraa, imgLetrae, imgLetrai, imgLetrao, imgLetrau;
    Button vocalA, vocalE, vocalI, vocalO, vocalU;
    int contadorPuntaje=0,vidas=3;
    private TextView txtNombre;
    private ImageView imgVidas;
    public TextView textViewNombre;


    private ImageView imgRepetir;
    private ImageView imgMenu;
    private ImageView imgSiguiente;
    private ImageView imgCorrecto;
    private ImageView imgIncorrecto;
    private TextView txtEspera;


    //Variables definidas para la base de datos
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    //sonidos de letras
    private MediaPlayer sonidoLetraa;
    private MediaPlayer sonidoLetrae;
    private MediaPlayer sonidoLetrai;
    private MediaPlayer sonidoLetrao;
    private MediaPlayer sonidoLetrau;
    private MediaPlayer sonidoIndicacion;
    private MediaPlayer respuestaIncorrecta;
    private MediaPlayer respuestaCorrecta;
    private MediaPlayer perdiste;
    private MediaPlayer juegoTerminado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_ordenar_vocales);

        txtEspera= findViewById(R.id.txtEspera);
        imgCorrecto=findViewById(R.id.imgCorrecto);
        imgIncorrecto=findViewById(R.id.imgIncorrecto);
        rlMenu= findViewById(R.id.rlMenu);
        txtResultado= findViewById(R.id.txtResultado);
        imgMenu=findViewById(R.id.imgMenu);
        imgRepetir=findViewById(R.id.imgRepetir);
        imgSiguiente=findViewById(R.id.imgSiguiente);
        txtNombre=(TextView) findViewById(R.id.txtNombre);

        imgMenu.setOnClickListener(this);
        imgRepetir.setOnClickListener(this);
        imgSiguiente.setOnClickListener(this);

        imgFlechaAtras= findViewById(R.id.imgAtras);
        imgFlechaAtras.setOnClickListener(this);


        txtPuntaje= findViewById(R.id.txtPuntaje);
        txtNombre= findViewById(R.id.txtNombre);
        chronometer= findViewById(R.id.chCronometro);
        imgVidas=findViewById(R.id.imgVidas);
        txtNombre=(TextView) findViewById(R.id.txtNombre);
        firebaseAuth= FirebaseAuth.getInstance();
        incializarFirebase();

        imgLetraa=findViewById(R.id.imgLetraA);
        imgLetrae=findViewById(R.id.imgLetraE);
        imgLetrai=findViewById(R.id.imgLetraI);
        imgLetrao=findViewById(R.id.imgLetraO);
        imgLetrau=findViewById(R.id.imgLetraU);

        lyVocal1=findViewById(R.id.lyVocal1);
        lyVocal2=findViewById(R.id.lyVocal2);
        lyVocal3=findViewById(R.id.lyVocal3);
        lyVocal4=findViewById(R.id.lyVocal4);
        lyVocal5=findViewById(R.id.lyVocal5);

        vocalA= findViewById(R.id.vocalA);
        vocalE= findViewById(R.id.vocalE);
        vocalI= findViewById(R.id.vocalI);
        vocalO= findViewById(R.id.vocalO);
        vocalU= findViewById(R.id.vocalU);


        imgLetraa.setOnTouchListener(this);
        imgLetrae.setOnTouchListener(this);
        imgLetrai.setOnTouchListener(this);
        imgLetrao.setOnTouchListener(this);
        imgLetrau.setOnTouchListener(this);


        lyVocal1.setOnDragListener(dragListener);
        lyVocal2.setOnDragListener(dragListener);
        lyVocal3.setOnDragListener(dragListener);
        lyVocal4.setOnDragListener(dragListener);
        lyVocal5.setOnDragListener(dragListener);

        //Cronometro de tiempo defindo en el juego
        chronometer.setBase(SystemClock.elapsedRealtime());
        if(!empezarCronometro){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            empezarCronometro=true;

        }
        //declarion de sonidos para letras
        sonidoLetraa =MediaPlayer.create(this, R.raw.sonidoa);
        sonidoLetrae =MediaPlayer.create(this, R.raw.sonidoe);
        sonidoLetrai =MediaPlayer.create(this, R.raw.sonidoi);
        sonidoLetrao =MediaPlayer.create(this, R.raw.sonidoo);
        sonidoLetrau =MediaPlayer.create(this, R.raw.sonidou);
        respuestaIncorrecta= MediaPlayer.create(this, R.raw.incorrecto);
        respuestaCorrecta= MediaPlayer.create(this, R.raw.correcto);
        perdiste= MediaPlayer.create(this, R.raw.perdiste);
        juegoTerminado= MediaPlayer.create(this, R.raw.juego_terminado);
        sonidoIndicacion= MediaPlayer.create(this,R.raw.buscar_letras);
        sonidoIndicacion.start();



    }

    View.OnLongClickListener longclickLister= new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data= ClipData.newPlainText("","");
            View.DragShadowBuilder builder= new View.DragShadowBuilder(v);
            v.startDrag(data,builder,v,0);
            return true;
        }
    };

    View.OnDragListener dragListener= new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent= event.getAction();
            final  View view= (View) event.getLocalState();

            switch (dragEvent){

                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    if (view.getId()== R.id.imgLetraA && v.getId()==R.id.lyVocal1){
                        //Toast.makeText(Vocales.this,"dropped", Toast.LENGTH_SHORT).show();
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        vocalA.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraa.setOnTouchListener(null);
                        sonidoLetraa.start();
                        contadorPuntaje=contadorPuntaje+1;
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==5){
                            guardarDatos();
                            finJuego();
                        }
                    }else if(view.getId()==R.id.imgLetraE && v.getId()==R.id.lyVocal2){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        vocalE.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetrae.setOnTouchListener(null);
                        sonidoLetrae.start();
                        contadorPuntaje=contadorPuntaje+1;
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==5){
                            guardarDatos();
                            finJuego();
                        }
                    }else if (view.getId()==R.id.imgLetraI && v.getId()==R.id.lyVocal3){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        vocalI.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetrai.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        sonidoLetrai.start();
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==5){
                            guardarDatos();
                            finJuego();
                        }
                    }else if (view.getId()==R.id.imgLetraO && v.getId()==R.id.lyVocal4) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        vocalO.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetrao.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        sonidoLetrao.start();
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==5){
                            guardarDatos();
                            finJuego();
                        }

                    }else if (view.getId()==R.id.imgLetraU && v.getId()==R.id.lyVocal5) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        vocalU.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetrau.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetrau.start();
                        imgCorrecto();
                        if(contadorPuntaje==5){
                            guardarDatos();
                            finJuego();

                        }

                    }else {
                        vidas--;
                        imgIncorrecto();

                        switch (vidas){
                            case 0:
                                guardarDatos();
                                imgVidas.setImageResource(vidas);
                                PerdisteJuego();

                                break;
                            case 1:
                                Toast.makeText(JuegoOrdenarVocales.this, "Tienes una vida",Toast.LENGTH_SHORT).show();
                                imgVidas.setImageResource(R.drawable.unavida);
                                break;
                            case 2:
                                Toast.makeText(JuegoOrdenarVocales.this, "Tienes dos vidas",Toast.LENGTH_SHORT).show();
                                imgVidas.setImageResource(R.drawable.dosvidas);
                                break;
                            case 3:
                                imgVidas.setImageResource(R.drawable.tresvidas);
                                break;

                        }

                    }

                    break;
            }
            return true;
        }
    };


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            view.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }
    void imgCorrecto(){
        new CountDownTimer(1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtEspera.setText(""+(millisUntilFinished/1000)+1);
                imgCorrecto.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFinish() {
                imgCorrecto.setVisibility(View.INVISIBLE);
                respuestaCorrecta.start();

            }
        }.start();


    }
    void imgIncorrecto(){
        new CountDownTimer(1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtEspera.setText(""+(millisUntilFinished/1000)+1);
                imgIncorrecto.setVisibility(View.VISIBLE);
                respuestaIncorrecta.start();

            }

            @Override
            public void onFinish() {
                imgIncorrecto.setVisibility(View.INVISIBLE);

            }
        }.start();


    }
    public void incializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }

    void finJuego(){
        new CountDownTimer(2000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtEspera.setText(""+(millisUntilFinished/1000)+1);
            }

            @Override
            public void onFinish() {
                txtResultado.setText("Juego Terminado");
                rlMenu.setVisibility(View.VISIBLE);
                juegoTerminado.start();
            }
        }.start();

    }
    void PerdisteJuego(){
        new CountDownTimer(2000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtEspera.setText(""+(millisUntilFinished/1000)+1);
            }

            @Override
            public void onFinish() {
                txtResultado.setText("PERDISTE");
                rlMenu.setVisibility(View.VISIBLE);
                perdiste.start();
            }
        }.start();

    }


    public void guardarDatos(){
        Puntaje puntaje= new Puntaje();
        puntaje.setUid(UUID.randomUUID().toString());
        puntaje.setPuntaje(contadorPuntaje);
        puntaje.setIdArea("Lenguaje");
        puntaje.setIdNivel("Nivel 1");
        puntaje.setIdUsuario(String.valueOf(txtNombre.getText()));
        puntaje.setVidas(vidas);
        puntaje.setTiempo(String.valueOf(chronometer.getText()));
        databaseReference.child("Puntaje").child(puntaje.getUid()).setValue(puntaje);
    }

    @Override
    public void onClick(View v) {
        if(v==imgFlechaAtras){
            startActivity(new Intent(getApplicationContext(), NivelesAre1Activity.class));
            finish();
        }
        if(v==imgRepetir){
            startActivity(new Intent(getApplicationContext(), JuegoOrdenarVocales.class));
            finish();
        }
        if(v==imgMenu){
            startActivity(new Intent(getApplicationContext(), NivelesAre1Activity.class));
            finish();
        }
        if(v==imgSiguiente){
            startActivity(new Intent(getApplicationContext(), JuegoCompletarPalabra.class));
            finish();
        }

    }
}
