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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Area;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AreasActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth; //Definción de variable Autenticación
    private ImageView imageViewLista;

    private ProgressDialog progressDialog; //Definición de variable para cuadros de Dialogo
    private StorageReference storageReference; //Definción de referencia para firebase
    private DatabaseReference databaseReference; //Definción de variable para la base de datos firebase
    FirebaseDatabase firebaseDatabase;
    private ImageButton imageButtonFlechaAtras;


    private ListView listViewArea;
    private List<Area> listArea = new ArrayList<Area>();
    ArrayAdapter<Area> arrayAdapterArea;
    Area areaSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        progressDialog= new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();
        imageButtonFlechaAtras= findViewById(R.id.imgAtras);
        imageButtonFlechaAtras.setOnClickListener(this);
        imageViewLista= findViewById(R.id.imgLista);

        FirebaseUser user= firebaseAuth.getCurrentUser();



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
        if (view == imageButtonFlechaAtras){
            startActivity(new Intent(this, MainPrincipalActivity.class));

        }


    }
}
