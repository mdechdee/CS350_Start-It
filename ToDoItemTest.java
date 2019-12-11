package com.example.myapplication;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ToDoItemTest {

    @Test
    public void isTagged() {
        ToDoItem testObj = new ToDoItem();
        testObj.setTags(new ArrayList<String>());
        testObj.addTagName("hello");
        assertEquals(true,testObj.isTagged("hello"));

    }

    @Test
    public void updateTimeTaken() {
        Duration beforeTest = Duration.of(10, ChronoUnit.MINUTES);

        ToDoItem test1 = new ToDoItem();
        test1.setTimeTaken(beforeTest);

        test1.updateTimeTaken(Duration.of(10,ChronoUnit.MINUTES));

        assertEquals(Duration.of(20,ChronoUnit.MINUTES),test1.getTimeTaken());

        ToDoItem test2 = new ToDoItem();

        assertEquals(Duration.of(0,ChronoUnit.MINUTES),test2.getTimeTaken());


    }

    @Test
    public void timeSpare() {
        ToDoItem testObj = new ToDoItem();

        testObj.setDueDate(LocalDateTime.of(2019,1,10,1,10));
        Duration output = testObj.timeSpare(LocalDateTime.of(2019,1,10,1,9));

        Duration expected = Duration.ofMinutes(1);

        assertEquals(expected,output);

    }

    @Test
    public void isPassDue() {
        ToDoItem test = new ToDoItem();
        test.setDueDate(LocalDateTime.of(2019,10,29,10,10));

        assertEquals(true,test.isPassDue());

    }

    @Test
    public void getTitle() {
        ToDoItem test = new ToDoItem();
        test.setTitle("test");
        assertEquals("test",test.getTitle());
    }

    @Test
    public void getNote() {
        ToDoItem test = new ToDoItem();
        test.setNote("test");
        assertEquals("test",test.getNote());
    }

    @Test
    public void getDateAdded() {
        ToDoItem test = new ToDoItem();

        LocalDateTime dateAdded = LocalDateTime.of(2000,1,1,1,1);

        test.setDateAdded(dateAdded);

        assertEquals(LocalDateTime.of(2000,1,1,1,1),test.getDateAdded());
    }

    @Test
    public void getDueDate() {
        ToDoItem test = new ToDoItem();

        LocalDateTime dueDate = LocalDateTime.of(2020,1,1,1,1);

        test.setDueDate(dueDate);

        assertEquals(LocalDateTime.of(2020,1,1,1,1),test.getDueDate());
    }

    @Test
    public void getEstimatedTime() {
        ToDoItem test = new ToDoItem();

        Duration estTime = Duration.ofMinutes(10);

        test.setEstimatedTime(estTime);

        assertEquals(Duration.ofMinutes(10),test.getEstimatedTime());
    }

    @Test
    public void getTimeTaken() {
        ToDoItem test = new ToDoItem();

        Duration timeTaken = Duration.ofMinutes(1);

        test.setTimeTaken(timeTaken);

        assertEquals(Duration.ofMinutes(1),test.getTimeTaken());
    }

    @Test
    public void getDifficulatyScore() {
        ToDoItem test = new ToDoItem();
        test.setDifficulatyScore(10);
        assertEquals(10,test.getDifficulatyScore());
    }

    @Test
    public void getScoreAchieved() {
        ToDoItem test = new ToDoItem();
        test.setScoreAchieved(1);
        assertEquals(1,test.getScoreAchieved());
    }

    @Test
    public void markAsDone() {
        ToDoItem test = new ToDoItem();
        test.markAsDone();
        assertEquals(true,test.isDone());
    }

    @Test
    public void isDone() {
        ToDoItem test = new ToDoItem();
        assertEquals(false,test.isDone());
    }

    @Test
    public void addTagName() {
        ToDoItem test = new ToDoItem();
        test.addTagName("1");
        test.addTagName("2");


        ArrayList<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");

        assertEquals(expected,test.getTags());
    }


    @Test
    public void deleteTagName() {
        ToDoItem test = new ToDoItem();
        test.addTagName("1");
        test.addTagName("2");
        test.addTagName("3");


        test.deleteTagName("3");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");

        assertEquals(expected,test.getTags());
    }

}