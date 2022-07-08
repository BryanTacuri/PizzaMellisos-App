package com.example.pizzamellisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.pizzamellisos.adapters.ListDevelopersAdapter;
import com.example.pizzamellisos.adapters.ListProductAdapter;
import com.example.pizzamellisos.dialogs.DialogAddProductFragment;
import com.example.pizzamellisos.entities.Developers;
import com.example.pizzamellisos.entities.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Producto extends AppCompatActivity {
    private DatabaseReference mDatabase;
    GenericTypeIndicator<Map<String, Product>> generic = new GenericTypeIndicator<Map<String, Product>>() {};
    private RecyclerView listProductsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        listProductsAdapter=findViewById(R.id.rcvwlistProduct);

        listProductsAdapter.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("products").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Product> productList=new ArrayList();
                        for(DataSnapshot singleSnapshot : snapshot.getChildren()){

                            Product p = singleSnapshot.getValue(Product.class);
                            System.out.println(p.getNameProduct());
                            productList.add(p);
                        }
                        prepareData(productList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

    }
    private void prepareData(List<Product> products){
        ListProductAdapter adapter= new ListProductAdapter(products);
        listProductsAdapter.setAdapter(adapter);
    }

    public void opendialog(View v){
        DialogAddProductFragment dialog=new DialogAddProductFragment();
        dialog.show(getSupportFragmentManager(), "DialogAddProduct");
    }
}