package Entities;

public class Notes {
    private String title;
    private String notes;

    public Notes(String title, String notes) {
        this.title = title;
        this.notes = notes;
    }

    public void addNotes(String notes) {
        this.notes = notes;
    }

    public void removeNotes() {
        this.notes = null;
    }

    public String getNotes() {
        return notes;
    }

    public String getTitle() {
        return title;
    }
}
