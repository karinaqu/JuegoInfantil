package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Puntaje;
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

public class PuntajesUsuariosTotalesActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReference; //Definción de variable para la base de datos firebase
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private ImageButton imageButtonFlechaAtras;


    private ListView listViewPuntaje;
    private List<Puntaje> listPuntaje = new ArrayList<Puntaje>();
    ArrayAdapter<Puntaje> arrayAdapterPuntaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajes_usuarios_totales);

        listViewPuntaje= findViewById(R.id.lstPuntajes);
        imageButtonFlechaAtras= findViewById(R.id.imgAtras);
        imageButtonFlechaAtras.setOnClickListener(this);

        incializarFirebase();
        listarPuntajes();


    }
    private void listarPuntajes() {
        ValueEventListener nivel = databaseReference.child("Puntaje").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPuntaje.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Puntaje puntaje = objSnapshot.getValue(Puntaje.class);
                    boolean add = listPuntaje.add(puntaje);
                    arrayAdapterPuntaje = new ArrayAdapter<Puntaje>(PuntajesUsuariosTotalesActivity.this, android.R.layout.simple_list_item_1, listPuntaje);
                    listViewPuntaje.setAdapter(arrayAdapterPuntaje);

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
            startActivity(new Intent(this, CrudsAdminActivity.class));

        }


    }
}
