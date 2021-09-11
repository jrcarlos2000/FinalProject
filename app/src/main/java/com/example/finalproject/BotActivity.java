package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalproject.Bot.BotAdapter;
import com.example.finalproject.Bot.BotChat;
import com.example.finalproject.DatabaseAdapter.DataAdapter;
import com.example.finalproject.DatabaseAdapter.Streamtool;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BotActivity extends AppCompatActivity {
    private RecyclerView chat;
    private EditText editMessage;
    private FloatingActionButton sendButton;

    private final String bot_key = "bot";
    private final String user_key = "user";
    private final String api_base_url = "http://api.brainshop.ai/";
    private final String api_url = "http://api.brainshop.ai/get?bid=159366&key=LQYrjNpdBQzUsSV6&uid=[uid]&msg=";

    private ArrayList<BotChat> messages;
    private BotAdapter botAdapter;

    private FloatingActionButton home_btn;
    private LinearLayout list_btn,me_btn,bot_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot);

        chat = findViewById(R.id.BotChatRV);
        editMessage = findViewById(R.id.BotEditMessage);
        sendButton = findViewById(R.id.BotSendButton);

        messages = new ArrayList<>();

        botAdapter = new BotAdapter(messages, this);
        LinearLayoutManager container = new LinearLayoutManager(BotActivity.this, RecyclerView.VERTICAL, false);

        chat.setLayoutManager(container);
        chat.setAdapter(botAdapter);

        editMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){

                    InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.toggleSoftInput(0, 0);

                    String msg = editMessage.getText().toString();
                    if(!msg.isEmpty()) {
                        messages.add(new BotChat(msg, user_key));
                        botAdapter.notifyDataSetChanged();
                        editMessage.setText("");
                        chat.scrollToPosition(messages.size()-1);
                        getResponse(msg);
                    }

                    return true;
                }
                return false;
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When a message is sent first hide keyboard
                InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(0, 0);
                String msg = editMessage.getText().toString();
                if(!msg.isEmpty()) {
                    messages.add(new BotChat(msg, user_key));
                    botAdapter.notifyDataSetChanged();
                    editMessage.setText("");
                    chat.scrollToPosition(messages.size()-1);
                    getResponse(msg);
                }
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
                startActivity(new Intent(BotActivity.this, MainActivity.class));
            }
        });
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BotActivity.this, listAllActivity.class);
                intent.putExtra("object","?");
                startActivity(intent);
            }
        });
        me_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BotActivity.this, UserActivity.class));
            }
        });

    }

    private void getResponse(String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // open connection
                    URL url = new URL(api_url + message);

                    System.out.println("sent " + message);

                    HttpURLConnection botConnection = (HttpURLConnection) url.openConnection();
                    botConnection.setConnectTimeout(2000);
                    botConnection.setRequestMethod("GET");
                    botConnection.connect();

                    InputStream in = botConnection.getInputStream();
                    String reply = Streamtool.read(in);
                    JSONObject replyJSON = new JSONObject(reply);

                    in.close();

                    System.out.println(replyJSON);

                    // display bot message
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                messages.add(new BotChat(replyJSON.getString("cnt"), bot_key));
                            } catch (Exception e) {
                                messages.add(new BotChat("Sorry, I don't understand.", bot_key));
                            }

                            botAdapter.notifyDataSetChanged();
                            chat.scrollToPosition(messages.size()-1);
                        }
                    });
                } catch (Exception e) {
                    messages.add(new BotChat("Sorry, I don't understand.", bot_key));
                }
            }
        }).start();
    }
}