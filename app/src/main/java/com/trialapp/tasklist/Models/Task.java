package com.trialapp.tasklist.Models;


import io.realm.RealmObject;

/**
 * Created by Muhammad on 2/25/2017.
 */

public class Task extends RealmObject {
    private String taskName;
    private boolean done;
    private String taskDescription;
    public Task(String name, String description){
        taskName = name;
        taskDescription = description;
        done = false;
    }

    public Task(){}

    public String getTaskName(){
        return taskName;
    }

    public String getTaskDescription() { return taskDescription; }

    public void changeTaskDescription(String description) {
        taskDescription = description;
    }

    public void changeTaskName(String name) {
        taskName = name;
    }

    public void done(){
        done = true;
    }

    public boolean isDone(){
        return done;
    }

    @Override
    public String toString() {
        return getTaskName();
    }

}
