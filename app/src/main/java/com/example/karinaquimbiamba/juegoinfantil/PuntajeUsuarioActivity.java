package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Nivel;
import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Puntaje;
import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PuntajeUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView edtUsuario;
    public static final String user= "names";


    private DatabaseReference databaseReference; //Definción de variable para la base de datos firebase
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private ImageButton imageButtonFlechaAtras;
    public TextView textViewNombre; //Definción de variable texto  para el nombre
    private StorageReference storageReference; //Definción de referencia para firebase
    private FirebaseAuth.AuthStateListener mAuthListener; //Definición de variable de autenticacipin
    private FirebaseAuth firebaseAuth;

    private ListView listViewPuntaje;
    private List<Puntaje> listPuntaje = new ArrayList<Puntaje>();
    ArrayAdapter<Puntaje> arrayAdapterPuntaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_usuario);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        edtUsuario= findViewById(R.id.edtNombre);
        final String user= getIntent().getStringExtra("names");
        edtUsuario.setText("Bienvenido "+user);

        listViewPuntaje= findViewById(R.id.lstPuntaje);

        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth!=null){
            edtUsuario.setText(""+firebaseAuth.getCurrentUser().getEmail());

        }

        imageButtonFlechaAtras= findViewById(R.id.imgAtras);
        imageButtonFlechaAtras.setOnClickListener(this);

        incializarFirebase();
        listarNiveles();


        textViewNombre=(TextView) findViewById(R.id.txtNombre);

        firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);{
                    textViewNombre.setText(String.valueOf(dataSnapshot.child("name").getValue()));//Traer desde la base de datos firebase el nombre para colocarlo en el cajón de texto
                    listarNiveles();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                if (firebaseAuth.getCurrentUser() !=null){

                    storageReference = FirebaseStorage.getInstance().getReference();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");//Refrenciar base de datos a la tabla Users creada
                    final ValueEventListener valueEventListener = databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);{
                                //textViewNombre.setText(String.valueOf(dataSnapshot.child("name").getValue()));//Traer desde la base de datos firebase el nombre para colocarlo en el cajón de texto
                                listarNiveles();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }

            }
        };*/



    }
    /*@Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }*/


    private void listarNiveles() {
        ValueEventListener nivel = databaseReference.child("Users/Puntaje").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPuntaje.clear();


                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Puntaje puntaje = objSnapshot.getValue(Puntaje.class);
                    if(puntaje.idUsuario.toString().equals(textViewNombre.getText())){

                    boolean add = listPuntaje.add(puntaje);


                    arrayAdapterPuntaje = new ArrayAdapter<Puntaje>(PuntajeUsuarioActivity.this, android.R.layout.simple_list_item_1, listPuntaje);
                        listViewPuntaje.setAdapter(arrayAdapterPuntaje);
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void incializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }
    @Override
    public void onClick(View view) {
        if (view == imageButtonFlechaAtras){
            startActivity(new Intent(this, MainPrincipalActivity.class));

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
