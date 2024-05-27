package org.jo.projet_olympiques.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jo.projet_olympiques.model.Athlete;

public class AthleteEditController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField genderField;

    private Athlete athlete;
    private AthleteService athleteService = new AthleteService();

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
        nameField.setText(athlete.getName());
        countryField.setText(athlete.getCountry());
        ageField.setText(String.valueOf(athlete.getAge()));
        genderField.setText(athlete.getGender());
    }

    @FXML
    public void saveAthlete() {
        athlete.setName(nameField.getText());
        athlete.setCountry(countryField.getText());
        athlete.setAge(Integer.parseInt(ageField.getText()));
        athlete.setGender(genderField.getText());

        athleteService.updateAthlete(athlete);

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}