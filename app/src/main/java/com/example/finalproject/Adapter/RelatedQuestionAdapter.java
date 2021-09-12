package com.example.finalproject.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.ArrayList;

public class RelatedQuestionAdapter extends RecyclerView.Adapter<RelatedQuestionAdapter.viewHolder> {
    ArrayList<String> stringList;
    int type;

    public RelatedQuestionAdapter(ArrayList<String> stringList, int type) {
        this.stringList = stringList;
        this.type = type;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_related,parent,false);
        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.related_description.setText(stringList.get(position));


        int drawableResourceId2;

        if(type==1){
            drawableResourceId2 = holder.itemView.getContext().getResources().getIdentifier("category_background_1","drawable",holder.itemView.getContext().getPackageName());

        }else{
            drawableResourceId2 = holder.itemView.getContext().getResources().getIdentifier("category_background_2","drawable",holder.itemView.getContext().getPackageName());

        }

        holder.related_layout.setBackground(holder.itemView.getContext().getResources().getDrawable(drawableResourceId2));


    }


    @Override
    public int getItemCount() {
        return stringList.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {

        TextView related_description;
        ConstraintLayout related_layout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            related_layout = itemView.findViewById(R.id.related_layout);
            related_description = itemView.findViewById(R.id.related_description);
        }
    }
}
