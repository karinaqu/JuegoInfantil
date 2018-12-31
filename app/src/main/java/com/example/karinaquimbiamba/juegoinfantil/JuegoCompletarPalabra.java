package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.UUID;

public class JuegoCompletarPalabra extends AppCompatActivity implements View.OnClickListener{

    private Chronometer chronometer;
    private boolean correr;
    private long pausarOffSet;
    private ImageView imagen;
    private ImageView imgVidas;
    private ImageView imgFlechaAtras;
    private MediaPlayer sonidoAnimales;
    private final String[] palabras={"VACA","PERRO","GATO","OSO","PATO","BURRO","MONO","CERDO","OVEJA","RANA"};
    private String[] imagenes={"vaca","perro","gato","oso","pato","burro","mono","cerdo","oveja","rana"};
    private String[] sonidos={"vaca","perro","gato","oso","pato","burro","mono","cerdo","oveja","rana"};
    LinearLayout ly_palabra;
    private TextView controles[];
    private Button btnLetra1;
    private Button btnLetra2;
    private Button btnLetra3;
    private Button btnLetra4;
    private Button btnLetra5;
    private TextView txtPuntaje;
    private TextView txtEspera;
    int contadorPuntaje=0;
    int vidas= 3;
    public int numeroCambio=0;

    private ImageView imgRepetir;
    private ImageView imgMenu;
    private ImageView imgSiguiente;
    private ImageView imgCorrecto;
    private ImageView imgIncorrecto;
    private RelativeLayout rlMenu;
    private TextView txtResultado;
    public TextView txtNombre;

