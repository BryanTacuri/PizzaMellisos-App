package com.example.pizzamellisos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pizzamellisos.R;

import java.util.function.DoubleBinaryOperator;

public class dbHelper extends SQLiteOpenHelper   {

    private static final int DATABASE_VERSION = 1;
    private  static final String DATABASE_NOMBRE = "tallerr.db";
    public static final String TABLE_USER ="t_users";
    public static final String CAMPO_ID = "id";

    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_USER +"(" +CAMPO_ID+" "+ "INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "apellido TEXT," +
                "edad TEXT," +
                "telefono TEXT," +
                "correo TEXT," +
                "password TEXT," +
                "ciudad TEXT," +
                "sexo TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_USER);
        onCreate(db);
    }
}
