package org.jo.projet_olympiques.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.jo.projet_olympiques.model.Athlete;

public class AthleteDetailsController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label genderLabel;

    public void setAthlete(Athlete athlete) {
        nameLabel.setText(athlete.getName());
        countryLabel.setText(athlete.getCountry());
        ageLabel.setText(String.valueOf(athlete.getAge()));
        genderLabel.setText(athlete.getGender());
    }
}