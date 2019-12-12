package com.example.startit;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TimeZone;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Instant;

//  use parse() for parse
//  use compose(toDoList) for de-parse
//  save data shape
//  {
//      "totalScore" : int
//      "accumulatedScore" : int
//      "level" : int
//      "toDoItems" : []
//  single entity shape
//  {
//      "title": "title",
//      "note": "note",
//      "tags": ["tag1", "tag2", \cdots,"tag10"],
//      "dateAdded": long   // by toInstant(ZoneOffset.UTC).toEpochMilli())
//      "dueDate": long
//      "estimatedTime": long  // by toMillis()
//      "timeTaken": long
//      "difficulatyScore" : int
//      "scoreAchieved" : int
//      "isDone" : boolean
//  }

public class Importer{
    private static Context mContext;
    private static ToDoList iToDoList;
    public Importer(Context context)
    {
        mContext = context;
    }
    public static ToDoList parse(){
        try{
            InputStream internalStorage = mContext.getAssets().open("internalStorage.json");

            int length = internalStorage.available();
            byte[] buffer = new byte[length];
            internalStorage.read(buffer);
            internalStorage.close();
            JSONObject jsonStorage = new JSONObject(new String(buffer, "UTF-8"));
            ArrayList<ToDoItem> toDoItems = new ArrayList<ToDoItem>();
            ToDoList toDoList = new ToDoList(jsonStorage.getInt("totalScore"), jsonStorage.getInt("accumulatedScore"), jsonStorage.getInt("level"), toDoItems);
            JSONArray jsonListArray = jsonStorage.getJSONArray("toDoItems");

            for(int i=0; i<jsonListArray.length(); i++)
            {
                JSONObject jsonItemObject = jsonListArray.getJSONObject(i);
                ToDoItem itemObject =  jObj2Item(jsonItemObject);
                toDoList.addAnItem(itemObject);
            }
            iToDoList = toDoList;
        }
        catch (IOException ex){
            // handle exception
            // if file doesn't exists yet
            ex.printStackTrace();
            iToDoList = new ToDoList();
        }
        catch (JSONException je){
            je.printStackTrace();
            iToDoList = new ToDoList();
        }
        return iToDoList;
    }

    private static ToDoItem jObj2Item(JSONObject obj) throws JSONException{
        JSONArray tagArray = obj.getJSONArray("tags");
        ArrayList<String> tags = new ArrayList<String>();
        for(int i=0; i<tagArray.length(); i++)
        {
            tags.add(tagArray.getString(i));
        }
        ToDoItem toDoItem = new ToDoItem(obj.getString("title"), obj.getString("note"), tags, LocalDateTime.ofInstant(Instant.ofEpochMilli(obj.getLong("dateAdded")), TimeZone.getDefault().toZoneId()), LocalDateTime.ofInstant(Instant.ofEpochMilli(obj.getLong("dueDate")), TimeZone.getDefault().toZoneId()), Duration.ofMillis(obj.getLong("estimatedTime")), Duration.ofMillis(obj.getLong("timeTaken")), obj.getInt("difficulatyScore"), obj.getInt("scoreAchieved"), obj.getBoolean("isDone"));
        return toDoItem;
    }

}