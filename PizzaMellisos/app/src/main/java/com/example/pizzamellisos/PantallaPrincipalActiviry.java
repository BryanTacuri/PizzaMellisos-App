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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzamellisos.dialogs.DialogAddProductFragment;
import com.example.pizzamellisos.dialogs.add_sales;
import com.google.firebase.auth.FirebaseAuth;

public class PantallaPrincipalActiviry extends AppCompatActivity {
    private TextView mostrar;
    private FirebaseAuth mAuth;
    private Button btnAddSales;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal_activiry);
        mAuth = FirebaseAuth.getInstance();
        btnAddSales = findViewById(R.id.btn_add_sales);
       // mostrar = (TextView)findViewById(R.id.textMostrar);
        //String sms = getIntent().getStringExtra("sms");
    //    mostrar.setText(sms);

        preferences = getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();

    }


    public void openAddSalesDialog(View v){
        add_sales dialog=new add_sales();
        dialog.show(getSupportFragmentManager(), "DialogAddSales");
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
            case R.id.opt_product:
                Intent i = new Intent(getApplicationContext(), Producto.class);
                this.goToActivity(i);
                break;
            case R.id.opt_perfil:
                Intent p =new Intent(getApplicationContext(), UserProfile.class);
                this.goToActivity(p);
                break;
            case R.id.opt_about_us:
                this.goToAboutUsActivity();
                break;
            default:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
    private void goToActivity(Intent i){
        startActivity(i);
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