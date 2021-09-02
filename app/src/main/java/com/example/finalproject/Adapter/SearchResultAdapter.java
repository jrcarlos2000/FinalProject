package com.example.finalproject.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.Domain.itemDomain;
import com.example.finalproject.ItemDetailActivity;
import com.example.finalproject.DatabaseAdapter.DataAdapter;
import com.example.finalproject.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.viewHolder> implements Filterable {
    ArrayList<itemDomain> itemDomains = new ArrayList<>();


    public SearchResultAdapter(ArrayList<itemDomain> itemDomains) {
        this.itemDomains.addAll(itemDomains);
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

        int drawableResourceId2 = holder.itemView.getContext().getResources().getIdentifier("recent_background","drawable",holder.itemView.getContext().getPackageName());


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

    @Override
    public Filter getFilter() {
        return searchFilter;
    }
    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<itemDomain> filteredList = new ArrayList<>();
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(constraint.toString().split("\\|")));
            String filters [] = constraint.toString().split("\\|");
            for(int i = 0; i <5 ;i++){
                filters[i] = filters[i].toLowerCase();
            }

            for(itemDomain item : DataAdapter.AllItems){
                if(perform_check(0,item,filters[0])
                    &&perform_check(1,item,filters[1])){
                        filteredList.add(item);
                }
            }


            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            itemDomains.clear();
            itemDomains.addAll((ArrayList<itemDomain>)filterResults.values);
            notifyDataSetChanged();
        }
    };

    private boolean perform_check(int i, itemDomain item, String filter) {
        if (filter.contains("?")){
            return true;
        }else{
            switch (i){
                case 0:{
                    return item.getSubjects().contains(filter);
                }
                case 1:{
                    return item.getDescription().toLowerCase().contains(filter);
                }
            }
        }
        return true;
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
