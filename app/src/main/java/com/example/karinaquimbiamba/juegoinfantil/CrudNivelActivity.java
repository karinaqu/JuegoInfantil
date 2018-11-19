package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrudNivelActivity extends AppCompatActivity implements View.OnClickListener{



    private EditText editTexttNombre;
    private EditText editTextDescripcion;
    private EditText editTextAreaNombre;
    private ImageButton imageButtonFlechaAtras;

    private ListView listViewNivel;
    private Spinner spinnerArea;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private List<Area> listArea = new ArrayList<Area>();
    ArrayAdapter<Area> arrayAdapterArea;

    private List<Nivel> listNivel = new ArrayList<Nivel>();
    ArrayAdapter<Nivel> arrayAdapterNivel;
    Area areaSeleccionado;
    Nivel nivelSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_nivel);



        editTexttNombre=findViewById(R.id.txtNombre);
        editTextDescripcion= findViewById(R.id.txtDescripcion);
        listViewNivel= findViewById(R.id.lstNivel);
        //editTextAreaNombre= findViewById(R.id.txtNombreArea);
        imageButtonFlechaAtras= findViewById(R.id.imgAtras);
        imageButtonFlechaAtras.setOnClickListener(this);


        spinnerArea= findViewById(R.id.spnArea);


        incializarFirebase();
        listarAreas();
        listarNiveles();

        //Selección de datos del combobox
        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaSeleccionado= (Area) parent.getItemAtPosition(position);
                //editTexttNombre.setText(areaSeleccionado.getName());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listViewNivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nivelSeleccionado= (Nivel) parent.getItemAtPosition(position);
                editTexttNombre.setText(nivelSeleccionado.getName());
                editTextDescripcion.setText(nivelSeleccionado.getDescripcion());
            }
        });
    }
    //Metodo para traer el layout del menú del crud
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_crud,menu);
        return super.onCreateOptionsMenu(menu);

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

                    arrayAdapterArea = new ArrayAdapter<Area>(CrudNivelActivity.this, android.R.layout.simple_list_item_1, listArea);
                    spinnerArea.setAdapter(arrayAdapterArea);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                    boolean add = listNivel.add(nivel);

                    arrayAdapterNivel = new ArrayAdapter<Nivel>(CrudNivelActivity.this, android.R.layout.simple_list_item_1, listNivel);
                    listViewNivel.setAdapter(arrayAdapterNivel);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String nombre = editTexttNombre.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        //String uid_Area = editTextAreaNombre.getText().toString();


        switch (item.getItemId()){
            case R.id.icon_insertar:{
                if(nombre.equals("")||descripcion.equals("")){
                    validacionIngreso();

                    break;
                }else {
                    final Nivel nivel= new Nivel();
                    nivel.setUid(UUID.randomUUID().toString());
                    nivel.setName(nombre);
                    nivel.setDescripcion(descripcion);
                    nivel.setUid_Area(areaSeleccionado.getName());



                    databaseReference.child("Nivel").child(nivel.getUid()).setValue(nivel);
                    Toast.makeText(this,"Añadido", Toast.LENGTH_LONG).show();;
                    CasillasBlancas();
                    break;
                }


            }
            case R.id.icon_guardar:{
                validacionIngreso();
                Nivel nivel = new Nivel();
                nivel.setUid(nivelSeleccionado.getUid());
                nivel.setName(editTexttNombre.getText().toString().trim());
                nivel.setDescripcion(editTextDescripcion.getText().toString().trim());
                databaseReference.child("Nivel").child(nivel.getUid()).setValue(nivel);
                Toast.makeText(this,"Modificado", Toast.LENGTH_LONG).show();;
                CasillasBlancas();
                break;

            }
            case R.id.icon_eliminar:{
                Nivel nivel = new Nivel();
                nivel.setUid(nivelSeleccionado.getUid());
                databaseReference.child("Nivel").child(nivel.getUid()).removeValue();
                Toast.makeText(this,"Eliminado", Toast.LENGTH_LONG).show();
                CasillasBlancas();

                break;

            }
            default:break;

        }
        return  true;
    }
    private  void CasillasBlancas(){
        editTexttNombre.setText("");
        editTextDescripcion.setText("");

    }

    private void validacionIngreso(){
        String nombre = editTexttNombre.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        if (nombre.equals("")){
            editTexttNombre.setError("Se requiere el ingreso del nombre");
        }else if (descripcion.equals("")){
            editTextDescripcion.setError("Se requiere el ingreso del nombre");
        }

    }

    @Override
    public void onClick(View view) {
        if (view == imageButtonFlechaAtras){
            startActivity(new Intent(this, CrudsAdminActivity.class));

        }
    }
}
