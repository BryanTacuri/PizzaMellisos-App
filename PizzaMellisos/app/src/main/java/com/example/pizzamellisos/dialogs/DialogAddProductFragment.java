package com.example.pizzamellisos.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzamellisos.R;
import com.example.pizzamellisos.entities.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class DialogAddProductFragment extends DialogFragment {
    private DatabaseReference fbDataBase;
    Activity actividad;
    //IComunicaFragments iComunicaFragments;

    Button btn_salir;
    Button btn_add_prodcut;

    LinearLayout lienarProducts;
    CardView  card;

    TextView txtNameProduct;
    TextView txtDescriptionProduct;
    TextView txtPrice;

    public DialogAddProductFragment() {
        // Required empty public constructor
        this.fbDataBase= FirebaseDatabase.getInstance().getReference();
    }


    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return createDialogAddProduct();
    }

    private AlertDialog createDialogAddProduct() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View v=inflater.inflate(R.layout.fragment_dialog_add_product, null);
        builder.setView(v);

        btn_add_prodcut=v.findViewById(R.id.btn_add_prduct_dialog);
        btn_salir=v.findViewById(R.id.btn_close_dialog_product);

        txtNameProduct=v.findViewById(R.id.txtNameProduct);
        txtDescriptionProduct=v.findViewById(R.id.txtDescriptionProduct);
        txtPrice=v.findViewById(R.id.txtPriceProduct);

        //lienarProducts=v.findViewById(R.id.lytProducts);
        createEvents();
        return builder.create();
    }

    private void createEvents() {

        btn_add_prodcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_product=txtNameProduct.getText().toString();
                String Description=txtDescriptionProduct.getText().toString();
                double price=Double.parseDouble(txtPrice.getText().toString());
                System.out.println(name_product+"  "+Description+" "+price );
                String url="";
                Product p= new Product(UUID.randomUUID().toString(), url, name_product, Description, price);
                fbDataBase.child("products").child(p.getUid()).setValue(p);
                Toast.makeText(getContext(), "Agregado", Toast.LENGTH_LONG);
                dismiss();
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Close", Toast.LENGTH_LONG);
                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  Activity){
            this.actividad=(Activity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_add_product, container, false);
    }
}