package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "notes.USERNAME";
    private Button login;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        name = findViewById(R.id.username);
    }

    public void displayWelcome(View view){
        Intent intent = new Intent(this, MainActivity2.class);
        String user = name.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, user);
        startActivity(intent);

    }

}