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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter popular_adapter,recent_adapter;
    private CategoryAdapter adapter;
    private RecyclerView recyclerViewCategoryList,recyclerViewPopularList,recyclerViewRecentList;
    private ArrayList<itemDomain> itemList;

    private Boolean initialized = false;
    private FloatingActionButton home_btn;
    private LinearLayout list_btn,me_btn,bot_btn;
    private TextView add_subject_btn,delete_subject_btn, ask_bot_btn,recentsText;
    private ArrayList<categoryDomain> categoryList;

    // BELOW VIEW ITEMS FROM THE ADD_DELETE_SUBJECT ACTIONS
    private Button mainActivity_add_btn,mainActivity_cancel_btn;
    private EditText mainActivity_add_input;
    private TextView add_error_notification;
    //--------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("YourTag", "YourOutput");
        recentsText = findViewById(R.id.recentsText);

        recyclerViewCategory();
        recyclerViewPopular();
        recyclerViewRecent();

        //SET UP APP BAR AT THE BOTTOM
        home_btn = findViewById(R.id.home_btn);
        list_btn = findViewById(R.id.list_btn);
        me_btn = findViewById(R.id.me_btn);
        bot_btn = findViewById(R.id.bot_btn);

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
        bot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BotActivity.class));
            }
        });

//------------------------------------------------------------------------------------------------
        //SET UP OTHER TOUCHABLE BUTTONS LOCATED IN THE LAYOUT

        add_subject_btn = findViewById(R.id.add_subject_btn);
        delete_subject_btn = findViewById(R.id.delete_subject_btn);
        ask_bot_btn = findViewById(R.id.AskBotTV);

        add_subject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAddSubject();
            }
        });

        delete_subject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performDeleteSubject();
            }
        });

        ask_bot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BotActivity.class));
            }
        });
    }


    private void performDeleteSubject() {

        if(adapter.getType() == 0){
            adapter.setType(1);
        }else{
            adapter.setType(0);
        }
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void performAddSubject() {

        Dialog inputDialog = new Dialog(MainActivity.this);
        inputDialog.setContentView(R.layout.add_subject_dialog);
        inputDialog.setTitle("Add Subject");
        inputDialog.setCancelable(true);
        mainActivity_add_btn = inputDialog.findViewById(R.id.mainActivity_add_btn);
        mainActivity_cancel_btn = inputDialog.findViewById(R.id.mainActivity_cancel_btn);
        mainActivity_add_input = inputDialog.findViewById(R.id.mainActivity_add_input);
        add_error_notification = inputDialog.findViewById(R.id.add_error_notification);

        mainActivity_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newSubjectName = mainActivity_add_input.getText().toString();
                if(!adapter.checkIfExist(newSubjectName)){
                    adapter.addItem(new categoryDomain(newSubjectName,"default_subject_icon"));
                    inputDialog.dismiss();
                }else{
                    add_error_notification.setVisibility(View.VISIBLE);
                }

            }
        });

        mainActivity_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.dismiss();
            }
        });


        inputDialog.show();
    }

    private boolean checkCategoryExist(String newSubjectName) {

        for(categoryDomain e : categoryList){
            if(e.getTitle().equalsIgnoreCase(newSubjectName)){
                return true;
            }
        }
        return false;
    }

//----------

    private void recyclerViewRecent() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewRecentList = findViewById(R.id.recyclerView3);
        recyclerViewRecentList.setLayoutManager(linearLayoutManager);

        recent_adapter = new RecentAdapter(DataAdapter.getRecents());
        recyclerViewRecentList.setAdapter(recent_adapter);

        if(DataAdapter.getRecents().size()==0){
            Log.d("entro en recents" ,"succeess" );
            recentsText.setText(recentsText.getText().toString());
            recentsText.append("        NO ITEMS VISITED RECENTLY");
        }else{
            recentsText.setText("RECENTS");
        }
    }

    private void recyclerViewPopular() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        popular_adapter = new PopularAdapter(DataAdapter.getPopulars());
        recyclerViewPopularList.setAdapter(popular_adapter);

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        adapter = new CategoryAdapter(DataAdapter.AllCategories);
        recyclerViewCategoryList.setAdapter(adapter);




    }

}