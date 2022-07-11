package com.example.pizzamellisos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class UserProfile extends AppCompatActivity {
    private Spinner spinnerCiudad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);



        ArrayAdapter<CharSequence> adapter  = ArrayAdapter.createFromResource(this, R.array.spinner_ciudades, android.R.layout.simple_spinner_item);
        spinnerCiudad.setAdapter(adapter);
    }
}