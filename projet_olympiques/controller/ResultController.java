package org.jo.projet_olympiques.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultController {
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
    public Button newResult;
    public Button validateResult;
    public Button seeResults;
    public Button seeLdb;

    @FXML
    public void initialize() {
        newResult.setOnAction(event -> openNewResult());
        validateResult.setOnAction(event -> openValidateResult());
        seeResults.setOnAction(event -> openSeeResults());
        seeLdb.setOnAction(event -> openSeeLdb());
    }

    private void openNewResult() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/jo/projet_olympiques/resultNew.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openValidateResult() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/jo/projet_olympiques/resultValidate.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openSeeResults() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/jo/projet_olympiques/resultDetails.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openSeeLdb() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/jo/projet_olympiques/resultLeaderboard.fxml"));
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