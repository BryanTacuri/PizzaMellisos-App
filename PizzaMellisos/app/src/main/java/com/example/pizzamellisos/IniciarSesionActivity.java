package com.example.pizzamellisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesionActivity extends AppCompatActivity {
    private EditText correo;
    private EditText password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        correo = findViewById(R.id.btnCorreoLogin);
        password = findViewById(R.id.btnPasswordLogin);
        mAuth = FirebaseAuth.getInstance();
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
                                    Intent i = new Intent(getApplicationContext(), PantallaPrincipalActiviry.class);
                                    i.putExtra("sms", "Datos correctos");
                                    startActivity(i);
                                    finish();
                                    //updateUI(user);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), PantallaPrincipalActiviry.class);
                                    i.putExtra("sms", "Datos incorrectos");
                                    startActivity(i);
                                    finish();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        }

        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            //updateUI(currentUser);
        }

        public void cancelar(View view){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

    }
