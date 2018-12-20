package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import java.util.Random;
import java.util.UUID;

public class JuegoNivel2Area1Activity extends AppCompatActivity implements View.OnClickListener {

    //Variables para el Menu cuando el juego termina
    private RelativeLayout rlMenuJuego;
    private Button btnIntentar;
    private Button btnMenu;

    //Variables usada para el juego
    private Chronometer chronometer;
    private boolean correr;
    private long pausarOffSet;

    //Variables definidas para el juego
    private ImageView imagen, imgVidas;
    private final String[] palabras={"VACA","PERRO","GATO","OSO","PATO","BURRO","MONO","CERDO","OVEJA","RANA"};
    private String[] imagenes={"vaca","perro","gato","oso","pato","burro","mono","cerdo","oveja","rana"};
    private String[] sonidos={"vaca","perro","gato","oso","pato","burro","mono","cerdo","oveja","rana"};
    LinearLayout ly_palabra;
    private TextView controles[];
    private Button btnLetra1, btnLetra2, btnLetra3, btnLetra4,btnLetra5;
    private TextView txtPuntaje, txtEspera, txtResultado, txtNombre;
    int contadorPuntaje=0, vidas= 3, numeroCambio=0;


    //sonidos de letras y animales
    private MediaPlayer sonidoAnimales;
    private MediaPlayer sonidoLetraa;
    private MediaPlayer sonidoLetrae;
    private MediaPlayer sonidoLetrai;
    private MediaPlayer sonidoLetrao;
    private MediaPlayer sonidoLetrau;
    private MediaPlayer respuestIncorrecta;
    private MediaPlayer sonidoIndicacion;

    //Parametro base de datos
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nivel2_area1);

        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Definición de variables para el menu
        btnIntentar= findViewById(R.id.btnIntentar);
        btnMenu= findViewById(R.id.btnMenu);
        btnIntentar.setOnClickListener(this);
        btnMenu.setOnClickListener(this);

        //Inicialización de instancias para la base de datos
        firebaseAuth= FirebaseAuth.getInstance();
        incializarFirebase();



        //Variables definidas para el juego
        txtResultado= findViewById(R.id.txtResultado);
        rlMenuJuego= findViewById(R.id.rlMenu);
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
        insertarBotones(numeroCambio);
        txtNombre= findViewById(R.id.txtNombre);



        //declarion de sonidos para letras
        sonidoLetraa =MediaPlayer.create(this, R.raw.sonidoa);
        sonidoLetrae =MediaPlayer.create(this, R.raw.sonidoe);
        sonidoLetrai =MediaPlayer.create(this, R.raw.sonidoi);
        sonidoLetrao =MediaPlayer.create(this, R.raw.sonidoo);
        sonidoLetrau =MediaPlayer.create(this, R.raw.sonidou);
        respuestIncorrecta= MediaPlayer.create(this, R.raw.incorrecto);
        sonidoIndicacion=MediaPlayer.create(this, R.raw.completar_palabra);
        sonidoIndicacion.start();

        //Cronometro
        chronometer= findViewById(R.id.chCronometro);
        chronometer.setBase(SystemClock.elapsedRealtime());
        if(!correr){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            correr=true;

        }


    }

    //Traer respuesta ingresada en el juego
    private  String traerTexto(){
        String respuesta= "";
        for(int i = 0; i<controles.length;i++){
            respuesta=respuesta+controles[i].getText().toString();
        }
        return respuesta;
    }

    //Metodo para validar si hay especios en balanco que falten completar
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

    //Metodo para realizar la interfaz del juego insercion de imagenes y botones
    private void insertarBotones(int numero){
        ly_palabra.removeAllViews();
        controles= new TextView[palabras[numero].length()];


        for(int i = 0; i<controles.length; i++){
            int id= getResources().getIdentifier(imagenes[numero],"drawable",getPackageName());
            int sonido= getResources().getIdentifier(sonidos[numero],"raw",getPackageName());
            imagen.setImageResource(id);
            sonidoAnimales= MediaPlayer.create(JuegoNivel2Area1Activity.this,sonido);
            sonidoAnimales.start();

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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();


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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();

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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();

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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();

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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();

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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();

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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();

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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();

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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();

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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                esperaCambio();
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
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);
                                guardarDatos();
                                txtResultado.setText("Juego Terminado");
                                rlMenuJuego.setVisibility(View.VISIBLE);

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

    //Metodo de vidas para ir descontando por cada equivocación
    void vidas(int numero){


        switch (numero){
            case 0:
                imgVidas.setImageResource(numero);
                txtResultado.setText("Perdiste");
                rlMenuJuego.setVisibility(View.VISIBLE);
                guardarDatos();
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

        }

    }


    //Realizar el cambio cada vez que vaya completando
    void esperaCambio(){
        new CountDownTimer(1000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtEspera.setText(""+(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {
                //btnComprobar.setVisibility(View.VISIBLE);
                txtEspera.setText("");
                numeroCambio=numeroCambio+1;
                insertarBotones(numeroCambio);

            }
        }.start();


    }
    private void errorLetraA(){
        btnLetra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonidoLetraa.start();
                Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                vidas(vidas--);
                respuestIncorrecta.start();

            }
        });

    }

    //Metodos correspondientes para los botones con la finalidad de ser llamados en la interfaz
    private void errorLetraE(){
        btnLetra2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonidoLetrae.start();
                Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                vidas(vidas--);
                respuestIncorrecta.start();

            }
        });

    }
    private void errorLetraI(){

        btnLetra3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonidoLetrai.start();
                Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                vidas(vidas--);
                respuestIncorrecta.start();

            }
        });

    }
    private void errorLetraO(){
        btnLetra4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonidoLetrao.start();
                Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                vidas(vidas--);
                respuestIncorrecta.start();
            }
        });

    }
    private void errorLetraU(){

        btnLetra5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonidoLetrau.start();
                Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                vidas(vidas--);
                respuestIncorrecta.start();
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


    //Inicialización de Firebase y traer dato del usuario que se encuentra logueado
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

    //Implemtación de boton click para todos los botones al dar clic
    @Override
    public void onClick(View v) {
        if(v==btnIntentar){
            startActivity(new Intent(getApplicationContext(),JuegoNivel2Area1Activity.class));

        }
        if(v==btnMenu){
            startActivity(new Intent(getApplicationContext(),NivelesAre1Activity.class));
        }

    }
}
