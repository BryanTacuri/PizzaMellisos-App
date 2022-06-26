package com.example.pizzamellisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarseActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText correo, password, confirmPassword, nombre, apellido, edad, telefono;
    private Spinner spinnerCiudad;
    private RadioButton rbmasculino, rbfemenino;
    private RadioGroup radioGroup;
    private String seleccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        mAuth = FirebaseAuth.getInstance();
        nombre = findViewById(R.id.txtNombreRegister);
        apellido = findViewById(R.id.txtApellidoRegister);
        edad = findViewById(R.id.txtEdadRegister);
        telefono = findViewById(R.id.editTextPhone);
        correo = findViewById(R.id.btnCorreoLogin);
        password = findViewById(R.id.btnPasswordLogin);
        confirmPassword = findViewById(R.id.btnConfirmPassword);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        rbmasculino = findViewById(R.id.rbMasculino);
        rbfemenino = findViewById(R.id.rbFemenino);
        radioGroup = findViewById(R.id.radioSexo);

        //seleccion = spinnerCiudad.getSelectedItem().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter <CharSequence> adapter  = ArrayAdapter.createFromResource(this, R.array.spinner_ciudades, android.R.layout.simple_spinner_item);
        spinnerCiudad.setAdapter(adapter);

        //para recuperar el spiiner

    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void register(){
        mAuth.createUserWithEmailAndPassword(correo.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Usuario " +nombre.getText().toString() + " "
                                            + apellido.getText().toString() + " registrado",
                                    Toast.LENGTH_SHORT).show();
                            Map<String, Object> map = new HashMap<>();
                            map.put("nombre", nombre.getText().toString());
                            map.put("apellido", apellido.getText().toString());
                            map.put("edad", edad.getText().toString());
                            map.put("telefono", telefono.getText().toString());
                            map.put("ciudad", seleccion.toString());
                            if(rbmasculino.isChecked()){
                                map.put("sexo", rbmasculino.getText().toString());
                            }else{
                                map.put("sexo", rbfemenino.getText().toString());
                            }
                            map.put("correo", correo.getText().toString());
                            map.put("password", password.getText().toString());
                            String id = mAuth.getCurrentUser().getUid();
                            mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if(task2.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent i = new Intent(getApplicationContext(), PantallaPrincipalActiviry.class);
                                        i.putExtra("sms", "Usuario " +nombre.getText().toString() + " "
                                        + apellido.getText().toString() + " registrado");
                                        startActivity(i);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Usuario no registrado.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario ya ha sido registrado antes.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void registerUser(View view){
        seleccion = spinnerCiudad.getSelectedItem().toString();
        if((nombre.getText().length() == 0) || (correo.getText().length() == 0) || (password.getText().length() == 0) || (apellido.getText().length() == 0)
                || (edad.getText().length() == 0) || (telefono.getText().length() == 0) || (confirmPassword.getText().length() == 0)
                 || (seleccion.equals("Seleccione una ciudad"))) {
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
        }else if(((password.getText().length() < 6) || (password.getText().length() < 6))) {
                Toast.makeText(this, "La contraseña debe ser igual o mayor a 6 digitos", Toast.LENGTH_SHORT).show();
        }else if(telefono.getText().length() < 10 || telefono.getText().length() > 10){
                Toast.makeText(this, "Ingrese un numero valido", Toast.LENGTH_SHORT).show();
        } else  if ((rbmasculino.isChecked())  || (rbfemenino.isChecked())){
                if (password.getText().toString().equals(confirmPassword.getText().toString())){
                    register();
                }else{
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(this, "Seleccione un sexo", Toast.LENGTH_SHORT).show();

            }
        }



    public void cancelar(View view){
        Intent i = new Intent(getApplicationContext(),IniciarSesionActivity.class);
        startActivity(i);



    }

    public void borrar(View view){
        nombre.setText("");
        apellido.setText("");
        edad.setText("");
        telefono.setText("");
        correo.setText("");
        password.setText("");
        confirmPassword.setText("");
        spinnerCiudad.setSelection(0);
        radioGroup.clearCheck();
        Toast.makeText(this, "Se han borrado todos los campos", Toast.LENGTH_SHORT).show();
    }
}