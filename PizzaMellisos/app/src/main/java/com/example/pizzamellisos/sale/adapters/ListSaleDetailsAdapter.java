package com.example.pizzamellisos.sale.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzamellisos.R;
import com.example.pizzamellisos.entities.Product;
import com.example.pizzamellisos.entities.SaleDetailForView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListSaleDetailsAdapter extends RecyclerView.Adapter<ListSaleDetailsAdapter.DetailViewHolder>{

    private final List<SaleDetailForView> listDetails;
    private final List<String> removedChilds;
    private boolean onEdit;
    private DatabaseReference mDatabase;

    public List<String> getRemovedChilds() {
        return removedChilds;
    }

    public List<SaleDetailForView> getListDetails() {
        return listDetails;
    }

    public ListSaleDetailsAdapter(List<SaleDetailForView> listDetails,
                                  List<String> removedChilds,
                                  boolean onEdit) {
        this.listDetails = listDetails;
        this.onEdit=onEdit;
        this.removedChilds=removedChilds;
        mDatabase= FirebaseDatabase.getInstance().getReference();
    }


    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.detail_item_sale, parent, false);
        return new ListSaleDetailsAdapter.DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        holder.bindData(listDetails.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listDetails.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
         TextView count;
         TextView product_name;
         TextView price;
         TextView subtotal;
         Button btnQuitar;

        public DetailViewHolder(@NonNull View itemView){
            super(itemView);
            System.out.println("ey");
            count=itemView.findViewById(R.id.txt_count_sdt);
            product_name=itemView.findViewById(R.id.txt_namep_sdt);
            price=itemView.findViewById(R.id.txt_price_sdt);
            subtotal=itemView.findViewById(R.id.txt_tsub_otal_sdt);
            btnQuitar=itemView.findViewById(R.id.btn_quitar_sdt);
        }
        public void bindData(SaleDetailForView sale_dt, int i){
            if(onEdit==true) {
                mDatabase.child("products").child(sale_dt.getProducto().getUid())
                        .get().addOnSuccessListener(
                                new OnSuccessListener<DataSnapshot>() {

                                    @Override
                                    public void onSuccess(DataSnapshot psanpshot) {
                                        System.out.println("eeeeeffffffffffff");
                                        Product p=psanpshot.getValue(Product.class);
                                        count.setText(""+sale_dt.getCount());
                                        product_name.setText(p.getNameProduct());
                                        price.setText(""+sale_dt.getPrice());
                                        subtotal.setText(""+sale_dt.getTotal());
                                        btnQuitar.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                listDetails.remove(i);
                                                notifyItemRemoved(i);
                                                notifyItemRangeChanged(i, listDetails.size());
                                                removedChilds.add(sale_dt.getUid());
                                            }
                                        });


                                    }
                                }
                        );
            }else{
                count.setText(""+sale_dt.getCount());
                product_name.setText(sale_dt.getProducto().getNameProduct());
                price.setText(""+sale_dt.getPrice());
                subtotal.setText(""+sale_dt.getTotal());
                btnQuitar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listDetails.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, listDetails.size());
                    }
                });
            }

        }


    }
}
