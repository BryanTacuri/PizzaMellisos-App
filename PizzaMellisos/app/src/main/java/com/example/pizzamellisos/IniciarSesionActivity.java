package com.example.pizzamellisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pizzamellisos.sale.PantallaPrincipalActiviry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesionActivity extends AppCompatActivity {
    private EditText correo;
    private EditText password;
    private FirebaseAuth mAuth;
    private Button btnLogin;

    private CheckBox checkGuardarSesion;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private String llave = "sesion";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        correo = findViewById(R.id.btnCorreoLogin);
        password = findViewById(R.id.btnPasswordLogin);
        mAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btnLogin);
        checkGuardarSesion = findViewById(R.id.checkSesionActiva);

        preferences = getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();

    }
        public void login(View view) {
            if ((correo.getText().length() == 0) || (password.getText().length() == 0)) {
                Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();

            } else {
                mAuth.signInWithEmailAndPassword(correo.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if(checkGuardarSesion.isChecked()){
                                       guardarSesion(checkGuardarSesion.isChecked(), correo.getText().toString(),
                                               password.getText().toString());
                                    }
                                    Intent i = new Intent(getApplicationContext(), PantallaPrincipalActiviry.class);
                                    startActivity(i);
                                    finish();
                                    //updateUI(user);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
           /* Intent i = new Intent(getApplicationContext(), PantallaPrincipalActiviry.class);
            startActivity(i);
            finish();*/
        }
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            //updateUI(currentUser);
        }

    public void guardarSesion(boolean checked, String nombre, String clave){
        editor.putBoolean(llave,  checked);
        editor.putString("nombre", nombre);
        editor.putString("clave", clave);
        editor.apply();
    }

        public void cancelar(View view){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }



    }
