package com.example.startit;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.time.Duration;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {
    //Generate placeholder toDoList for show
    public static final ToDoList toDoList = generateData();
    private static CustomAdapter recyclerAdapter = new CustomAdapter(toDoList.getToDoItems());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        View itemListView = findViewById(R.id.itemListView);
        RecyclerView recyclerView = itemListView.findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public static void addToTodoList(ToDoItem toDoItem){
        toDoList.addAnItem(toDoItem);
        recyclerAdapter.notifyDataSetChanged();
        Log.d("D", "Size of todoList: "+ String.valueOf(toDoList.getToDoItems().size()));
    }

    public static ToDoList getMainTodoList(){
        return toDoList;
    }

    public void addItem(View view){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    private static ToDoList generateData() {
        ToDoList toDoList = new ToDoList();
        for (int i = 0; i < 2; i++) {
            toDoList.addAnItem(new ToDoItem("Temp todo", LocalDateTime.now(), LocalDateTime.now(), Duration.ZERO, 1));
        }
        return toDoList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
