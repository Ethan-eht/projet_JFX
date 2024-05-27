package org.jo.projet_olympiques.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jo.projet_olympiques.model.Sport;

public class SportEditController {
    @FXML
    private TextField nameField;


    private Sport sport;
    private SportService SportService = new SportService();

    public void setSport(Sport sport) {
        this.sport = sport;
        nameField.setText(sport.getName());
    }

    @FXML
    public void saveSport() {
        sport.setName(nameField.getText());

        SportService.updateSport(sport);

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}