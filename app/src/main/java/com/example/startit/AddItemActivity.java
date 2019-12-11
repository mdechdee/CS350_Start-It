package com.example.startit;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.time.Duration;

import static java.time.LocalDateTime.*;

public class AddItemActivity extends AppCompatActivity {
    private int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.importance_level, R.layout.custom_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new SpinnerActivity());

        final ChipGroup chipGroup = findViewById(R.id.chipGroup);
        EditText editText = findViewById(R.id.inputCategories);
        editText.setOnEditorActionListener( new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                CharSequence txtVal = v.getText();
                if(txtVal != null && txtVal.toString() != "") {
                    addChipToGroup(v.getText().toString(), chipGroup);
                    v.setText("");
                    return true;
                }
                return false;
            }

            private void addChipToGroup(String txt, final ChipGroup chipGroup) {
                final Chip chip = new Chip(AddItemActivity.this);
                chip.setText(txt);
                chip.setChipIconTintResource(R.color.chipIconTint);

                chip.setClickable(false);
                chip.setCheckable(false);
                chipGroup.addView(chip);
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chipGroup.removeView(chip);
                    }
                });
            }
        });


    }
    public void onCancle(View view){
        finish();
    }
    public void onConfirm(View view){
        TextInputEditText inputText = findViewById(R.id.titleName);
        ToDoItem toDoItem = new ToDoItem(inputText.getText().toString(), now(), now() , Duration.ZERO, 1);
        ToDoFragment.addToTodoList(toDoItem);
        finish();
    }

    private class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            Log.d("D", Integer.toString(pos));
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}



