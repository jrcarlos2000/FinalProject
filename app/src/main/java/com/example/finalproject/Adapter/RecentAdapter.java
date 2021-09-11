package com.example.finalproject.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.Domain.itemDomain;
import com.example.finalproject.ItemDetailActivity;
import com.example.finalproject.R;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.viewHolder> {
    ArrayList<itemDomain> itemDomains;

    public RecentAdapter(ArrayList<itemDomain> itemDomains) {
        this.itemDomains = itemDomains;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular,parent,false);
        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.item_title.setText(itemDomains.get(position).getTitle());
        holder.item_related_1.setText(itemDomains.get(position).getRelated_1());
        holder.item_related_2.setText(itemDomains.get(position).getRelated_2());
        holder.item_related_3.setText(itemDomains.get(position).getRelated_3());

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(itemDomains.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.item_pic);

        int drawableResourceId2 = holder.itemView.getContext().getResources().getIdentifier("item_visited_background","drawable",holder.itemView.getContext().getPackageName());


        holder.item_layout.setBackground(holder.itemView.getContext().getResources().getDrawable(drawableResourceId2));

        holder.item_layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(), ItemDetailActivity.class);
                intent.putExtra("object",itemDomains.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return itemDomains.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {

        TextView item_title,item_related_1,item_related_2,item_related_3;
        ImageView item_pic;
        ConstraintLayout item_layout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.item_title);
            item_pic = itemView.findViewById(R.id.item_pic);
            item_related_1 = itemView.findViewById(R.id.item_related_1);
            item_related_2 = itemView.findViewById(R.id.item_related_2);
            item_related_3 = itemView.findViewById(R.id.item_related_3);
            item_layout = itemView.findViewById(R.id.item_layout);
        }

    }


}
