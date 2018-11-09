package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class NivelesAre2Activity extends AppCompatActivity implements View.OnClickListener {

    ListView lstViewNivel;
    private StorageReference storageReference; //Definción de referencia para firebase
    private DatabaseReference databaseReference; //Definción de variable para la base de datos firebase
    FirebaseDatabase firebaseDatabase;
    private Button buttonSalir;
    private FirebaseAuth firebaseAuth;

    private ListView listViewNivel;
    private List<Nivel> listNivel = new ArrayList<Nivel>();
    ArrayAdapter<Nivel> arrayAdapterNivel;
    Area areaSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveles_are2);

        listViewNivel= findViewById(R.id.lstNivel);
        firebaseAuth= FirebaseAuth.getInstance();
        buttonSalir=(Button) findViewById(R.id.btnSalir);
        buttonSalir.setOnClickListener(this);
        incializarFirebase();
        listarNiveles();
    }
    private void listarNiveles() {
        ValueEventListener nivel = databaseReference.child("Nivel").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listNivel.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Nivel nivel = objSnapshot.getValue(Nivel.class);
                    nivel.getUid_Area();
                    boolean add = listNivel.add(nivel);
                    arrayAdapterNivel = new ArrayAdapter<Nivel>(NivelesAre2Activity.this, android.R.layout.simple_list_item_1, listNivel);
                    listViewNivel.setAdapter(arrayAdapterNivel);

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
        //Condición que establece que si se da clic en el Boton desloguearse
        if(view==buttonSalir){
            firebaseAuth.signOut();//Permite salir al usuario es decir desloguearse
            finish();
            startActivity(new Intent(this, LoginActivity.class));//Llama a la pantalla de Logeo para ingresar de nuevo
        }

    }


}
