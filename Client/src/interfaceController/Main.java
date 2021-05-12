package interfaceController;


import ClientThread.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Client c = new Client();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new AnchorPane(),600,400);
        primaryStage.setTitle("ChatApp");
        primaryStage.setScene(scene);
        setController(scene);
        primaryStage.show();
    }

    public void setController(Scene scene){

        try {
            SceneController.setScene(scene);
            FXMLLoader start = new FXMLLoader(getClass().getResource("/interface/interface.fxml"));
            FXMLLoader select = new FXMLLoader(getClass().getResource("/interface/list_user.fxml"));
            FXMLLoader message = new FXMLLoader(getClass().getResource("/interface/Message_interface.fxml"));
            SceneController.addScene("start",start.load(),start);
            SceneController.addScene("select", select.load(), select);
            SceneController.addScene("message",message.load(),message);
            SceneController.activeScene("start");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void stop() throws Exception {
        c.close();
        super.stop();
    }


}
