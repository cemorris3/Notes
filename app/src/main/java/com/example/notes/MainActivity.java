package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "notes.USERNAME";
    //public static String userKey;
    //private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(userKey,"").equals("")){
            //user was logged in before the app close
            String welcomeName = sharedPreferences.getString(userKey,"");
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra(EXTRA_MESSAGE,welcomeName);
            startActivity(intent);
        } else{
            //start screen 1
            setContentView(R.layout.activity_main);
        }


    }

    public void displayWelcome(View view){
        EditText name = (EditText) findViewById(R.id.username);
        Intent intent = new Intent(this, MainActivity2.class);
        String user = name.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username",user).apply();

        intent.putExtra(EXTRA_MESSAGE, user);
        startActivity(intent);

    }

}