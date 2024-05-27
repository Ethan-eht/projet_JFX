package org.jo.projet_olympiques.model;

public class Event {
    private String name;
    private int id;
    private String sport;
    private String date;

    public Event(String name, int id, String sport, String date) {
        this.name = name;
        this.id = id;
        this.sport = sport;
    }

    public Event() {
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getSport() {
        return sport;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
