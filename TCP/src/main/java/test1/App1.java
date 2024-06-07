package test1;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class App1 extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Button button = new Button("Click to me");
        button.setLayoutX(400);
        button.setLayoutY(30);
        button.setOnAction(e->sendData());
        root.getChildren().add(button);
        Scene scene = new Scene(root, 900, 800);
        stage.setScene(scene);
        stage.setTitle("App1");
        stage.show();
    }

    private void sendData(){
        try {
            Socket socket=new Socket("127.0.0.1",9990);
            DataOutputStream sendNumber = new DataOutputStream(socket.getOutputStream());
            sendNumber.writeInt(5);
            sendNumber.writeUTF("Hello from App1");
            sendNumber.close();
            socket.close();
        } catch (IOException e) {
            displayAlert("Error send data!");
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
