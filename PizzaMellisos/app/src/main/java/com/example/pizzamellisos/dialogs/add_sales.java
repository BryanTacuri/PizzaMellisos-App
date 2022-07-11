package com.example.pizzamellisos.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pizzamellisos.R;
import com.example.pizzamellisos.entities.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;


public class add_sales extends DialogFragment {
    private DatabaseReference fbDataBase;
    private Spinner spinnerProducts;


  //  private FirebaseStorage storage;

    public add_sales() {
        // Required empty public constructor
        this.fbDataBase= FirebaseDatabase.getInstance().getReference();

     //   this.storageReference=FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return initDialog();
    }
    private void initSpinner(){

        List<Product> productList=new ArrayList();
        fbDataBase.child("products").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                       if(snapshot.exists()){
                           for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                               String id=singleSnapshot.getKey();
                               Product p = singleSnapshot.getValue(Product.class);
                               // System.out.println(p.getNameProduct());
                               productList.add(p);
                           }
                           ArrayAdapter<Product> adapterProduct=new ArrayAdapter<Product>(
                                   getContext(),
                                  android.R.layout.simple_dropdown_item_1line, productList );
                           spinnerProducts.setAdapter(adapterProduct);
                       }
                        //prepareData(productList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }
    private Dialog initDialog (){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View v=inflater.inflate(R.layout.fragment_add_sales, null);
        this.spinnerProducts =v.findViewById(R.id.spn_sales_products);
        initSpinner();
        builder.setView(v);


        return builder.create();
    }
}