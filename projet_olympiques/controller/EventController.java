package org.jo.projet_olympiques.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.jo.projet_olympiques.model.Event;
import org.jo.projet_olympiques.model.Sport;

import java.io.IOException;
import java.util.List;
import org.jo.projet_olympiques.controller.SportService;

import java.time.LocalDate;

public class EventController {

    public Button eventListButton;
    @FXML
    protected void loadAthletesView(MouseEvent event) throws IOException {
        loadView("athlete.fxml", event);
    }

    @FXML
    protected void loadSportsView(MouseEvent event) throws IOException {
        loadView("sport.fxml", event);
    }

    @FXML
    protected void loadEventsView(MouseEvent event) throws IOException {
        loadView("event.fxml", event);
    }

    @FXML
    protected void loadResultsView(MouseEvent event) throws IOException {
        loadView("result.fxml", event);
    }
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

        eventListButton.setCursor(Cursor.HAND);
        eventListButton.setOnAction(event -> openEventList());
    }

    @FXML
    public void saveEvent() {
        String name = nameField.getText();
        Sport sport = sportChoiceBox.getValue();
        LocalDate date = datePicker.getValue();

        Event event = new Event();
        event.setName(name);
        event.setSport(sport.getName());
        event.setDate(date.toString());

        int maxId = eventService.getAllEvents().stream()
                .mapToInt(Event::getId)
                .max()
                .orElse(0);

        event.setId(maxId + 1);

        eventService.saveEvent(event);

        nameField.clear();
        sportChoiceBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);
    }

    private void openEventList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jo/projet_olympiques/eventList.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadView(String fxmlFile, MouseEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/org/jo/projet_olympiques/" + fxmlFile));
        stage.setScene(new Scene(root));
        stage.show();

        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }
}