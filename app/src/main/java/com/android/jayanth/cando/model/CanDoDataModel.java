package com.android.jayanth.cando.model;

import java.io.Serializable;

/**
 * Created by Jayanth Devulapally on 8/7/17.
 * Data model claass to set and get data
 */

public class CanDoDataModel implements Serializable {

    private int id;
    private String title;
    private String notes;
    private String date;
    private int priority;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
