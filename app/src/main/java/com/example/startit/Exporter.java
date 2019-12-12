package com.example.startit;

import java.util.ArrayList;
import java.util.TimeZone;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Instant;

public class Exporter{
    private static ToDoList iToDoList;
    public Exporter(){}
    public static void compose(ToDoList toDoList){
        try{
            JSONObject obj = new JSONObject();
            obj.put("totalScore", toDoList.getTotalScore());
            obj.put("accumulatedScore", toDoList.getAccumulatedScore());
            obj.put("level", toDoList.getLevel());
            ArrayList<ToDoItem> toDoItems = toDoList.getToDoItems();
            JSONArray jsonArray = new JSONArray();
            for(int i=0; i<toDoItems.size(); i++)
            {
                jsonArray.put(item2JObj(toDoItems.get(i)));
            }
            obj.put("toDoItems", jsonArray);

            OutputStreamWriter internalStorage = new OutputStreamWriter(StaticContext.getAppContext().openFileOutput("internalStorage.json", Context.MODE_PRIVATE));
            internalStorage.write(obj.toString());
            internalStorage.close();
        }
        catch (IOException ex) {

        }
        catch (JSONException je) {

        }
    }

    private static JSONObject item2JObj(ToDoItem item) throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("title", item.getTitle());
        obj.put("note", item.getNote());
        obj.put("dateAdded",item.getDateAdded().atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli());
        obj.put("dueDate",item.getDueDate().atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli());
        obj.put("estimatedTime", item.getEstimatedTime().toMillis());
        obj.put("timeTaken", item.getTimeTaken().toMillis());
        obj.put("difficulatyScore", item.getDifficulatyScore());
        obj.put("scoreAchieved", item.getScoreAchieved());
        obj.put("isDone", item.isDone());
        return obj;
    }
}