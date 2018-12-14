package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.util.UUID;

public class JuegoNivel2Area2Activity extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean empezarCronometro;
    private long pausarOffSet;
    private ImageView imgVidas;
    private TextView txtPuntaje;
    private TextView txtNumero1;
    private ImageView imgNumeros;
    private Button btnIntentar;
    private Button btnMenuTerminado;
    private Button btnMenu;
    private GridView grvNumeros;
    private TextView edtRespuesta;
    private TextView textViewNombre;
    int contadorPuntaje=0,vidas=3,numero1, suma;
    String numeros[]={"numero0","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","diez"};
    String opciones[]={"0","1","2","3","4","5","6","7","8","9","10"};
    int numerosIconos[]={R.drawable.numero0,R.drawable.numero1, R.drawable.numero2,
            R.drawable.numero3,R.drawable.numero4,
            R.drawable.numero5,R.drawable.numero6,
            R.drawable.numero7,R.drawable.numero8,R.drawable.numero9,
            R.drawable.numero10};
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nivel2_area2);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        edtRespuesta=findViewById(R.id.edtRespuesta);
        grvNumeros=findViewById(R.id.grvNumeros);
        chronometer= findViewById(R.id.chCronometro);
        btnIntentar=findViewById(R.id.btnIntentar);
        btnMenuTerminado=findViewById(R.id.btnMenuTerminado);
        btnMenu=findViewById(R.id.btnMenu);
        imgVidas=findViewById(R.id.imgVidas);
        txtPuntaje=findViewById(R.id.txtPuntaje);
        txtNumero1=findViewById(R.id.txtNumero1);
        imgNumeros=findViewById(R.id.imgNumeros);
        textViewNombre=(TextView) findViewById(R.id.txtNombre);
        firebaseAuth= FirebaseAuth.getInstance();
        incializarFirebase();

        numerosAleatorios();

        chronometer.setBase(SystemClock.elapsedRealtime());
        if(!empezarCronometro){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            empezarCronometro=true;

        }

        GridViewAdapter adapter= new GridViewAdapter(JuegoNivel2Area2Activity.this,numerosIconos,opciones);
        grvNumeros.setAdapter(adapter);
        grvNumeros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,"Clic:"+opciones[position],Toast.LENGTH_SHORT).show();
                edtRespuesta.setText(""+ opciones[position]);

            }
        });

        firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);{
                    textViewNombre.setText(String.valueOf(dataSnapshot.child("name").getValue()));//Traer desde la base de datos firebase el nombre para colocarlo en el cajón de texto


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void numerosAleatorios(){
        if(contadorPuntaje<=9){
            numero1=(int)(Math.random()*10);
            //numero2=(int)(Math.random()*10);
            suma=numero1;
            if(suma<=10){
                for(int i=0;i<numeros.length;i++){
                    int id= getResources().getIdentifier(numeros[i],"drawable",getPackageName());
                    if(numero1==i) {
                        imgNumeros.setImageResource(id);
                    }
                }
            }else {
                numerosAleatorios();

            }

        }else {
            Puntaje puntaje= new Puntaje();
            puntaje.setUid(UUID.randomUUID().toString());
            puntaje.setPuntaje(contadorPuntaje);
            puntaje.setIdArea("Matemáticas");
            puntaje.setIdNivel("Nivel 2");
            puntaje.setIdUsuario(String.valueOf(textViewNombre.getText()));
            puntaje.setVidas(vidas);
            puntaje.setTiempo(String.valueOf(chronometer.getText()));
            //puntaje.setCronometro(chronometer);
            databaseReference.child("Puntaje").child(puntaje.getUid()).setValue(puntaje);


            btnMenuTerminado.setText("GANASTE");
            btnIntentar.setVisibility(View.VISIBLE);
            btnMenu.setVisibility(View.VISIBLE);
            btnMenuTerminado.setVisibility(View.VISIBLE);
            chronometer.stop();
            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(getApplicationContext(),NivelesAre2Activity.class);
                    startActivity(intent);

                }
            });
            btnIntentar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnIntentar.setVisibility(View.INVISIBLE);
                    btnMenu.setVisibility(View.INVISIBLE);
                    btnMenuTerminado.setVisibility(View.INVISIBLE);
                    imgVidas.setImageResource(R.drawable.tresvidas);
                    txtPuntaje.setText("");
                    reiniciarCronometro();
                    chronometer.start();
                    contadorPuntaje=0;
                    vidas=3;

                }
            });


        }
    }
    public void compararResultado(View view){
        String respuesta= edtRespuesta.getText().toString();
        if(!respuesta.equals("")){
            int sumaJugador= Integer.parseInt(respuesta);
            if(suma==sumaJugador){
                contadorPuntaje++;
                txtPuntaje.setText(""+contadorPuntaje);
                edtRespuesta.setText("");
            }else{
                vidas--;
                switch (vidas){
                    case 0:
                        Puntaje puntaje= new Puntaje();
                        puntaje.setUid(UUID.randomUUID().toString());
                        puntaje.setPuntaje(contadorPuntaje);
                        puntaje.setIdArea("Matemáticas");
                        puntaje.setIdNivel("Nivel 2");
                        puntaje.setIdUsuario(String.valueOf(textViewNombre.getText()));
                        puntaje.setVidas(vidas);
                        puntaje.setTiempo(String.valueOf(chronometer.getText()));
                        databaseReference.child("Puntaje").child(puntaje.getUid()).setValue(puntaje);

                        imgVidas.setImageResource(vidas);
                        btnIntentar.setVisibility(View.VISIBLE);
                        btnMenu.setVisibility(View.VISIBLE);
                        btnMenuTerminado.setVisibility(View.VISIBLE);
                        chronometer.stop();

                        btnMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent= new Intent(getApplicationContext(),NivelesAre2Activity.class);
                                startActivity(intent);

                            }
                        });
                        btnIntentar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btnIntentar.setVisibility(View.INVISIBLE);
                                btnMenu.setVisibility(View.INVISIBLE);
                                btnMenuTerminado.setVisibility(View.INVISIBLE);
                                imgVidas.setImageResource(R.drawable.tresvidas);
                                txtPuntaje.setText("");
                                reiniciarCronometro();
                                chronometer.start();
                                contadorPuntaje=0;
                                vidas=3;

                            }
                        });
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

                } edtRespuesta.setText("");

            }numerosAleatorios();
        }
        else {
            Toast.makeText(this, "Escribir tu respuesta", Toast.LENGTH_SHORT).show();
        }
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
