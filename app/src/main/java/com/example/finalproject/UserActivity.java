package com.example.finalproject;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalproject.DatabaseAdapter.DataAdapter;
import com.example.finalproject.Domain.userDomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class UserActivity extends AppCompatActivity {

    String userList = "Carlos,carlos,gothere,data { } |Manuel,manuel,god, data {}";

    private TextView login_btn,register_btn;
    private TextView user_title,user_att_1,user_att_2,user_att_3,log_in_message;
    private String username,password,alias,lastLoggedDate,user_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Log.d("about to init","Trying...");
        init();
        Log.d("about to init","Success");

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!DataAdapter.User.isLogged())inputUser(false);
                else logOutAction();
            }
        });
    }

    private void logOutAction() {
        DataAdapter.initUser();
        init();
    }

    private void init(){

        log_in_message = findViewById(R.id.log_in_message);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);
        user_title = findViewById(R.id.user_title);
        user_att_1 = findViewById(R.id.user_att_1);
        user_att_2 = findViewById(R.id.user_att_2);
        user_att_3 = findViewById(R.id.user_att_3);

        if(DataAdapter.User.isLogged()){
            log_in_message.setText("CURRENTLY LOGGED IN");
            log_in_message.setBackgroundColor(Color.GREEN);
            login_btn.setText(R.string.logout_string);
        }else{
            log_in_message.setText("CURRENTLY NOT LOGGED IN");
            log_in_message.setBackgroundColor(Color.RED);
            login_btn.setText(R.string.login_string);
        }
        user_title.setText(DataAdapter.User.getUsername());
        user_att_1.setText(DataAdapter.User.getUsername());
        user_att_2.setText(DataAdapter.User.getLastLog());
        user_att_3.setText(DataAdapter.User.getAlias());
    }


    private void inputUser(boolean failed) {

        Dialog inputDialog = new Dialog(UserActivity.this);
        inputDialog.setContentView(R.layout.login_dialog);
        inputDialog.setTitle("Log In");
        inputDialog.setCancelable(true);

        EditText usernameInput = inputDialog.findViewById(R.id.usernameInput);
        EditText passwordInput = inputDialog.findViewById(R.id.passwordInput);
        TextView log_in_failed_text = inputDialog.findViewById((R.id.log_in_failed_text));
        Button dialog_login_btn = inputDialog.findViewById(R.id.dialog_login_btn);
        Button dialog_cancel_btn = inputDialog.findViewById(R.id.dialog_cancel_btn);

        if(failed){
            log_in_failed_text.setVisibility(View.VISIBLE);
        }
        dialog_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("pressed button login","success");
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                Log.d(username,password);
                if(checkCredentials(username,password)){
                    Log.d("found: ","SUCCESS2");
                    inputDialog.dismiss();
                }else{
                    inputDialog.dismiss();
                    inputUser(true);
                }

            }
        });

        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.dismiss();
            }
        });

        inputDialog.show();

    }

    private boolean checkCredentials(String username, String password) {

            ArrayList<String> users = new ArrayList<String>(Arrays.asList(userList.split("\\|")));
            boolean found =  false;
            for(String s: users){

                String userLine[] = s.split(",");

                if(userLine[0].equalsIgnoreCase(username) && userLine[1].equals(password)){
                    alias = userLine[2];
                    user_data = userLine[3];
                    found = true;
                    break;
                }
            }

            if(found) {

                lastLoggedDate = Calendar.getInstance().getTime().toString();
                DataAdapter.User.setUsername(username);
                DataAdapter.User.setData(user_data);
                DataAdapter.User.setAlias(alias);
                DataAdapter.User.setLastLog(lastLoggedDate);
                DataAdapter.User.setPassword(password);
                DataAdapter.User.log();
                DataAdapter.User.setSearchLog(new ArrayList<String>());

                init();

            }


            return found;

    }
}