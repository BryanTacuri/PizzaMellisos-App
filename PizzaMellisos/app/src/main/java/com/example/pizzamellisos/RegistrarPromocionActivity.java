package com.example.pizzamellisos;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class RegistrarPromocionActivity extends AppCompatActivity {
    private EditText nombre, apellido, edad, telefono, correo, password, confirmPassword;
    private Spinner spinnerCiudad;
    private RadioButton rbmasculino, rbfemenino;
    private RadioGroup radioGroup;
    private String seleccion;
    private Button verificar;

    private static final int PERMISSION_REQUEST_CODE = 1;

    private static final String TAG = "MainActivity";
    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registrar_promocion);

        ActivityCompat.requestPermissions(this, PERMISSIONS, 112);
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

        verificar = (Button) findViewById(R.id.btnBorrarRegister);
        verificar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), mostrararchivoActivity.class);
                startActivity(i);
            }
        });
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

                File raizSD = new File(getExternalFilesDir(null), "datos_sda.txt");

                if(!raizSD.exists()){
                    raizSD.createNewFile();
                }
                if(hasPermissions(this, PERMISSIONS)) {
                    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(raizSD));
                    osw.write(data);
                    osw.close();
                }
            } catch (Exception e) {
                Log.e("Ficheros", "Error al escribir ficheros a tarjeta SD " +e.toString());
            }
        }else{
            Log.e("Excepcion", "No se puede guardar");
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
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
