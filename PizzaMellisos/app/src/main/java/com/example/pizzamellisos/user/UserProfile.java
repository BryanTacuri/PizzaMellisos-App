package com.example.pizzamellisos.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pizzamellisos.R;

import com.example.pizzamellisos.sale.PantallaPrincipalActiviry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    private Spinner spinnerCiudad;
    private FirebaseAuth mAuth;
  private EditText correo, password, nombre, apellido, edad, telefono;
    private RadioButton rbmasculino, rbfemenino;
    private RadioGroup radioGroup;
    private String seleccion;
    private TextView correoUser;
    private String llave = "credenciales";
    String idUseer;
    DatabaseReference reference;

   private TextView fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        nombre = findViewById(R.id.txtNombreRegister);
        apellido = findViewById(R.id.txtApellidoRegister);
        edad = findViewById(R.id.txtEdadRegister);
        telefono = findViewById(R.id.editTextPhone);
        correo = findViewById(R.id.txtCorreoUser);

        password = findViewById(R.id.txtPassword);
        spinnerCiudad = findViewById(R.id.spinnerCiudadd);
        rbmasculino = findViewById(R.id.rbMasculino);
        rbfemenino = findViewById(R.id.rbFemenino);
        radioGroup = findViewById(R.id.radioSexo);
    correoUser = findViewById(R.id.correoUser);
        fullName = findViewById(R.id.full_name);




        ArrayAdapter <CharSequence> adapter  = ArrayAdapter.createFromResource(this, R.array.spinner_ciudades, android.R.layout.simple_spinner_item);
        spinnerCiudad.setAdapter(adapter);
        mostrarUsuario();
    }


    private void mostrarUsuario() {
        User user1 = new User();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        idUseer = mAuth.getCurrentUser().getUid();
        correo.setText(user.getEmail());


      DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reference.orderByChild("id").equalTo(idUseer);


        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child(idUseer).child("nombre").getValue(String.class);
                    String apellid = snapshot.child(idUseer).child("apellido").getValue(String.class);
                    String corr = snapshot.child(idUseer).child("correo").getValue(String.class);
                   String ciud = snapshot.child(idUseer).child("ciudad").getValue(String.class);
                     String eda = snapshot.child(idUseer).child("edad").getValue(String.class);
                    String tel = snapshot.child(idUseer).child("telefono").getValue(String.class);


                    String pass = snapshot.child(idUseer).child("password").getValue(String.class);

                String sex = snapshot.child(idUseer).child("sexo").getValue(String.class);
                      if(sex.equals("Masculino")){
                        rbmasculino.setChecked(true);
                    }else{
                        rbfemenino.setChecked(true);
                    }

                    if(ciud.equals("Quito") ){
                        spinnerCiudad.setSelection(2);
                    }

                    if(ciud.equals("Guayaquil") ){
                        spinnerCiudad.setSelection(1);
                    }else if(ciud.equals("Cuenca") ){
                        spinnerCiudad.setSelection(3);
                    }else{
                        spinnerCiudad.setSelection(2);
                    }

                    correoUser.setText(corr);
                    fullName.setText(name+" "+apellid);
                    nombre.setText(name);
                    apellido.setText(apellid);
                    correo.setText(corr);
                   edad.setText(eda);
                    password.setText(pass);
                     telefono.setText(tel);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void cancelar(View view){
        Intent i = new Intent(getApplicationContext(), PantallaPrincipalActiviry.class);
        startActivity(i);

    }


    public void editarUsuario(View view){
        fullName.setText(nombre.getText().toString()+" "+apellido.getText().toString());
        correoUser.setText(correo.getText().toString());
        seleccion = spinnerCiudad.getSelectedItem().toString();
        mAuth = FirebaseAuth.getInstance();
        idUseer = mAuth.getCurrentUser().getUid();
        reference.child(idUseer).child("nombre").setValue(nombre.getText().toString());
        reference.child(idUseer).child("apellido").setValue(apellido.getText().toString());
        reference.child(idUseer).child("edad").setValue(edad.getText().toString());
        reference.child(idUseer).child("telefono").setValue(telefono.getText().toString());
        reference.child(idUseer).child("ciudad").setValue(seleccion.toString());

        if(rbmasculino.isChecked()){
            reference.child(idUseer).child("sexo").setValue(rbmasculino.getText().toString());
        }else{
            reference.child(idUseer).child("sexo").setValue( rbfemenino.getText().toString());
        }
        reference.child(idUseer).child("correo").setValue(correo.getText().toString());
        reference.child(idUseer).child("password").setValue(password.getText().toString());

        Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
    }
}