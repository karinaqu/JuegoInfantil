package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Puntaje;
import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class JuegoNivel1Activity extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean correr;
    private long pausarOffSet;
    private Button buttonJuegoTerminado;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference; //Definción de referencia para firebase
    private FirebaseAuth.AuthStateListener mAuthListener; //Definición de variable de autenticacipin
    private FirebaseAuth firebaseAuth; //Definción de variable Autenticación
    private TextView textViewNombre; //Definción de variable texto  para el nombre
    private TextView textViewRol;
    int contador=0;
    int vidas= 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nivel1);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        chronometer= findViewById(R.id.chCronometro);
        //chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());


        buttonJuegoTerminado= findViewById(R.id.btnTerminar);
        incializarFirebase();

        firebaseAuth= FirebaseAuth.getInstance();



        if(!correr){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            correr=true;

        }

        textViewNombre=(TextView) findViewById(R.id.txtNombre);

        firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);{
                    textViewNombre.setText(String.valueOf(dataSnapshot.child("name").getValue()));//Traer desde la base de datos firebase el nombre para colocarlo en el cajón de texto
                    //listarNiveles();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        buttonJuegoTerminado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Puntaje puntaje= new Puntaje();
                puntaje.setUid(UUID.randomUUID().toString());
                puntaje.setPuntaje(contador);
                puntaje.setIdArea("Matematica");
                puntaje.setIdNivel("Nivel1");
                puntaje.setIdUsuario(String.valueOf(textViewNombre.getText()));
                puntaje.setVidas(vidas);
                puntaje.setTiempo(String.valueOf(chronometer.getText()));
                //puntaje.setCronometro(chronometer);
                databaseReference.child("Puntaje").child(puntaje.getUid()).setValue(puntaje);
                startActivity(new Intent(getApplicationContext(), NivelesAre1Activity.class));


            }
        });




    }


    //Iniciar cronometro
    public void startCronometro(View v){
        if(!correr){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            correr=true;

        }


    }
    //Pausar cronometro
    public void pausarCronometro(View v){
        if(correr){
            chronometer.stop();
            pausarOffSet= SystemClock.elapsedRealtime()- chronometer.getBase();
            correr=false;
        }



    }
    //Reiniciar Cronometro
    public void riniciarCronometro(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());

    }

    public void incializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }



}
