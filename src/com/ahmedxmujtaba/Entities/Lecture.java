package com.ahmedxmujtaba.Entities;

import java.util.ArrayList;
import java.util.List;

public class Lecture {
    private int id;
    private int courseId;
    private String title;

    public Lecture(int courseId, String title) {
        this.courseId = courseId;
        this.title = title;
    }
    public Lecture(int id ,int courseId, String title) {
        this.courseId = courseId;
        this.title = title;
        this.id = id;
    }


    // Getters and setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
