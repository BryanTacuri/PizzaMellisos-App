package com.example.pizzamellisos.about_us;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pizzamellisos.IniciarSesionActivity;
import com.example.pizzamellisos.R;
import com.example.pizzamellisos.about_us.adapters.ListDevelopersAdapter;
import com.example.pizzamellisos.entities.Developers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AboutUs extends AppCompatActivity {

    private String entityDataBaseDevelopers="Developers";
    private DatabaseReference fbDataBase;
    private RecyclerView listDevelopers;
   // private ArrayList<Developers> lista_devs;

    GenericTypeIndicator<Map<String,Developers>> generic = new GenericTypeIndicator<Map<String, Developers>>() {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_about_us);
        listDevelopers=findViewById(R.id.listDevps);
        //LinearLayoutManager lil = new LinearLayoutManager(this);

        listDevelopers.setLayoutManager(new LinearLayoutManager(this));

       // this.lista_devs=new ArrayList<Developers>();
        this.fbDataBase= FirebaseDatabase.getInstance().getReference();

        this.fbDataBase.child(this.entityDataBaseDevelopers).get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){

                            Map<String,Developers> list=task.getResult().getValue(generic);

                            List <Developers>listDevps=new ArrayList();
                            //prepareData(list);
                            for (Map.Entry<String, Developers> entry : list.entrySet()) {
                                //System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());
                                listDevps.add(entry.getValue());
                            }
                           // System.out.println(listDevps.get(0).getEmail());
                            prepareData(listDevps);

                        }else{
                            makeToast("No se Pudo Obtener los Desarrolladores. Intente mas Tarde.");
                        }
                    }
                }
        );
    }

    private void prepareData(List<Developers> devps){
        ListDevelopersAdapter adapter= new ListDevelopersAdapter(devps);
        listDevelopers.setAdapter(adapter);
    }
    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void gotToBack(View v){
        //onBackPressed();
        Intent i = new Intent(getApplicationContext(), IniciarSesionActivity.class);

        startActivity(i);


        finish();
    }
}