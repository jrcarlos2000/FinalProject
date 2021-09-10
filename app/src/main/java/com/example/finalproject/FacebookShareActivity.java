package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.Domain.ShareableItem;
import com.example.finalproject.Domain.itemDomain;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Share;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class FacebookShareActivity extends AppCompatActivity {

    private CallbackManager callBackManager;
    private LoginButton loginButton;
    private ShareButton shareFacebookButton_pic;
    private ImageView picToShare;
    private TextView showText;
    private Bitmap b;
    private String TextToShare ;
    private ShareableItem shareableItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_share);
        callBackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.loginButton);
        picToShare = findViewById(R.id.picToShare);
        shareFacebookButton_pic = findViewById(R.id.shareFacebookButton_pic);
        showText = findViewById(R.id.showText);

        picToShare.setVisibility(View.INVISIBLE);
        showText.setVisibility(View.INVISIBLE);

        getBundle();

        loginButton.setPermissions(Arrays.asList("user_gender","user_friends"));
        loginButton.registerCallback(callBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    private void getBundle() {

        shareableItem = (ShareableItem)getIntent().getSerializableExtra("itemToShare");
        byte[] byteArray = shareableItem.getByteArray();
        b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        TextToShare = shareableItem.getData();
        if(shareableItem.is_pic()){
            picToShare.setImageBitmap(b);
            picToShare.setVisibility(View.VISIBLE);
        }else{
            showText.setText(TextToShare);
            showText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callBackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setBitmap(b)
                .build();
        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build();

        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setQuote(TextToShare)
                .build();

        if(shareableItem.is_pic()){
            shareFacebookButton_pic.setShareContent(sharePhotoContent);
        }else{
            shareFacebookButton_pic.setShareContent(shareLinkContent);
        }

//        ShareLinkContent shareContent = new ShareLinkContent.Builder()
//                .setShareHashtag(new ShareHashtag.Builder()
//                                .setHashtag("FinalProject").build())
//                .build();
//
//        shareFacebookButton.setShareContent(shareContent);
    }
}