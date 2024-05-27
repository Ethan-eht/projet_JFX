package org.jo.projet_olympiques.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.jo.projet_olympiques.model.Athlete;
import org.jo.projet_olympiques.model.Sport;

import java.util.List;
import java.util.function.Consumer;

public class SportDetailsController {
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

    private Sport sport;
    private SportService sportService = new SportService();
    private AthleteService athleteService = new AthleteService();
    private int sportId;

    @FXML
    public void initialize() {
        athleteNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allAthleteNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        removeAthleteColumn.setCellFactory(getButtonCellFactory("Remove", this::removeAthlete));
        addAthleteColumn.setCellFactory(getButtonCellFactory("Add", this::addAthlete));

        loadAssignedAthletes();
        loadAllAthletes();
    }

    public void setSport(Sport sport) {
        this.sport = sport;
        this.sportId = sport.getId();
        nameLabel.setText(sport.getName());
        loadAssignedAthletes();
        loadAllAthletes();
    }

    private void loadAssignedAthletes() {
        List<Athlete> athletes = sportService.getAthletesAssignedToSport(sportId);
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
        sportService.addAthleteToSport(sport, athlete);
        loadAssignedAthletes();
        loadAllAthletes();
    }

    @FXML
    public void removeAthlete(Athlete athlete) {
        sportService.removeAthleteFromSport(sport, athlete);
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