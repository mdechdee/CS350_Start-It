package com.example.myapplication;

import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;
import org.json.JSONArray;

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

    public static ToDoList parse(){
        try{
            InputStream internalStorage = context.openFileInput("internalStorage.json");

            int length = internalStorage.available();
            byte[] buffer = new byte[length];
            internalStorage.read(buffer);
            internalStorage.close();
            JSONObject jsonStorage = new JSONObject(new String(buffer, ""));
            ArrayList<ToDoItem> toDoItems = new ArrayList<ToDoItem>();
            ToDoList toDoList = new ToDoList(jsonStorage.getInt("totalScore"), jsonStorage.getInt("accumulatedScore"), jsonStorage.getInt("level"), toDoItems);
            JSONArray jsonListArray = jsonStorage.getJSONArray("toDoItems");

            for(int i=0; i<jsonListArray.length(); i++)
            {
                JSONObject jsonItemObject = jsonListArray.getJSONObject(i);
                ToDoItem itemObject =  jObj2Item(jsonItemObject);
                addAnItem(itemObject);
            }
        }
        catch (IOException ex){
            // handle exception
            // if file doesn't exists yet
            //
        }
    }

    private ToDoItem jObj2Item(JSONObject obj){
        JSONArray tagArray = obj.getJSONArray("tags");
        ArrayList<String> tags = new ArrayList<String>();
        for(int i=0; i<tagArray.length(); i++)
        {
            tags.add(tagArray.getString(i));
        }
        ToDoItem toDoItem = new ToDoItem(obj.getString("title"), obj.getString("note"), tags, LocalDateTime.ofInstant(Instant.ofEpochMilli(obj.getLong("dateAdded")), TimeZone.getDefault().toZoneId()), LocalDateTime.ofInstant(Instant.ofEpochMilli(obj.getLong("dueDate")), TimeZone.getDefault().toZoneId()), Duration.ofMilli(obj.getLong("estimatedTime")), Duration.ofMilli(obj.getLong("timeTaken")), obj.getInt("difficulatyScore"), obj.getInt("scoreAchieved"), obj.getBoolean("isDone"));
        return toDoItem;
    }

}