package com.example.pizzamellisos.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pizzamellisos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {
    private Spinner spinnerCiudad;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText correo, password, confirmPassword, nombre, apellido, edad, telefono;
    private RadioButton rbmasculino, rbfemenino;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        mAuth = FirebaseAuth.getInstance();
        nombre = findViewById(R.id.txtNombreUpdate);
        apellido = findViewById(R.id.txtApellidoUpdate);
        edad = findViewById(R.id.txtEdadUpdate);
        telefono = findViewById(R.id.editTextPhoneUpdate);
        password = findViewById(R.id.updatePassword);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        rbmasculino = findViewById(R.id.rbMasculino);
        rbfemenino = findViewById(R.id.rbFemenino);
        radioGroup = findViewById(R.id.radioSexo);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("user", "");
        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();

        ArrayAdapter<CharSequence> adapter  = ArrayAdapter.createFromResource(this, R.array.spinner_ciudades, android.R.layout.simple_spinner_item);
        spinnerCiudad.setAdapter(adapter);

        mostrarUsuario();
    }

    private void mostrarUsuario() {

    }
}