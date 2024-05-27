package org.jo.projet_olympiques.model;

public class Result {
    private int id;
    private int athleteId;
    private int eventId;
    private int result;
    private String time;
    private int valid;

    public int getId() {
        return id;
    }

    public int getAthleteId() {
        return athleteId;
    }

    public int getEventId() {
        return eventId;
    }

    public int getResult() {
        return result;
    }

    public String getTime() {
        return time;
    }

    public int getValid() {
        return valid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }
}
