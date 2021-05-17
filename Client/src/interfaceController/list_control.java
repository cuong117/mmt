package interfaceController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

public class list_control implements Initializable {

    public ListView list_user;
    public TextField search_receiver;
    private ObservableList<String> user;
    private List<String> allUser = new ArrayList<>();

    public void back(){
        Alert confim = new Alert(Alert.AlertType.CONFIRMATION);
        confim.setTitle("Thông Báo");
        confim.setHeaderText("Bạn sẽ đăng xuất.");
        confim.setContentText("Bạn có muốn tiếp tục");
        ButtonType accept = new ButtonType("có", ButtonBar.ButtonData.YES);
        ButtonType deni = new ButtonType("không", ButtonBar.ButtonData.NO);
        confim.getButtonTypes().setAll(accept,deni);
        Optional<ButtonType> result = confim.showAndWait();
        if(result.get().getButtonData() == ButtonBar.ButtonData.YES){
            SceneController.activeScene("start");
            Main.c.sendMessage("@ClientLogout");
            Main.c.setUserName(null);
        } else if(result.get().getButtonData() == ButtonBar.ButtonData.NO) {
            confim.close();
        }
    }

    public void addList(){
        Platform.runLater(() -> user.setAll(allUser));
    }

    public void createList(String source){
        allUser.clear();
        Collections.addAll(allUser,source.split("/"));
        if(Main.c.getUserName() != null)
            allUser.remove(Main.c.getUserName());
        if(search_receiver.getText().trim().equals(""))
            addList();
    }

    public void select(){
        if(!list_user.getSelectionModel().getSelectedItems().isEmpty()){
            MessageController messageController = SceneController.getLoader("message").getController();
            String nameReceiver = list_user.getSelectionModel().getSelectedItem().toString();
            messageController.setReceiver(nameReceiver);
            messageController.setVbox();
            Main.c.sendMessage("@ClientReceive/" + nameReceiver);
            SceneController.activeScene("message");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = FXCollections.observableArrayList();
        list_user.setItems(user);
        list_user.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        search_receiver.textProperty().addListener(((observableValue, s, newValue) ->  {
            newValue = newValue.trim().toLowerCase();
            if(!newValue.equals("")) {
                for (int i = 0; i < allUser.size(); i++) {
                    if (!allUser.get(i).toLowerCase().contains(newValue)) {
                        if (user.size() == 1 && !user.get(0).equals("Người Dùng Không Tồn Tại")) {
                            user.add("Người Dùng Không Tồn Tại");
                            user.remove(allUser.get(i));
                            System.out.println("ys");
                        } else user.remove(allUser.get(i));
                    }
                }
                if(user.size() == 0) {
                    user.add("Người Dùng Không Tồn Tại");
                }
            } else
                addList();
        }));


    }
}
