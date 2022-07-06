package com.example.pizzamellisos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class mostrararchivoActivity extends AppCompatActivity {
    private TextView mostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrararchivo_activity);
        mostrar = (TextView) findViewById(R.id.txtv_mostrar);
        try{
            BufferedReader aux = new BufferedReader(new InputStreamReader(openFileInput("datos_sd.txt")));
            String data = aux.readLine();
            mostrar.setText(data);
        } catch (Exception e) {
            Log.e("Archivos", "Error al leer el archivo desde la memoria externa");
        }
    }
}
