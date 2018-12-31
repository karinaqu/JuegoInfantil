package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Puntaje;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class JuegoNumerosImagen extends AppCompatActivity implements View.OnClickListener{

    private Chronometer chronometer;
    private boolean empezarCronometro;
    private long pausarOffSet;
    private ImageView imgVidas;
    private TextView txtPuntaje;
    private TextView txtNumero1;
    private ImageView imgNumeros;
    private ImageView imgFlechaAtras;
    private GridView grvNumeros;
    private TextView edtRespuesta;
    int contadorPuntaje=0,vidas=3,numero1, cantidad;
    int numeros[]={R.drawable.contar0,R.drawable.contar1,R.drawable.contar2,R.drawable.contar3,R.drawable.contar4,
            R.drawable.contar5,R.drawable.contar6,R.drawable.contar7,R.drawable.contar8,R.drawable.contar9,R.drawable.contar10};
    String opciones[]={"0","1","2","3","4","5","6","7","8","9","10"};
    int numerosIconos[]={R.drawable.numero0,R.drawable.numero1, R.drawable.numero2,
            R.drawable.numero3,R.drawable.numero4,
            R.drawable.numero5,R.drawable.numero6,
            R.drawable.numero7,R.drawable.numero8,R.drawable.numero9,
            R.drawable.numero10};

    int numerosSonido[]={R.raw.sonido0,R.raw.sonido1, R.raw.sonido2,
            R.raw.sonido3,R.raw.sonido4, R.raw.sonido5,R.raw.sonido6,
            R.raw.sonido7,R.raw.sonido8,R.raw.sonido9,
            R.raw.sonido10};

    private String[] sonidoNumeros={"sonido0","sonido1","sonido2","sonido3","sonido4","sonido5","sonido6","sonido7","sonido8","sonido9",
    "sonido10"};

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

    //Definción de variables para los sonidos
    private MediaPlayer respuestIncorrecta;
    private MediaPlayer respuestCorrecta;
    private MediaPlayer sonidoIndicacion;
    private MediaPlayer perdiste;
    private MediaPlayer juegoTerminado;
    private MediaPlayer sonidosNumeros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_numeros_imagen);

        edtRespuesta=findViewById(R.id.edtRespuesta);
        grvNumeros=findViewById(R.id.grvNumeros);
        chronometer= findViewById(R.id.chCronometro);
        imgVidas=findViewById(R.id.imgVidas);
        txtPuntaje=findViewById(R.id.txtPuntaje);
        txtNumero1=findViewById(R.id.txtNumero1);
        imgNumeros=findViewById(R.id.imgNumeros);
        imgFlechaAtras= findViewById(R.id.imgAtras);
        imgFlechaAtras.setOnClickListener(this);

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



        imgFlechaAtras.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        imgRepetir.setOnClickListener(this);
        imgSiguiente.setOnClickListener(this);


        respuestIncorrecta= MediaPlayer.create(this, R.raw.incorrecto);
        respuestCorrecta= MediaPlayer.create(this, R.raw.correcto);
        perdiste= MediaPlayer.create(this, R.raw.perdiste);
        juegoTerminado= MediaPlayer.create(this, R.raw.juego_terminado);
        sonidoIndicacion=MediaPlayer.create(this, R.raw.numero_figura);
        sonidoIndicacion.start();


        numerosAleatorios();
        incializarFirebase();

        chronometer.setBase(SystemClock.elapsedRealtime());
        if(!empezarCronometro){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            empezarCronometro=true;

        }

        GridViewAdapter adapter= new GridViewAdapter(JuegoNumerosImagen.this,numerosIconos,opciones);
        grvNumeros.setAdapter(adapter);
        grvNumeros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edtRespuesta.setText(""+ opciones[position]);
                //int sonido= getResources().getIdentifier(sonidoNumeros[position],"raw",getPackageName());
                sonidosNumeros =MediaPlayer.create(getApplicationContext(),numerosSonido[position]);
                sonidosNumeros.start();
                String respuesta= edtRespuesta.getText().toString();
                if(!respuesta.equals("")){
                    int cantidadJugador= Integer.parseInt(respuesta);
                    if(cantidad==cantidadJugador){
                        contadorPuntaje++;
                        txtPuntaje.setText(""+contadorPuntaje);
                        edtRespuesta.setText("");
                        imgCorrecto();
                    }else{
                        vidas--;
                        imgIncorrecto();
                        switch (vidas){
                            case 0:
                                imgVidas.setImageResource(vidas);
                                PerdisteJuego();
                                guardarPuntaje();
                                chronometer.stop();
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(), "Tienes una vida",Toast.LENGTH_LONG).show();
                                imgVidas.setImageResource(R.drawable.unavida);
                                break;
                            case 2:
                                Toast.makeText(getApplicationContext(), "Tienes dos vidas",Toast.LENGTH_LONG).show();
                                imgVidas.setImageResource(R.drawable.dosvidas);
                                break;
                            case 3:
                                imgVidas.setImageResource(R.drawable.tresvidas);
                                break;

                        } edtRespuesta.setText("");

                    }numerosAleatorios();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Escribir tu respuesta", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void numerosAleatorios(){
        if(contadorPuntaje<=9){
            numero1=(int)(Math.random()*10);
            cantidad=numero1;
            if(cantidad<=10){
                for(int i=0;i<numeros.length;i++){
                    if(numero1==i) {
                        imgNumeros.setImageResource(numeros[i]);
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
            int cantidadJugador= Integer.parseInt(respuesta);
            if(cantidad==cantidadJugador){
                contadorPuntaje++;
                txtPuntaje.setText(""+contadorPuntaje);
                edtRespuesta.setText("");
                imgCorrecto();
            }else{
                vidas--;
                imgIncorrecto();
                switch (vidas){
                    case 0:
                        imgVidas.setImageResource(vidas);
                        PerdisteJuego();
                        guardarPuntaje();
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

    private void guardarPuntaje(){
        btnVerificar.setVisibility(View.GONE);
        Puntaje puntaje= new Puntaje();
        puntaje.setUid(UUID.randomUUID().toString());
        puntaje.setPuntaje(contadorPuntaje);
        puntaje.setIdArea("Matemáticas");
        puntaje.setIdNivel("Nivel 1");
        puntaje.setIdUsuario(String.valueOf(txtNombre.getText()));
        puntaje.setVidas(vidas);
        puntaje.setTiempo(String.valueOf(chronometer.getText()));
        databaseReference.child("Puntaje").child(puntaje.getUid()).setValue(puntaje);
    }

    @Override
    public void onClick(View v) {
        if(v==imgFlechaAtras){
            startActivity(new Intent(getApplicationContext(), NivelesAre2Activity.class));
            finish();
        }
        if(v==imgRepetir){
            startActivity(new Intent(getApplicationContext(), JuegoNumerosImagen.class));
            finish();
        }
        if(v==imgMenu){
            startActivity(new Intent(getApplicationContext(), NivelesAre2Activity.class));
            finish();
        }
        if(v==imgSiguiente){
            startActivity(new Intent(getApplicationContext(), JuegoSumaNumeros.class));
            finish();
        }

    }
}
