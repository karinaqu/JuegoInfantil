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
import android.widget.GridLayout;
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

public class JuegoOrdenarAbecedario extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{

    private RelativeLayout rlMenu;
    private GridLayout glPisos;

    private TextView txtResultado;
    private Chronometer chronometer;
    private boolean empezarCronometro;
    private TextView txtPuntaje;
    private ImageView imgFlechaAtras, imgTecho;
    private long pausarOffSet;
    LinearLayout lyLetraA, lyLetraB, lyLetraC, lyLetraD, lyLetraE, lyLetraF, lyLetraG, lyLetraH, lyLetraI, lyLetraJ,
    lyLetraK, lyLetraL, lyLetraM, lyLetraN, lyLetraÑ, lyLetraO, lyLetraP, lyLetraQ, lyLetraR, lyLetraS,
    lyLetraT, lyLetraU, lyLetraV, lyLetraW, lyLetraX, lyLetraY, lyLetraZ;
    ImageView imgLetraA, imgLetraB, imgLetraC, imgLetraD, imgLetraE, imgLetraF, imgLetraG, imgLetraH, imgLetraI, imgLetraJ,
    imgLetraK, imgLetraL, imgLetraM, imgLetraN, imgLetraÑ, imgLetraO, imgLetraP, imgLetraQ, imgLetraR, imgLetraS,
    imgLetraT, imgLetraU, imgLetraV, imgLetraW, imgLetraX, imgLetraY,imgLetraZ;
    Button btnLetraA, btnLetraB, btnLetraC, btnLetraD, btnLetraE, btnLetraF, btnLetraG, btnLetraH, btnLetraI, btnLetraJ,
            btnLetraK, btnLetraL, btnLetraM, btnLetraN, btnLetraÑ, btnLetraO, btnLetraP, btnLetraQ, btnLetraR, btnLetraS,
            btnLetraT, btnLetraU, btnLetraV, btnLetraW, btnLetraX, btnLetraY, btnLetraZ;
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
    private MediaPlayer sonidoLetraA;
    private MediaPlayer sonidoLetraB;
    private MediaPlayer sonidoLetraC;
    private MediaPlayer sonidoLetraD;
    private MediaPlayer sonidoLetraE;
    private MediaPlayer sonidoLetraF;
    private MediaPlayer sonidoLetraG;
    private MediaPlayer sonidoLetraH;
    private MediaPlayer sonidoLetraI;
    private MediaPlayer sonidoLetraJ;
    private MediaPlayer sonidoLetraK;
    private MediaPlayer sonidoLetraL;
    private MediaPlayer sonidoLetraM;
    private MediaPlayer sonidoLetraN;
    private MediaPlayer sonidoLetraÑ;
    private MediaPlayer sonidoLetraO;
    private MediaPlayer sonidoLetraP;
    private MediaPlayer sonidoLetraQ;
    private MediaPlayer sonidoLetraR;
    private MediaPlayer sonidoLetraS;
    private MediaPlayer sonidoLetraT;
    private MediaPlayer sonidoLetraU;
    private MediaPlayer sonidoLetraV;
    private MediaPlayer sonidoLetraW;
    private MediaPlayer sonidoLetraX;
    private MediaPlayer sonidoLetraY;
    private MediaPlayer sonidoLetraZ;


