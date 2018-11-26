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

public class NivelesAre2Activity extends AppCompatActivity implements View.OnClickListener {

    ListView lstViewNivel;
    private StorageReference storageReference; //Definción de referencia para firebase
    private DatabaseReference databaseReference; //Definción de variable para la base de datos firebase
    FirebaseDatabase firebaseDatabase;
    private ImageButton imageButtonFlechaAtras;
    private TextView txtArea;

    private FirebaseAuth firebaseAuth;

    private ListView listViewNivel;
    private List<Nivel> listNivel = new ArrayList<Nivel>();
    ArrayAdapter<Nivel> arrayAdapterNivel;
    Area areaSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveles_are2);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        listViewNivel= findViewById(R.id.lstNivel);
        firebaseAuth= FirebaseAuth.getInstance();
        imageButtonFlechaAtras= findViewById(R.id.imgAtras);
        imageButtonFlechaAtras.setOnClickListener(this);

        incializarFirebase();
        listarNiveles();

        listViewNivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    startActivity( new Intent(view.getContext(), JuegoNivel1Area2Activity.class));

                }
                if(position == 1) {
                    // Abre una nueva Activity:
                    startActivity( new Intent(view.getContext(), JuegoNivel2Area2Activity.class));

                }
                if(position == 2) {
                    // Abre una nueva Activity:
                    startActivity( new Intent(view.getContext(), JuegoNivel3Area2Activity.class));

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
                    arrayAdapterNivel = new ArrayAdapter<Nivel>(NivelesAre2Activity.this, android.R.layout.simple_list_item_1, listNivel);
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
        if (view == imageButtonFlechaAtras){
            startActivity(new Intent(this,LoginActivity.class));

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
                startActivity(new Intent(this, AreasActivity.class));//Llama a la pantalla de Logeo para ingresar de nuevo

            }break;

        }
        return  true;
    }


}
