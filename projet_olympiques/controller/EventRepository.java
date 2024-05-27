package org.jo.projet_olympiques.controller;

import org.jo.projet_olympiques.model.Athlete;
import org.jo.projet_olympiques.model.Event;
import org.jo.projet_olympiques.model.Sport;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class EventRepository {
    private static final String FILE_NAME = "src/main/java/org/jo/projet_olympiques/dtb/events.txt";
    private static final String FILE_NAME_ATHLETE_EVENTS = "src/main/java/org/jo/projet_olympiques/dtb/athlete_events.txt";


    public void save(Event event) throws IOException {
        List<String> lines = Files.readAllLines(getFilePath());
        lines.add(eventToString(event));
        Files.write(getFilePath(), lines);
    }

    private String eventToString(Event event) {
        return event.getId() + "," + event.getName() + "," + event.getSport() + "," + event.getDate();
    }

    private Path getFilePath() {
        return Paths.get(FILE_NAME);
    }

    public void update(Event event) throws IOException {
        List<String> lines = Files.readAllLines(getFilePath());
        lines = lines.stream()
                .map(line -> line.startsWith(String.valueOf(event.getId())) ? eventToString(event) : line)
                .collect(Collectors.toList());
        Files.write(getFilePath(), lines);
    }

    public void delete(String name) throws IOException {
        List<String> lines = Files.readAllLines(getFilePath());
        lines.removeIf(line -> line.split(",")[1].equals(name));
        Files.write(getFilePath(), lines);
    }


    public List<Event> load() throws IOException {
        List<Event> events = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String sport = parts[2];
                String date = parts[3];

                events.add(new Event(name, id, sport, date));
            }
        }
        return events;
    }


    public void addAthleteToEvent(Event event, Athlete athlete) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME_ATHLETE_EVENTS));
        lines.add(event.getId() + "," + athlete.getId());
        Files.write(Paths.get(FILE_NAME_ATHLETE_EVENTS), lines);

    }

    public List<Athlete> getAthletesAssignedToEvent(int eventId) throws IOException {
          List<String> lines = Files.readAllLines(Paths.get(FILE_NAME_ATHLETE_EVENTS));
        return lines.stream()
                .filter(line -> Integer.parseInt(line.split(",")[0]) == eventId)
                .map(line -> {
                    try {
                        return new AthleteRepository().load().stream()
                                .filter(athlete -> athlete.getId() == Integer.parseInt(line.split(",")[1]))
                                .findFirst()
                                .get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());

    }





    public void removeAthleteFromEvent(Event event, Athlete athlete) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME_ATHLETE_EVENTS));
        lines.removeIf(line -> line.equals(event.getId() + "," + athlete.getId()));
        Files.write(Paths.get(FILE_NAME_ATHLETE_EVENTS), lines);
    }


}
