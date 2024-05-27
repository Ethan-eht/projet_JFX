package org.jo.projet_olympiques.controller;

import org.jo.projet_olympiques.model.Result;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ResultService {
    private ResultRepository resultRepository = new ResultRepository();
    private AthleteService athleteService = new AthleteService();


    public void saveResult(Result result) {
        try {
            resultRepository.save(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateResult(Result result) {
        try {
            resultRepository.update(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteResult(int id) {
        try {
            resultRepository.delete(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Result> getAllResults() {
        try {
            return resultRepository.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void validateResult(int id) {
        try {
            Result result = resultRepository.load().stream()
                    .filter(r -> r.getId() == id)
                    .findFirst()
                    .orElse(null);
            if (result != null) {
                result.setValid(1);
                resultRepository.update(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Result> getUnvalidatedResults() {
        try {
            return resultRepository.load().stream()
                    .filter(r -> r.getValid() == 0)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Result> getResultsByAthlete(int athleteId) {
        try {
            return resultRepository.load().stream()
                    .filter(r -> r.getAthleteId() == athleteId)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Result> getResultsByEvent(int eventId) {
        try {
            return resultRepository.load().stream()
                    .filter(r -> r.getEventId() == eventId)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMedalName(int result) {
        switch (result) {
            case 1:
                return "Or";
            case 2:
                return "Argent";
            case 3:
                return "Bronze";
            default:
                return "No Medal";
        }
    }

    public Map<String, Map<String, Integer>> getMedalsCountByAthlete() {
        return getAllResults().stream()
                .collect(Collectors.groupingBy(result -> athleteService.getAthleteById(result.getAthleteId()).getName(),
                        Collectors.groupingBy(result -> getMedalName(result.getResult()), Collectors.summingInt(e -> 1))));
    }

    public Map<String, Map<String, Integer>> getMedalsCountByCountry() {
        return getAllResults().stream()
                .collect(Collectors.groupingBy(result -> athleteService.getAthleteById(result.getAthleteId()).getCountry(),
                        Collectors.groupingBy(result -> getMedalName(result.getResult()), Collectors.summingInt(e -> 1))));
    }

    public List<Result> getLatestResults() {
        try {
            return resultRepository.load().stream()
                    .sorted((r1, r2) -> r2.getId() - r1.getId())
                    .limit(5)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}