package org.jo.projet_olympiques.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.jo.projet_olympiques.model.Sport;
import org.jo.projet_olympiques.controller.SportService;

import java.io.IOException;
import java.util.List;

public class SportController {
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
    private TextField sportNameField;

    @FXML
    private TextField nameField;

    @FXML
    private TableView<Sport> sportTable;

    @FXML
    private TableColumn<Sport, String> nameColumn;

    @FXML
    private TableColumn<Sport, Void> showColumn;

    @FXML
    private TableColumn<Sport, Void> editColumn;

    @FXML
    private TableColumn<Sport, Void> deleteColumn;

    private SportService sportService = new SportService();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        showColumn.setCellFactory(getButtonCellFactory("Afficher"));
        editColumn.setCellFactory(getButtonCellFactory("Modifier"));
        deleteColumn.setCellFactory(getButtonCellFactory("Supprimer"));

        loadSports();
    }

    @FXML
    public void saveSport() {
        Sport sport = new Sport();
        sport.setId(sportService.getAllSports().size() + 1);
        sport.setName(nameField.getText());

        sportService.saveSport(sport);

        nameField.clear();

        loadSports();
    }

    private void loadSports() {
        List<Sport> sports = sportService.getAllSports();
        ObservableList<Sport> observableList = FXCollections.observableArrayList(sports);
        sportTable.setItems(observableList);
    }

    private Callback<TableColumn<Sport, Void>, TableCell<Sport, Void>> getButtonCellFactory(String buttonText) {
        return param -> new TableCell<>() {
            private final Button button = new Button(buttonText);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(event -> {
                        Sport sport = getTableView().getItems().get(getIndex());
                        switch (buttonText) {
                            case "Afficher":
                                displaySportDetails(sport);
                                break;
                            case "Modifier":
                                modifySportDetails(sport);
                                break;
                            case "Supprimer":
                                sportService.deleteSport(sport.getName());
                                loadSports();
                                break;
                        }
                    });
                    setGraphic(button);
                }
            }
        };
    }

    private void displaySportDetails(Sport sport) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jo/projet_olympiques/sportDetails.fxml"));
            VBox root = loader.load();

            SportDetailsController controller = loader.getController();
            controller.setSport(sport);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifySportDetails(Sport sport) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jo/projet_olympiques/sportEdit.fxml"));
            VBox root = loader.load();

            SportEditController controller = loader.getController();
            controller.setSport(sport);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadSports();
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