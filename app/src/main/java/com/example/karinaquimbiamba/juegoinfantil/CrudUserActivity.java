package com.example.karinaquimbiamba.juegoinfantil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.Rol;
import com.example.karinaquimbiamba.juegoinfantil.CapaEntidades.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CrudUserActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView listViewUser;

    private Spinner spinnerRol;
    private EditText editTextEmail; //Definición de variable de texto para ingresar el email
    private EditText editTextPassword; //Definción de variable de texto para el ingreso de contraseña
    private EditText editTextNombre; // Definición de variable de texto para ingreso de nombre
    private ProgressDialog progressDialog; //Definición de variable de Dialogo
    private FirebaseAuth firebaseAuth; //Definición de variable para registro de usuarios en firebase
    private ImageView imgFlechaAtras;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private List<User> listUser = new ArrayList<User>();
    ArrayAdapter<User> arrayAdapterUser;
    User userSeleccionado;

    private List<Rol> listRol = new ArrayList<Rol>();
    ArrayAdapter<Rol> arrayAdapterRol;
    Rol rolSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_user);

        progressDialog= new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();

        editTextEmail =(EditText) findViewById(R.id.txtEmail);
        editTextPassword =(EditText) findViewById(R.id.txtPassword);
        editTextNombre= (EditText) findViewById(R.id.txtNombre);
        spinnerRol= findViewById(R.id.spnRol);
        imgFlechaAtras= findViewById(R.id.imgAtras);
        imgFlechaAtras.setOnClickListener(this);

        listViewUser= findViewById(R.id.lstUser);

        incializarFirebase();
        listarUsuarios();
        listarRoles();

        listViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userSeleccionado= (User) parent.getItemAtPosition(position);
                editTextNombre.setText(userSeleccionado.getName());
                editTextEmail.setText(userSeleccionado.getEmail());

            }
        });
        spinnerRol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rolSeleccionado= (Rol) parent.getItemAtPosition(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void incializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
    }
    private void listarUsuarios() {
        final ValueEventListener user = databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUser.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    User user = objSnapshot.getValue(User.class);
                    boolean add = listUser.add(user);

                    arrayAdapterUser = new ArrayAdapter<User>(CrudUserActivity.this, android.R.layout.simple_list_item_1, listUser);
                    listViewUser.setAdapter(arrayAdapterUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //Metodo para traer el layout del menú del crud
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_crud,menu);
        return super.onCreateOptionsMenu(menu);

    }
    //Mótodo usado para describir las opciones del menú

    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String name = editTextNombre.getText().toString().trim();
        final String rol= spinnerRol.getSelectedItem().toString().trim();


        //final String edad = editTextEdad.getText().toString().trim(); //Definción del tipo de variable edad

        if (name.isEmpty()){
            Toast.makeText(this,"Ingrese el nombre", Toast.LENGTH_SHORT).show();
            editTextNombre.setError("Es necesario ingresar el nombre");
            editTextNombre.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Ingrese un correo electronico", Toast.LENGTH_SHORT).show();
            editTextEmail.setError("Es necesario ingresar el correo");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Ingrese un correo válido");
            editTextEmail.requestFocus();
            return;
        }


        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingrese la contraseña", Toast.LENGTH_SHORT).show();
            editTextPassword.setError("Es necesario ingresar la contraseña");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            Toast.makeText(this,"Ingrese su contraseña", Toast.LENGTH_SHORT).show();
            editTextPassword.setError("Contraseña debe tener por lo menos 6 caracteres");
            editTextPassword.requestFocus();
            return;
        }


        //progressDialog.setMessage("Registrando Usuario.....");
        //progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            User user = new User(name,email,rol);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(CrudUserActivity.this, "Registro exitoso",Toast.LENGTH_SHORT).show();
                                        CasillasBlancas();
                                    }else {

                                    }

                                }
                            });

                        }else {
                            Toast.makeText(CrudUserActivity.this, "Could not register, please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.icon_insertar:{
                registerUser();


            }
            case R.id.icon_guardar:{

                break;



            }
            case R.id.icon_eliminar:{


                break;

            }
            default:break;

        }
        return  true;
    }

    private  void CasillasBlancas(){
        editTextEmail.setText("");
        editTextPassword.setText("");
        editTextNombre.setText("");


    }
    private void listarRoles() {
        ValueEventListener rol = databaseReference.child("Rol").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listRol.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Rol rol = objSnapshot.getValue(Rol.class);
                    boolean add = listRol.add(rol);

                    arrayAdapterRol = new ArrayAdapter<Rol>(CrudUserActivity.this, android.R.layout.simple_list_item_1, listRol);
                    spinnerRol.setAdapter(arrayAdapterRol);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





    @Override
    public void onClick(View view) {
        if (view == imgFlechaAtras){
            startActivity(new Intent(this, CrudsAdminActivity.class));

        }

    }
}
