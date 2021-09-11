package com.example.finalproject.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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


    private int type;

    public CategoryAdapter(ArrayList<categoryDomain> CategoryDomains) {
        this.CategoryDomains = CategoryDomains;
        this.type = 0;

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
        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background_5));
        if(type == 1){
            holder.delete_btn.setVisibility(View.VISIBLE);
        }else{
            holder.delete_btn.setVisibility(View.INVISIBLE);
        }

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(CategoryDomains.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());
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

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryDomains.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,CategoryDomains.size());
                notifyDataSetChanged();
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
        ImageButton delete_btn;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            delete_btn = itemView.findViewById(R.id.delete_btn);
        }
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public void addItem(categoryDomain newItem){
        CategoryDomains.add(newItem);
        notifyDataSetChanged();
    }

    public boolean checkIfExist(String newitem){
        for(categoryDomain e: CategoryDomains){
            if(e.getTitle().equalsIgnoreCase(newitem)){
                return true;
            }
        }
        return false;
    }


}
