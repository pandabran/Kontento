package com.example.dell.kontento;

public class TaskModel {

    int id;
    String status;
    String task;
    String type;

    public TaskModel(int id,String status, String task) {
        this.id = id;
        this.status = status;
        this.task = task;
    }

    public TaskModel(String status, String task) {
        this.status = status;
        this.task = task;
    }

    public TaskModel(int id, String status, String task, String type) {
        this.id = id;
        this.status = status;
        this.task = task;
        this.type = type;
    }

    public TaskModel(String task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
