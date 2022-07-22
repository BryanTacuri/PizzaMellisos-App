package com.example.pizzamellisos.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pizzamellisos.R;
import com.example.pizzamellisos.producto.dialog.DialogAddProductFragment;
import com.example.pizzamellisos.entities.Product;
import com.example.pizzamellisos.utils.Api;
import com.example.pizzamellisos.utils.DownloadImage;

import java.util.List;

public class ListProductAdapter  extends RecyclerView.Adapter<ListProductAdapter.ProductViewHolder>{

    private final List<Product> productsList;
    androidx.fragment.app.FragmentManager manager;
    Context context;
    public ListProductAdapter(List<Product> productsList, androidx.fragment.app.FragmentManager manager, Context context){
        this.productsList=productsList;
        this.manager=manager;
        this.context=context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_product, parent, false);
        return new ListProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bindData(productsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameProduct;
        TextView descriptionProduct;
        ImageView imageViewProduct;
        TextView priceProduct;
        Button btnDelete, btnEdit;
        public ProductViewHolder( @NonNull View itemView){
            super(itemView);
            nameProduct=itemView.findViewById(R.id.txt_card_product_item);
            descriptionProduct=itemView.findViewById(R.id.txt_desc_product_item);
            imageViewProduct=itemView.findViewById(R.id.img_card_product_item);
            priceProduct=itemView.findViewById(R.id.txt_price_product_item);
            btnEdit=itemView.findViewById(R.id.btn_update_item_product);
            btnDelete= itemView.findViewById(R.id.btn_delete_item_product);
          //  img=itemView.findViewById(R.id.dev_img);
        }

        public void bindData(Product prod, int index){
           // System.out.println(index+"kakaka");
            nameProduct.setText(prod.getNameProduct());
            descriptionProduct.setText(prod.getDescriptionProduct());
            priceProduct.setText(""+prod.getPriceProduct());
           // title.setText("Developer " + (index+1));
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Api.deleteChild("products", prod.getUid(), context);
                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogAddProductFragment dialog=new DialogAddProductFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("url", prod.getUrl());
                    bundle.putString("name", prod.getNameProduct());
                    bundle.putString("desc", prod.getDescriptionProduct());
                    bundle.putString("price", ""+prod.getPriceProduct());
                    bundle.putBoolean("edit", true);
                    bundle.putString("uuid", prod.getUid());
                    dialog.setArguments(bundle);
                    dialog.show(manager, "DialogAddProduct");
                }
            });
            new DownloadImage(imageViewProduct)
                    .execute(prod.getUrl());

        }

    }
}
