package com.example.karinaquimbiamba.juegoinfantil;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class JuegoNivel2Area1Activity extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean correr;
    private long pausarOffSet;
    private ImageView imagen;
    private ImageView imgVidas;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nivel2_area1);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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

        chronometer= findViewById(R.id.chCronometro);
        insertarBotones(numeroCambio);


        chronometer.setBase(SystemClock.elapsedRealtime());
        if(!correr){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            correr=true;

        }

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
            sonidoAnimales= MediaPlayer.create(JuegoNivel2Area1Activity.this,sonido);
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

                btnLetra2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);

                    }
                });
                btnLetra3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);

                    }
                });
                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });


                break;
            case 1:
                controles[0].setText("P");
                controles[2].setText("R");
                controles[3].setText("R");
                btnLetra2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });

                break;
            case 2:
                controles[0].setText("G");
                controles[2].setText("T");
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                btnLetra2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
                btnLetra5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });

                break;
            case 3:
                controles[1].setText("S");
                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });

                break;
            case 4:
                controles[0].setText("P");
                controles[2].setText("T");
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                btnLetra2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                btnLetra5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });

                break;
            case 5:
                controles[0].setText("B");
                controles[2].setText("R");
                controles[3].setText("R");
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });

                btnLetra2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });

                btnLetra2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                btnLetra5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });


                break;
            case 7:
                controles[0].setText("C");
                controles[2].setText("R");
                controles[3].setText("D");
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });

                btnLetra2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                btnLetra3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });

                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                btnLetra5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });


                break;
            case 8:
                controles[1].setText("V");
                controles[3].setText("J");

                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                btnLetra3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });



                break;
            case 9:
                controles[0].setText("R");
                controles[2].setText("N");
                btnLetra1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controles[1].setText("A");
                        controles[3].setText("A");
                        if(espaciosBlancos()){
                            if(traerTexto().equals(palabras[numeroCambio])){
                                Toast.makeText(getApplicationContext(),"palabra correcta", Toast.LENGTH_LONG).show();
                                contadorPuntaje++;
                                txtPuntaje.setText(""+contadorPuntaje);

                            }

                        }

                    }
                });
                btnLetra2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });
                btnLetra4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Error letra incorrecta", Toast.LENGTH_LONG).show();
                        vidas(vidas--);
                    }
                });



                break;

        }

    }
    void vidas(int numero){


        switch (numero){
            case 0:
                imgVidas.setImageResource(numero);
                        /*imgBtnIntentar.setVisibility(View.VISIBLE);
                        chronometer.stop();
                        imgBtnIntentar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imgBtnIntentar.setVisibility(View.INVISIBLE);
                                imgVidas.setImageResource(R.drawable.tresvidas);
                                txtPuntaje.setText("");
                                reiniciarCronometro();
                                chronometer.start();
                                vidas=3;

                            }
                        });*/
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
}
