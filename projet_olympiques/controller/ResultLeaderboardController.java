package org.jo.projet_olympiques.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Map;

public class ResultLeaderboardController {
    @FXML
    private TableView<Map.Entry<String, Map<String, Integer>>> athleteLeaderboardTable;
    @FXML
    private TableView<Map.Entry<String, Map<String, Integer>>> countryLeaderboardTable;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, String> athleteColumn;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, String> countryColumn;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, String> goldColumn;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, String> silverColumn;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, String> bronzeColumn;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, String> totalColumn;

    @FXML
    public void initialize() {
        ResultService resultService = new ResultService();
        Map<String, Map<String, Integer>> athleteMedalsCount = resultService.getMedalsCountByAthlete();
        Map<String, Map<String, Integer>> countryMedalsCount = resultService.getMedalsCountByCountry();

        setTableData(athleteLeaderboardTable, athleteMedalsCount);
        setTableData(countryLeaderboardTable, countryMedalsCount);
    }

    private void setTableData(TableView<Map.Entry<String, Map<String, Integer>>> table, Map<String, Map<String, Integer>> data) {
        ObservableList<Map.Entry<String, Map<String, Integer>>> items = FXCollections.observableArrayList(data.entrySet());
        table.setItems(items);

        athleteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        goldColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getOrDefault("Or", 0))));
        silverColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getOrDefault("Argent", 0))));
        bronzeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getOrDefault("Bronze", 0))));
        totalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().values().stream().mapToInt(Integer::intValue).sum())));
    }
}