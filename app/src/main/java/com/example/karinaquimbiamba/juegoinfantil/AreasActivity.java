package com.example.karinaquimbiamba.juegoinfantil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class AreasActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth.AuthStateListener mAuthListener; //Definición de variable de autenticacipin
    private FirebaseAuth firebaseAuth; //Definción de variable Autenticación
    private TextView textViewNombre; //Definción de variable texto  para el nombre
    private TextView textViewRol;
    private Button buttonSalir; //Definición de variable para el botón Salir
    private ProgressDialog progressDialog; //Definición de variable para cuadros de Dialogo
    private StorageReference storageReference; //Definción de referencia para firebase
    private DatabaseReference databaseReference; //Definción de variable para la base de datos firebase
    FirebaseDatabase firebaseDatabase;
    private Button buttonAdministrar;
    private Button buttonJugar;


    private ListView listViewArea;
    private List<Area> listArea = new ArrayList<Area>();
    ArrayAdapter<Area> arrayAdapterArea;
    Area areaSeleccionado;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);

        progressDialog= new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();

        textViewRol=(TextView) findViewById(R.id.txtRol);

        buttonJugar=(Button) findViewById(R.id.btnJugar);
        buttonAdministrar=(Button) findViewById(R.id.btnAdminitrar);



        FirebaseUser user= firebaseAuth.getCurrentUser();
        textViewNombre=(TextView) findViewById(R.id.txtNombre);
        buttonSalir=(Button) findViewById(R.id.btnSalir);
        buttonSalir.setOnClickListener(this);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final String rol="3";

                if (firebaseAuth.getCurrentUser() !=null){


                    //
                    storageReference = FirebaseStorage.getInstance().getReference();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");//Refrenciar base de datos a la tabla Users creada
                    final ValueEventListener valueEventListener = databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                                User user = dataSnapshot.getValue(User.class);{
                                    //User user = dataSnapshot.getValue(User.class);
                                    //textViewRol.toString() =user.getEdad().toString();
                                    user.getEdad();
                                    textViewNombre.setText(String.valueOf(dataSnapshot.child("name").getValue()));//Traer desde la base de datos firebase el nombre para colocarlo en el cajón de texto
                                    if (user.getEdad().equals("3")){
                                        buttonJugar.setVisibility(View.VISIBLE);


                                    }
                                    if (user.getEdad().equals("1")){
                                        buttonAdministrar.setVisibility(View.VISIBLE);

                                    }

                                }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }

            }
        };
        listViewArea= findViewById(R.id.lstArea);
        incializarFirebase();
        listarAreas();

        listViewArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    // Abre una nueva Activity:
                    startActivity( new Intent(view.getContext(), MainAdminActivity.class));

                }
                if(position == 1) {
                    // Abre una nueva Activity:
                    startActivity( new Intent(view.getContext(), MainAdminActivity.class));
                }

            }
        });
    }
    public void incializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }

    private void listarAreas() {
        ValueEventListener area = databaseReference.child("Area").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listArea.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Area area = objSnapshot.getValue(Area.class);
                    boolean add = listArea.add(area);

                    arrayAdapterArea = new ArrayAdapter<Area>(AreasActivity.this, android.R.layout.simple_list_item_1, listArea);
                    listViewArea.setAdapter(arrayAdapterArea);
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
        if(view==buttonSalir){
            firebaseAuth.signOut();//Permite salir al usuario es decir desloguearse
            finish();
            startActivity(new Intent(this, LoginActivity.class));//Llama a la pantalla de Logeo para ingresar de nuevo
        }

    }
}
