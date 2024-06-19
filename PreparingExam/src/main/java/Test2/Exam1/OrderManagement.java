package Test2.Exam1;

import javafx.application.Application;
import javafx.application.Platform;
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
    private Customer customer;
    private ComboBox<MenuItem> coffeeComboBox;
    private ComboBox<MenuItem> dessertComboBox;
    private DatePicker datePicker;
    private Spinner<LocalTime> timeSpinner;
    private ToggleGroup paymentGroup;

    @Override
    public void start(Stage stage) {
        Random random = new Random();
        int indexCustomer = random.nextInt(256);
        customer = new Customer(indexCustomer);

        List<MenuItem> coffeeMenu = Arrays.asList(
                new MenuItem("Espresso", 2.50),
                new MenuItem("Americano", 3.00),
                new MenuItem("Cappuccino", 3.50),
                new MenuItem("Latte", 4.00)
        );

        List<MenuItem> dessertMenu = Arrays.asList(
                new MenuItem("Tiramisu", 5.00),
                new MenuItem("Cheesecake", 4.50),
                new MenuItem("Eclair", 3.75),
                new MenuItem("Muffin", 3.25)
        );

        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        Label coffeeLabel = new Label("Select Coffee:");
        coffeeComboBox = new ComboBox<>();
        coffeeComboBox.getItems().addAll(coffeeMenu);
        coffeeComboBox.setValue(coffeeMenu.get(0));

        // Custom cell factory to show item names with prices
        coffeeComboBox.setCellFactory(lv -> new ListCell<MenuItem>() {
            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        coffeeComboBox.setButtonCell(new ListCell<MenuItem>() {
            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });

        Label dessertLabel = new Label("Select Dessert:");
        dessertComboBox = new ComboBox<>();
        dessertComboBox.getItems().addAll(dessertMenu);
        dessertComboBox.setValue(dessertMenu.get(0));

        // Custom cell factory to show item names with prices
        dessertComboBox.setCellFactory(lv -> new ListCell<MenuItem>() {
            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        dessertComboBox.setButtonCell(new ListCell<MenuItem>() {
            @Override
            protected void updateItem(MenuItem item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });

        Label paymentLabel = new Label("Select Payment Method:");
        paymentGroup = new ToggleGroup();
        RadioButton cardRadioButton = new RadioButton("Credit Card");
        cardRadioButton.setToggleGroup(paymentGroup);
        cardRadioButton.setSelected(true);
        RadioButton googlePayRadioButton = new RadioButton("Google Pay");
        googlePayRadioButton.setToggleGroup(paymentGroup);

        Label dateLabel = new Label("Select Order Date:");
        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        Label timeLabel = new Label("Select Order Time:");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        timeSpinner = new Spinner<>();
        SpinnerValueFactory<LocalTime> timeValueFactory =
                new SpinnerValueFactory<LocalTime>() {
                    {
                        setConverter(new StringConverter<LocalTime>() {
                            @Override
                            public String toString(LocalTime time) {
                                return time != null ? time.format(timeFormatter) : "";
                            }

                            @Override
                            public LocalTime fromString(String string) {
                                return string != null && !string.isEmpty() ? LocalTime.parse(string, timeFormatter) : null;
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
        orderButton.setOnAction(e -> validateAndSendOrder());

        root.add(coffeeLabel, 0, 0);
        root.add(coffeeComboBox, 1, 0);
        root.add(dessertLabel, 0, 1);
        root.add(dessertComboBox, 1, 1);
        root.add(paymentLabel, 0, 2);
        root.add(cardRadioButton, 1, 2);
        root.add(googlePayRadioButton, 1, 3);
        root.add(dateLabel, 0, 4);
        root.add(datePicker, 1, 4);
        root.add(timeLabel, 0, 5);
        root.add(timeSpinner, 1, 5);
        root.add(orderButton, 1, 6);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Order Client");
        stage.show();
    }

    private void validateAndSendOrder() {
        LocalDate date = datePicker.getValue();
        LocalTime time = timeSpinner.getValue();
        LocalTime now = LocalTime.now();

        if (date.isBefore(LocalDate.now())) {
            showAlert("Invalid Date", "Please select a date that is today or in the future.");
        } else if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0))) {
            showAlert("Invalid Time", "Please select a time between 08:00 and 23:00.");
        } else if (date.equals(LocalDate.now()) && time.isBefore(now)) {
            showAlert("Invalid Time", "Please select a time that is later than the current time.");
        } else {
            new Thread(this::sendData).start();
        }
    }

    private void sendData() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try (Socket socket = new Socket("127.0.0.1", 9993);
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

            customer.incrementCountOrders();
            int discount = calculateDiscount(customer.getCountOrders());
            customer.setDiscount(discount);

            MenuItem coffee = coffeeComboBox.getValue();
            MenuItem dessert = dessertComboBox.getValue();
            String paymentMethod = ((RadioButton) paymentGroup.getSelectedToggle()).getText();
            LocalDate date = datePicker.getValue();
            LocalTime time = timeSpinner.getValue();
            double totalPrice = coffee.getPrice() + dessert.getPrice();
            if (customer.getDiscount() > 0) {
                totalPrice *= (1 - customer.getDiscount() / 100.0);
            }

            outputStream.writeInt(customer.getUniqueNumber());
            outputStream.writeInt(customer.getCountOrders());
            outputStream.writeUTF(coffee.getName());
            outputStream.writeUTF(dessert.getName());
            outputStream.writeUTF(paymentMethod);
            outputStream.writeUTF(date.toString());
            outputStream.writeUTF(time.toString());
            outputStream.writeDouble(totalPrice);

            // Creating final variables for use in the lambda
            final int finalUniqueNumber = customer.getUniqueNumber();
            final int finalCountOrders = customer.getCountOrders();
            final int finalDiscount = customer.getDiscount();
            final String finalCoffeeName = coffee.getName();
            final String finalDessertName = dessert.getName();
            final String finalPaymentMethod = paymentMethod;
            final LocalDate finalDate = date;
            final String finalTime = time.format(timeFormatter);
            final double finalTotalPrice = totalPrice;

            // Show alert on the FX Application Thread
            Platform.runLater(() -> showAlert("Order Placed", String.format(
                    "Order details:\nCustomer Number: %d\nOrder Count: %d\nDiscount: %d%%\nCoffee: %s\nDessert: %s\nPayment: %s\nDate: %s\nTime: %s\nTotal Price: $%.2f",
                    finalUniqueNumber, finalCountOrders, finalDiscount, finalCoffeeName, finalDessertName, finalPaymentMethod, finalDate, finalTime, finalTotalPrice
            )));

        } catch (IOException e) {
            Platform.runLater(() -> showAlert("Error", "Error creating socket."));
        }
    }


    private int calculateDiscount(int orderCount) {
        if (orderCount > 14) {
            return 50;
        } else if (orderCount > 9) {
            return 30;
        } else if (orderCount > 4) {
            return 15;
        }
        return 0;
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
