package com.sayone.firebaseexampleproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sayone.firebaseexampleproject.R;

public class UserNameActivity extends AppCompatActivity {

    private EditText userName;
    private Button submitButton;
    private String user;
    private String MY_PREFS_NAME;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);

        MY_PREFS_NAME = getResources().getString(R.string.sharedpref_key);
        mSharedPreferences = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);

        userName = (EditText) findViewById(R.id.user_name);
        submitButton = (Button) findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = userName.getText().toString();
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("user_name", user);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
