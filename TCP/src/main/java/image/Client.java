package image;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import java.io.*;
import java.net.Socket;

public class Client extends Application {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9993;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Sender");

        FileChooser fileChooser = new FileChooser();
        Button sendButton = new Button("Send Image");
        sendButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                sendImage(file);
            }
        });

        VBox root = new VBox(sendButton);
        Scene scene = new Scene(root, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void sendImage(File file) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream outputStream = socket.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Image sent to server.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

