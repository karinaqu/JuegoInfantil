package com.example.karinaquimbiamba.juegoinfantil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainAdminActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth.AuthStateListener mAuthListener; //Definición de variable de autenticacipin
    private FirebaseAuth firebaseAuth; //Definción de variable Autenticación
    private TextView textViewNombre; //Definción de variable texto  para el nombre

    private ProgressDialog progressDialog; //Definición de variable para cuadros de Dialogo
    private StorageReference storageReference; //Definción de referencia para firebase
    private DatabaseReference databaseReference; //Definción de variable para la base de datos firebase
    FirebaseDatabase firebaseDatabase;
    private Button buttonAdministrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        progressDialog= new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();





        buttonAdministrar=(Button) findViewById(R.id.btnAdminitrar);
        buttonAdministrar.setOnClickListener(this);

        FirebaseUser user= firebaseAuth.getCurrentUser();
        textViewNombre=(TextView) findViewById(R.id.txtNombre);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                if (firebaseAuth.getCurrentUser() !=null){


                    //
                    storageReference = FirebaseStorage.getInstance().getReference();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");//Refrenciar base de datos a la tabla Users creada
                    final ValueEventListener valueEventListener = databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);{
                                textViewNombre.setText(String.valueOf(dataSnapshot.child("name").getValue()));//Traer desde la base de datos firebase el nombre para colocarlo en el cajón de texto


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }

            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }
    //Implemetación del método OnClickListener

    @Override
    public void onClick(View view) {


        //Condición que establece que si se da clic en el Boton desloguearse
        if(view==buttonAdministrar){
            //firebaseAuth.signOut();//Permite salir al usuario es decir desloguearse
            //finish();
            startActivity(new Intent(this, CrudsAdminActivity.class));//Llama a la pantalla de Logeo para ingresar de nuevo
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
}
