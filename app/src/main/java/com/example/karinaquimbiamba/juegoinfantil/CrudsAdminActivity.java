package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class CrudsAdminActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonUsuarios;
    private Button buttonRoles;
    private Button buttonAreas;
    private Button buttonNiveles;
    private Button buttonPuntajes;
    private ImageButton imageButtonFlechaAtras;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cruds_admin);
        buttonUsuarios = (Button) findViewById(R.id.btnUsuarios);
        buttonRoles = (Button) findViewById(R.id.btnRoles);
        buttonAreas = (Button) findViewById(R.id.btnAreas);
        buttonNiveles = (Button) findViewById(R.id.btnNiveles);
        buttonPuntajes = (Button) findViewById(R.id.btnPuntajes);

        buttonUsuarios.setOnClickListener(this);
        buttonRoles.setOnClickListener(this);
        buttonAreas.setOnClickListener(this);
        buttonNiveles.setOnClickListener(this);
        buttonPuntajes.setOnClickListener(this);

        firebaseAuth= FirebaseAuth.getInstance();
        imageButtonFlechaAtras= findViewById(R.id.imgAtras);
        imageButtonFlechaAtras.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //condición de texto al dar clic sobre el boton
        if (view == buttonUsuarios) {
            startActivity(new Intent(this, CrudUserActivity.class));//Abrir  pantalla de areas


        }
        //condición de texto al dar clic sobre el boton
        if (view == buttonRoles) {
            //finish();
            startActivity(new Intent(this, CrudRolActivity.class));//Abrir  pantalla de roles

        }
        //condición de texto al dar clic sobre el boton
        if (view == buttonAreas) {
            //finish();
            startActivity(new Intent(this, CrudAreaActivity.class));//Abrir  pantalla de areas

        }
        //condición de texto al dar clic sobre el boton
        if (view == buttonNiveles) {
            startActivity(new Intent(this, CrudNivelActivity.class));//Abrir  pantalla de niveles


        }
        if (view == imageButtonFlechaAtras){
            startActivity(new Intent(this, MainAdminActivity.class));

        }
        if (view == buttonPuntajes){
            startActivity(new Intent(this, PuntajesUsuariosTotalesActivity.class));

        }


    }

}
