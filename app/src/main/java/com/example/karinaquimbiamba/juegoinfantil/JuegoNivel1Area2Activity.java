package com.example.karinaquimbiamba.juegoinfantil;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;

public class JuegoNivel1Area2Activity extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean empezarCronometro;
    private long pausarOffSet;
    private TextView txtPuntaje;
    private TextView txtNumero1;
    private TextView txtNumero2;
    private TextView edtRespuesta;
    private ImageView imgVidas;
    private ImageButton imgBtnIntentar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nivel1_area2);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        chronometer= findViewById(R.id.chCronometro);
        txtPuntaje=findViewById(R.id.txtPuntaje);
        txtNumero1=findViewById(R.id.txtNumero1);
        txtNumero2=findViewById(R.id.txtNumero2);
        edtRespuesta=findViewById(R.id.edtRespuesta);
        imgVidas=findViewById(R.id.imgVidas);
        imgBtnIntentar=findViewById(R.id.imgBtnIntentar);
        grvNumeros=findViewById(R.id.grvNumeros);


        chronometer.setBase(SystemClock.elapsedRealtime());
        if(!empezarCronometro){
            chronometer.setBase(SystemClock.elapsedRealtime()-pausarOffSet);
            chronometer.start();
            empezarCronometro=true;

        }
        numerosAleatorios();
        GridViewAdapter adapter= new GridViewAdapter(JuegoNivel1Area2Activity.this,numerosIconos,opciones);
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
                    }if(numero2==i){
                        txtNumero2.setText(""+i);
                    }
                }
            }else {
                numerosAleatorios();

            }

        }else {
            //Intent intent= new Intent(this,NivelesArea2.class);


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
            }else{
                vidas--;
                switch (vidas){
                    case 0:
                        imgVidas.setImageResource(vidas);
                        imgBtnIntentar.setVisibility(View.VISIBLE);
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
}
