package Test2.Exam1;

import Test1.Order;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AppForCafe extends Application {
    private TableView<Order> tableView = new TableView<>();

    @Override
    public void start(Stage stage)  {
        setupTableView();
        setupTableView();

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 600, 400);
       // new Thread(()->receiveData()).start();
        stage.setScene(scene);
        stage.setTitle("Restaurant Orders");
        stage.show();
    }

    private void setupTableView() {
        TableColumn<Order, String> coffeeColumn = new TableColumn<>("Coffee");
        coffeeColumn.setCellValueFactory(new PropertyValueFactory<>("coffee"));

        TableColumn<Order, String> dessertColumn = new TableColumn<>("Dessert");
        dessertColumn.setCellValueFactory(new PropertyValueFactory<>("dessert"));

        TableColumn<Order, String> paymentColumn = new TableColumn<>("Payment");
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("payment"));

        TableColumn<Order, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Order, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(coffeeColumn, dessertColumn, paymentColumn, timeColumn, priceColumn);
    }

//    private void receiveData() {
//        try {
//            ServerSocket serverSocket = new ServerSocket(9993);
//
//            while (true) {
//                Socket socket = serverSocket.accept();
//                new Thread(() -> {
//                    try (DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
//                        while (true) {
//                            try {
//
//
//
//                            } catch (EOFException eofException) {
//                                break;
//                            } catch (IOException ioException) {
//                                displayAlert("Data reading error");
//                            }
//                        }
//                    } catch (IOException ioException) {
//                        displayAlert("Error receiving data");
//                    }
//                }).start();
//            }
//            serverSocket.close();
//        } catch (IOException e) {
//            displayAlert("Error while receiving data, you may need to run the first program.");
//            Platform.exit();
//        }
//    }
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
