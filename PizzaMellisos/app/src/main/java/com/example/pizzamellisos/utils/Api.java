package com.example.pizzamellisos.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Api {

    static DatabaseReference fbDataBase= FirebaseDatabase.getInstance().getReference();

    public static boolean deleteChild(String child, String uid, Context context){
        Boolean resultDelete=true;
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Está Seguro que desea Eliminar?").setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    fbDataBase.getRoot().child(child).child(uid).setValue(null);
                                }
                            })
                    .setNegativeButton("No",null).show();

        }catch (Exception e){
            System.out.println(e.toString());
            resultDelete=false;
        }
        return   resultDelete;
    }


}
