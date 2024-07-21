package com.example.notes.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Notes_table")
public class Notes {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Noid")
    private int noid;

    @ColumnInfo(name="title")
    private String title;

    @ColumnInfo(name="message")
    private String message;

    @ColumnInfo(name = "image_uri")
    private String image;

    @ColumnInfo(name="date")
    private Date date;



    public Notes() {
    }



    public Notes(String title, String message, String image,Date date) {
        this.title = title;
        this.message = message;
        this.image = image;
        this.date = date;

    }

    public int getNoid() {
        return noid;
    }

    public void setNoid(int noid) {
        this.noid = noid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}