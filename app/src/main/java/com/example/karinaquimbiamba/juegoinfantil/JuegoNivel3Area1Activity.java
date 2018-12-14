package com.example.karinaquimbiamba.juegoinfantil;

import android.content.pm.ActivityInfo;
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

public class JuegoNivel3Area1Activity extends AppCompatActivity {
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
    int contadorPuntaje=0,vidas=3,numero1, numero2, suma;
    // List<String> lstNumeros=new ArrayList<>();
    int numerosIconos[]={R.drawable.numero0,R.drawable.numero1, R.drawable.numero2,
            R.drawable.numero3,R.drawable.numero4,
            R.drawable.numero5,R.drawable.numero6,
            R.drawable.numero7,R.drawable.numero8,R.drawable.numero9,
            R.drawable.numero10};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_nivel3_area1);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


    }


}
