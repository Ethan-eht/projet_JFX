package org.jo.projet_olympiques.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import org.jo.projet_olympiques.model.Result;
import org.jo.projet_olympiques.model.Athlete;
import org.jo.projet_olympiques.model.Event;

public class ResultValidateController {
    @FXML
    private TableView<Result> resultTable;
    @FXML
    private TableColumn<Result, String> athleteColumn;
    @FXML
    private TableColumn<Result, String> eventColumn;
    @FXML
    private TableColumn<Result, String> resultColumn;
    @FXML
    private TableColumn<Result, Void> validateColumn;
    @FXML
    private TableColumn<Result, Void> deleteColumn;

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

        validateColumn.setCellFactory(col -> {
            final TableCell<Result, Void> cell = new TableCell<>() {
                private final Button btn = new Button("Valider");

                {
                    btn.setOnAction(event -> {
                        Result result = getTableView().getItems().get(getIndex());
                        resultService.validateResult(result.getId());
                        // Refresh the table
                        ObservableList<Result> results = FXCollections.observableArrayList(resultService.getUnvalidatedResults());
                        resultTable.setItems(results);
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        });

        deleteColumn.setCellFactory(col -> {
            final TableCell<Result, Void> cell = new TableCell<>() {
                private final Button btn = new Button("Supprimer");

                {
                    btn.setOnAction(event -> {
                        Result result = getTableView().getItems().get(getIndex());
                        resultService.deleteResult(result.getId());
                        resultTable.getItems().remove(result);
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        });

        ObservableList<Result> results = FXCollections.observableArrayList(resultService.getUnvalidatedResults());
        resultTable.setItems(results);
    }
}