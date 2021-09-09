package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.finalproject.Adapter.RecentAdapter;
import com.example.finalproject.Adapter.SearchResultAdapter;
import com.example.finalproject.DatabaseAdapter.DataAdapter;
import com.example.finalproject.Domain.categoryDomain;
import com.example.finalproject.Domain.itemDomain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.Locale;

public class listAllActivity extends AppCompatActivity {
    private SearchResultAdapter searchResultAdapter;
    private RecyclerView recyclerViewResultList;
    private EditText search_bar;
    private TextView listall_filter_btn,listall_sort_btn,listall_history_btn,listall_history_display;
    private Button sort_layout_accept_btn,sort_layout_cancel_btn;
    private RadioButton selectedRadioButton;
    private RadioGroup RadioGroup;
    private String filters[];
    private FloatingActionButton home_btn;
    private LinearLayout list_btn,me_btn,bot_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        filters = new String[5];
        resetFilters();
        recyclerViewResults();
        getBundle();
        initButtons();
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
        search_bar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    DataAdapter.User.add_search_log(search_bar.getText().toString());

                    InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.toggleSoftInput(0, 0);

                    return true;
                }
                return false;
            }
        });

        //SET UP APP BAR AT THE BOTTOM
        home_btn = findViewById(R.id.home_btn);
        list_btn = findViewById(R.id.list_btn);
        me_btn = findViewById(R.id.me_btn);
        bot_btn = findViewById(R.id.bot_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(listAllActivity.this, MainActivity.class));
            }
        });
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listAllActivity.this, listAllActivity.class);
                intent.putExtra("object","?");
                startActivity(intent);
            }
        });
        me_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(listAllActivity.this, UserActivity.class));
            }
        });
        bot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(listAllActivity.this, BotActivity.class));
            }
        });

    }

    private void initButtons() {
        listall_filter_btn = findViewById(R.id.listall_filter_btn);
        listall_sort_btn = findViewById(R.id.listall_sort_btn);
        listall_history_btn = findViewById(R.id.listall_history_btn);

        //add function to call dialog when pressed

        listall_filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_btn_pressed();
            }
        });
        listall_sort_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_btn_pressed();
            }
        });
        listall_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history_btn_pressed();
            }
        });

    }

    private void sort_btn_pressed() {
        Dialog sort_dialog = new Dialog(listAllActivity.this);
        sort_dialog.setContentView(R.layout.sort_layout);
        sort_dialog.setCancelable(true);
        RadioGroup = sort_dialog.findViewById(R.id.RadioGroup);
        sort_layout_accept_btn = sort_dialog.findViewById(R.id.sort_layout_accept_btn);
        sort_layout_cancel_btn = sort_dialog.findViewById(R.id.sort_layout_cancel_btn);


        sort_layout_accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedRadioButton = sort_dialog.findViewById(RadioGroup.getCheckedRadioButtonId());

                String id_string = selectedRadioButton.getText().toString();
                switch(id_string){
                    case "NAME ( decreasing)" :{
                        filters[3]= Integer.toString(searchResultAdapter.SORT_NAME_DECREASING);
                        searchResultAdapter.getFilter().filter(TextUtils.join("|",filters));
                        break;
                    }
                    case "NAME (non-decreasing)":{
                        filters[3]= Integer.toString(searchResultAdapter.SORT_NAME_NON_DECREASING);
                        searchResultAdapter.getFilter().filter(TextUtils.join("|",filters));
                        break;
                    }
                    case "DIFFICULTY ( decreasing)":{
                        filters[3]= Integer.toString(searchResultAdapter.SORT_DIFFICULTY_DECREASING);
                        searchResultAdapter.getFilter().filter(TextUtils.join("|",filters));
                        break;
                    }
                    case "DIFFICULTY (non-decreasing)":{
                        filters[3]= Integer.toString(searchResultAdapter.SORT_DIFFICULTY_NON_DECREASING);
                        searchResultAdapter.getFilter().filter(TextUtils.join("|",filters));
                        break;
                    }
                    case "none":{
                        filters[3]= "?";
                        searchResultAdapter.getFilter().filter(TextUtils.join("|",filters));
                        break;
                    }
                }

                sort_dialog.dismiss();

            }
        });
        sort_layout_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_dialog.dismiss();
            }
        });

        sort_dialog.show();
    }

    private void history_btn_pressed() {

        Dialog history_dialog = new Dialog(listAllActivity.this);
        history_dialog.setContentView(R.layout.history_layout);
        history_dialog.setCancelable(true);
        listall_history_display = history_dialog.findViewById(R.id.listall_history_display);
        if(DataAdapter.User.getSearch_log().size() == 0){
            listall_history_display.setText("No Search history");
        }else{
           for(String s:DataAdapter.User.getSearch_log()){
               listall_history_display.append(s);
               listall_history_display.append("\n");
           }
        }
        history_dialog.show();

    }

    private void filter_btn_pressed() {
        //set up filter button
    }

    private void resetFilters() {
        filters [0] = "?";
        filters [1] = "?";
        filters [2] = "?";
        filters [3] = "?";
        filters [4] = "?";

    }

    private void getBundle() {
        String object = (String) getIntent().getSerializableExtra("object");
        filters[0] = object;
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