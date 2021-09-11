package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalproject.DatabaseAdapter.DataAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IntroActivity extends AppCompatActivity {
    private ConstraintLayout startButton;
    private DataAdapter dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2);

        startButton = findViewById(R.id.startbtn);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this,MainActivity.class));
            }
        });
        try {
            parseData();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}