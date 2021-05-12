package interfaceController;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class SceneController {
    private static HashMap<String, Pane> sceneMap = new HashMap<>();
    private static HashMap<String, FXMLLoader> loader = new HashMap<>();
    public static Scene scene;

    public static void addScene(String name, Pane pane, FXMLLoader load){
        sceneMap.put(name,pane);
        loader.put(name,load);
    }

    public static void activeScene(String name){
        Platform.runLater(() -> {
            scene.setRoot(sceneMap.get(name));
        });
    }

    public static void setScene(Scene sc) {
        scene = sc;
    }

    public static FXMLLoader getLoader(String name){
        return loader.get(name);
    }
}
