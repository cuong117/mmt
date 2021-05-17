package interfaceController;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;

public class MessageController {
    public Label receiver;
    public TextField message;
    public ScrollPane sp;
    public VBox vbox;
    private HashMap<String, VBox> listVb = new HashMap<>();

    public void back(){
        SceneController.activeScene("select");
        Main.c.sendMessage("@NullReceive");
        receiver.setText("");
        vbox = null;
    }

    public void send(){
        String str = message.getText().trim();
        if(!str.isEmpty()) {
            Main.c.sendMessage("@ClientMessage/" + Main.c.getUserName() + "/" + str);
            setLabel(str, Pos.TOP_RIGHT, vbox);
            message.clear();
        }
    }

    public void sendByKey(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            String str = message.getText().trim();
            if(!str.isEmpty()) {
                Main.c.sendMessage("@ClientMessage/" + Main.c.getUserName() + "/" + str);
                setLabel(str, Pos.TOP_RIGHT, vbox);
                message.clear();
            }
        }
    }


    public void receiveMessage(String string){
        String[] arr = string.split("/",2);
        if(!listVb.containsKey(arr[0]))
            addVbox(arr[0]);
        Platform.runLater(() -> setLabel(arr[1],Pos.TOP_LEFT, listVb.get(arr[0])));
    }

    public void setReceiver(String name){
        receiver.setText(name);
    }

    public void getError(String error){
        Platform.runLater(() -> {
            Label lb = new Label(error);
            lb.setTextFill(Color.RED);
            HBox hb = new HBox(lb);
            hb.setAlignment(Pos.BASELINE_CENTER);
            vbox.getChildren().add(hb);
            sp.vvalueProperty().bind(vbox.heightProperty());
        });
    }

    public void setLabel(String str, Pos pos, VBox vb){
        Label lb = new Label(str);
        lb.setFont(Font.font(18));
        lb.setWrapText(true);
        lb.setMaxWidth(vb.getPrefWidth()/2);
        lb.setTextFill(Color.BLACK);
        HBox hb = new HBox(lb);
        hb.setAlignment(pos);
        vb.getChildren().add(hb);
        sp.vvalueProperty().bind(vb.heightProperty());
    }

    public void setVbox(){
        String name = receiver.getText();
        if(!listVb.containsKey(name))
            addVbox(name);
        vbox = listVb.get(name);
        sp.setContent(vbox);
    }

    public void addVbox(String key){
        listVb.put(key,new VBox());
        listVb.get(key).setBackground(new Background(
                new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        listVb.get(key).setPrefHeight(273);
        listVb.get(key).setPrefWidth(575);
    }
}
