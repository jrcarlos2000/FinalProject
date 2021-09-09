package com.example.finalproject.Bot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.ArrayList;

public class BotAdapter extends RecyclerView.Adapter {
    private ArrayList<BotChat> messages;
    private Context context;

    public BotAdapter(ArrayList<BotChat> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_user_message, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_bot_message, parent, false);
            return new BotViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BotChat botChat = messages.get(position);
        if (botChat.getSender().equals("user")) {
            ((UserViewHolder) holder).userTextView.setText(botChat.getMessage());
        } else if (botChat.getSender().equals("bot")) {
            ((BotViewHolder) holder).botTextView.setText(botChat.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        String sender = messages.get(position).getSender();
        if (sender.equals("user")) {
            return 0;
        } else if (sender.equals("bot")) {
            return 1;
        } else {
            return -1;
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userTextView;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userTextView = itemView.findViewById(R.id.BotUserMessageText);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView botTextView;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            botTextView = itemView.findViewById(R.id.BotBotMessageText);
        }
    }
}
