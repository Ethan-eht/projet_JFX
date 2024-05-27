package org.jo.projet_olympiques.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.jo.projet_olympiques.model.Athlete;
import org.jo.projet_olympiques.model.Event;

import java.util.List;
import java.util.function.Consumer;

public class EventListDetailsController {
    @FXML
    private Label nameLabel;
    @FXML
    private TableView<Athlete> assignedAthleteTable;
    @FXML
    private TableView<Athlete> allAthleteTable;
    @FXML
    private TableColumn<Athlete, String> athleteNameColumn;
    @FXML
    private TableColumn<Athlete, Void> removeAthleteColumn;
    @FXML
    private TableColumn<Athlete, String> allAthleteNameColumn;
    @FXML
    private TableColumn<Athlete, Void> addAthleteColumn;

    private EventService eventService = new EventService();
    private AthleteService athleteService = new AthleteService();
    private Event event;
    private int eventId;

    @FXML
    public void initialize() {
        athleteNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allAthleteNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        removeAthleteColumn.setCellFactory(getButtonCellFactory("Remove", this::removeAthlete));
        addAthleteColumn.setCellFactory(getButtonCellFactory("Add", this::addAthlete));

        loadAssignedAthletes();
        loadAllAthletes();
    }

    public void setEvent(Event event) {
        this.event = event;
        this.eventId = event.getId();
        nameLabel.setText(event.getName());
        loadAssignedAthletes();
        loadAllAthletes();
    }

    private void loadAssignedAthletes() {
        List<Athlete> athletes = eventService.getAthletesAssignedToEvent(eventId);
        ObservableList<Athlete> observableList = FXCollections.observableArrayList(athletes);
        assignedAthleteTable.setItems(observableList);
    }

    private void loadAllAthletes() {
        List<Athlete> athletes = athleteService.getAllAthletes();
        ObservableList<Athlete> observableList = FXCollections.observableArrayList(athletes);
        allAthleteTable.setItems(observableList);
    }

    @FXML
    public void addAthlete(Athlete athlete) {
        eventService.addAthleteToEvent(event, athlete);
        loadAssignedAthletes();
        loadAllAthletes();
    }

    @FXML
    public void removeAthlete(Athlete athlete) {
        eventService.removeAthleteFromEvent(event, athlete);
        loadAssignedAthletes();
        loadAllAthletes();
    }

    private Callback<TableColumn<Athlete, Void>, TableCell<Athlete, Void>> getButtonCellFactory(String buttonText, Consumer<Athlete> action) {
        return param -> new TableCell<>() {
            private final Button button = new Button(buttonText);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(event -> {
                        Athlete athlete = getTableView().getItems().get(getIndex());
                        action.accept(athlete);
                    });
                    setGraphic(button);
                }
            }
        };
    }
}