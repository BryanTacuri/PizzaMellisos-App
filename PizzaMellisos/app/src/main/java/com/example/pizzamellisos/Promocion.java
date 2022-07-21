package com.example.pizzamellisos;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzamellisos.adapters.ListPromotionAdapter;
import com.example.pizzamellisos.dialogs.DialogPromotionFragment;
import com.example.pizzamellisos.entities.Promotion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Promocion extends AppCompatActivity {
    private DatabaseReference mDatabase;
    GenericTypeIndicator<Map<String, Promotion>> generic = new GenericTypeIndicator<Map<String, Promotion>>() {};
    private RecyclerView listPromotionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        listPromotionAdapter = findViewById(R.id.rcv_promotion);

        listPromotionAdapter.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("promotions").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Promotion> productList = new ArrayList();
                        for(DataSnapshot singleSnapshot : snapshot.getChildren()){

                            Promotion p = singleSnapshot.getValue(Promotion.class);

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

    private void prepareData(List<Promotion> promo){
        ListPromotionAdapter adapter = new ListPromotionAdapter(promo
                , getSupportFragmentManager(), this);
        listPromotionAdapter.setAdapter(adapter);
    }

    public void opendialog(View v){
        DialogPromotionFragment dialog = new DialogPromotionFragment();
        dialog.show(getSupportFragmentManager(), "DialogPromotion");
    }
}
