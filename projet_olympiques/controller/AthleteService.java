package org.jo.projet_olympiques.controller;

import org.jo.projet_olympiques.model.Athlete;
import org.jo.projet_olympiques.controller.AthleteRepository;

import java.io.IOException;
import java.util.List;

public class AthleteService {
    private AthleteRepository athleteRepository = new AthleteRepository();

    public void saveAthlete(Athlete athlete) {
        try {
            athleteRepository.save(athlete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAthlete(Athlete athlete) {
        try {
            athleteRepository.update(athlete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAthlete(int id) {
        try {
            athleteRepository.delete(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Athlete> getAllAthletes() {
        try {
            return athleteRepository.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Athlete getAthleteById(int id) {
    return getAllAthletes().stream()
            .filter(athlete -> athlete.getId() == id)
            .findFirst()
            .orElse(null);
}

}