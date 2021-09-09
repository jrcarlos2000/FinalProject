package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.DatabaseAdapter.HttpAdapter;
import com.example.finalproject.Domain.itemDomain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemDetailActivity extends AppCompatActivity {
    private TextView item_detail_title,item_detail_description,item_detail_attributes;
    private ImageView item_detail_image;
    private ArrayList<itemDomain> item_detail_related;
    private ArrayList<itemDomain> item_detail_questions;
    private itemDomain object;

    private FloatingActionButton item_detail_close_btn,item_detail_share_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        initView();
        getBundle();

        String url = "http://10.0.2.2:8080/searchitem?course=geo&searchKey=北京";
        try{
            HttpAdapter.getUrl(url,handler1,1);
        }catch (Exception e){
            Log.d("error: ",e.toString());
        }

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
        item_detail_attributes.setText(object.getAttsAsString());
        item_detail_close_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        item_detail_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "my body here";
                String shareSub = "your subject here";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareSub);
                startActivity(Intent.createChooser(shareIntent,"Share using:"));
            }
        });
    }

    private void initView() {
        item_detail_title = findViewById(R.id.item_detail_title);
        item_detail_description = findViewById(R.id.item_detail_description);
        item_detail_image = findViewById(R.id.item_detail_image);
        item_detail_close_btn = findViewById(R.id.item_detail_close_btn);
        item_detail_attributes = findViewById(R.id.item_detail_attributes);
        item_detail_share_btn = findViewById(R.id.item_detail_share_btn);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            JSONObject text = null;

            System.out.println("handling");

            try{
                text =new JSONObject(msg.obj.toString());
                JSONArray property = text.getJSONObject("data").getJSONArray("property");
                for (int i =0;i<property.length();i++){
                    String _key = null;
                    try{
                        _key = property.getJSONObject(i).getString("predicateLabel");
                    } catch (Exception e){
                        continue;
                    }
                    if(_key .equals( "内容")){
//                        item_detail_description.setText(property.getJSONObject(i).getString("object"));
                        Log.d("content: ",property.getJSONObject(i).getString("object"));
                    }
                }
            }catch (Exception e){
                Log.d("error: ",e.toString());
            }
        }
    };
}