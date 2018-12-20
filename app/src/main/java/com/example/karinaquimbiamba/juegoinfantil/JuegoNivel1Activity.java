package com.example.karinaquimbiamba.juegoinfantil;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.annotation.NonNull;
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

import java.util.Random;
import java.util.UUID;

public class JuegoNivel1Activity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{

    //Variables definidas para la realización del menú al finalizar el juego
    private RelativeLayout rlPerder;
    private Button btnIntentar;
    private Button btnMenu;

    //Variables para la parte grafica del juego
    private TextView txtResultado;
    private Chronometer chronometer;
    private boolean empezarCronometro;
    private TextView txtPuntaje;
    private long pausarOffSet;
    private TextView txtNombre;
    private ImageView imgReiniciar;
    private ImageView imgVidas, imgInicio;
    int contadorPuntaje=0,vidas=3;
    LinearLayout lyVocal1, lyVocal2, lyVocal3, lyVocal4, lyVocal5;
    ImageView imgLetraa, imgLetrae, imgLetrai, imgLetrao, imgLetrau;
    Button vocalA, vocalE, vocalI, vocalO, vocalU;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nivel1);
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Varibles definidas para el menu cuando el juego termine
        btnIntentar= findViewById(R.id.btnIntentar);
        btnMenu= findViewById(R.id.btnMenu);
        txtResultado= findViewById(R.id.txtResultado);
        rlPerder= findViewById(R.id.rlPerder);


        txtPuntaje= findViewById(R.id.txtPuntaje);
        txtNombre= findViewById(R.id.txtNombre);
        chronometer= findViewById(R.id.chCronometro);
        imgInicio= findViewById(R.id.imgInicio);
        imgReiniciar=findViewById(R.id.imgReiniciar);
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

        //declarion de sonidos para letras
        sonidoLetraa =MediaPlayer.create(this, R.raw.sonidoa);
        sonidoLetrae =MediaPlayer.create(this, R.raw.sonidoe);
        sonidoLetrai =MediaPlayer.create(this, R.raw.sonidoi);
        sonidoLetrao =MediaPlayer.create(this, R.raw.sonidoo);
        sonidoLetrau =MediaPlayer.create(this, R.raw.sonidou);
        respuestaIncorrecta= MediaPlayer.create(this, R.raw.incorrecto);
        sonidoIndicacion= MediaPlayer.create(this,R.raw.buscar_letras);
        sonidoIndicacion.start();


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

        //Evento clic defindo para los botones
        imgInicio.setOnClickListener(this);
        imgReiniciar.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
        btnIntentar.setOnClickListener(this);

    }

    //Metodo que permite colocar los objetos en una parte definidad
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
                        sonidoLetraa.start();
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==5){
                            guardarDatos();
                            txtResultado.setText("Juego Terminado");
                            rlPerder.setVisibility(View.VISIBLE);

                        }

                    }else if(view.getId()==R.id.imgLetraE && v.getId()==R.id.lyVocal2){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        vocalE.setVisibility(View.GONE);
                        nuevo.addView(view);
                        contadorPuntaje=contadorPuntaje+1;
                        sonidoLetrae.start();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==5){
                            guardarDatos();
                            txtResultado.setText("Juegi Terminado");
                            rlPerder.setVisibility(View.VISIBLE);
                        }


                    }else if (view.getId()==R.id.imgLetraI && v.getId()==R.id.lyVocal3){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        vocalI.setVisibility(View.GONE);
                        nuevo.addView(view);
                        contadorPuntaje=contadorPuntaje+1;
                        sonidoLetrai.start();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==5){
                            guardarDatos();
                            txtResultado.setText("Juego Terminado");
                            rlPerder.setVisibility(View.VISIBLE);
                        }


                    }else if (view.getId()==R.id.imgLetraO && v.getId()==R.id.lyVocal4) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        vocalO.setVisibility(View.GONE);
                        nuevo.addView(view);
                        contadorPuntaje=contadorPuntaje+1;
                        sonidoLetrao.start();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==5){
                            guardarDatos();
                            txtResultado.setText("Juego Terminado");
                            rlPerder.setVisibility(View.VISIBLE);
                        }


                    }else if (view.getId()==R.id.imgLetraU && v.getId()==R.id.lyVocal5) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        vocalU.setVisibility(View.GONE);
                        nuevo.addView(view);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetrau.start();
                        if(contadorPuntaje==5){
                            guardarDatos();
                            txtResultado.setText("Juego Terminado");
                            rlPerder.setVisibility(View.VISIBLE);

                        }

                    }else {
                        vidas--;
                        respuestaIncorrecta.start();

                        switch (vidas){
                            case 0:
                                guardarDatos();
                                imgVidas.setImageResource(vidas);
                                txtResultado.setText("Perdiste");
                                rlPerder.setVisibility(View.VISIBLE);
                                break;
                            case 1:
                                Toast.makeText(JuegoNivel1Activity.this, "Tienes una vida",Toast.LENGTH_LONG).show();
                                imgVidas.setImageResource(R.drawable.unavida);
                                break;
                            case 2:
                                Toast.makeText(JuegoNivel1Activity.this, "Tienes dos vidas",Toast.LENGTH_LONG).show();
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

    //Implementacion del metodo touch para arrastrar elementos
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            view.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    public void reiniciarCronometro(){
        chronometer.setBase(SystemClock.elapsedRealtime());

    }

    //Iniicalizacion de Firebase para obtner datos del usuario logueado
    public void incializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();

        firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);{
                    txtNombre.setText(String.valueOf(dataSnapshot.child("name").getValue()));//Traer desde la base de datos firebase el nombre para colocarlo en el cajón de texto


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Guardar datos Puntaje del usuario obtneido durante el juego
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

    //Implematación del metodo de clic para los botones definidos
    @Override
    public void onClick(View v) {
        if(v==imgInicio){
            startActivity(new Intent(getApplicationContext(),NivelesAre1Activity.class));
        }
        if (v== imgReiniciar){
            startActivity(new Intent(getApplicationContext(),JuegoNivel1Activity.class));
        }
        if (v==btnMenu){
            startActivity(new Intent(getApplicationContext(),NivelesAre1Activity.class));
        }
        if (v==btnIntentar){
            startActivity(new Intent(getApplicationContext(),JuegoNivel1Activity.class));
        }
    }
}
