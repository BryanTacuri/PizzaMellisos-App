package com.example.pizzamellisos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzamellisos.R;
import com.example.pizzamellisos.entities.Product;
import com.example.pizzamellisos.entities.SaleDetailForView;
import com.example.pizzamellisos.utils.DownloadImage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListSaleDetailFotDialogAdapter extends RecyclerView.Adapter<ListSaleDetailFotDialogAdapter.DetailViewHolder>{

    private final List<SaleDetailForView> listDetails;
    private DatabaseReference mDatabase;

    public ListSaleDetailFotDialogAdapter(List<SaleDetailForView> listDetails) {
        this.listDetails = listDetails;
        mDatabase= FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.show_sale_detail_item_dialog, parent, false);
        return new ListSaleDetailFotDialogAdapter.DetailViewHolder(view);
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
        TextView txt_npd_item;
        TextView txt_pdp_item;
        TextView txt_cdp_item;
        TextView txt_tdv_item;

        ImageView img_idp_item;
        public DetailViewHolder(@NonNull View itemView){
            super(itemView);
            System.out.println("ey");
            txt_npd_item=itemView.findViewById(R.id.txt_npd_item);
            txt_pdp_item=itemView.findViewById(R.id.txt_pdp_item);
            txt_cdp_item=itemView.findViewById(R.id.txt_cdp_item);
            txt_tdv_item=itemView.findViewById(R.id.txt_tdv_item);
            img_idp_item=itemView.findViewById(R.id.img_idp_item);
        }

        public void bindData(SaleDetailForView sale_dt, int i){

            mDatabase.child("products").child(sale_dt.getProducto().getUid())
                    .get().addOnSuccessListener(
                            new OnSuccessListener<DataSnapshot>() {

                                @Override
                                public void onSuccess(DataSnapshot psanpshot) {
                                    System.out.println("eeeeeffffffffffff");
                                    Product p=psanpshot.getValue(Product.class);
                                    txt_cdp_item.setText(""+sale_dt.getCount());
                                    txt_npd_item.setText(p.getNameProduct());
                                    txt_pdp_item.setText(""+sale_dt.getPrice());
                                    txt_tdv_item.setText(""+sale_dt.getTotal());
                                    new DownloadImage(img_idp_item)
                                            .execute(p.getUrl());


                                }
                            }
                    );




        }
    }
}
