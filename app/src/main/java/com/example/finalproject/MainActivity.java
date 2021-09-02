package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Adapter.CategoryAdapter;
import com.example.finalproject.Adapter.PopularAdapter;
import com.example.finalproject.Adapter.RecentAdapter;
import com.example.finalproject.DatabaseAdapter.DataAdapter;
import com.example.finalproject.Domain.categoryDomain;
import com.example.finalproject.Domain.itemDomain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter,popular_adapter,recent_adapter;
    private RecyclerView recyclerViewCategoryList,recyclerViewPopularList,recyclerViewRecentList;
    private ArrayList<itemDomain> itemList;
    private DataAdapter dataAdapter;
    private Boolean initialized = false;
    private LinearLayout list_btn,me_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("YourTag", "YourOutput");
        try {
            parseData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("YourTag", "YourOutput2");
        if(!initialized){
            try {
                parseData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            initialized = true;
        }
        recyclerViewCategory();
        recyclerViewPopular();
        recyclerViewRecent();

        list_btn = findViewById(R.id.list_btn);
        me_btn = findViewById(R.id.me_btn);
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, listAllActivity.class);
                intent.putExtra("object","?");
                startActivity(intent);
            }
        });
        me_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });
    }

    private void parseData() throws IOException {
        InputStreamReader is = new InputStreamReader(getAssets()
                .open("data.csv"));
        BufferedReader csvReader = new BufferedReader(is);
        dataAdapter = new DataAdapter("test_data",csvReader);
    }

    private void recyclerViewRecent() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewRecentList = findViewById(R.id.recyclerView3);
        recyclerViewRecentList.setLayoutManager(linearLayoutManager);

        recent_adapter = new RecentAdapter(dataAdapter.AllItems);
        recyclerViewRecentList.setAdapter(recent_adapter);
    }

    private void recyclerViewPopular() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        popular_adapter = new PopularAdapter(dataAdapter.AllItems);
        recyclerViewPopularList.setAdapter(popular_adapter);

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<categoryDomain> categoryList = new ArrayList<>();
        categoryList.add(new categoryDomain("Physics","physics_icon"));
        categoryList.add(new categoryDomain("Math","math_icon"));
        categoryList.add(new categoryDomain("Chemistry","chemistry_icon"));
        categoryList.add(new categoryDomain("History","history_icon"));
        categoryList.add(new categoryDomain("Biology","biology_icon"));

        adapter = new CategoryAdapter(categoryList);

        recyclerViewCategoryList.setAdapter(adapter);




    }

}