package com.example.pizzamellisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class PantallaPrincipalActiviry extends AppCompatActivity {
    private TextView mostrar;
    private FirebaseAuth mAuth;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pantalla_principal_activiry);
        mAuth = FirebaseAuth.getInstance();
       // mostrar = (TextView)findViewById(R.id.textMostrar);
        //String sms = getIntent().getStringExtra("sms");
    //    mostrar.setText(sms);

        preferences = getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.opt_logout:{
                this.logOut();
                break;
            }
            case R.id.opt_about_us:
                this.goToAboutUsActivity();
                break;
            default:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public void cerrarSesion(View view){

        this.logOut();
    }
    private void goToAboutUsActivity(){
        Intent i = new Intent(getApplicationContext(), AboutUs.class);

        startActivity(i);

    }
    private void logOut(){
        mAuth.signOut();
        editor.putBoolean("sesion", false);
        editor.apply();
        Intent i = new Intent(getApplicationContext(), IniciarSesionActivity.class);
        startActivity(i);


        finish();
    }
}