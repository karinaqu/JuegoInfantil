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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Area;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrudAreaActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTexttNombre;
    private EditText editTextDescripcion;
    private ListView listViewArea;
    private ImageView imgFlechaAtras;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private List<Area> listArea = new ArrayList<Area>();
    ArrayAdapter<Area> arrayAdapterArea;
    Area areaSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_area);

        editTexttNombre=findViewById(R.id.txtNombre);
        editTextDescripcion= findViewById(R.id.txtDescripcion);
        listViewArea= findViewById(R.id.lstArea);
        imgFlechaAtras= findViewById(R.id.imgAtras);
        imgFlechaAtras.setOnClickListener(this);

        incializarFirebase();
        listarAreas();

        listViewArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaSeleccionado= (Area) parent.getItemAtPosition(position);
                editTexttNombre.setText(areaSeleccionado.getName());
                editTextDescripcion.setText(areaSeleccionado.getDescripcion());
            }
        });

    }
    private void listarAreas() {
        ValueEventListener area = databaseReference.child("Area").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listArea.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Area area = objSnapshot.getValue(Area.class);
                    boolean add = listArea.add(area);

                    arrayAdapterArea = new ArrayAdapter<Area>(CrudAreaActivity.this, android.R.layout.simple_list_item_1, listArea);
                    listViewArea.setAdapter(arrayAdapterArea);
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

    //Metodo para traer el layout del menú del crud
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_crud,menu);
        return super.onCreateOptionsMenu(menu);

    }
    //Mótodo usado para describir las opciones del menú

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        final String nombre = editTexttNombre.getText().toString();
        final String descripcion = editTextDescripcion.getText().toString();


        switch (item.getItemId()){
            case R.id.icon_insertar:{
                if(nombre.equals("")||descripcion.equals("")){
                    validacionIngreso();

                    break;
                }else {
                    Query buequeda= FirebaseDatabase.getInstance().getReference().child("Area").orderByChild("name").equalTo(nombre);
                    buequeda.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getChildrenCount()>0){
                                Toast.makeText(CrudAreaActivity.this,"Nombre ya existente ingrese otro", Toast.LENGTH_SHORT).show();
                            }else{

                                Area area= new Area();
                                area.setUid(UUID.randomUUID().toString());
                                area.setName(nombre);
                                area.setDescripcion(descripcion);
                                databaseReference.child("Area").child(area.getUid()).setValue(area);
                                Toast.makeText(CrudAreaActivity.this,"Añadido con exito", Toast.LENGTH_SHORT).show();
                                CasillasBlancas();


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }
            case R.id.icon_guardar:{
                Area area = new Area();
                area.setUid(areaSeleccionado.getUid());
                area.setName(editTexttNombre.getText().toString().trim());
                area.setDescripcion(editTextDescripcion.getText().toString().trim());
                databaseReference.child("Area").child(area.getUid()).setValue(area);
                Toast.makeText(this,"Modificado", Toast.LENGTH_LONG).show();;
                CasillasBlancas();
                break;

            }
            case R.id.icon_eliminar:{
                Area area = new Area();
                area.setUid(areaSeleccionado.getUid());
                databaseReference.child("Area").child(area.getUid()).removeValue();
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
            editTextDescripcion.setError("Se requiere el ingreso de descripción");
        }

    }

    @Override
    public void onClick(View view) {

        if (view == imgFlechaAtras){
            startActivity(new Intent(this, CrudsAdminActivity.class));

        }
    }
}



