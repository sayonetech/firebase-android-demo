package com.sayone.firebaseexampleproject.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sayone.firebaseexampleproject.Activity.MainActivity;
import com.sayone.firebaseexampleproject.Model.MessageDetails;
import com.sayone.firebaseexampleproject.R;

import java.util.ArrayList;

/**
 * Created by sayone on 16/3/17.say
 */

public class MessageListAdpater extends RecyclerView.Adapter<MessageListAdpater.MessageViewHolder> {

    private ArrayList<MessageDetails> messageArray;
    private Context context;
    private SharedPreferences mSharedPreferences;
    private String MY_PREFS_NAME;

    public MessageListAdpater(MainActivity mainActivity, ArrayList<MessageDetails> messageArray) {

        this.messageArray = messageArray;
        context = mainActivity;
        MY_PREFS_NAME = context.getResources().getString(R.string.sharedpref_key);
        mSharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView user, textMessage;
        private CardView messageCard;
        private LinearLayout layout;

        public MessageViewHolder(View itemView) {
            super(itemView);

            user = (TextView) itemView.findViewById(R.id.user_name);
            textMessage = (TextView) itemView.findViewById(R.id.message);
            messageCard = (CardView) itemView.findViewById(R.id.message_card);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageListAdpater.MessageViewHolder holder, int position) {

        MessageDetails messageDetails = messageArray.get(position);

        holder.user.setText(messageDetails.getUserName().toString().trim());
        holder.textMessage.setText(messageDetails.getMessage().toString().trim());

        if (messageDetails.getUserName().toString().equals(mSharedPreferences.getString("user_name", ""))) {
            holder.layout.setGravity(Gravity.RIGHT);
            holder.messageCard.setCardBackgroundColor(Color.parseColor("#fefff1"));
        } else {
            holder.layout.setGravity(Gravity.LEFT);
            holder.messageCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return messageArray.size();
    }
}
