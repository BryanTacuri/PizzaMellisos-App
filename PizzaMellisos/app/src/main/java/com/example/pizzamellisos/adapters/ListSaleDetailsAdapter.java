package com.example.pizzamellisos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzamellisos.R;
import com.example.pizzamellisos.entities.SaleDetailForView;

import java.util.List;

public class ListSaleDetailsAdapter extends RecyclerView.Adapter<ListSaleDetailsAdapter.DetailViewHolder>{

    private final List<SaleDetailForView> listDetails;

    public List<SaleDetailForView> getListDetails() {
        return listDetails;
    }

    public ListSaleDetailsAdapter(List<SaleDetailForView> listDetails) {
        this.listDetails = listDetails;
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
