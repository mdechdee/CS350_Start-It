package com.example.startit;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ToDoItem {

    /*
    * ::::::::::::::::::::::::ToDoItem class index::::::::::::::::::::::::
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
    * isEqual(object) line = 199
    *
    * @Override
    * toString() line = 214
    *
    * ::::::::::::::::::::::::Methods::::::::::::::::::::::::
    *
    * markAsDone() ; mark the item as done.
    * isDone() ; checks weather this item is done or not.
    * addTagName(tag) ; add a new tag to the item.
    * isTagged(tag) ; checks weather this item has the specified tag
    * deleteTagName(tag) ; delete a specific tag from the item's tag list and returns that deleted item.
    * updateTimeTaken(extraTime) ; add an extra time to ( timeTaken ) to record any progress.
    * timeSpare(date) ; checks the remaining time until the due date.
    * isPassDue() ; check weather it is passed due or not.
    *
    *
    * ::::::::::::::::::::::::Requirements::::::::::::::::::::::::
    *
    * Android version 8.0 or later.
    *
    * */


    //identity of an item.
    private String title;
    private String note;
    private ArrayList<String> tags;
    private final int TAGS_CAP = 10;

    //time constraints
    private LocalDateTime dateAdded;
    private LocalDateTime dueDate;
    private Duration estimatedTime;
    private Duration timeTaken;

    //score system
    private int difficulatyScore;
    private int scoreAchieved;

    //achievement status ( is it done ? )
    private boolean isDone;


    //constructors
    public ToDoItem(){
        this("","",new ArrayList<String>(),LocalDateTime.now(),LocalDateTime.MAX, Duration.ofMinutes(0),Duration.ofSeconds(0),0,0,false);
    }

    public ToDoItem(String title , String note, ArrayList<String> tags , LocalDateTime dateAdded , LocalDateTime dueDate , Duration timeTaken , Duration estimatedTime , int difficulatyScore , int scoreAchieved , boolean isDone){
        this.title = title;
        this.note = note;
        this.tags = tags;
        this.dateAdded = dateAdded;
        this.dueDate = dueDate;
        this.timeTaken = timeTaken;
        this.estimatedTime = estimatedTime;
        this.difficulatyScore = difficulatyScore;
        this.scoreAchieved = scoreAchieved;
        this.isDone = isDone;
    }
    public ToDoItem(String title , String note, ArrayList<String> tags , LocalDateTime dateAdded , LocalDateTime dueDate , Duration estimatedTime , int difficulatyScore ){
        this(title,note,tags,dateAdded,dueDate,Duration.ofMinutes(0),estimatedTime,difficulatyScore,0,false);
    }
    public ToDoItem(String title , String note, LocalDateTime dateAdded , LocalDateTime dueDate , Duration estimatedTime , int difficulatyScore ){
        this(title,note,new ArrayList<String>(),dateAdded,dueDate,Duration.ofMinutes(0),estimatedTime,difficulatyScore,0,false);
    }
    public ToDoItem(String title , LocalDateTime dateAdded , LocalDateTime dueDate , Duration estimatedTime , int difficulatyScore ){
        this(title,"",dateAdded,dueDate,estimatedTime,difficulatyScore);
    }




    //Setters
    public void setTitle(String title){
        this.title = title;
    }
    public void setNote(String note){
        this.note = note;
    }
    public void setTags(ArrayList<String> tags){
        this.tags = tags;
    }
    public void setDateAdded(LocalDateTime dateAdded){
        this.dateAdded = dateAdded;
    }
    public void setDueDate(LocalDateTime dueDate){
        this.dueDate = dueDate;
    }
    public void setEstimatedTime(Duration estimatedTime){
        this.estimatedTime = estimatedTime;
    }
    public void setDifficulatyScore(int difficulatyScore){
        this.difficulatyScore = difficulatyScore;
    }
    public void setTimeTaken(Duration timeTaken){
        this.timeTaken = timeTaken;
    }

    //getters
    public String getTitle(){
        return title;
    }
    public String getNote(){
        return note;
    }

    public LocalDateTime getDateAdded(){
        return dateAdded;
    }

    public LocalDateTime getDueDate(){
        return dueDate;
    }

    public Duration getEstimatedTime(){
        return estimatedTime;
    }

    public Duration getTimeTaken() {
        return timeTaken;
    }

    public int getDifficulatyScore(){
        return difficulatyScore;
    }

    public int getScoreAchieved(){
        return scoreAchieved;
    }

    //methods
    public void markAsDone(){
        isDone = true;
    }
    public boolean isDone(){
        return isDone;
    }

    public void addTagName(String newTag){

        if(newTag instanceof String){
            newTag = newTag.toLowerCase();

            //potential exception (duplicate tag && full array)
            if( !(tags.contains(newTag)) && tags.size() <= TAGS_CAP )//checks existence & not exceeding the capacity
                tags.add(newTag);
        }

    }

    public boolean isTagged(String tag){

        return tags.contains(tag);//returns true if the tag is found in the list
    }

    public String deleteTagName(String deletedTag){

        //potential exception ( notFoundTag && remove from empty list )
        if(tags.remove(deletedTag))//returns true if deleted successfully
            return deletedTag;

        return "";
    }

    public void updateTimeTaken(Duration extraTime){
        timeTaken = timeTaken.plus(extraTime);//update the time taken on this item with passed argument added
    }

    public Duration timeSpare(LocalDateTime date){

        //potential exception ( passDue exception )
        if(dueDate.isBefore(date)){//if it is passed due then return 0 seconds spare time;
            return Duration.of(0,ChronoUnit.SECONDS);
        }


        return Duration.between(date,dueDate);//else it returns the spare time until due date
    }

    public boolean isPassDue(){
        return dueDate.isBefore(LocalDateTime.now());//checks if due date comes before the date of calling this function.
    }


    //this function compares to Items by title. It returns true if equal and false otherwise.
    public boolean isEqual(Object o) {

        if(this.getClass() != o.getClass()){
            return false;
        }else{
            if(this.title != ((ToDoItem)o).title )
                return false;
        }

        return true;
    }

    @Override
    public String toString(){
        return"";
    }


}
