package com.example.finalproject.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.Domain.categoryDomain;
import com.example.finalproject.ItemDetailActivity;
import com.example.finalproject.R;
import com.example.finalproject.listAllActivity;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {
    ArrayList<categoryDomain> CategoryDomains;

    public CategoryAdapter(ArrayList<categoryDomain> categoryDomains) {
        this.CategoryDomains = categoryDomains;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cat,parent,false);
        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.categoryName.setText(CategoryDomains.get(position).getTitle());
        String picUrl = "";
        switch(position){
            case 0:{
                picUrl = "physics_icon";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background_1));
                break;
            }
            case 1:{
                picUrl = "math_icon";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background_2));
                break;
            }
            case 2:{
                picUrl = "biology_icon";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background_3));
                break;
            }
            case 3:{
                picUrl = "history_icon";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background_4));
                break;
            }
            case 4:{
                picUrl = "chemistry_icon";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background_5));
                break;
            }

        }

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoryPic);

        holder.mainLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(), listAllActivity.class);
                intent.putExtra("object",CategoryDomains.get(position).getTitle());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return CategoryDomains.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
