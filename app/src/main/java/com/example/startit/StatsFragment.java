package com.example.startit;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;


public class StatsFragment extends Fragment {
    ToDoList toDoList = ToDoFragment.toDoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        this.initializeGraph();

        TextView avgRelativeTime = view.findViewById(R.id.avgRelativeTime);
        TextView timeToday = view.findViewById(R.id.timeToday);
        TextView timeLastSevenDays = view.findViewById(R.id.timeLastSevenDays);
        TextView avgTimePerDay = view.findViewById(R.id.avgTimePerDay);

        timeLastSevenDays.setText(StatsCalc.getTimeSpentInAWeekAgo(toDoList).toString());
        //avgTimePerDay.setText(StatsCalc.getTimeSpentInTheApp(toDoList).dividedBy(toDoList.getToDoItems().size()).toString());

    }

    private void initializeGraph(){
        LineChart chart = (LineChart) getActivity().findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<Entry>();
        Duration totalTime = Duration.ZERO;
        // This is to create graph of X-axis as duedate (day) for each todos and Y-axis as accumulated estimatedTime
        for (ToDoItem toDoItem : toDoList.getToDoItems()) {
            // turn toDoItems into Entry objects
            totalTime = totalTime.plus(toDoItem.getEstimatedTime());
            entries.add(new Entry((int) (toDoItem.getDueDate().toEpochSecond(ZoneOffset.UTC) / 86400), totalTime.toHours()));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(R.color.colorPrimaryWhite);
        dataSet.setFillColor(R.color.colorPrimaryWhite);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setFillAlpha(255);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(true);
        dataSet.setLineWidth(3.0f);
        dataSet.setDrawCircles(false);

        YAxis y = chart.getAxisLeft();
        y.setLabelCount(6, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        XAxis x = chart.getXAxis();
        x.setLabelCount(6, false);
        x.setTextColor(Color.WHITE);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setAxisLineColor(Color.WHITE);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.setDrawGridBackground(false);
        chart.invalidate(); // refresh
    }
}
