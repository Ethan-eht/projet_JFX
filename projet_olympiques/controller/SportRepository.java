package org.jo.projet_olympiques.controller;

import org.jo.projet_olympiques.model.Sport;
import org.jo.projet_olympiques.model.Athlete;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SportRepository {
    private static final String FILE_NAME = "src/main/java/org/jo/projet_olympiques/dtb/disciplines.txt";
    private static final String ATHLETE_DISCIPLINES_FILE_NAME = "src/main/java/org/jo/projet_olympiques/dtb/athlete_disciplines.txt";

    public static Sport findByName(String name) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts[1].equals(name)) {
                    Sport sport = new Sport();
                    sport.setId(Integer.parseInt(parts[0]));
                    sport.setName(parts[1]);
                    return sport;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Sport> load() throws IOException {
        List<Sport> sports = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                sports.add(new Sport(id, name));
            }
        }
        return sports;
    }

    public void save(Sport sport) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(sport.getId() + "," + sport.getName());
            writer.newLine();
        }
    }

    public void update(Sport sport) throws IOException {
        List<Sport> sports = load();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Sport s : sports) {
                if (s.getId() == sport.getId()) {
                    writer.write(sport.getId() + "," + sport.getName());
                } else {
                    writer.write(s.getId() + "," + s.getName());
                }
                writer.newLine();
            }
        }
    }

    public void delete(String name) throws IOException {
        List<Sport> sports = load();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Sport s : sports) {
                if (s.getName().equals(name)) {
                    continue;
                }
                writer.write(s.getId() + "," + s.getName());
                writer.newLine();
            }
        }
    }

    public void addAthleteToSport(Sport sport, Athlete athlete) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(ATHLETE_DISCIPLINES_FILE_NAME));
        lines.add(athlete.getId() + "," + sport.getId());
        Files.write(Paths.get(ATHLETE_DISCIPLINES_FILE_NAME), lines);
    }

    public void removeAthleteFromSport(Sport sport, Athlete athlete) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(ATHLETE_DISCIPLINES_FILE_NAME));
        lines.removeIf(line -> line.equals(athlete.getId() + "," + sport.getId()));
        Files.write(Paths.get(ATHLETE_DISCIPLINES_FILE_NAME), lines);
    }


    public List<Athlete> getAthletesAssignedToSport(int sport) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get(ATHLETE_DISCIPLINES_FILE_NAME));
        List<Athlete> athletes = new ArrayList<>();
        AthleteRepository athleteRepository = new AthleteRepository();

        for (String line : lines) {
            String[] parts = line.split(",");

            if (Integer.parseInt(parts[1]) == sport) {
                Athlete athlete = athleteRepository.load().stream()
                    .filter(a -> a.getId() == Integer.parseInt(parts[0]))
                    .findFirst()
                    .orElse(null);
                if (athlete != null) {

                    athletes.add(athlete);
                }
            }
        }
        return athletes;

    }
}