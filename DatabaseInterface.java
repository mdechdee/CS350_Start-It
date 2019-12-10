package com.example.myapplication;

import java.io.FileWriter;
import java.io.IOException;

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


public DatabaseInterface{
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
    private Importer{
        public ToDoList parse(){
            try{
                InputStream internalStorage = context.openFileInput("internalStorage.txt");

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
    }
    private Exporter{
        public void compose(ToDoList toDoList){
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
            catch (IOException ex){

            }
        }
    }

}