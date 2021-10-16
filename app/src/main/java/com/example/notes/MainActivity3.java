package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {

    int noteid = -1;
    String userKey = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText editText = (EditText) findViewById(R.id.textView);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        noteid = extras.getInt("noteid");

        if(noteid != -1){
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    editText.setText(noteContent);
                }
            });
        }
    }

    public void savedNote(View view) {
        EditText editText = (EditText) findViewById(R.id.textView);
        String content = editText.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString(userKey, "");
        String username = "<" + user + ">";

        //save info to database?
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) { //add note
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else { //update note
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }
        //go to second activity using intents
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }
}