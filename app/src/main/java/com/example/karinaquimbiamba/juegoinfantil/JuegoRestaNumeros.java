package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.os.CountDownTimer;
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

import java.util.UUID;

public class JuegoRestaNumeros extends AppCompatActivity implements View.OnClickListener{

    private Chronometer chronometer;
    private boolean empezarCronometro;
    private long pausarOffSet;
    private TextView txtPuntaje;
    private TextView txtNumero1;
    private TextView txtNumero2;
    private TextView edtRespuesta;
    private ImageView imgVidas;
    private ImageButton imgBtnIntentar;
    private ImageView imgFlechaAtras;
    private GridView grvNumeros;
    int numeros[]={0,1,2,3,4,5,6,7,8,9,10};
    String opciones[]={"0","1","2","3","4","5","6","7","8","9","10"};
    int contadorPuntaje=0,vidas=3,numero1, numero2, resta;
    // List<String> lstNumeros=new ArrayList<>();
    int numerosIconos[]={R.drawable.numero0,R.drawable.numero1, R.drawable.numero2,
            R.drawable.numero3,R.drawable.numero4,
            R.drawable.numero5,R.drawable.numero6,
            R.drawable.numero7,R.drawable.numero8,R.drawable.numero9,
            R.drawable.numero10};

    int cantidades[]={R.drawable.cerocirculos,R.drawable.uncirculo, R.drawable.doscirculos,
            R.drawable.trescirculos,R.drawable.cuatrocirculos,
            R.drawable.cincocirculos,R.drawable.seiscirculos,
            R.drawable.sietecirculos,R.drawable.ochocirculos,R.drawable.nuevecirculos,
            R.drawable.diezcirculos};

    private ImageView imgRepetir;
    private ImageView imgMenu;
    private ImageView imgSiguiente;
    private ImageView imgCorrecto;
    private ImageView imgIncorrecto;
    private Button btnVerificar;
    private RelativeLayout rlMenu;
    private TextView txtResultado;
    public TextView txtNombre;
    private TextView txtEspera;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    private ImageView imgCantidad1;
    private ImageView imgCantidad2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_resta_numeros);

        chronometer= findViewById(R.id.chCronometro);
        txtPuntaje=findViewById(R.id.txtPuntaje);
        txtNumero1=findViewById(R.id.txtNumero1);
        txtNumero2=findViewById(R.id.txtNumero2);
        edtRespuesta=findViewById(R.id.edtRespuesta);
        imgVidas=findViewById(R.id.imgVidas);
        imgBtnIntentar=findViewById(R.id.imgBtnIntentar);
        grvNumeros=findViewById(R.id.grvNumeros);
        imgFlechaAtras= findViewById(R.id.imgAtras);
        imgMenu=findViewById(R.id.imgMenu);
        imgRepetir=findViewById(R.id.imgRepetir);
        imgSiguiente=findViewById(R.id.imgSiguiente);
        txtEspera= findViewById(R.id.txtEspera);

        imgCorrecto=findViewById(R.id.imgCorrecto);
        imgIncorrecto=findViewById(R.id.imgIncorrecto);
        rlMenu= findViewById(R.id.rlMenu);
        txtResultado= findViewById(R.id.txtResultado);
        btnVerificar=findViewById(R.id.btnVerificar);
        txtNombre=(TextView) findViewById(R.id.txtNombre);


        imgCantidad1=findViewById(R.id.imgCantidad1);
        imgCantidad2=findViewById(R.id.imgCantidad2);
        firebaseAuth= FirebaseAuth.getInstance();

        imgFlechaAtras.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        imgRepetir.setOnClickListener(this);
        imgSiguiente.setOnClickListener(this);


        chronometer.setBase(SystemClock.elapsedRealtime());
        if(!empezarCronometro){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            empezarCronometro=true;

        }
        numerosAleatorios();
        incializarFirebase();
        GridViewAdapter adapter= new GridViewAdapter(JuegoRestaNumeros.this,numerosIconos,opciones);
        grvNumeros.setAdapter(adapter);
        grvNumeros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,"Clic:"+opciones[position],Toast.LENGTH_SHORT).show();
                edtRespuesta.setText(""+ opciones[position]);

            }
        });
    }
    public void numerosAleatorios(){
        if(contadorPuntaje<=9){
            numero1 = (int) (Math.random() * (10-5)+5);
            numero2 = (int) (Math.random() * (5-0)+0);
            if(numero1>numero2){
                resta=numero1-numero2;
            }
            if(resta<=10){
                for(int i=0;i<numeros.length;i++){
                    if(numero1==i){
                        txtNumero1.setText(""+i);
                        imgCantidad1.setImageResource(cantidades[i]);
                    }if(numero2==i){
                        txtNumero2.setText(""+i);
                        imgCantidad2.setImageResource(cantidades[i]);
                    }
                }
            }else {
                numerosAleatorios();

            }

        }else {
            chronometer.stop();
            finJuego();
            guardarPuntaje();
        }
    }
    public void compararResultado(View view){
        String respuesta= edtRespuesta.getText().toString();
        if(!respuesta.equals("")){
            int sumaJugador= Integer.parseInt(respuesta);
            if(resta==sumaJugador){
                contadorPuntaje++;
                txtPuntaje.setText(""+contadorPuntaje);
                edtRespuesta.setText("");
                imgCorrecto();
            }else{
                imgIncorrecto();
                vidas--;
                switch (vidas){
                    case 0:
                        imgVidas.setImageResource(vidas);
                        PerdisteJuego();
                        guardarPuntaje();
                        chronometer.stop();
                        break;
                    case 1:
                        Toast.makeText(this, "Tienes una vida",Toast.LENGTH_SHORT).show();
                        imgVidas.setImageResource(R.drawable.unavida);
                        break;
                    case 2:
                        Toast.makeText(this, "Tienes dos vidas",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        if(v==imgFlechaAtras){
            startActivity(new Intent(getApplicationContext(), NivelesAre2Activity.class));
            finish();
        }
        if(v==imgRepetir){
            startActivity(new Intent(getApplicationContext(), JuegoRestaNumeros.class));
            finish();
        }
        if(v==imgMenu){
            startActivity(new Intent(getApplicationContext(), NivelesAre2Activity.class));
            finish();
        }
        if(v==imgSiguiente){
            startActivity(new Intent(getApplicationContext(), JuegoNumerosImagen.class));
            finish();
        }

    }

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

            }
        }.start();


    }
    void imgIncorrecto(){
        new CountDownTimer(1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtEspera.setText(""+(millisUntilFinished/1000)+1);
                imgIncorrecto.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                imgIncorrecto.setVisibility(View.INVISIBLE);
            }
        }.start();


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
            }
        }.start();

    }

    private void guardarPuntaje(){
        btnVerificar.setVisibility(View.GONE);
        Puntaje puntaje= new Puntaje();
        puntaje.setUid(UUID.randomUUID().toString());
        puntaje.setPuntaje(contadorPuntaje);
        puntaje.setIdArea("Matemáticas");
        puntaje.setIdNivel("Nivel 3");
        puntaje.setIdUsuario(String.valueOf(txtNombre.getText()));
        puntaje.setVidas(vidas);
        puntaje.setTiempo(String.valueOf(chronometer.getText()));
        databaseReference.child("Puntaje").child(puntaje.getUid()).setValue(puntaje);
    }
}
