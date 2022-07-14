package com.example.pizzamellisos.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pizzamellisos.R;


public class show_sale_dialog extends DialogFragment {

     TextView txt_dialog_client;
     TextView txt_dialog_observation;
     TextView txt_dialog_total;
     Activity actividad;

     Button btn_dialog_aceptar_;
    public show_sale_dialog(){

    }
    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        return createDialogSaleTotal();
    }
    private Dialog createDialogSaleTotal(){
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v= inflater.inflate(R.layout.fragment_show_sale_dialog, null);

        txt_dialog_client=v.findViewById(R.id.txt_dialog_client);
        txt_dialog_client.setText( getArguments().getString("client") );

        txt_dialog_observation=v.findViewById(R.id.txt_dialog_observation);
        txt_dialog_observation.setText( getArguments().getString("observation") );

        txt_dialog_total=v.findViewById(R.id.txt_dialog_total);
        txt_dialog_total.setText( getArguments().getString("total") );

        btn_dialog_aceptar_=v.findViewById(R.id.btn_dialog_aceptar_);


        builder.setView(v);
        initEvents();



        return  builder.create();
    }
    private void initEvents(){
        btn_dialog_aceptar_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public View onCreateView( LayoutInflater inflater,
                              ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_sale_dialog, container, false);
    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        if(context instanceof  Activity){
            this.actividad=(Activity) context;
        }
    }
}