    private MediaPlayer sonidoLetraa;
    private MediaPlayer sonidoLetrae;
    private MediaPlayer sonidoLetrai;
    private MediaPlayer sonidoLetrao;
    private MediaPlayer sonidoLetrau;
    private MediaPlayer respuestIncorrecta;
    private MediaPlayer respuestCorrecta;
    private MediaPlayer sonidoIndicacion;
    private MediaPlayer perdiste;
    private MediaPlayer juegoTerminado;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_completar_palabra);
        ly_palabra=findViewById(R.id.ly_palabra);
        imagen= findViewById(R.id.imgFoto);
        txtPuntaje= findViewById(R.id.txtPuntaje);
        txtEspera= findViewById(R.id.txtEspera);
        imgVidas=findViewById(R.id.imgVidas);
        controles= new TextView[palabras.length];
        btnLetra1=findViewById(R.id.btnLetra1);
        btnLetra2= findViewById(R.id.btnLetra2);
        btnLetra3= findViewById(R.id.btnLetra3);
        btnLetra4= findViewById(R.id.btnLetra4);
        btnLetra5= findViewById(R.id.btnLetra5);
        imgMenu=findViewById(R.id.imgMenu);
        imgRepetir=findViewById(R.id.imgRepetir);
        imgSiguiente=findViewById(R.id.imgSiguiente);
        imgFlechaAtras= findViewById(R.id.imgAtras);
        imgFlechaAtras.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        imgRepetir.setOnClickListener(this);
        imgSiguiente.setOnClickListener(this);

        imgCorrecto=findViewById(R.id.imgCorrecto);
        imgIncorrecto=findViewById(R.id.imgIncorrecto);
        rlMenu= findViewById(R.id.rlMenu);
        txtResultado= findViewById(R.id.txtResultado);
        txtNombre=(TextView) findViewById(R.id.txtNombre);

        firebaseAuth= FirebaseAuth.getInstance();
        incializarFirebase();
        chronometer= findViewById(R.id.chCronometro);
        insertarBotones(numeroCambio);


        chronometer.setBase(SystemClock.elapsedRealtime());
        if(!correr){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            correr=true;

        }
        //declarion de sonidos para letras
        sonidoLetraa =MediaPlayer.create(this, R.raw.sonidoa);
        sonidoLetrae =MediaPlayer.create(this, R.raw.sonidoe);
        sonidoLetrai =MediaPlayer.create(this, R.raw.sonidoi);
        sonidoLetrao =MediaPlayer.create(this, R.raw.sonidoo);
        sonidoLetrau =MediaPlayer.create(this, R.raw.sonidou);
        respuestIncorrecta= MediaPlayer.create(this, R.raw.incorrecto);
        respuestCorrecta= MediaPlayer.create(this, R.raw.correcto);
        perdiste= MediaPlayer.create(this, R.raw.perdiste);
        juegoTerminado= MediaPlayer.create(this, R.raw.juego_terminado);
        sonidoIndicacion=MediaPlayer.create(this, R.raw.completar_palabra);
        sonidoIndicacion.start();



    }


    private  String traerTexto(){
        String respuesta= "";
        for(int i = 0; i<controles.length;i++){
            respuesta=respuesta+controles[i].getText().toString();
        }
        return respuesta;
    }

    private boolean espaciosBlancos(){
        boolean valor= true;
        for(int i= 0; i<controles.length;i++){
            if(controles[i].getText().toString().isEmpty()){
                valor= false;
                break;
            }
        }
        return valor;

    }


    void insertarBotones(int numero){
        ly_palabra.removeAllViews();
        controles= new TextView[palabras[numero].length()];


        for(int i = 0; i<controles.length; i++){
            int id= getResources().getIdentifier(imagenes[numero],"drawable",getPackageName());
            int sonido= getResources().getIdentifier(sonidos[numero],"raw",getPackageName());
            sonidoAnimales=MediaPlayer.create(JuegoCompletarPalabra.this,sonido);
            sonidoAnimales.start();
            imagen.setImageResource(id);
            controles[i]=new TextView(getApplicationContext());
            controles[i].setTextColor(Color.BLUE);
            controles[i].setTextSize(50);
            controles[i].setMinWidth(10);
            controles[i].setHint("  ");
            controles[i].setText("_");
            ly_palabra.addView(controles[i]);

        }

        switch(numeroCambio){
            case 0:
                controles[0].setText("V");
                controles[2].setText("C");
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetraa.start();
                        controles[1].setText("A");
                        controles[3].setText("A");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();

                            }

                        }

                    }
                });
                errorLetraE();
                errorLetraI();
                errorLetraO();
                errorLetraU();

                break;
            case 1:
                controles[0].setText("P");
                controles[2].setText("R");
                controles[3].setText("R");
                btnLetra2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrae.start();
                        controles[1].setText("E");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();
                            }

                        }

                    }
                });
                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrao.start();
                        controles[4].setText("O");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();

                            }

                        }

                    }
                });
                errorLetraA();
                errorLetraI();
                errorLetraU();

                break;
            case 2:
                controles[0].setText("G");
                controles[2].setText("T");
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetraa.start();
                        controles[1].setText("A");

                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();
                            }

                        }

                    }
                });
                errorLetraE();
                errorLetraI();
                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrao.start();
                        controles[3].setText("O");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();
                            }

                        }

                    }
                });
                errorLetraU();

                break;
            case 3:
                controles[1].setText("S");
                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrao.start();
                        controles[0].setText("O");
                        controles[2].setText("O");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();
                            }

                        }

                    }
                });
                errorLetraA();
                errorLetraE();
                errorLetraI();
                errorLetraU();

                break;
            case 4:
                controles[0].setText("P");
                controles[2].setText("T");
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetraa.start();
                        controles[1].setText("A");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();

                            }

                        }

                    }
                });
                errorLetraE();
                errorLetraI();

                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrao.start();
                        controles[3].setText("O");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();

                            }

                        }

                    }
                });
                errorLetraU();

                break;
            case 5:
                controles[0].setText("B");
                controles[2].setText("R");
                controles[3].setText("R");
                errorLetraA();
                errorLetraE();
                errorLetraI();

                btnLetra5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrau.start();
                        controles[1].setText("U");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();

                            }

                        }
                    }
                });
                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrao.start();
                        controles[4].setText("O");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();

                            }

                        }

                    }
                });


                break;
            case 6:
                controles[0].setText("M");
                controles[2].setText("N");
                errorLetraA();
                errorLetraE();
                errorLetraI();
                errorLetraU();

                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrao.start();
                        controles[1].setText("O");
                        controles[3].setText("O");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();

                            }

                        }

                    }
                });

                break;
            case 7:
                controles[0].setText("C");
                controles[2].setText("R");
                controles[3].setText("D");
                errorLetraA();
                errorLetraU();
                errorLetraI();

                btnLetra2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrae.start();
                        controles[1].setText("E");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();
                            }

                        }

                    }
                });

                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrao.start();
                        controles[4].setText("O");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();

                            }

                        }

                    }
                });

                break;
            case 8:
                controles[1].setText("V");
                controles[3].setText("J");
                errorLetraI();
                errorLetraU();
                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrao.start();
                        controles[0].setText("O");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();

                            }

                        }

                    }
                });

                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controles[4].setText("A");
                        sonidoLetrae.start();
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();

                            }

                        }

                    }
                });
                btnLetra2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetrae.start();
                        controles[2].setText("E");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
                                imgCorrecto();
                            }

                        }

                    }
                });


                break;
            case 9:
                controles[0].setText("R");
                controles[2].setText("N");
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sonidoLetraa.start();
                        controles[1].setText("A");
                        controles[3].setText("A");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_SHORT).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                guardarDatos();
                                finJuego();
                                imgCorrecto();

                            }

                        }

                    }
                });
                errorLetraE();
                errorLetraI();
                errorLetraO();
                errorLetraU();

                break;

        }


    }
    private void errorLetraA(){
        btnLetra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonidoLetraa.start();
                Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_SHORT).show();
                vidas(vidas--);
                imgIncorrecto();

            }
        });

    }

    //Metodos correspondientes para los botones con la finalidad de ser llamados en la interfaz
    private void errorLetraE(){
        btnLetra2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonidoLetrae.start();
                Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_SHORT).show();
                vidas(vidas--);
                imgIncorrecto();

            }
        });

    }
    private void errorLetraI(){
        btnLetra3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonidoLetrai.start();
                Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_SHORT).show();
                vidas(vidas--);
                imgIncorrecto();

            }
        });

    }
    private void errorLetraO(){
        btnLetra4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonidoLetrao.start();
                Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_SHORT).show();
                vidas(vidas--);
                imgIncorrecto();
            }
        });

    }
    private void errorLetraU(){

        btnLetra5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonidoLetrau.start();
                Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_SHORT).show();
                vidas(vidas--);
                imgIncorrecto();
            }
        });

    }
    void vidas(int numero){
        numero--;

        switch (numero){
            case 0:
                imgVidas.setImageResource(numero);
                PerdisteJuego();
                guardarDatos();
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

        }

    }


    void esperaCambio(){
        new CountDownTimer(2000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtEspera.setText(""+(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {
                txtEspera.setText("");
                numeroCambio=numeroCambio+1;
                insertarBotones(numeroCambio);
                imgCorrecto.setVisibility(View.GONE);
                imgIncorrecto.setVisibility(View.GONE);

            }
        }.start();

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
    //Metodo usado para guardar los datos
    public void guardarDatos(){
        btnLetra1.setVisibility(View.GONE);
        btnLetra2.setVisibility(View.GONE);
        btnLetra3.setVisibility(View.GONE);
        btnLetra4.setVisibility(View.GONE);
        btnLetra5.setVisibility(View.GONE);

        Puntaje puntaje= new Puntaje();
        puntaje.setUid(UUID.randomUUID().toString());
        puntaje.setPuntaje(contadorPuntaje);
        puntaje.setIdArea("Lenguaje");
        puntaje.setIdNivel("Nivel 3");
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
            startActivity(new Intent(getApplicationContext(), JuegoCompletarPalabra.class));
            finish();
        }
        if(v==imgMenu){
            startActivity(new Intent(getApplicationContext(), NivelesAre1Activity.class));
            finish();
        }
        if(v==imgSiguiente){
            startActivity(new Intent(getApplicationContext(), JuegoOrdenarVocales.class));
            finish();
        }

    }
}
