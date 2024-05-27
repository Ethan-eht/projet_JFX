package org.jo.projet_olympiques.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.jo.projet_olympiques.model.Event;
import org.jo.projet_olympiques.model.Sport;

import java.time.LocalDate;
import java.util.List;

public class EventListEditController {
    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<Sport> sportChoiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button saveButton;

    private EventService eventService = new EventService();
    private SportService sportService = new SportService();
    private Event event;

    @FXML
    public void initialize() {
        List<Sport> sports = sportService.getAllSports();
        sports.add(0, new Sport("Choisissez une discipline")); // Add default option
        sportChoiceBox.setItems(FXCollections.observableArrayList(sports));
        sportChoiceBox.setConverter(new StringConverter<Sport>() {
            @Override
            public String toString(Sport sport) {
                return sport == null ? "" : sport.getName();
            }

            @Override
            public Sport fromString(String string) {
                return sports.stream()
                        .filter(sport -> sport.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        sportChoiceBox.getSelectionModel().select(0); // Select the default option

        saveButton.setOnAction(event -> saveEvent());
    }

    public void setEvent(Event event) {
        this.event = event;
        nameField.setText(event.getName());
        sportChoiceBox.setValue(new Sport(event.getSport()));
        if (event.getDate() != null && !event.getDate().isEmpty()) {
            datePicker.setValue(LocalDate.parse(event.getDate()));
        }
    }

    @FXML
    public void saveEvent() {
        String name = nameField.getText();
        Sport sport = sportChoiceBox.getValue();
        LocalDate date = datePicker.getValue();

        event.setName(name);
        event.setSport(sport.getName());
        event.setDate(date.toString());

        eventService.updateEvent(event);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}