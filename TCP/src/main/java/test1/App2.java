package test1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App2 extends Application {
    Label message1FromApp1=new Label();
    Label message2FromApp1=new Label();
    int num;


    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        message1FromApp1.setStyle("-fx-font-size: 24px;");
        message2FromApp1.setStyle("-fx-font-size: 24px;");
        message1FromApp1.setLayoutX(200);
        message1FromApp1.setLayoutY(100);
       // message1FromApp1.setText(Integer.toString(num));
        message2FromApp1.setLayoutX(200);
        message2FromApp1.setLayoutY(200);
        root.getChildren().addAll(message1FromApp1,message2FromApp1);
        Scene scene = new Scene(root, 900, 800);
        new Thread(()->receiveData()).start();
        stage.setScene(scene);
        stage.setTitle("App2");
        stage.show();
    }

    private void receiveData(){
        try {
            ServerSocket serverSocket=new ServerSocket(9990);
            Socket socket = serverSocket.accept();
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            num = inputStream.readInt();
            String string = inputStream.readUTF();
            inputStream.close();
            socket.close();
            serverSocket.close();
            Platform.runLater(()-> message1FromApp1.setText(Integer.toString(num)));
            Platform.runLater(()-> message2FromApp1.setText(string));
        } catch (IOException e) {
            displayAlert("Error receive data!");
        }
    }

    private void displayAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
