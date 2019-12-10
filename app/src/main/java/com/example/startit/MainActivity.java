package com.example.startit;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.time.Duration;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
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

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ToDoFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_to_do);
        }
    }

    public static void addToTodoList(ToDoItem toDoItem)
    {
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_to_do:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ToDoFragment()).commit();
                break;
            case R.id.nav_stats:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new StatsFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}