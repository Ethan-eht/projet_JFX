package org.jo.projet_olympiques.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;
import org.jo.projet_olympiques.model.Event;

import java.util.List;

public class EventListController {
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event, String> eventNameColumn;
    @FXML
    private TableColumn<Event, String> sportColumn;
    @FXML
    private TableColumn<Event, String> dateColumn;
    @FXML
    private TableColumn<Event, Void> infoColumn;
    @FXML
    private TableColumn<Event, Void> editColumn;
    @FXML
    private TableColumn<Event, Void> deleteColumn;

    private EventService eventService = new EventService();

    @FXML
    public void initialize() {
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sportColumn.setCellValueFactory(new PropertyValueFactory<>("sport"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        infoColumn.setCellFactory(getButtonCellFactory("Plus d'informations", this::displayEventDetails));
        editColumn.setCellFactory(getButtonCellFactory("Modifier", this::editEvent));
        deleteColumn.setCellFactory(getButtonCellFactory("Supprimer", this::deleteEvent));

        loadEvents();
    }

    private void loadEvents() {
        List<Event> events = eventService.getAllEvents();
        eventTable.setItems(FXCollections.observableArrayList(events));
    }

    private Callback<TableColumn<Event, Void>, TableCell<Event, Void>> getButtonCellFactory(String buttonText, Consumer<Event> action) {
        return param -> new TableCell<>() {
            private final Button button = new Button(buttonText);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                        button.setOnAction(actionEvent -> {
                            Event event = getTableView().getItems().get(getIndex());
                            action.accept(event);
                        });
                    setGraphic(button);
                }
            }
        };
    }

    private void displayEventDetails(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jo/projet_olympiques/eventListDetails.fxml"));
            Parent root = loader.load();

            EventListDetailsController controller = loader.getController();
            controller.setEvent(event);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editEvent(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jo/projet_olympiques/eventListEdit.fxml"));
            Parent root = loader.load();

            EventListEditController controller = loader.getController();
            controller.setEvent(event);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteEvent(Event event) {
        eventService.deleteEvent(event.getName());
        loadEvents();
    }
}