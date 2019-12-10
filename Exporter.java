package com.example.myapplication;

import java.io.IOException;
import java.io.OutputStreamWriter;
import org.json.JSONObject;
import org.json.JSONArray;

public class Exporter{

    public static void compose(ToDoList toDoList){
        JSONObject obj = new JSONObject();
        obj.put("totalScore", toDoList.getTotalScore());
        obj.put("accumulatedScore", toDoList.getAccumulatedScore());
        obj.put("level", toDoList.getLevel())
        ArrayList toDoItems = toDoList.getToDoItems();
        JSONArray jsonArray = new JSONArray();
        for(int i=0; i<toDoItems.length(); i++)
        {
            jsonArray.add(item2JObj(toDoItems[i]));
        }
        obj.put("toDoItems", jsonArray);
        try{
            OutputStreamWriter internalStorage = new OutputStreamWriter(context.openFileOutput("internalStorage.json", Context.MODE_PRIVATE));
            internalStorage.write(obj.toString());
            internalStorage.close();
        }
        catch (IOException ex) {

        }
    }

    private JSONObject item2JObj(ToDoItem item){
        JSONObject obj = new JSONObject();
        obj.put("title", item.getTitle());
        obj.put("note", item.getNote());
        obj.put("dateAdded",item.getDateAdded().atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli());
        obj.put("dueDate",item.getDueDate().atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli());
        obj.put("estimatedTime", item.getEstimatedTime().toEpochMillis());
        obj.put("timeTaken", item.getEstimatedTime().toEpochMillis());
        obj.put("difficulatyScore", item.getDifficulatyScore());
        obj.put("scoreAchieved", item.getScoreAchieved());
        obj.put("isDone", item.isDone());
        return obj;
    }
}