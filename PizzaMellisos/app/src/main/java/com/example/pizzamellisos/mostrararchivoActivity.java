package com.example.pizzamellisos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class mostrararchivoActivity extends AppCompatActivity {
    private TextView mostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrararchivo_activity);
        mostrar = (TextView) findViewById(R.id.txtv_mostrar);
        try{
            File file = new File(getExternalFilesDir(null)+ "/datos_sda.txt");
            InputStream in = new FileInputStream(file);
            BufferedReader aux = new
                    BufferedReader(new InputStreamReader(in));
           // String data = aux.readLine();
            Scanner myReader = new Scanner(file);
            String data="";

            while(myReader.hasNextLine()){
                data+=myReader.nextLine();

            }
          //  while(aux.)
           /* while (data != null){
                //a.add(curLine);
                data += aux.readLine();
            }*/

            mostrar.setText(data);
        } catch (Exception e) {
            Log.e("Archivos",
                    "Error al leer el archivo desde la memoria externa--"+e.toString());
        }
    }
}
