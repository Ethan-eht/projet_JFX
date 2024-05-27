package org.jo.projet_olympiques.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jo.projet_olympiques.model.Result;
import org.jo.projet_olympiques.model.Athlete;
import org.jo.projet_olympiques.model.Event;


public class ResultDetailsController {
    @FXML
    private TableView<Result> resultTable;
    @FXML
    private TableColumn<Result, String> athleteColumn;
    @FXML
    private TableColumn<Result, String> eventColumn;
    @FXML
    private TableColumn<Result, String> resultColumn;
    @FXML
    private TableColumn<Result, String> timeColumn;
    @FXML
    private TableColumn<Result, String> validColumn;

    private ResultService resultService = new ResultService();
    private AthleteService athleteService = new AthleteService();
    private EventService eventService = new EventService();

    @FXML
    public void initialize() {
        athleteColumn.setCellValueFactory(cellData -> {
            int athleteId = cellData.getValue().getAthleteId();
            String athleteName = athleteService.getAllAthletes().stream()
                    .filter(athlete -> athlete.getId() == athleteId)
                    .map(Athlete::getName)
                    .findFirst()
                    .orElse("");
            return new SimpleStringProperty(athleteName);
        });

        eventColumn.setCellValueFactory(cellData -> {
            int eventId = cellData.getValue().getEventId();
            String eventName = eventService.getAllEvents().stream()
                    .filter(event -> event.getId() == eventId)
                    .map(Event::getName)
                    .findFirst()
                    .orElse("");
            return new SimpleStringProperty(eventName);
        });

        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        ObservableList<Result> results = FXCollections.observableArrayList(resultService.getAllResults());
        resultTable.setItems(results);

        validColumn.setCellValueFactory(cellData -> {
            int valid = cellData.getValue().getValid();
            String validStatus = valid == 1 ? "Validé" : "Non validé";
            return new SimpleStringProperty(validStatus);
        });
    }
}