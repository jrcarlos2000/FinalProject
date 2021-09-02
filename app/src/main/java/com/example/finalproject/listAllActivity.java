package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalproject.Adapter.RecentAdapter;
import com.example.finalproject.Adapter.SearchResultAdapter;
import com.example.finalproject.DatabaseAdapter.DataAdapter;
import com.example.finalproject.Domain.categoryDomain;
import com.example.finalproject.Domain.itemDomain;

import java.lang.reflect.Array;
import java.util.Locale;

public class listAllActivity extends AppCompatActivity {
    private SearchResultAdapter searchResultAdapter;
    private RecyclerView recyclerViewResultList;
    private EditText search_bar;
    private String filters[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        filters = new String[5];
        resetFilters();
        recyclerViewResults();
        getBundle();
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filters[0] = "?";
                filters[1] = s.toString();
                searchResultAdapter.getFilter().filter(TextUtils.join("|",filters));
                return;
            }
        });


    }

    private void resetFilters() {
        filters [0] = "?";
        filters [1] = "?";
        filters [2] = "?";
        filters [3] = "?";
        filters [4] = "?";

    }

    private void getBundle() {
        Log.d("testing","GOT IN HERE");
        String object = (String) getIntent().getSerializableExtra("object");
        filters[0] = object;
        Log.d("testing",TextUtils.join("|",filters));
        searchResultAdapter.getFilter().filter(TextUtils.join("|",filters));
    }

    private void recyclerViewResults() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewResultList = findViewById(R.id.results_view);
        search_bar = findViewById(R.id.search_bar);
        recyclerViewResultList.setLayoutManager(linearLayoutManager);
        searchResultAdapter = new SearchResultAdapter(DataAdapter.AllItems);
        recyclerViewResultList.setAdapter(searchResultAdapter);
    }
}