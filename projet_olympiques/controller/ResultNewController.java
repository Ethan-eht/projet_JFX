package org.jo.projet_olympiques.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.jo.projet_olympiques.model.Athlete;
import org.jo.projet_olympiques.model.Event;
import org.jo.projet_olympiques.model.Result;
import org.jo.projet_olympiques.model.Sport;

import java.io.IOException;
import java.util.List;

public class ResultNewController {
    @FXML
    private TextField resultField;
    @FXML
    private TextField timeField;
    @FXML
    private ChoiceBox<Athlete> athleteChoiceBox;
    @FXML
    private ChoiceBox<Event> eventChoiceBox;
    @FXML
    private Button saveButton;

    private ResultService resultService = new ResultService();
    private AthleteService athleteService = new AthleteService();
    private EventService eventService = new EventService();

    @FXML
public void initialize() {
    List<Event> events = eventService.getAllEvents();
    eventChoiceBox.setItems(FXCollections.observableArrayList(events));
    eventChoiceBox.setConverter(new StringConverter<Event>() {
        @Override
        public String toString(Event event) {
            return event == null ? "" : event.getName();
        }

        @Override
        public Event fromString(String string) {
            return events.stream()
                    .filter(event -> event.getName().equals(string))
                    .findFirst()
                    .orElse(null);
        }
    });

    eventChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            int eventId = newValue.getId();

            List<Athlete> athletes = eventService.getAthletesAssignedToEvent(eventId);

            athleteChoiceBox.setItems(FXCollections.observableArrayList(athletes));
            athleteChoiceBox.setConverter(new StringConverter<Athlete>() {
                @Override
                public String toString(Athlete athlete) {
                    return athlete == null ? "" : athlete.getName();
                }

                @Override
                public Athlete fromString(String string) {
                    return athletes.stream()
                            .filter(athlete -> athlete.getName().equals(string))
                            .findFirst()
                            .orElse(null);
                }
            });
        }
    });

    saveButton.setOnAction(event -> saveResult());
}

    @FXML
public void saveResult() {
        int result = Integer.parseInt(resultField.getText());
        String time = timeField.getText();
        Athlete athlete = athleteChoiceBox.getValue();
        Event event = eventChoiceBox.getValue();

        Result resultObj = new Result();

        List<Result> results = resultService.getAllResults();
        int maxId = results.stream()
                .mapToInt(Result::getId)
                .max()
                .orElse(0);
        resultObj.setId(maxId + 1);

        resultObj.setAthleteId(athlete.getId());
        resultObj.setEventId(event.getId());
        resultObj.setResult(result);
        resultObj.setTime(time);
        resultObj.setValid(0);

        resultService.saveResult(resultObj);

        resultField.clear();
        timeField.clear();
        athleteChoiceBox.getSelectionModel().clearSelection();
        eventChoiceBox.getSelectionModel().clearSelection();
    }
}