    private MediaPlayer sonidoIndicacion;
    private MediaPlayer respuestIncorrecta;
    private MediaPlayer respuestCorrecta;
    private MediaPlayer perdiste;
    private MediaPlayer juegoTerminado;
    private MediaPlayer sonidosNumeros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_ordenar_abecedario);


        txtEspera= findViewById(R.id.txtEspera);
        imgCorrecto=findViewById(R.id.imgCorrecto);
        imgIncorrecto=findViewById(R.id.imgIncorrecto);
        rlMenu= findViewById(R.id.rlMenu);
        glPisos =findViewById(R.id.glPisos);
        txtResultado= findViewById(R.id.txtResultado);
        imgMenu=findViewById(R.id.imgMenu);
        imgRepetir=findViewById(R.id.imgRepetir);
        imgSiguiente=findViewById(R.id.imgSiguiente);
        textViewNombre=(TextView) findViewById(R.id.txtNombre);
        //textViewNombre.setText(""+ UusarioLogeado.currentUser.getName());

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

        imgLetraA=findViewById(R.id.imgLetraA);
        imgLetraB=findViewById(R.id.imgLetraB);
        imgLetraC=findViewById(R.id.imgLetraC);
        imgLetraD=findViewById(R.id.imgLetraD);
        imgLetraE=findViewById(R.id.imgLetraE);
        imgLetraF=findViewById(R.id.imgLetraF);
        imgLetraG=findViewById(R.id.imgLetraG);
        imgLetraH=findViewById(R.id.imgLetraH);
        imgLetraI=findViewById(R.id.imgLetraI);
        imgLetraJ=findViewById(R.id.imgLetraJ);
        imgLetraK=findViewById(R.id.imgLetraK);
        imgLetraL=findViewById(R.id.imgLetraL);
        imgLetraM=findViewById(R.id.imgLetraM);
        imgLetraN=findViewById(R.id.imgLetraN);
        imgLetraÑ=findViewById(R.id.imgLetraÑ);
        imgLetraO =findViewById(R.id.imgLetraO);
        imgLetraP=findViewById(R.id.imgLetraP);
        imgLetraQ=findViewById(R.id.imgLetraQ);
        imgLetraR=findViewById(R.id.imgLetraR);
        imgLetraS=findViewById(R.id.imgLetraS);
        imgLetraT=findViewById(R.id.imgLetraT);
        imgLetraU=findViewById(R.id.imgLetraU);
        imgLetraV=findViewById(R.id.imgLetraV);
        imgLetraW=findViewById(R.id.imgLetraW);
        imgLetraX=findViewById(R.id.imgLetraX);
        imgLetraY =findViewById(R.id.imgLetraY);
        imgLetraZ=findViewById(R.id.imgLetraZ);


        lyLetraA=findViewById(R.id.lyLetraA);
        lyLetraB=findViewById(R.id.lyLetraB);
        lyLetraC=findViewById(R.id.lyLetraC);
        lyLetraD=findViewById(R.id.lyLetraD);
        lyLetraE=findViewById(R.id.lyLetraE);
        lyLetraF=findViewById(R.id.lyLetraF);
        lyLetraG=findViewById(R.id.lyLetraG);
        lyLetraH=findViewById(R.id.lyLetraH);
        lyLetraI=findViewById(R.id.lyLetraI);
        lyLetraJ=findViewById(R.id.lyLetraJ);
        lyLetraK=findViewById(R.id.lyLetraK);
        lyLetraL=findViewById(R.id.lyLetraL);
        lyLetraM=findViewById(R.id.lyLetraM);
        lyLetraN=findViewById(R.id.lyLetraN);
        lyLetraÑ=findViewById(R.id.lyLetraÑ);
        lyLetraO=findViewById(R.id.lyLetraO);
        lyLetraP=findViewById(R.id.lyLetraP);
        lyLetraQ=findViewById(R.id.lyLetraQ);
        lyLetraR=findViewById(R.id.lyLetraR);
        lyLetraS=findViewById(R.id.lyLetraS);
        lyLetraT=findViewById(R.id.lyLetraT);
        lyLetraU=findViewById(R.id.lyLetraU);
        lyLetraV=findViewById(R.id.lyLetraV);
        lyLetraW=findViewById(R.id.lyLetraW);
        lyLetraX=findViewById(R.id.lyLetraX);
        lyLetraY=findViewById(R.id.lyLetraY);
        lyLetraZ=findViewById(R.id.lyLetraz);


        btnLetraA= findViewById(R.id.btnLetraA);
        btnLetraB= findViewById(R.id.btnLetraB);
        btnLetraC= findViewById(R.id.btnLetraC);
        btnLetraD= findViewById(R.id.btnLetraD);
        btnLetraE= findViewById(R.id.btnLetraE);
        btnLetraF= findViewById(R.id.btnLetraF);
        btnLetraG= findViewById(R.id.btnLetraG);
        btnLetraH= findViewById(R.id.btnLetrasH);
        btnLetraI= findViewById(R.id.btnLetraI);
        btnLetraJ= findViewById(R.id.btnLetraJ);
        btnLetraK= findViewById(R.id.btnLetraK);
        btnLetraL= findViewById(R.id.btnLetraL);
        btnLetraM= findViewById(R.id.btnLetraM);
        btnLetraN= findViewById(R.id.btnLetraN);
        btnLetraÑ= findViewById(R.id.btnLetraÑ);
        btnLetraO= findViewById(R.id.btnLetraO);
        btnLetraP= findViewById(R.id.btnLetraP);
        btnLetraQ= findViewById(R.id.btnLetraQ);
        btnLetraR= findViewById(R.id.btnLetraR);
        btnLetraS= findViewById(R.id.btnLetraS);
        btnLetraT= findViewById(R.id.btnLetraT);
        btnLetraU= findViewById(R.id.btnLetraU);
        btnLetraV= findViewById(R.id.btnLetraV);
        btnLetraW= findViewById(R.id.btnLetraW);
        btnLetraX= findViewById(R.id.btnLetraX);
        btnLetraY= findViewById(R.id.btnLetraY);
        btnLetraZ= findViewById(R.id.btnLetraZ);



        imgLetraA.setOnTouchListener(this);
        imgLetraB.setOnTouchListener(this);
        imgLetraC.setOnTouchListener(this);
        imgLetraD.setOnTouchListener(this);
        imgLetraE.setOnTouchListener(this);
        imgLetraF.setOnTouchListener(this);
        imgLetraG.setOnTouchListener(this);
        imgLetraH.setOnTouchListener(this);
        imgLetraI.setOnTouchListener(this);
        imgLetraJ.setOnTouchListener(this);
        imgLetraK.setOnTouchListener(this);
        imgLetraL.setOnTouchListener(this);
        imgLetraM.setOnTouchListener(this);
        imgLetraN.setOnTouchListener(this);
        imgLetraÑ.setOnTouchListener(this);
        imgLetraO.setOnTouchListener(this);
        imgLetraP.setOnTouchListener(this);
        imgLetraQ.setOnTouchListener(this);
        imgLetraR.setOnTouchListener(this);
        imgLetraS.setOnTouchListener(this);
        imgLetraT.setOnTouchListener(this);
        imgLetraU.setOnTouchListener(this);
        imgLetraV.setOnTouchListener(this);
        imgLetraW.setOnTouchListener(this);
        imgLetraX.setOnTouchListener(this);
        imgLetraY.setOnTouchListener(this);
        imgLetraZ.setOnTouchListener(this);


        lyLetraA.setOnDragListener(dragListener);
        lyLetraB.setOnDragListener(dragListener);
        lyLetraC.setOnDragListener(dragListener);
        lyLetraD.setOnDragListener(dragListener);
        lyLetraE.setOnDragListener(dragListener);
        lyLetraF.setOnDragListener(dragListener);
        lyLetraG.setOnDragListener(dragListener);
        lyLetraH.setOnDragListener(dragListener);
        lyLetraI.setOnDragListener(dragListener);
        lyLetraJ.setOnDragListener(dragListener);
        lyLetraK.setOnDragListener(dragListener);
        lyLetraL.setOnDragListener(dragListener);
        lyLetraM.setOnDragListener(dragListener);
        lyLetraN.setOnDragListener(dragListener);
        lyLetraÑ.setOnDragListener(dragListener);
        lyLetraO.setOnDragListener(dragListener);
        lyLetraP.setOnDragListener(dragListener);
        lyLetraQ.setOnDragListener(dragListener);
        lyLetraR.setOnDragListener(dragListener);
        lyLetraS.setOnDragListener(dragListener);
        lyLetraT.setOnDragListener(dragListener);
        lyLetraU.setOnDragListener(dragListener);
        lyLetraV.setOnDragListener(dragListener);
        lyLetraW.setOnDragListener(dragListener);
        lyLetraX.setOnDragListener(dragListener);
        lyLetraY.setOnDragListener(dragListener);
        lyLetraZ.setOnDragListener(dragListener);


        //Cronometro de tiempo defindo en el juego
        chronometer.setBase(SystemClock.elapsedRealtime());
        if(!empezarCronometro){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            empezarCronometro=true;

        }
        //declarion de sonidos para letras
        sonidoLetraA = MediaPlayer.create(this, R.raw.sonidoa);
        sonidoLetraB =MediaPlayer.create(this, R.raw.sonidob);
        sonidoLetraC =MediaPlayer.create(this, R.raw.sonidoc);
        sonidoLetraD =MediaPlayer.create(this, R.raw.sonidod);
        sonidoLetraE =MediaPlayer.create(this, R.raw.sonidoe);
        sonidoLetraF =MediaPlayer.create(this, R.raw.sonidof);
        sonidoLetraG =MediaPlayer.create(this, R.raw.sonidog);
        sonidoLetraH =MediaPlayer.create(this, R.raw.sonidoh);
        sonidoLetraI =MediaPlayer.create(this, R.raw.sonidoi);
        sonidoLetraJ =MediaPlayer.create(this, R.raw.sonidoj);
        sonidoLetraK = MediaPlayer.create(this, R.raw.sonidok);
        sonidoLetraL =MediaPlayer.create(this, R.raw.sonidol);
        sonidoLetraM =MediaPlayer.create(this, R.raw.sonidom);
        sonidoLetraN =MediaPlayer.create(this, R.raw.sonidon);
        sonidoLetraÑ =MediaPlayer.create(this, R.raw.sonidoene);
        sonidoLetraO =MediaPlayer.create(this, R.raw.sonidoo);
        sonidoLetraP =MediaPlayer.create(this, R.raw.sonidop);
        sonidoLetraQ =MediaPlayer.create(this, R.raw.sonidoq);
        sonidoLetraR =MediaPlayer.create(this, R.raw.sonidor);
        sonidoLetraS =MediaPlayer.create(this, R.raw.sonidos);
        sonidoLetraT =MediaPlayer.create(this, R.raw.sonidot);
        sonidoLetraU =MediaPlayer.create(this, R.raw.sonidou);
        sonidoLetraV =MediaPlayer.create(this, R.raw.sonidov);
        sonidoLetraW =MediaPlayer.create(this, R.raw.sonidow);
        sonidoLetraX =MediaPlayer.create(this, R.raw.sonidox);
        sonidoLetraY =MediaPlayer.create(this, R.raw.sonidoy);
        sonidoLetraZ =MediaPlayer.create(this, R.raw.sonidoz);



        respuestIncorrecta= MediaPlayer.create(this, R.raw.incorrecto);
        respuestCorrecta= MediaPlayer.create(this, R.raw.correcto);
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
                    if (view.getId()== R.id.imgLetraA && v.getId()==R.id.lyLetraA){
                        //Toast.makeText(Vocales.this,"dropped", Toast.LENGTH_SHORT).show();
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        btnLetraA.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraA.setOnTouchListener(null);
                        sonidoLetraA.start();
                        contadorPuntaje=contadorPuntaje+1;
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();
                        }
                    }else if(view.getId()==R.id.imgLetraB && v.getId()==R.id.lyLetraB){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        btnLetraB.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraB.setOnTouchListener(null);
                        sonidoLetraB.start();
                        contadorPuntaje=contadorPuntaje+1;
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();
                        }
                    }else if (view.getId()==R.id.imgLetraC && v.getId()==R.id.lyLetraC){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        btnLetraC.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraC.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        sonidoLetraC.start();
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();
                        }
                    }else if (view.getId()==R.id.imgLetraD && v.getId()==R.id.lyLetraD) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraD.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraD.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        sonidoLetraD.start();
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();
                        }

                    }else if (view.getId()==R.id.imgLetraE && v.getId()==R.id.lyLetraE) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraE.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraE.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraE.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraF && v.getId()==R.id.lyLetraF) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraF.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraF.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraF.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraG && v.getId()==R.id.lyLetraG) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraG.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraG.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraG.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraH && v.getId()==R.id.lyLetraH) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraH.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraH.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraH.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraI && v.getId()==R.id.lyLetraI) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraI.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraI.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraI.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraJ && v.getId()==R.id.lyLetraJ) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraJ.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraJ.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraJ.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if(view.getId()==R.id.imgLetraK && v.getId()==R.id.lyLetraK){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        btnLetraK.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraK.setOnTouchListener(null);
                        sonidoLetraK.start();
                        contadorPuntaje=contadorPuntaje+1;
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();
                        }
                    }else if (view.getId()==R.id.imgLetraL && v.getId()==R.id.lyLetraL){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        btnLetraL.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraL.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        sonidoLetraL.start();
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();
                        }
                    }else if (view.getId()==R.id.imgLetraM && v.getId()==R.id.lyLetraM) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraM.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraM.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        sonidoLetraM.start();
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();
                        }

                    }else if (view.getId()==R.id.imgLetraN && v.getId()==R.id.lyLetraN) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraN.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraN.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraN.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraÑ && v.getId()==R.id.lyLetraÑ) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraÑ.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraÑ.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraÑ.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraO && v.getId()==R.id.lyLetraO) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraO.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraO.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraO.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraP && v.getId()==R.id.lyLetraP) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraP.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraP.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraP.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraQ && v.getId()==R.id.lyLetraQ) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraQ.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraQ.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraQ.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraR && v.getId()==R.id.lyLetraR) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraR.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraR.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraR.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if(view.getId()==R.id.imgLetraS && v.getId()==R.id.lyLetraS){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        btnLetraS.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraS.setOnTouchListener(null);
                        sonidoLetraS.start();
                        contadorPuntaje=contadorPuntaje+1;
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();
                        }
                    }else if (view.getId()==R.id.imgLetraT && v.getId()==R.id.lyLetraT){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        btnLetraT.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraT.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        sonidoLetraT.start();
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();
                        }
                    }else if (view.getId()==R.id.imgLetraU && v.getId()==R.id.lyLetraU) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraU.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraU.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        sonidoLetraU.start();
                        imgCorrecto();
                        txtPuntaje.setText(""+contadorPuntaje);
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();
                        }

                    }else if (view.getId()==R.id.imgLetraV && v.getId()==R.id.lyLetraV) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraV.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraV.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraV.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraW && v.getId()==R.id.lyLetraW) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraW.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraW.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraW.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraX && v.getId()==R.id.lyLetraX) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraX.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraX.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraX.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraY && v.getId()==R.id.lyLetraY) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraY.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraY.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraY.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }else if (view.getId()==R.id.imgLetraZ && v.getId()==R.id.lyLetraz) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        btnLetraZ.setVisibility(View.GONE);
                        nuevo.addView(view);
                        imgLetraZ.setOnTouchListener(null);
                        contadorPuntaje=contadorPuntaje+1;
                        txtPuntaje.setText(""+contadorPuntaje);
                        sonidoLetraZ.start();
                        imgCorrecto();
                        if(contadorPuntaje==27){
                            guardarDatos();
                            finJuego();

                        }

                    }

                    else {
                        vidas--;
                        imgIncorrecto();
                        //respuestaIncorrecta.start();

                        switch (vidas){
                            case 0:
                                guardarDatos();
                                imgVidas.setImageResource(vidas);
                                PerdisteJuego();
                                break;
                            case 1:
                                Toast.makeText(JuegoOrdenarAbecedario.this, "Tienes una vida",Toast.LENGTH_SHORT).show();
                                imgVidas.setImageResource(R.drawable.unavida);
                                break;
                            case 2:
                                Toast.makeText(JuegoOrdenarAbecedario.this, "Tienes dos vidas",Toast.LENGTH_SHORT).show();
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
                respuestCorrecta.start();

            }
        }.start();


    }
    void imgIncorrecto(){
        new CountDownTimer(1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtEspera.setText(""+(millisUntilFinished/1000)+1);
                imgIncorrecto.setVisibility(View.VISIBLE);
                respuestIncorrecta.start();
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
                glPisos.setVisibility(View.GONE);

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
                glPisos.setVisibility(View.GONE);
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
        /*Puntaje puntaje= new Puntaje();
        puntaje.setUid(UUID.randomUUID().toString());
        puntaje.setPuntaje(contadorPuntaje);
        puntaje.setIdArea("Lenguaje");
        puntaje.setIdNivel("Nivel 2");
        puntaje.setIdUsuario(String.valueOf(txtNombre.getText()));
        puntaje.setVidas(vidas);
        puntaje.setTiempo(String.valueOf(chronometer.getText()));
        databaseReference.child("Puntaje").child(puntaje.getUid()).setValue(puntaje);*/
    }

    @Override
    public void onClick(View v) {
        if(v==imgFlechaAtras){
            startActivity(new Intent(getApplicationContext(), NivelesAre1Activity.class));
            finish();
        }
        if(v==imgRepetir){
            startActivity(new Intent(getApplicationContext(), JuegoOrdenarAbecedario.class));
            finish();
        }
        if(v==imgMenu){
            startActivity(new Intent(getApplicationContext(), NivelesAre1Activity.class));
            finish();
        }
        if(v==imgSiguiente){
            startActivity(new Intent(getApplicationContext(), JuegoNivel3Area1Activity.class));
            finish();
        }

    }
}
