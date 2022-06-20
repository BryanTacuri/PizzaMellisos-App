package com.example.pizzamellisos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PantallaPrincipalActiviry extends AppCompatActivity {
    private TextView mostrar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_activiry);
        mAuth = FirebaseAuth.getInstance();
        mostrar = (TextView)findViewById(R.id.textMostrar);
        String sms = getIntent().getStringExtra("sms");
        mostrar.setText(sms);
    }

    public void cerrarSesion(View view){
        mAuth.signOut();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);startActivity(i);
        startActivity(i);
        finish();

    }
}