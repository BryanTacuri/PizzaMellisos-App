package com.example.pizzamellisos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pizzamellisos.R;
import com.example.pizzamellisos.entities.Developers;
import com.example.pizzamellisos.entities.Product;
import com.example.pizzamellisos.utils.DownloadImage;

import java.util.List;

public class ListProductAdapter  extends RecyclerView.Adapter<ListProductAdapter.ProductViewHolder>{

    private final List<Product> productsList;
    public ListProductAdapter(List<Product> productsList){
        this.productsList=productsList;
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
        public ProductViewHolder( @NonNull View itemView){
            super(itemView);
            nameProduct=itemView.findViewById(R.id.txt_card_product_item);
            descriptionProduct=itemView.findViewById(R.id.txt_desc_product_item);
            imageViewProduct=itemView.findViewById(R.id.img_card_product_item);
            priceProduct=itemView.findViewById(R.id.txt_price_product_item);
          //  img=itemView.findViewById(R.id.dev_img);
        }

        public void bindData(Product prod, int index){
            System.out.println(index+"kakaka");
            nameProduct.setText(prod.getNameProduct());
            descriptionProduct.setText(prod.getDescriptionProduct());
            priceProduct.setText(""+prod.getPriceProduct());
           // title.setText("Developer " + (index+1));

            new DownloadImage(imageViewProduct)
                    .execute(prod.getUrl());

        }

    }
}
