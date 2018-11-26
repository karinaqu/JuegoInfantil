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
import android.widget.ListView;
import android.widget.Toast;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Rol;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrudRolActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTexttNombre;
    private EditText editTextDescripcion;

    private ListView listViewRol;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private List<Rol> listRol = new ArrayList<Rol>();
    ArrayAdapter<Rol> arrayAdapterRol;
    Rol rolSeleccionado;
    private ImageButton imageButtonFlechaAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_rol);

        editTexttNombre=findViewById(R.id.txtNombre);
        editTextDescripcion= findViewById(R.id.txtDescripcion);
        listViewRol= findViewById(R.id.lstRol);

        incializarFirebase();
        listarRoles();
        imageButtonFlechaAtras= findViewById(R.id.imgAtras);
        imageButtonFlechaAtras.setOnClickListener(this);

        listViewRol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rolSeleccionado= (Rol) parent.getItemAtPosition(position);
                editTexttNombre.setText(rolSeleccionado.getName());
                editTextDescripcion.setText(rolSeleccionado.getDescripcion());
            }
        });
    }

    private void listarRoles() {
        ValueEventListener rol = databaseReference.child("Rol").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listRol.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Rol rol = objSnapshot.getValue(Rol.class);
                    listRol.add(rol);

                    arrayAdapterRol = new ArrayAdapter<Rol>(CrudRolActivity.this, android.R.layout.simple_list_item_1, listRol);
                    listViewRol.setAdapter(arrayAdapterRol);
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
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_crud,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String nombre = editTexttNombre.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();


        switch (item.getItemId()){
            case R.id.icon_insertar:{
                if(nombre.equals("")||descripcion.equals("")){
                    validacionIngreso();

                    break;
                }else {
                    Rol rol= new Rol();
                    rol.setUid(UUID.randomUUID().toString());
                    rol.setName(nombre);
                    rol.setDescripcion(descripcion);
                    databaseReference.child("Rol").child(rol.getUid()).setValue(rol);
                    Toast.makeText(this,"Añadido", Toast.LENGTH_LONG).show();;
                    CasillasBlancas();
                    break;
                }


            }
            case R.id.icon_guardar:{
                Rol rol = new Rol();
                rol.setUid(rolSeleccionado.getUid());
                rol.setName(editTexttNombre.getText().toString().trim());
                rol.setDescripcion(editTextDescripcion.getText().toString().trim());
                databaseReference.child("Rol").child(rol.getUid()).setValue(rol);
                Toast.makeText(this,"Modificado", Toast.LENGTH_LONG).show();;
                CasillasBlancas();
                break;

            }
            case R.id.icon_eliminar:{
                Rol rol = new Rol();
                rol.setUid(rolSeleccionado.getUid());
                databaseReference.child("Rol").child(rol.getUid()).removeValue();
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
            editTextDescripcion.setError("Se requiere el ingreso de la descripción");
        }

    }

    @Override
    public void onClick(View view) {
        if (view == imageButtonFlechaAtras){
            startActivity(new Intent(this, CrudsAdminActivity.class));

        }
    }
}

