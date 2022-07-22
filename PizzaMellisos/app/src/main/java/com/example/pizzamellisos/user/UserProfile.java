package com.example.pizzamellisos.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pizzamellisos.R;
import com.example.pizzamellisos.entities.Product;
import com.example.pizzamellisos.entities.SaleDetailForView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    private Spinner spinnerCiudad;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText correo, password, nombre, apellido, edad, telefono;
    private RadioButton rbmasculino, rbfemenino;
    private RadioGroup radioGroup;
    private String llave = "credenciales";
    String idUseer;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        nombre = findViewById(R.id.txtNombreRegister);
        apellido = findViewById(R.id.txtApellidoRegister);
        edad = findViewById(R.id.txtEdadRegister);
        telefono = findViewById(R.id.editTextPhone);
        correo = findViewById(R.id.txtCorreoUser);

        password = findViewById(R.id.btnPasswordLogin);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        rbmasculino = findViewById(R.id.rbMasculino);
        rbfemenino = findViewById(R.id.rbFemenino);
        radioGroup = findViewById(R.id.radioSexo);






        ArrayAdapter <CharSequence> adapter  = ArrayAdapter.createFromResource(this, R.array.spinner_ciudades, android.R.layout.simple_spinner_item);
        spinnerCiudad.setAdapter(adapter);
        mostrarUsuario();
    }


    private void mostrarUsuario() {
        User user1 = new User();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        idUseer = mAuth.getCurrentUser().getUid();
        correo.setText(user.getEmail());
        Toast.makeText(this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();


      DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reference.orderByChild("id").equalTo(idUseer);


        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child(idUseer).child("nombre").getValue(String.class);
                    String apellid = snapshot.child(idUseer).child("apellido").getValue(String.class);
                    String corr = snapshot.child(idUseer).child("correo").getValue(String.class);
                    String ciud = snapshot.child(idUseer).child("ciudad").getValue(String.class);
                    String eda = snapshot.child(idUseer).child("edad").getValue(String.class);
                    String pass = snapshot.child(idUseer).child("password").getValue(String.class);
                    String sex = snapshot.child(idUseer).child("sexo").getValue(String.class);
                    String tel = snapshot.child(idUseer).child("telefono").getValue(String.class);

                    nombre.setText(name);
                    apellido.setText(apellid);
                    apellido.setText(apellid);
                    apellido.setText(apellid);
                    apellido.setText(apellid);
                    apellido.setText(apellid);
                    apellido.setText(apellid);
                    apellido.setText(apellid);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}