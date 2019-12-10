package com.example.startit;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class StatsCalc {

    /*::::::::::::::::::::::::StatsCalc class index::::::::::::::::::::::::
    * this class has a set of static methods to calculate the stats of a given list.
    * you can call a method as follows:
    *               StatsCalc.methodName(parameters)
    *
    *
    * ::::::::::::::::::::::::Methods::::::::::::::::::::::::
    *
    * calculateAverageTimeSpentInTag(list, tag); returns how much time spent in a single given tag.
    * getTimeSpentInAMonthAgo(list); gets the timeTaken of all items that are aged less/equal a month.
    * getTimeSpentInAWeekAgo(list); gets the timeTaken of all items that are aged less/equal a week.
    * getTimeSpentInTheApp(list); gets the timeTaken of all items.
    * getMostTimeConsumingTag(list,tag); (will implement if time enough)
    * getLeastTimeConsumingTag(list,tag); (will implement if time enough)
    * getHighestScoreAchievedItem(list); returns which item has the highest score achieved.
    * getLowestScoreAchievedItem(list); returns which item has the lowest score achieved.
    *
    * */

    private static final Duration WEEK_DURATION = Duration.ofDays(7);
    private static final Duration MONTH_DURATION = Duration.ofDays(30);


    public static Duration calculateAverageTimeSpentInTag(ToDoList list, String tag){
        ArrayList<ToDoItem> taggedItems = list.getTaggedItems(tag);
        Duration total = Duration.ofMinutes(0);

        if(taggedItems.isEmpty())
            return total;//the same as = Duration.ofMinutes(0)

        for(int i = 0 ; i < taggedItems.size() ; i++) {
            Duration timeTakenByAnItem = taggedItems.get(i).getTimeTaken();
            total.plus(timeTakenByAnItem);
        }
        return total.dividedBy(taggedItems.size());
    }

    public static Duration getTimeSpentInAMonthAgo(ToDoList list){
        Duration total = Duration.ofMinutes(0);
        ArrayList<ToDoItem> items = list.getToDoItems();

        if(items.isEmpty())
            return total;

        LocalDateTime currentDate = LocalDateTime.now();

        for(int i = 0 ; i < items.size() ; i++){
            ToDoItem examinedItem = items.get(i);
            if(Duration.between(examinedItem.getDateAdded() , currentDate).compareTo( MONTH_DURATION ) <= 0){
                total.plus(examinedItem.getTimeTaken());
            }
        }

        return total;
    }
    public static Duration getTimeSpentInAWeekAgo(ToDoList list){
        Duration total = Duration.ofMinutes(0);
        ArrayList<ToDoItem> items = list.getToDoItems();

        if(items.isEmpty())
            return total;

        LocalDateTime currentDate = LocalDateTime.now();

        for(int i = 0 ; i < items.size() ; i++){
            ToDoItem examinedItem = items.get(i);
            if(Duration.between(examinedItem.getDateAdded() , currentDate).compareTo( WEEK_DURATION ) <= 0){
                total.plus(examinedItem.getTimeTaken());
            }
        }

        return total;
    }

    public static Duration getTimeSpentInTheApp(ToDoList list){
        Duration total = Duration.ofMinutes(0);
        ArrayList<ToDoItem> items = list.getToDoItems();

        if(items.isEmpty())
            return total;


        for(int i = 0 ; i < items.size() ; i++){
            ToDoItem examinedItem = items.get(i);
            total.plus(examinedItem.getTimeTaken());
        }

        return total;
    }


    public static ToDoItem getHighestScoreAchievedItem(ToDoList list) throws Exception{
        ArrayList<ToDoItem> items = list.getToDoItems();

        if(items.isEmpty())
            throw new Exception("ERROR: the list given is empty.");

        ToDoItem max = items.get(0);

        for(int i = 1; i < items.size() ; i++){
            ToDoItem examinedItem = items.get(i);
            if(max.getScoreAchieved() < examinedItem.getScoreAchieved())
                max = examinedItem;
        }

        return max;
    }

    public static ToDoItem getLowestScoreAchievedItem(ToDoList list) throws Exception{
        ArrayList<ToDoItem> items = list.getToDoItems();

        if(items.isEmpty())
            throw new Exception("ERROR: the list given is empty.");

        ToDoItem min = items.get(0);

        for(int i = 1; i < items.size() ; i++){
            ToDoItem examinedItem = items.get(i);
            if(min.getScoreAchieved() > examinedItem.getScoreAchieved())
                min = examinedItem;
        }

        return min;
    }



}
