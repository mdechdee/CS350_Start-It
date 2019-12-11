package com.example.startit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Duration;
import java.time.LocalDateTime;

public class ToDoFragment extends Fragment {

    public static final ToDoList toDoList = generateData();
    private static CustomAdapter recyclerAdapter = new CustomAdapter(toDoList.getToDoItems());
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_do, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //initialize your view here for use view.findViewById("your view id")
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addItem(view);
            }
        });

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
        Intent intent = new Intent(getActivity(), AddItemActivity.class);
        startActivity(intent);
    }

    private static ToDoList generateData() {
        ToDoList toDoList = new ToDoList();
        /*for (int i = 0; i < 2; i++) {
            toDoList.addAnItem(new ToDoItem("Temp todo", LocalDateTime.now(), LocalDateTime.now(), Duration.ZERO, 1));
        }*/
        return toDoList;
    }

}
