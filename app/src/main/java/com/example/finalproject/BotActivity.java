package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.finalproject.Bot.BotAdapter;
import com.example.finalproject.Bot.BotChat;
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

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When a message is sent
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