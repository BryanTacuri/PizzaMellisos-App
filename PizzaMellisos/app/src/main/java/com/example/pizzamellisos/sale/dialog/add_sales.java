package com.example.pizzamellisos.sale.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pizzamellisos.R;
import com.example.pizzamellisos.sale.adapters.ListSaleDetailsAdapter;
import com.example.pizzamellisos.entities.Product;
import com.example.pizzamellisos.entities.SaleDetail;
import com.example.pizzamellisos.entities.SaleDetailForView;
import com.example.pizzamellisos.entities.SaleHeader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class add_sales extends DialogFragment {
    private DatabaseReference fbDataBase;
    private Spinner spinnerProducts;
    private SaleHeader saleHeaderForViews;
    private ArrayList<SaleDetailForView> details=new ArrayList<>();
    private TextView client;
    private TextView observation;
    private TextView txt_cantidad_sale;
    private Button btnAddDetail;
    private Button btn_save_sale;
    private List<Product> productList=new ArrayList();
    private Product currentProduct;
    RecyclerView rcv_details_sale;
    ListSaleDetailsAdapter adapter;
    private boolean isEditing;


  //  private FirebaseStorage storage;

    public add_sales(String uuid, boolean isEditing, ArrayList<SaleDetailForView> details) {
        // Required empty public constructor
        this.isEditing=isEditing;
        this.details=details;
        this.fbDataBase= FirebaseDatabase.getInstance().getReference();
        saleHeaderForViews= new SaleHeader(uuid);


     //   this.storageReference=FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return initDialog();
    }
    private void initSpinner(){
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
        btnAddDetail=v.findViewById(R.id.btn_add_sale_detail);
        txt_cantidad_sale=v.findViewById(R.id.txt_cantidad_sale);
        this.spinnerProducts =v.findViewById(R.id.spn_sales_products);
        rcv_details_sale=v.findViewById(R.id.rcv_details_sale);
        btn_save_sale=v.findViewById(R.id.btn_save_sale);
        client=v.findViewById(R.id.txt_sales_client);
        observation=v.findViewById(R.id.txt_sales_observacion);
        //rcv_details_sale.setLayoutManager(new LinearLayoutManager(getContext()));
        initSpinner();
        initEvents();
        builder.setView(v);

        if(isEditing){
            prepareDataOnEdit();
        }


        return builder.create();
    }

    private void prepareData(){
        List<String> removedChilds=new ArrayList<>();
         adapter= new ListSaleDetailsAdapter(details,removedChilds, false
                );
        rcv_details_sale.setAdapter(adapter);
    }

    private void prepareDataOnEdit(){
        List<String> removedChilds=new ArrayList<>();
        if(adapter!=null){
            removedChilds=adapter.getRemovedChilds();
        }
        client.setText(getArguments().getString("client"));
                observation.setText(getArguments().getString("observation"));
        adapter= new ListSaleDetailsAdapter(details,removedChilds, true
        );
        rcv_details_sale.setAdapter(adapter);
    }
    private void initEvents(){
        spinnerProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentProduct=productList.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_save_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String client_=client.getText().toString();
                String observation_=observation.getText().toString();
                SaleHeader sale_h= new SaleHeader(UUID.randomUUID().toString());
                String currentUid=sale_h.getUid();
                if(isEditing==true) {
                    currentUid=saleHeaderForViews.getUid();
                    sale_h.setUid(currentUid);
                    if(adapter.getRemovedChilds().size()>0){

                        for (String uid: adapter.getRemovedChilds()) {
                            System.out.println(uid);
                            fbDataBase.child("sale_details").child(uid).setValue(null);
                        }
                    }
                }
                ArrayList<SaleDetail> sdtList=new ArrayList<>();
                double total=0;
                for (SaleDetailForView sdtv: adapter.getListDetails()) {
                    total+=sdtv.getTotal();
                    sdtList.add(new SaleDetail(sdtv.getUid(), currentUid, sdtv.getProducto().getUid(), sdtv.getCount(), sdtv.getPrice(), sdtv.getTotal()));
                }
                sale_h.setClient(client_);
                sale_h.setTotal(total);
                sale_h.setObservation(observation_);


                fbDataBase.child("sales").child(currentUid).setValue(sale_h)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                for (SaleDetail sdt: sdtList) {
                                    fbDataBase.child("sale_details").child(sdt.getUid())
                                            .setValue(sdt);
                                }
                                dismiss();
                            }
                        });



            }
        });
        btnAddDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cantidad=Integer.parseInt(txt_cantidad_sale.getText().toString());
                double total=cantidad*currentProduct.getPriceProduct();
                details.add(
                      new SaleDetailForView(
                              UUID.randomUUID().toString(),
                              currentProduct,
                              cantidad,
                              currentProduct.getPriceProduct(),
                              total
                      )
                );
                txt_cantidad_sale.setText("");
                if(isEditing==true){
                    prepareDataOnEdit();
                }else{
                    //btn_save_sale.setEnabled(true);
                    prepareData();
                }


            }
        });
    }
}