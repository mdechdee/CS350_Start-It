package com.example.startit;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static java.time.LocalDateTime.*;

public class AddItemFragment extends Fragment {
    private int number = 0;
    private final ArrayList<String> tagsAdded = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        //Setup spinner for importance picking
        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.importance_level, R.layout.custom_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        //Setup onclick listener for date picker button on the layout
        Button datePickerButton = view.findViewById(R.id.datePickerButton);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        //Setup onclick listener for discard button on the layout
        Button discardButton = view.findViewById(R.id.discardButton);
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancle(v);
            }
        });

        //Setup onclick listener for confirm button on the layout
        Button confirmButton = view.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirm(v);
            }
        });

        //Setup tags input listener for adding new tags
        final ChipGroup chipGroup = view.findViewById(R.id.chipGroup);
        EditText editText = view.findViewById(R.id.inputCategories);
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
                final Chip chip =(Chip) getLayoutInflater().inflate(R.layout.custom_chip, chipGroup, false);
                chip.setText(txt);
                chip.setChipIconTintResource(R.color.chipIconTint);
                chip.setClickable(false);
                chip.setCheckable(false);
                tagsAdded.add(txt);

                chipGroup.addView(chip);
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    chipGroup.removeView(chip);
                    tagsAdded.remove(chip.getText().toString());
                    }
                });
            }
        });
    }

    public void showDatePicker(View view){
        final EditText inputDeadline = (EditText) getActivity().findViewById(R.id.inputDeadline);
        DatePickerDialog dd = new DatePickerDialog(getActivity());
        dd.setOnDateSetListener(
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    inputDeadline.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }
            }
        );
        dd.show();

    }

    public void onCancle(View view){
        getFragmentManager().popBackStack();
    }

    public void onConfirm(View view){
        String todoName;
        TextInputEditText inputTitle = getView().findViewById(R.id.titleName);
        todoName = inputTitle.getText().toString();

        LocalDateTime deadline = now();
        TextInputEditText inputDeadline = getView().findViewById(R.id.inputDeadline);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = format.parse(inputDeadline.getText().toString());
            deadline = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int difficulty = 0;
        Spinner inputImportance = getView().findViewById(R.id.spinner);
        String importanceString = inputImportance.getSelectedItem().toString();
        switch(importanceString){
            case "Highest":
                difficulty = 5; break;
            case "High":
                difficulty = 4; break;
            case "Medium":
                difficulty = 3; break;
            case "Low":
                difficulty = 2; break;
            case "Lowest":
                difficulty = 1; break;
        }

        ToDoItem toDoItem = new ToDoItem(todoName,"", tagsAdded, now() ,deadline,  Duration.ZERO, difficulty);
        ToDoFragment.addToTodoList(toDoItem);
        getFragmentManager().popBackStack();
    }

}



