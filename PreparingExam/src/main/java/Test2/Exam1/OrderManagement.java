package Test2.Exam1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderManagement extends Application {
    ComboBox<MenuItem> coffeeComboBox;
    ComboBox<MenuItem> dessertComboBox;
    ToggleGroup paymentGroup;
    public void start(Stage stage) {

        List<MenuItem> coffeeMenu = new ArrayList<>();
        coffeeMenu.add(new MenuItem("Espresso", 2.50));
        coffeeMenu.add(new MenuItem("Americano", 3.00));
        coffeeMenu.add(new MenuItem("Cappuccino", 3.50));
        coffeeMenu.add(new MenuItem("Latte", 4.00));

        List<MenuItem> dessertMenu = new ArrayList<>();
        dessertMenu.add(new MenuItem("Tiramisu", 5.00));
        dessertMenu.add(new MenuItem("Cheesecake", 4.50));
        dessertMenu.add(new MenuItem("Eclair", 3.75));
        dessertMenu.add(new MenuItem("Muffin", 3.25));

        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        Label coffeeLabel = new Label("Select Coffee:");
        coffeeComboBox = new ComboBox<>();
        coffeeComboBox.getItems().addAll(coffeeMenu);
        coffeeComboBox.setValue(coffeeMenu.get(0));

        Label dessertLabel = new Label("Select Dessert:");
        dessertComboBox = new ComboBox<>();
        dessertComboBox.getItems().addAll(dessertMenu);
        dessertComboBox.setValue(dessertMenu.get(0));

        Label paymentLabel = new Label("Select Payment Method:");
        paymentGroup = new ToggleGroup();
        RadioButton cardRadioButton = new RadioButton("Credit Card");
        cardRadioButton.setToggleGroup(paymentGroup);
        cardRadioButton.setSelected(true);
        RadioButton googlePayRadioButton = new RadioButton("Google Pay");
        googlePayRadioButton.setToggleGroup(paymentGroup);

        Label timeLabel = new Label("Select Order Time:");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        Spinner<LocalTime> timeSpinner = new Spinner<>();
        SpinnerValueFactory<LocalTime> timeValueFactory =
                new SpinnerValueFactory<LocalTime>() {
                    {
                        setConverter(new StringConverter<LocalTime>() {
                            @Override
                            public String toString(LocalTime time) {
                                if (time != null) {
                                    return time.format(timeFormatter);
                                } else {
                                    return "";
                                }
                            }

                            @Override
                            public LocalTime fromString(String string) {
                                if (string != null && !string.isEmpty()) {
                                    return LocalTime.parse(string, timeFormatter);
                                } else {
                                    return null;
                                }
                            }
                        });
                        setValue(LocalTime.now());
                    }

                    @Override
                    public void decrement(int steps) {
                        setValue(getValue().minusMinutes(steps));
                    }

                    @Override
                    public void increment(int steps) {
                        setValue(getValue().plusMinutes(steps));
                    }
                };
        timeSpinner.setValueFactory(timeValueFactory);

        Button orderButton = new Button("Place Order");
        orderButton.setOnAction(e -> {
            MenuItem coffee = coffeeComboBox.getValue();
            MenuItem dessert = dessertComboBox.getValue();
            String paymentMethod = ((RadioButton) paymentGroup.getSelectedToggle()).getText();
            LocalDate date = datePicker.getValue();
            LocalTime time = timeSpinner.getValue();

            LocalTime now = LocalTime.now();

            if (date.isBefore(LocalDate.now())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Date");
                alert.setHeaderText(null);
                alert.setContentText("Please select a date that is today or in the future.");
                alert.showAndWait();
            } else if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Time");
                alert.setHeaderText(null);
                alert.setContentText("Please select a time between 08:00 and 23:00.");
                alert.showAndWait();
            } else if (date.equals(LocalDate.now()) && time.isBefore(now)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Time");
                alert.setHeaderText(null);
                alert.setContentText("Please select a time that is later than the current time.");
                alert.showAndWait();
            } else {
                double totalPrice = coffee.getPrice() + dessert.getPrice();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Order Placed");
                alert.setHeaderText(null);
                alert.setContentText(String.format("Order details:\nCoffee: %s\nDessert: %s\nPayment: %s\nDate: %s\nTime: %s\nTotal Price: $%.2f",
                        coffee, dessert, paymentMethod, date, time.format(timeFormatter), totalPrice));
                alert.showAndWait();
            }
        });

        root.add(coffeeLabel, 0, 0);
        root.add(coffeeComboBox, 1, 0);
        root.add(dessertLabel, 0, 1);
        root.add(dessertComboBox, 1, 1);
        root.add(paymentLabel, 0, 2);
        root.add(cardRadioButton, 1, 2);
        root.add(googlePayRadioButton, 1, 3);
        root.add(timeLabel, 0, 4);
        root.add(datePicker, 1, 4);
        root.add(timeSpinner, 1, 5);
        root.add(orderButton, 1, 6);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Order Client");
        stage.show();
    }

    private void sendData() {
        try {
            Socket socket = new Socket("127.0.0.1", 9993);

            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.

            outputStream.close();
            socket.close();

        } catch (IOException | InterruptedException e) {
            displayAlert("Error creating socket.");
        }
    }
    private void displayAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("App2");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
