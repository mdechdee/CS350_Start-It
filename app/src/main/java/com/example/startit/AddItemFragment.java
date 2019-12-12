package com.example.startit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.time.Duration;

import static java.time.LocalDateTime.*;

public class AddItemFragment extends Fragment {
    private int number = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.importance_level, R.layout.custom_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new SpinnerActivity());
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
        getFragmentManager().popBackStack();
    }
    public void onConfirm(View view){
        TextInputEditText inputText = getView().findViewById(R.id.titleName);
        ToDoItem toDoItem = new ToDoItem(inputText.getText().toString(), now(), now() , Duration.ZERO, 1);
        ToDoFragment.addToTodoList(toDoItem);
        getFragmentManager().popBackStack();
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



