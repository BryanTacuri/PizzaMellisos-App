package com.example.pizzamellisos.promotion.adapters;

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
import com.example.pizzamellisos.promotion.dialogs.DialogPromotionFragment;
import com.example.pizzamellisos.entities.Promotion;
import com.example.pizzamellisos.utils.Api;
import com.example.pizzamellisos.utils.DownloadImage;

import java.util.List;

public class ListPromotionAdapter extends RecyclerView.Adapter<ListPromotionAdapter.PromotionViewHolder> {
    private final List<Promotion> promotionsList;
    androidx.fragment.app.FragmentManager manager;
    Context context;

    public ListPromotionAdapter(List<Promotion> promotionsList, androidx.fragment.app.FragmentManager manager, Context context){
        this.promotionsList=promotionsList;
        this.manager=manager;
        this.context=context;
    }

    @Override
    public int getItemCount() {
        return promotionsList.size();
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_promotion, parent, false);
        return new ListPromotionAdapter.PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPromotionAdapter.PromotionViewHolder holder, int position) {
        holder.bindData(promotionsList.get(position), position);
    }
    
    public class PromotionViewHolder extends RecyclerView.ViewHolder {
        TextView namePromotion;
        TextView productsPromotion;
        TextView discountPromotion;
        TextView pricePromotion;
        //TextView dateStarPromotion;
        //TextView dateEndPromotion;
        ImageView imageViewPromotion;
        Button btnDelete, btnEdit;

        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            namePromotion=itemView.findViewById(R.id.txt_name_promotion);
            productsPromotion=itemView.findViewById(R.id.txt_products_promotion);
            discountPromotion=itemView.findViewById(R.id.txt_discount_promotion);
            pricePromotion=itemView.findViewById(R.id.txt_price_promotion);
            //dateStarPromotion=itemView.findViewById(R.id.txt_date_start_promotion);
            //dateEndPromotion=itemView.findViewById(R.id.txt_date_end_promotion);
            imageViewPromotion=itemView.findViewById(R.id.img_card_promotion);
            btnEdit=itemView.findViewById(R.id.btn_update_promotion);
            btnDelete=itemView.findViewById(R.id.btn_delete_promotion);
        }

        public void bindData(Promotion promo, int position) {
            namePromotion.setText(promo.getName());
            productsPromotion.setText(promo.getProducts());
            discountPromotion.setText(""+promo.getDiscount());
            pricePromotion.setText(""+promo.getPrice());
            //dateStarPromotion.setText(""+promo.getDate_start());
            //dateEndPromotion.setText(""+promo.getDate_end());

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Api.deleteChild("promotions", promo.getUid(), context);
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogPromotionFragment dialog = new DialogPromotionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("uuid", promo.getUid());
                    bundle.putString("name", promo.getName());
                    bundle.putString("prods", promo.getProducts());
                    bundle.putString("disc", ""+promo.getDiscount());
                    bundle.putString("price", ""+promo.getPrice());
                    //bundle.putString("dateStart", ""+promo.getDate_start());
                    //bundle.putString("dateEnd", ""+promo.getDate_end());
                    bundle.putString("url", promo.getUrl());
                    bundle.putBoolean("edit", true);
                    dialog.setArguments(bundle);
                    dialog.show(manager, "DialogPromotion");
                }
            });
            new DownloadImage(imageViewPromotion).execute(promo.getUrl());
        }
    }
}