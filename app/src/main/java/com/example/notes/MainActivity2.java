package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private TextView welcome;
    String userKey = "username";
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        welcome = findViewById(R.id.welcome);
        // get welcome message
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        /*// display the message
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                welcome.setText("Welcome " + username + "!");
            }
        });*/
        // get SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);

        DBHelper helper = new DBHelper(sqLiteDatabase);
        notes = helper.readNotes(username);
        Log.i("here", "viewing notes");
        Log.i("user", username);
        Log.i("length", String.valueOf(notes.size()));
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes){
            Log.i("date",note.getTitle());
            Log.i("date",note.getDate());
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(),note.getDate()));
        }

        // use listview to display them on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesList);
        listView.setAdapter(adapter);

        // add onItemClickListener for ListView item, a note
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),MainActivity3.class);
                //intent.putExtra("noteid",position);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if(item.getItemId() == R.id.logout){
            //erase username from shared preferences
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes",Context.MODE_PRIVATE);
            sharedPreferences.edit().remove("username").apply();
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.addNote){
            Intent intent = new Intent(this,MainActivity3.class);
            intent.putExtra("noteid",-1);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}