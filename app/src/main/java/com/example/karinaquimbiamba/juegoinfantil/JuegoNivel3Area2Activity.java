package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
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

public class JuegoNivel3Area2Activity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout rlPerder;
    private TextView txtResultado;
    private Chronometer chronometer;
    private boolean empezarCronometro;
    private long pausarOffSet;
    private  ImageView imgCantidad1;
    private  ImageView imgCantidad2;
    private ImageView imgReiniciar;
    private TextView txtPuntaje;
    private TextView txtNumero1;
    private TextView txtNumero2;
    private TextView edtRespuesta;
    private ImageView imgVidas, imgInicio;
    private Button imgBtnIntentar;
    private Button btnComparar;
    private Button btnMenu;
    private GridView grvNumeros;
    private TextView textViewNombre;
    private TextView txtEspera;
    private Button btnCorrecto;
    private Button btnIncorrecto;
    int numeros[]={0,1,2,3,4,5,6,7,8,9,10};
    String opciones[]={"0","1","2","3","4","5","6","7","8","9","10"};
    int contadorPuntaje=0,vidas=3,numero1, numero2, suma;
    int numerosIconos[]={R.drawable.numero0,R.drawable.numero1, R.drawable.numero2,
            R.drawable.numero3,R.drawable.numero4,
            R.drawable.numero5,R.drawable.numero6,
            R.drawable.numero7,R.drawable.numero8,R.drawable.numero9,
            R.drawable.numero10};

    String cantidades[]={"","uncirculo_nivel3_area2_suma","doscirculos_nivel3_area2_suma",
            "trescirculos_nivel3_area2_suma","cuatrocirculos_nivel3_area2_suma",
            "cincocirculos_nivel3_area2_suma","seiscirculos_nivel3_area2_suma",
            "sietecirculos_nivel3_area2_suma","ochocirculos_nivel3_area2_suma",
            "nuevecirculos_nivel3_area2_suma","diezcirculos_nivel3_area2_suma"};
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    //Sonido Indicación
    private MediaPlayer sonidoIndicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nivel3_area2);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        txtResultado= findViewById(R.id.txtResultado);
        btnComparar=findViewById(R.id.btnComparar);
        imgCantidad1= findViewById(R.id.imgCantidades1);
        imgCantidad2=findViewById(R.id.imgCantidades2);
        chronometer= findViewById(R.id.chCronometro);
        imgInicio= findViewById(R.id.imgInicio);
        imgReiniciar=findViewById(R.id.imgReiniciar);
        txtPuntaje=findViewById(R.id.txtPuntaje);
        txtNumero1=findViewById(R.id.txtNumero1);
        txtNumero2=findViewById(R.id.txtNumero2);
        txtEspera= findViewById(R.id.txtEspera);
        edtRespuesta=findViewById(R.id.edtRespuesta);
        imgVidas=findViewById(R.id.imgVidas);
        imgBtnIntentar=findViewById(R.id.imgBtnIntentar);
        grvNumeros=findViewById(R.id.grvNumeros);
        rlPerder= findViewById(R.id.rlPerder);
        btnMenu=findViewById(R.id.btnMenu);
        btnCorrecto=findViewById(R.id.btnCorrecto);
        btnIncorrecto=findViewById(R.id.btnIncorrecto);
        textViewNombre=findViewById(R.id.txtNombre);
        firebaseAuth= FirebaseAuth.getInstance();
        incializarFirebase();

        btnMenu.setOnClickListener(this);
        imgBtnIntentar.setOnClickListener(this);
        imgInicio.setOnClickListener(this);
        imgReiniciar.setOnClickListener(this);


        chronometer.setBase(SystemClock.elapsedRealtime());
        if(!empezarCronometro){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            empezarCronometro=true;

        }
        numerosAleatorios();
        GridViewAdapter adapter= new GridViewAdapter(JuegoNivel3Area2Activity.this,numerosIconos,opciones);
        grvNumeros.setAdapter(adapter);
        grvNumeros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,"Clic:"+opciones[position],Toast.LENGTH_SHORT).show();
                edtRespuesta.setText(""+ opciones[position]);

            }
        });

        //Sonidos
        sonidoIndicacion= MediaPlayer.create(this,R.raw.seleccionar_suma);
        sonidoIndicacion.start();



    }
    public void numerosAleatorios(){
        if(contadorPuntaje<=9){
            numero1=(int)(Math.random()*10);
            numero2=(int)(Math.random()*10);
            suma=numero1+numero2;
            if(suma<=10){
                for(int i=0;i<numeros.length;i++){
                    int identificador= getResources().getIdentifier(cantidades[i],"drawable",getPackageName());
                    if(numero1==i){
                        txtNumero1.setText(""+i);
                        imgCantidad1.setImageResource(identificador);
                    }if(numero2==i){
                        txtNumero2.setText(""+i);
                        imgCantidad2.setImageResource(identificador);
                    }
                }
            }else {
                numerosAleatorios();

            }

        }else {
            chronometer.stop();
            txtResultado.setText("JUEGO TERMINADO");
            rlPerder.setVisibility(View.VISIBLE);
            guardarPuntaje();


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
                imgCorrecto();
            }else{
                imgIncorrecto();
                vidas--;
                switch (vidas){
                    case 0:
                        txtResultado.setText("PERDISTE");
                        rlPerder.setVisibility(View.VISIBLE);
                        guardarPuntaje();
                        imgVidas.setImageResource(vidas);
                        chronometer.stop();

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

    void imgCorrecto(){
        new CountDownTimer(1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtEspera.setText(""+(millisUntilFinished/1000)+1);
                btnCorrecto.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                btnCorrecto.setVisibility(View.INVISIBLE);

            }
        }.start();


    }
    void imgIncorrecto(){
        new CountDownTimer(1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtEspera.setText(""+(millisUntilFinished/1000)+1);
                btnIncorrecto.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                btnIncorrecto.setVisibility(View.INVISIBLE);
            }
        }.start();


    }
    public void guardarPuntaje(){
        btnComparar.setVisibility(View.GONE);
        Puntaje puntaje= new Puntaje();
        puntaje.setUid(UUID.randomUUID().toString());
        puntaje.setPuntaje(contadorPuntaje);
        puntaje.setIdArea("Matemáticas");
        puntaje.setIdNivel("Nivel 3");
        puntaje.setIdUsuario(String.valueOf(textViewNombre.getText()));
        puntaje.setVidas(vidas);
        puntaje.setTiempo(String.valueOf(chronometer.getText()));
        databaseReference.child("Puntaje").child(puntaje.getUid()).setValue(puntaje);
    }

    @Override
    public void onClick(View v) {
        if(v==btnMenu){
            startActivity(new Intent(getApplicationContext(),NivelesAre2Activity.class));
        }if (v==imgBtnIntentar){
            startActivity(new Intent(getApplicationContext(),JuegoNivel3Area2Activity.class));
        }
        if(v==imgInicio){
            startActivity(new Intent(getApplicationContext(),NivelesAre2Activity.class));
        }
        if(v==imgReiniciar){
            startActivity(new Intent(getApplicationContext(),JuegoNivel3Area2Activity.class));
        }

    }
}
