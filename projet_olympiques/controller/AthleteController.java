package org.jo.projet_olympiques.controller;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import org.jo.projet_olympiques.controller.AthleteService;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.jo.projet_olympiques.model.Athlete;


public class AthleteController {
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
    private TextField countryField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField genderField;
    @FXML
    private TableView<Athlete> athleteTable;
    @FXML
    private TableColumn<Athlete, String> nameColumn;
    @FXML
    private TableColumn<Athlete, Void> showColumn;
    @FXML
    private TableColumn<Athlete, Void> editColumn;
    @FXML
    private TableColumn<Athlete, Void> deleteColumn;

    private AthleteService athleteService = new AthleteService();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        showColumn.setCellFactory(getButtonCellFactory("Afficher"));
        editColumn.setCellFactory(getButtonCellFactory("Modifier"));
        deleteColumn.setCellFactory(getButtonCellFactory("Supprimer"));

        loadAthletes();
    }

    @FXML
    public void saveAthlete() {
        Athlete athlete = new Athlete();

        List<Athlete> athletes = athleteService.getAllAthletes();
        int maxId = athletes.stream()
                .mapToInt(Athlete::getId)
                .max()
                .orElse(0);

        athlete.setId(maxId + 1);
        athlete.setName(nameField.getText());
        athlete.setCountry(countryField.getText());
        athlete.setAge(Integer.parseInt(ageField.getText()));
        athlete.setGender(genderField.getText());

        athleteService.saveAthlete(athlete);

        nameField.clear();
        countryField.clear();
        ageField.clear();
        genderField.clear();

        loadAthletes();
    }

    private void loadAthletes() {
        List<Athlete> athletes = athleteService.getAllAthletes();
        ObservableList<Athlete> observableList = FXCollections.observableArrayList(athletes);
        athleteTable.setItems(observableList);
    }

    private Callback<TableColumn<Athlete, Void>, TableCell<Athlete, Void>> getButtonCellFactory(String buttonText) {
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
                        switch (buttonText) {
                            case "Afficher":
                                displayAthleteDetails(athlete);
                                break;
                            case "Modifier":
                                modifyAthleteDetails(athlete);
                                break;
                            case "Supprimer":
                                athleteService.deleteAthlete(athlete.getId());
                                loadAthletes();
                                break;
                        }
                    });
                    setGraphic(button);
                }
            }
        };
    }

    private void displayAthleteDetails(Athlete athlete) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jo/projet_olympiques/athleteDetails.fxml"));
            VBox root = loader.load();

            AthleteDetailsController controller = loader.getController();
            controller.setAthlete(athlete);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifyAthleteDetails(Athlete athlete) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jo/projet_olympiques/athleteEdit.fxml"));
            VBox root = loader.load();

            AthleteEditController controller = loader.getController();
            controller.setAthlete(athlete);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadAthletes();
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