package com.example.karinaquimbiamba.juegoinfantil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainPrincipalActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView edtUsuario;
    public static final String user= "names";

    private ImageView imgSalir;
    private ImageView imgPuntaje;


    private FirebaseAuth.AuthStateListener mAuthListener; //Definición de variable de autenticacipin
    private FirebaseAuth firebaseAuth; //Definción de variable Autenticación
    private TextView textViewNombre; //Definción de variable texto  para el nombre
    private TextView textViewRol;
    private ProgressDialog progressDialog; //Definición de variable para cuadros de Dialogo
    private StorageReference storageReference; //Definción de referencia para firebase
    private DatabaseReference databaseReference; //Definción de variable para la base de datos firebase
    FirebaseDatabase firebaseDatabase;
    private Button buttonJugar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        imgPuntaje=findViewById(R.id.imgPuntaje);
        imgPuntaje.setOnClickListener(this);
        imgSalir=findViewById(R.id.imgSalir);
        imgSalir.setOnClickListener(this);
        edtUsuario= findViewById(R.id.edtNombre);
        String user= getIntent().getStringExtra("names");
        edtUsuario.setText("Bienvenido "+user);

        incializarFirebase();


        progressDialog= new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();

        textViewRol=(TextView) findViewById(R.id.txtRol);

        buttonJugar=(Button) findViewById(R.id.btnJugar);
        buttonJugar.setOnClickListener(this);



        textViewNombre=(TextView) findViewById(R.id.txtNombre);


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

    @Override
    public void onClick(View view) {
        //Condición que establece que si se da clic en el Boton desloguearse

        if(view==buttonJugar){
            startActivity(new Intent(this, AreasActivity.class));//Llama a la pantalla de Logeo para ingresar de nuevo
        }
        if(view==imgPuntaje){
            //firebaseAuth.signOut();//Permite salir al usuario es decir desloguearse
            //finish();
            startActivity(new Intent(this, PuntajeUsuarioActivity.class));//Llama a la pantalla de Logeo para ingresar de nuevo
        }
        if(view==imgSalir){
            firebaseAuth.signOut();//Permite salir al usuario es decir desloguearse
            finish();
            startActivity(new Intent(this, LoginActivity.class));//Llama a la pantalla de Logeo para ingresar de nuevo
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.icon_cerrar:{
                firebaseAuth.signOut();//Permite salir al usuario es decir desloguearse
                finish();
                startActivity(new Intent(this, LoginActivity.class));//Llama a la pantalla de Logeo para ingresar de nuevo

            }break;

        }
        return  true;
    }

    public void incializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }


}
