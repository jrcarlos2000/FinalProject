package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.Adapter.CategoryAdapter;
import com.example.finalproject.Adapter.RelatedQuestionAdapter;
import com.example.finalproject.DatabaseAdapter.DataAdapter;
import com.example.finalproject.DatabaseAdapter.HttpAdapter;
import com.example.finalproject.Domain.itemDomain;
import com.example.finalproject.Domain.ShareableItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ItemDetailActivity extends AppCompatActivity {
    private TextView item_detail_title,item_detail_description,item_detail_attributes;
    private ImageView item_detail_image;
    private ArrayList<itemDomain> item_detail_related;
    private ArrayList<itemDomain> item_detail_questions;
    private itemDomain object;
    private ConstraintLayout viewToShare;
    boolean isTextViewClicked = false;

    private RelatedQuestionAdapter relatedAdapter;
    private RelatedQuestionAdapter questionAdapter;
    private RecyclerView recyclerViewRelatedList;
    private RecyclerView recyclerViewQuestionList;

    private FloatingActionButton item_detail_close_btn,item_detail_share_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        initView();
        getBundle();

        recyclerViewRelated();
        recyclerViewQuestions();

        String url = "http://10.0.2.2:8080/searchitem?course=geo&searchKey=北京";
        try{
            HttpAdapter.getUrl(url,handler1,1);
        }catch (Exception e){
            Log.d("error: ",e.toString());
        }

    }

    private void getBundle() {

        object = (itemDomain) getIntent().getSerializableExtra("object");
        int drawableResourceId = this.getResources().getIdentifier(object.getPic(),"drawable",this.getPackageName());
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

                  showPopup(item_detail_share_btn);

            }
        });
        item_detail_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(isTextViewClicked){
                        item_detail_description.setMaxLines(3);
                        isTextViewClicked = false;
                    } else {
                        item_detail_description.setMaxLines(Integer.MAX_VALUE);
                        isTextViewClicked = true;
                    }
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
        viewToShare = findViewById(R.id.layoutToShare);
    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share_photo:
                        ShareItem(true);
                        return true;
                    case R.id.share_text:
                        ShareItem(false);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }
    private void ShareItem(boolean asPic){

        viewToShare.setDrawingCacheEnabled(true);
        Bitmap b = viewToShare.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        ShareableItem itemToShare = new ShareableItem(asPic,object.getDataAsString(),byteArray);

        Intent intent = new Intent(ItemDetailActivity.this, FacebookShareActivity.class);
        intent.putExtra("itemToShare",itemToShare);
        startActivity(intent);

    }

    private void recyclerViewRelated() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewRelatedList = findViewById(R.id.related_recycler_view);
        recyclerViewRelatedList.setLayoutManager(linearLayoutManager);

        ArrayList<String> data = new ArrayList<>();

        //ADD DATA :
        data.add("how are you doing today?");
        data.add("hi");
        data.add("hi");
        //--------------------
        relatedAdapter = new RelatedQuestionAdapter(data,1);
        recyclerViewRelatedList.setAdapter(relatedAdapter);

    }
    private void recyclerViewQuestions() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewQuestionList = findViewById(R.id.questions_recycler_view);
        recyclerViewQuestionList.setLayoutManager(linearLayoutManager);

        ArrayList<String> data = new ArrayList<>();

        //ADD DATA :
        data.add("how are you doing today?");
        data.add("hi");
        //--------------------
        questionAdapter = new RelatedQuestionAdapter(data,2);
        recyclerViewQuestionList.setAdapter(questionAdapter);

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