package com.example.pizzamellisos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void redirectLogin(View view){
        Intent i = new Intent(this, IniciarSesionActivity.class);
        startActivity(i);
    }

    public void redirectRgister(View view){
        Intent i = new Intent(this, RegistrarseActivity.class);
        startActivity(i);
    }
}