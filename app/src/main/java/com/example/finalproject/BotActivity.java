package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.finalproject.Bot.BotAdapter;
import com.example.finalproject.Bot.BotChat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
                    getResponse(msg);
                }
            }
        });
    }

    private void getResponse(String message) {
        String url = api_url + message;
    }
}