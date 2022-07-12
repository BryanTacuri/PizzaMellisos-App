package com.example.pizzamellisos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class dbUser extends dbHelper{

Context context;
    public dbUser(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insertarUser(String nombre, String apellido, String edad, String telefono, String correo, String password, String ciudad, String sexo){
        long id=0;
        try{
            dbHelper dbh = new dbHelper(context);
            SQLiteDatabase db = dbh.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellido", apellido);
            values.put("edad", edad);
            values.put("telefono", telefono);
            values.put("correo", correo);
            values.put("password", password);
            values.put("ciudad", ciudad);
            values.put("sexo", sexo);
            id = db.insert(TABLE_USER, null, values);
        }catch (Exception e)
        {
            e.toString();
        }

        return id;
    }
}
