package com.sayone.firebaseexampleproject.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sayone.firebaseexampleproject.Adapter.MessageListAdpater;
import com.sayone.firebaseexampleproject.Model.MessageDetails;
import com.sayone.firebaseexampleproject.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    private EditText textMessage;
    private ImageView sendButton;
    private RecyclerView messageListRecyclerView;
    private String MY_PREFS_NAME;
    private SharedPreferences mSharedPreferences;
    private String userName;
    private String message;
    private DatabaseReference dataBaseReference;
    private DatabaseReference dataRef;
    private FirebaseDatabase database;
    private ArrayList<MessageDetails> messageArray;
    private MessageListAdpater mAdapter;

    //Method to hide keyboard
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MY_PREFS_NAME = getResources().getString(R.string.sharedpref_key);
        mSharedPreferences = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        database = FirebaseDatabase.getInstance();
        dataBaseReference = database.getReference();
        dataRef = database.getReference().child("Chat");
        messageArray = new ArrayList<>();

        sendButton = (ImageView) findViewById(R.id.button_send);
        textMessage = (EditText) findViewById(R.id.message_text);
        messageListRecyclerView = (RecyclerView) findViewById(R.id.message_list);

        mAdapter = new MessageListAdpater(this, messageArray);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        messageListRecyclerView.setLayoutManager(mLayoutManager);
        messageListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        messageListRecyclerView.setAdapter(mAdapter);

        sendButton.setOnClickListener(this);
        //Reading data from databse
        dataRef.addValueEventListener(this);
    }

    //Method to add data to Firebase database
    public void addMessage(String userName, String message) {
        MessageDetails messageDetails = new MessageDetails(userName, message);
        String key = getResources().getString(R.string.sharedpref_key) + getMillis();
        dataBaseReference.child("Chat").child(key).setValue(messageDetails);
    }

    //Method to generate Uique key
    public long getMillis() {

        Date d1 = null;
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        int month = calendar.get(Calendar.MONTH);
        String dateStop = calendar.get(Calendar.YEAR) + "-" + (month + 1) + "-" + calendar.get(Calendar.DATE)
                + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

        try {
            d1 = format.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d1.getTime();
    }

    @Override
    public void onClick(View v) {

        message = textMessage.getText().toString();
        userName = mSharedPreferences.getString("user_name", "");
        addMessage(userName, message);
        textMessage.setText("");
        hideSoftKeyboard(MainActivity.this);
        messageListRecyclerView.scrollToPosition(messageArray.size() - 1);

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        messageArray.clear();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            MessageDetails messageDetails = postSnapshot.getValue(MessageDetails.class);
            messageArray.add(0, messageDetails);
            mAdapter.notifyDataSetChanged();
        }
        Collections.reverse(messageArray);
        mAdapter.notifyDataSetChanged();
        messageListRecyclerView.scrollToPosition(messageArray.size() - 1);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
