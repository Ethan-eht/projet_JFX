package org.jo.projet_olympiques.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;


import java.io.IOException;

public class DashboardController {
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