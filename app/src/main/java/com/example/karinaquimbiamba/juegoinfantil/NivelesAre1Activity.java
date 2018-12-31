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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Area;
import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Nivel;
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

public class NivelesAre1Activity extends AppCompatActivity implements View.OnClickListener {


    private DatabaseReference databaseReference; //Definción de variable para la base de datos firebase
    FirebaseDatabase firebaseDatabase;
    private ImageView imgFlechaAtras;


    private ListView listViewNivel;
    private List<Nivel> listNivel = new ArrayList<Nivel>();
    ArrayAdapter<Nivel> arrayAdapterNivel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveles_are1);


        listViewNivel= findViewById(R.id.lstNivel);



        imgFlechaAtras= findViewById(R.id.imgAtras);
        imgFlechaAtras.setOnClickListener(this);

        incializarFirebase();
        listarNiveles();
        listViewNivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    startActivity( new Intent(view.getContext(), JuegoOrdenarVocales.class));

                }
                if(position == 1) {
                    // Abre una nueva Activity:
                    startActivity( new Intent(view.getContext(), JuegoCompletarPalabra.class));

                }
                if(position == 2) {
                    // Abre una nueva Activity:
                    startActivity( new Intent(view.getContext(), JuegoNivel3Area1Activity.class));

                }

            }
        });
    }
    private void listarNiveles() {
        ValueEventListener nivel = databaseReference.child("Nivel").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listNivel.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Nivel nivel = objSnapshot.getValue(Nivel.class);
                    if (nivel.getUid_Area().equals("Lenguaje")){
                        boolean add = listNivel.add(nivel);
                        arrayAdapterNivel = new ArrayAdapter<Nivel>(NivelesAre1Activity.this, R.layout.tamanoletra_listview, listNivel);
                        listViewNivel.setAdapter(arrayAdapterNivel);
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
        if (view == imgFlechaAtras){
            startActivity(new Intent(this, AreasActivity.class));

        }

    }


}
