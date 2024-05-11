package com.priyanka.todoappandroid.model;

public class toDoModel {
    private String task;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
