package org.jo.projet_olympiques.controller;

import org.jo.projet_olympiques.model.Result;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ResultRepository {
    private static final String FILE_NAME = "src/main/java/org/jo/projet_olympiques/dtb/results.txt";

    public void save(Result result) throws IOException {
        List<String> lines = Files.readAllLines(getFilePath());
        lines.add(resultToString(result));
        Files.write(getFilePath(), lines);
    }

    public void update(Result result) throws IOException {
        List<String> lines = Files.readAllLines(getFilePath());
        lines = lines.stream()
                .map(line -> line.startsWith(String.valueOf(result.getId())) ? resultToString(result) : line)
                .collect(Collectors.toList());
        Files.write(getFilePath(), lines);
    }

    public void delete(int id) throws IOException {
        List<String> lines = Files.readAllLines(getFilePath());
        lines.removeIf(line -> Integer.parseInt(line.split(",")[0]) == id);
        Files.write(getFilePath(), lines);
    }

    private Path getFilePath() {
        return Paths.get(FILE_NAME);
    }

    private String resultToString(Result result) {
        return result.getId() + "," + result.getAthleteId() + "," + result.getEventId() + "," + result.getResult() + "," + result.getTime() + "," + result.getValid();
    }

    public List<Result> load() throws IOException {
        return Files.readAllLines(getFilePath()).stream()
                .map(line -> {
                    String[] parts = line.split(",");
                    Result result = new Result();
                    result.setId(Integer.parseInt(parts[0]));
                    result.setAthleteId(Integer.parseInt(parts[1]));
                    result.setEventId(Integer.parseInt(parts[2]));
                    result.setResult(Integer.parseInt(parts[3]));
                    result.setTime(parts.length > 4 ? parts[4] : null);
                    result.setValid(parts.length > 5 ? Integer.parseInt(parts[5]) : 0);
                    return result;
                })
                .collect(Collectors.toList());
    }
}