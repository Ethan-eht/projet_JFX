package org.jo.projet_olympiques.controller;

import org.jo.projet_olympiques.model.Athlete;
import org.jo.projet_olympiques.model.Event;
import org.jo.projet_olympiques.controller.EventRepository;
import org.jo.projet_olympiques.model.Sport;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventService {

    private EventRepository EventRepository = new EventRepository();

    public void saveEvent(Event Event) {
        try {
            EventRepository.save(Event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEvent(Event Event) {
        try {
            EventRepository.update(Event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent(String name) {
        try {
            EventRepository.delete(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getAllEvents() {
        try {
            return EventRepository.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void addAthleteToEvent(Event Event, Athlete athlete) {
        try {
            EventRepository.addAthleteToEvent(Event, athlete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Athlete> getAthletesAssignedToEvent(int EventId) {
        try {
            return EventRepository.getAthletesAssignedToEvent(EventId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeAthleteFromEvent(Event Event, Athlete athlete) {
        try {
            EventRepository.removeAthleteFromEvent(Event, athlete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

