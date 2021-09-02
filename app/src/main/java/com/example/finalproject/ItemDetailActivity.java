package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.Domain.itemDomain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ItemDetailActivity extends AppCompatActivity {
    private TextView item_detail_title,item_detail_description;
    private ImageView item_detail_image;
    private ArrayList<itemDomain> item_detail_related;
    private ArrayList<itemDomain> item_detail_questions;
    private itemDomain object;

    private FloatingActionButton item_detail_close_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        initView();
        getBundle();
    }

    private void getBundle() {

        Log.d("got data",".....trying");
        object = (itemDomain) getIntent().getSerializableExtra("object");
        Log.d("got data",".....success");
        int drawableResourceId = this.getResources().getIdentifier(object.getPic(),"drawable",this.getPackageName());
        Log.d("got resourcePic",".....success");
        Glide.with(this)
                .load(drawableResourceId)
                .into(item_detail_image);

        item_detail_title.setText(object.getTitle());
        item_detail_description.setText(object.getDescription());
        item_detail_description.setMovementMethod(new ScrollingMovementMethod());
        item_detail_close_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        item_detail_title = findViewById(R.id.item_detail_title);
        item_detail_description = findViewById(R.id.item_detail_description);
        item_detail_image = findViewById(R.id.item_detail_image);
        item_detail_close_btn = findViewById(R.id.item_detail_close_btn);
    }
}