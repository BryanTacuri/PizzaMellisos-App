package com.example.pizzamellisos;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class RegistrarPromocionActivity extends AppCompatActivity {
    private EditText nombre, apellido, edad, telefono, correo, password, confirmPassword;
    private Spinner spinnerCiudad;
    private RadioButton rbmasculino, rbfemenino;
    private RadioGroup radioGroup;
    private String seleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registrar_promocion);

        nombre = findViewById(R.id.txtNombreRegister);
        apellido = findViewById(R.id.txtApellidoRegister);
        edad = findViewById(R.id.txtEdadRegister);
        telefono = findViewById(R.id.editTextPhone);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        radioGroup = findViewById(R.id.radioSexo);
        rbmasculino = findViewById(R.id.rbMasculino);
        rbfemenino = findViewById(R.id.rbFemenino);
        correo = findViewById(R.id.btnCorreoLogin);
        password = findViewById(R.id.btnPasswordLogin);
        confirmPassword = findViewById(R.id.btnConfirmPassword);
        //ArrayAdapter<CharSequence> adapter  = ArrayAdapter.createFromResource(this, R.array.spinner_ciudades, android.R.layout.simple_spinner_item);
        //spinnerCiudad.setAdapter(adapter);
    }

    public void guardarSD(View v){
        String data = nombre.getText().toString()+"; "+apellido.getText().toString()+"; "+
                edad.getText().toString()+"; "+telefono.getText().toString()+"; "+
                spinnerCiudad.getSelectedItem().toString()+"; "+
                correo.getText().toString()+"; "+password.getText().toString()+"; "+
                confirmPassword.getText().toString();
        int statusSD = verificarStatusSD();
        FileOutputStream fileOutputStream = null;

        if(statusSD == 0) {
            try {
                File ruta_sd = Environment.getExternalStorageDirectory();
                File raizSD = new File(ruta_sd.getAbsolutePath(), "datos_sd.txt");
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(raizSD));
                osw.write(data);
                osw.close();
                /*fileOutputStream = openFileOutput("datos_sd.txt", MODE_PRIVATE);
                fileOutputStream.write(data.getBytes());
                File ruta_sd = new File(Environment.getExternalStorageState());
                Log.d("TAG1", "Guardado en: "+ruta_sd);
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(ruta_sd));
                osw.write(data);
                osw.close();*/
                Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT).show();
                /*File raizSD = new File(ruta_sd.getAbsolutePath(), "datos_sd.txt");
                FileOutputStream f = new FileOutputStream(raizSD);

                PrintWriter pw = new PrintWriter(f);
                pw.println(data);
                pw.println(".........");
                pw.flush();
                pw.close();
                f.close();
                Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT).show();
                Log.d("TAG1", "Guardado en: "+f);*/
            } catch (Exception e) {
                Log.e("Ficheros", "Error al escribir ficheros a tarjeta SD");
            } /*finally {
                if (fileOutputStream != null){
                    try{
                        fileOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }*/
        }else{
            Toast.makeText(getApplicationContext(), "No se puede guardar", Toast.LENGTH_SHORT).show();
        }
    }

    private int verificarStatusSD(){
        String estado = Environment.getExternalStorageState();
        if(estado.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(getApplicationContext(), "Montando SD", Toast.LENGTH_SHORT).show();
            return 0;
        } else if(estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
            Toast.makeText(getApplicationContext(), "Montando solo lectura", Toast.LENGTH_SHORT).show();
            return 1;
        }else {
            Toast.makeText(getApplicationContext(), "No está montado", Toast.LENGTH_SHORT).show();
            return 2;
        }
    }
}
