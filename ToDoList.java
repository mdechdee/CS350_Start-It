package com.example.myapplication;

import java.util.ArrayList;

public class ToDoList {

    /*
     * ::::::::::::::::::::::::ToDoList class index::::::::::::::::::::::::
     *
     * ::::::::::::::::::::::::Default Properties Of Classes::::::::::::::::::::::::
     *
     * attributes line = 45
     *
     * constructors line = 65
     *
     * setters line = 95
     * getters line = 121
     *
     *
     * @Override
     * toString() line = 129
     *
     * ::::::::::::::::::::::::Methods::::::::::::::::::::::::
     *
     * addAnItem(item); add a new to-do item to the list
     * containsAnItem(item); checks weather it has the item passed or not
     * deleteAnItem(item); delete the passed item from the list
     * updateLevel(); updates the level if needed.
     * getTaggedItems(tag); returns a list with all items that are tagged with the specified tag
     * getItemTitle(title); returns the item with same title, else it returns item titled "not found".
     *
     *
     *
     * */

    //attributes
    private int totalScore;
    private int accumulatedScore;
    private int level;
    private static final int PROGRESS_REQUIRED = 1000;

    private ArrayList<ToDoItem> toDoItems;


    //constructors
    public ToDoList(int totalScore, int accumulatedScore, int level, ArrayList<ToDoItem> toDoItems){
        this.totalScore = totalScore;
        this.accumulatedScore = accumulatedScore;
        this.level = level;
        this.toDoItems = toDoItems;
    }
    public ToDoList(){
        this(0,0,0,new ArrayList<ToDoItem>());
    }


    //setters
    public void setTotalScore(int totalScore){
        this.totalScore = totalScore;
    }
    public void setAccumulatedScore (int accumulatedScore){
        this.accumulatedScore = accumulatedScore;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public void setToDoItems(ArrayList<ToDoItem> list){
        this.toDoItems = list;
    }

    //getters
    public int getAccumulatedScore() {
        return accumulatedScore;
    }
    public int getTotalScore() {
        return totalScore;
    }
    public int getLevel() {
        return level;
    }

    //this getMethod() introduces information leak, if time allows we might work on it.
    public ArrayList<ToDoItem> getToDoItems() {
        return toDoItems;
    }

    public boolean addAnItem(ToDoItem newToDoItem){
        if(this.containsAnItem(newToDoItem))
            return false;

        toDoItems.add(newToDoItem);
        totalScore += newToDoItem.getDifficulatyScore();
        accumulatedScore += newToDoItem.getScoreAchieved();

        updateLevel();

        return true;
    }

    public boolean containsAnItem(ToDoItem toDoItem){
        return toDoItems.contains(toDoItem);
    }

    public boolean deleteAnItem(ToDoItem toDoItem){
        if(toDoItems.isEmpty() || !( toDoItems.contains(toDoItem) ) )
            return false;

        toDoItems.remove(toDoItem);

        return true;

    }

    public void updateLevel(){
        level /= PROGRESS_REQUIRED;
    }


    public ArrayList<ToDoItem> getTaggedItems(String tag){

        ArrayList<ToDoItem> taggedItems = new ArrayList<ToDoItem>();

        for(int i = 0 ; i < toDoItems.size() ; i++){
            if(toDoItems.get(i).isTagged(tag))
                taggedItems.add(toDoItems.get(i));
        }

        return taggedItems;
    }


    public ToDoItem getItemTitled(String title) throws Exception{


        int i;
        boolean isFound = false;

        for(i = 0 ; i < toDoItems.size() && !isFound ; i++){
            if(toDoItems.get(i).getTitle() == title)
                isFound = true;
        }

        //at call site, we need to check weather an item exists or not. by checking the title.
        if(!isFound){
            ToDoItem notFound = new ToDoItem();
            notFound.setTitle("not found");
            return notFound;
        }else{
            return toDoItems.get(i);
        }


    }


    @Override
    public String toString(){
        return "";
    }
}
