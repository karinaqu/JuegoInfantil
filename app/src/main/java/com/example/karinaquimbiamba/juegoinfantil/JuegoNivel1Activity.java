package com.example.karinaquimbiamba.juegoinfantil;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

public class JuegoNivel1Activity extends AppCompatActivity implements View.OnTouchListener{

    private Chronometer chronometer;
    private boolean empezarCronometro;
    private long pausarOffSet;
    private ImageView imgReiniciar;
    private ImageView imgVidas, imgInicio;
    int contadorPuntaje=0,vidas=3;

    LinearLayout lyVocal1, lyVocal2, lyVocal3, lyVocal4, lyVocal5;
    ImageView imgLetraa, imgLetrae, imgLetrai, imgLetrao, imgLetrau;
    int[] aleatorio1={R.id.imgLetraA,R.id.imgLetraE,R.id.imgLetraI, R.id.imgLetraO,R.id.imgLetraU};

    Random r1= new Random();
    int a1=r1.nextInt(4);


    Button vocalA, vocalE, vocalI, vocalO, vocalU, btnA, btnE, btnI, btnO, btnU;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nivel1);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        chronometer= findViewById(R.id.chCronometro);
        imgInicio= findViewById(R.id.imgInicio);
        imgReiniciar=findViewById(R.id.imgReiniciar);
        imgVidas=findViewById(R.id.imgVidas);
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

        imgLetraa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*btnA.setOnLongClickListener(longclickLister);
        btnE.setOnLongClickListener(longclickLister);
        btnI.setOnLongClickListener(longclickLister);
        btnO.setOnLongClickListener(longclickLister);
        btnU.setOnLongClickListener(longclickLister);*/

        lyVocal1.setOnDragListener(dragListener);
        lyVocal2.setOnDragListener(dragListener);
        lyVocal3.setOnDragListener(dragListener);
        lyVocal4.setOnDragListener(dragListener);
        lyVocal5.setOnDragListener(dragListener);

        imgInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),NivelesAre2Activity.class);
                startActivity(intent);
            }
        });
        imgReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),JuegoNivel1Activity.class);
                startActivity(intent);
            }
        });

        chronometer.setBase(SystemClock.elapsedRealtime());
        if(!empezarCronometro){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            empezarCronometro=true;

        }
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
                    }else if(view.getId()==R.id.imgLetraE && v.getId()==R.id.lyVocal2){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        vocalE.setVisibility(View.GONE);
                        nuevo.addView(view);
                    }else if (view.getId()==R.id.imgLetraI && v.getId()==R.id.lyVocal3){
                        RelativeLayout antiguo=(RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo =(LinearLayout)v;
                        vocalI.setVisibility(View.GONE);
                        nuevo.addView(view);
                    }else if (view.getId()==R.id.imgLetraO && v.getId()==R.id.lyVocal4) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        vocalO.setVisibility(View.GONE);
                        nuevo.addView(view);
                    }else if (view.getId()==R.id.imgLetraU && v.getId()==R.id.lyVocal5) {
                        RelativeLayout antiguo = (RelativeLayout) view.getParent();
                        antiguo.removeView(view);
                        LinearLayout nuevo = (LinearLayout) v;
                        vocalU.setVisibility(View.GONE);
                        nuevo.addView(view);
                    }else {
                        Toast.makeText(JuegoNivel1Activity.this,"Te equivocaste", Toast.LENGTH_SHORT).show();

                    }

                    break;
            }
            return true;
        }
    };


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
    public void incializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }
}
