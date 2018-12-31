package com.example.karinaquimbiamba.juegoinfantil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Area;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AreasActivity extends AppCompatActivity implements View.OnClickListener {


    private DatabaseReference databaseReference; //Definción de variable para la base de datos firebase
    FirebaseDatabase firebaseDatabase;
    private ImageView imgFlechaAtras;


    private ListView listViewArea;
    private List<Area> listArea = new ArrayList<Area>();
    ArrayAdapter<Area> arrayAdapterArea;
    Area areaSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);



        //firebaseAuth= FirebaseAuth.getInstance();
        imgFlechaAtras= findViewById(R.id.imgAtras);
        imgFlechaAtras.setOnClickListener(this);

        //FirebaseUser user= firebaseAuth.getCurrentUser();



        listViewArea= findViewById(R.id.lstArea);
        incializarFirebase();
        listarAreas();

        listViewArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    startActivity( new Intent(view.getContext(), NivelesAre1Activity.class));

                }
                if(position == 1) {
                    // Abre una nueva Activity:
                    startActivity( new Intent(view.getContext(), NivelesAre2Activity.class));
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


                    arrayAdapterArea = new ArrayAdapter<Area>(AreasActivity.this, R.layout.tamanoletra_listview, listArea);
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
        if (view == imgFlechaAtras){
            startActivity(new Intent(this, MainUsuarioActivity.class));

        }


    }
}
