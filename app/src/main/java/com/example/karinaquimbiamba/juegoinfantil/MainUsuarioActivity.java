package com.example.karinaquimbiamba.juegoinfantil;


import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainUsuarioActivity extends AppCompatActivity implements View.OnClickListener{

    private LottieAnimationView ltanimacion;

    private Button buttonPuntaje;
    private Button buttonJugar;
    private Button btnSalir;
    private MediaPlayer mdMusica;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_usuario);

        ltanimacion= findViewById(R.id.ltanimacion);
        ltanimacion.loop(true);
        ltanimacion.playAnimation();
        ltanimacion.setOnClickListener(this);

        btnSalir = findViewById(R.id.btnSalir);
        buttonJugar = findViewById(R.id.btnJugar);
        buttonPuntaje = findViewById(R.id.btnPuntajes);

        buttonJugar.setOnClickListener(this);
        buttonPuntaje.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

        firebaseAuth= FirebaseAuth.getInstance();

        mdMusica = MediaPlayer.create(this, R.raw.fondo);
        mdMusica.setLooping(true);
        mdMusica.start();



    }



    @Override
    public void onClick(View view) {

        if(view==buttonJugar){
            startActivity(new Intent(this, AreasActivity.class));
        }
        if(view==buttonPuntaje){

            startActivity(new Intent(this, PuntajeUsuarioActivity.class));
        }
        if(view==btnSalir){
            firebaseAuth.signOut();//Permite salir al usuario es decir desloguearse
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view==ltanimacion){
            startActivity(new Intent(this, AreasActivity.class));

        }


    }
    @Override
    protected  void  onDestroy(){
        super.onDestroy();
        if (mdMusica.isPlaying()){
            mdMusica.stop();
            mdMusica.release();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        mdMusica.start();
    }
    /*@Override
    protected void onPause(){
        super.onPause();
        mdMusica.pause();
    }*/

}
