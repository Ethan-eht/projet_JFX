package org.jo.projet_olympiques.controller;

import org.jo.projet_olympiques.model.Athlete;
import org.jo.projet_olympiques.model.Sport;
import org.jo.projet_olympiques.controller.SportRepository;
import org.jo.projet_olympiques.model.Athlete;

import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class SportService {
    private SportRepository sportRepository = new SportRepository();

    public void saveSport(Sport sport) {
        try {
            sportRepository.save(sport);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSport(Sport sport) {
        try {
            sportRepository.update(sport);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteSport(String name) {
        try {
            sportRepository.delete(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Sport> getAllSports() {
        try {
            return sportRepository.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addAthleteToSport(Sport sport, Athlete athlete) {
        try {
            sportRepository.addAthleteToSport(sport, athlete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Athlete> getAthletesAssignedToSport(int sportId) {
        try {
            return sportRepository.getAthletesAssignedToSport(sportId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void removeAthleteFromSport(Sport sport, Athlete athlete) {
        try {
            sportRepository.removeAthleteFromSport(sport, athlete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}