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
    private DataAdapter dataAdapter;
    private Boolean initialized = false;
    private LinearLayout list_btn,me_btn,bot_btn;
    private TextView add_subject_btn,delete_subject_btn, ask_bot_btn;
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

        //SET UP APP BAR AT THE BOTTOM
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

    //--------------------------------------------------------------------------------------------------------------
    private void parseData() throws IOException {

        //这边需要写标准数据，如果没有标准数据，什么都不能显示
        //这个是第一个请求
        //dataAdapter = new DataAdapter(请求args[]);
        //dataAdapter上 专门写了一个请求function ：readDataFromHttp（args）
        //dataAdapter 的 allitems 不能initialized之后修改，这就是标准数据（default data）
        //下面的代码课删掉

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

        adapter = new CategoryAdapter();

        adapter.addItem(new categoryDomain("Physics","physics_icon"));
        adapter.addItem(new categoryDomain("Math","math_icon"));
        adapter.addItem(new categoryDomain("Chemistry","chemistry_icon"));
        adapter.addItem(new categoryDomain("History","history_icon"));
        adapter.addItem(new categoryDomain("Biology","biology_icon"));

        recyclerViewCategoryList.setAdapter(adapter);




    }

}