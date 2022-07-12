package com.example.pizzamellisos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pizzamellisos.db.dbHelper;

public class DML extends AppCompatActivity {

    private EditText correo, password, confirmPassword, nombre, apellido, edad, telefono, id;
    private Spinner spinnerCiudad;
    private RadioButton rbmasculino, rbfemenino;
    private RadioGroup radioGroup;
    private String seleccion;
    private String sexo;
    private dbHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         dbh = new dbHelper(this);
        setContentView(R.layout.activity_dml);
        id = findViewById(R.id.txtBusqueda);
        nombre = findViewById(R.id.txtSetNombre);
        apellido = findViewById(R.id.txtSetApellido);
        edad = findViewById(R.id.txtSetEdad);
        telefono = findViewById(R.id.editTextPhone4);
        correo = findViewById(R.id.setCorreo);
        password = findViewById(R.id.setPassword);
        spinnerCiudad = findViewById(R.id.setSpiner);
        rbmasculino = findViewById(R.id.rbSetMasculino);
        rbfemenino = findViewById(R.id.rbSetFemenino);
        radioGroup = findViewById(R.id.radioSexo);

        ArrayAdapter<CharSequence> adapter  = ArrayAdapter.createFromResource(this, R.array.spinner_ciudades, android.R.layout.simple_spinner_item);
        spinnerCiudad.setAdapter(adapter);
    }



    public void consultar(View view){
        SQLiteDatabase db = dbh.getReadableDatabase();
        String[] parametros = {id.getText().toString()};

        String[] campos = {"nombre", "apellido", "edad", "telefono", "correo", "password", "ciudad", "sexo"};
        try {
            Cursor cursor = db.query(dbHelper.TABLE_USER, campos, dbHelper.CAMPO_ID+"=?", parametros,null,null,null);
            cursor.moveToFirst();
            nombre.setText(cursor.getString(0));
           apellido.setText(cursor.getString(1));
            edad.setText(cursor.getString(2));
            telefono.setText(cursor.getString(3));
            correo.setText(cursor.getString(4));
            password.setText(cursor.getString(5));
            if(cursor.getString(6).equals("Quito") ){
                spinnerCiudad.setSelection(2);
            }

            if(cursor.getString(6).equals("Guayaquil") ){
                spinnerCiudad.setSelection(1);
            }else if(cursor.getString(6).equals("Cuenca") ){
                spinnerCiudad.setSelection(3);
            }else{
                spinnerCiudad.setSelection(2);
            }

            if(cursor.getString(7).equals("Masculino")){
                rbmasculino.setChecked(true);
            }else{
                rbfemenino.setChecked(true);
            }
            cursor.close();
        }catch (Exception e){
            borrar();
            Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_LONG).show();

        }



    }

    public void actualizar(View view){
        seleccion = spinnerCiudad.getSelectedItem().toString();
        String[] campos = {"nombre", "apellido", "edad", "telefono", "correo", "password", "ciudad", "sexo"};

        SQLiteDatabase db = dbh.getWritableDatabase();
        String[] parametros = {id.getText().toString()};
        ContentValues values = new ContentValues();
        values.put("nombre", nombre.getText().toString());
        values.put("edad", edad.getText().toString());
        values.put("correo", correo.getText().toString());
        values.put("password", password.getText().toString());
        values.put("ciudad", seleccion.toString());
        if(rbmasculino.isChecked()){
            values.put("sexo", rbmasculino.getText().toString());
        }else{
            values.put("sexo", rbfemenino.getText().toString());
        }
        values.put("apellido", apellido.getText().toString());

        db.update(dbHelper.TABLE_USER, values, dbHelper.CAMPO_ID+"=?", parametros);
        id.setText("");
        db.close();
        borrar();
        Toast.makeText(getApplicationContext(), "El usuario ha sido actualizado", Toast.LENGTH_LONG).show();

    }

    public void eliminar(View view){
        SQLiteDatabase db = dbh.getWritableDatabase();
        String[] parametros = {id.getText().toString()};
        db.delete(dbHelper.TABLE_USER, dbHelper.CAMPO_ID+"=?", parametros);
        id.setText("");
        db.close();
        borrar();
        Toast.makeText(getApplicationContext(), "El usuario ha sido eliminado", Toast.LENGTH_LONG).show();

    }

    public void borrar(){
        nombre.setText("");
        apellido.setText("");
        edad.setText("");
        telefono.setText("");
        correo.setText("");
        password.setText("");
        spinnerCiudad.setSelection(0);
        radioGroup.clearCheck();
    }
}