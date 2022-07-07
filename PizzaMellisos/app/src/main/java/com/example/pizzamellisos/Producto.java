package com.example.pizzamellisos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.pizzamellisos.dialogs.DialogAddProductFragment;
import com.google.firebase.auth.FirebaseAuth;

public class Producto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

    }


    public void opendialog(View v){
        DialogAddProductFragment dialog=new DialogAddProductFragment();
        dialog.show(getSupportFragmentManager(), "DialogAddProduct");
    }
}