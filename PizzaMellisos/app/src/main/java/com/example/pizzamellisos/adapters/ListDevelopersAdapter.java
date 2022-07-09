package com.example.pizzamellisos.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzamellisos.R;
import com.example.pizzamellisos.dialogs.DialogAddProductFragment;
import com.example.pizzamellisos.entities.Developers;
import com.example.pizzamellisos.utils.DownloadImage;


import java.util.List;

public class ListDevelopersAdapter extends RecyclerView.Adapter<ListDevelopersAdapter.DeveloperViewHolder> {

    private final List<Developers> developersList;
    public ListDevelopersAdapter(List<Developers> developersList){
        this.developersList=developersList;
    }
    @NonNull
    @Override
    public DeveloperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_dev_contact, parent, false);
        return new DeveloperViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DeveloperViewHolder holder, int position) {
        /*holder.name.setText(developersList.get(position).getFullName());
        holder.email.setText(developersList.get(position).getEmail());
        holder.phone.setText(developersList.get(position).getPhone());*/
        holder.bindData(developersList.get(position), position);
        //holder.img.setImageURI(developersList.get(position).getUrl_image());
    }

    @Override
    public int getItemCount() {
        return this.developersList.size();
    }

    public  class DeveloperViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, phone, title;
        ImageView img;

        public DeveloperViewHolder(@NonNull View itemView) {
            super(itemView);
            email=itemView.findViewById(R.id.dev_email);
            name=itemView.findViewById(R.id.dev_name);
            title=itemView.findViewById(R.id.dev_title);
            phone=itemView.findViewById(R.id.dev_phone);
            img=itemView.findViewById(R.id.dev_img);



        }

        public void bindData(Developers dev, int index){
            name.setText(dev.getFullName());
            email.setText(dev.getEmail());
            phone.setText(dev.getPhone());
            title.setText("Developer " + (index+1));



            new DownloadImage(img)
                    .execute(dev.getUrl_image());

        }
    }
}
