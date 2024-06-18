import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppForRestaurant extends Application {
    private static final int PORT = 9993;
    private TableView<Order> tableView = new TableView<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void start(Stage stage) {
        setupTableView();

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Restaurant Orders");
        stage.show();

        startServer();
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

    private void startServer() {
        executorService.submit(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    executorService.submit(new ClientHandler(socket, this));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void addOrder(Order order) {
        Platform.runLater(() -> tableView.getItems().add(order));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
