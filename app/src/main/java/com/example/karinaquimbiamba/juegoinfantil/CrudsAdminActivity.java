package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class CrudsAdminActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonUsuarios;
    Button buttonRoles;
    Button buttonAreas;
    Button buttonNiveles;
    private Button buttonSalir;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cruds_admin);
        buttonUsuarios = (Button) findViewById(R.id.btnUsuarios);
        buttonRoles = (Button) findViewById(R.id.btnRoles);
        buttonAreas = (Button) findViewById(R.id.btnAreas);
        buttonNiveles = (Button) findViewById(R.id.btnNiveles);

        buttonUsuarios.setOnClickListener(this);
        buttonRoles.setOnClickListener(this);
        buttonAreas.setOnClickListener(this);
        buttonNiveles.setOnClickListener(this);
        buttonSalir=(Button) findViewById(R.id.btnSalir);
        buttonSalir.setOnClickListener(this);
        firebaseAuth= FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        //condición de texto al dar clic sobre el boton
        if (view == buttonUsuarios) {
            startActivity(new Intent(this, CrudUserActivity.class));//Abrir  pantalla de areas


        }
        //condición de texto al dar clic sobre el boton
        if (view == buttonRoles) {
            finish();
            startActivity(new Intent(this, CrudRolActivity.class));//Abrir  pantalla de roles

        }
        //condición de texto al dar clic sobre el boton
        if (view == buttonAreas) {
            finish();
            startActivity(new Intent(this, CrudAreaActivity.class));//Abrir  pantalla de areas

        }
        //condición de texto al dar clic sobre el boton
        if (view == buttonNiveles) {
            startActivity(new Intent(this, CrudNivelActivity.class));//Abrir  pantalla de niveles


        }
        //Condición que establece que si se da clic en el Boton desloguearse
        if(view==buttonSalir){
            firebaseAuth.signOut();//Permite salir al usuario es decir desloguearse
            finish();
            startActivity(new Intent(this, LoginActivity.class));//Llama a la pantalla de Logeo para ingresar de nuevo
        }

    }
}