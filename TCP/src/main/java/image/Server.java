package image;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {

    private static final int SERVER_PORT = 9993;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Receiver");

        ImageView imageView = new ImageView();
        VBox root = new VBox(imageView);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(() -> receiveImage(imageView)).start();
    }

    private void receiveImage(ImageView imageView) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
             Socket socket = serverSocket.accept();
             InputStream inputStream = socket.getInputStream()) {

            byte[] buffer = new byte[4096];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            Image image = new Image(byteArrayInputStream);
            imageView.setImage(image);

            System.out.println("Image received from client.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

