package com.example.startit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;

public class AddItemActivity extends AppCompatActivity {
    private int number = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Intent intent = getIntent();
    }
    public void onCancle(View view){
        finish();
    }
    public void onConfirm(View view){
        TextInputEditText inputText = findViewById(R.id.titleName);
        ToDoItem toDoItem = new ToDoItem(inputText.getText().toString(), now(), now() , Duration.ZERO, 1);
        MainActivity.addToTodoList(toDoItem);
        finish();
    }
}
