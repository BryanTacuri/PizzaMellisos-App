package com.example.pizzamellisos.sale.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pizzamellisos.R;
import com.example.pizzamellisos.adapters.ListProductAdapter;
import com.example.pizzamellisos.adapters.ListSaleDetailFotDialogAdapter;
import com.example.pizzamellisos.entities.Product;
import com.example.pizzamellisos.entities.SaleDetail;
import com.example.pizzamellisos.entities.SaleDetailForView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import com.google.android.gms.tasks.Tasks;

public class show_sale_dialog extends DialogFragment {

     TextView txt_dialog_client;
     TextView txt_dialog_observation;
     TextView txt_dialog_total;
     Activity actividad;
     RecyclerView rcv_dtl_dialog;

     Button btn_dialog_aceptar_;
     String uid;
    ArrayList<SaleDetailForView> productsList =new ArrayList<>();
    private DatabaseReference mDatabase;
    public show_sale_dialog(){
        mDatabase= FirebaseDatabase.getInstance().getReference();
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
        uid=getArguments().getString("uiid");
        txt_dialog_observation=v.findViewById(R.id.txt_dialog_observation);
        txt_dialog_observation.setText( getArguments().getString("observation") );
        rcv_dtl_dialog=v.findViewById(R.id.rcv_dtl_dialog);
        rcv_dtl_dialog.setLayoutManager(new LinearLayoutManager(getActivity()));
        txt_dialog_total=v.findViewById(R.id.txt_dialog_total);
        txt_dialog_total.setText( getArguments().getString("total") );

        btn_dialog_aceptar_=v.findViewById(R.id.btn_dialog_aceptar_);


        builder.setView(v);
        initEvents();
        initList();

        return  builder.create();
    }

    private void generateViewList(){
        System.out.println("eeeeeeeeeeeeeee");
        ListSaleDetailFotDialogAdapter adapter= new ListSaleDetailFotDialogAdapter( productsList
                );
        rcv_dtl_dialog.setAdapter(adapter);
    }
    private void initList(){



        mDatabase.child("sale_details").orderByChild("uidSaleHeader").equalTo(uid)
        .addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot single: snapshot.getChildren()) {

                            SaleDetail sale_detail=single.getValue(SaleDetail.class);
                            productsList.add(new SaleDetailForView(
                                    sale_detail.getUid(), new Product(sale_detail.getUidProduct()), sale_detail.getCount(),
                                    sale_detail.getPrice(), sale_detail.getTotal()
                            ));



                        }
                        generateViewList();
                        System.out.println("sss"+productsList.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

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