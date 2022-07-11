package com.example.pizzamellisos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.pizzamellisos.db.dbHelper;

public class MainActivity extends AppCompatActivity {


    private String llave = "sesion";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();

        if(revisarSesion()){
            Intent i = new Intent(this, PantallaPrincipalActiviry.class);
            startActivity(i);
        }



    }


    public void redirectLogin(View view){
        Intent i = new Intent(this, IniciarSesionActivity.class);
        startActivity(i);
    }

    public void redirectRgister(View view){
        Intent i = new Intent(this, RegistrarseActivity.class);
        startActivity(i);
    }


    public boolean revisarSesion(){
        return this.preferences.getBoolean(llave, false);
    }


}