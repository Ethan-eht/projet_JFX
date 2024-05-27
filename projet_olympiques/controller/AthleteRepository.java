package org.jo.projet_olympiques.controller;

import org.jo.projet_olympiques.model.Athlete;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class AthleteRepository {
    private static final String FILE_NAME = "src/main/java/org/jo/projet_olympiques/dtb/athlete.txt";

    public void save(Athlete athlete) throws IOException {
        List<String> lines = Files.readAllLines(getFilePath());
        lines.add(athleteToString(athlete));
        Files.write(getFilePath(), lines);
    }

    public void update(Athlete athlete) throws IOException {
        List<String> lines = Files.readAllLines(getFilePath());
        lines = lines.stream()
                .map(line -> line.startsWith(String.valueOf(athlete.getId())) ? athleteToString(athlete) : line)
                .collect(Collectors.toList());
        Files.write(getFilePath(), lines);
    }

    public void delete(int id) throws IOException {
        List<String> lines = Files.readAllLines(getFilePath());
        lines.removeIf(line -> line.startsWith(String.valueOf(id)));
        Files.write(getFilePath(), lines);
    }

    private Path getFilePath() {
        return Paths.get(FILE_NAME);
    }

    private String athleteToString(Athlete athlete) {
        return athlete.getId() + "," + athlete.getName() + "," + athlete.getCountry() + "," + athlete.getAge() + "," + athlete.getGender();
    }

    public List<Athlete> load() throws IOException {
        return Files.readAllLines(getFilePath()).stream()
                .map(line -> {
                    String[] parts = line.split(",");
                    Athlete athlete = new Athlete();
                    athlete.setId(Integer.parseInt(parts[0]));
                    athlete.setName(parts[1]);
                    athlete.setCountry(parts[2]);
                    athlete.setAge(Integer.parseInt(parts[3]));
                    athlete.setGender(parts[4]);
                    return athlete;
                })
                .collect(Collectors.toList());
    }
}