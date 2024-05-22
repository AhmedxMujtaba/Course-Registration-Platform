package Entities;

public class Notes {
    private int id;
    private String title;
    private String notes;
    private int lectureId; // Added lectureId attribute

    // Constructor
    public Notes(String title, String notes, int lectureId) {
        this.title = title;
        this.notes = notes;
        this.lectureId = lectureId;
    }
    public Notes(int id, String title, String notes, int lectureId) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.lectureId = lectureId;
    }

    // Getters and Setters
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

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }
}
