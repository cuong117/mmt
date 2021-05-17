package interfaceController;

import javafx.application.Platform;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Controller {

    public TextField inputName;
    public Labeled error;

    public void getName(){
        String name = inputName.getText().trim();
        if(!name.equals("")) {
            Main.c.sendMessage("@ClientName/" + name);
            Main.c.setUserName(name);
        }
    }

    public void setError(String message){
        error.setVisible(true);
        error.setTextFill(Color.RED);
        Platform.runLater(() -> error.setText(message));
    }

    public void name(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            String str = inputName.getText().trim();
            if(!str.equals("")){
                Main.c.sendMessage("@ClientName/" + str);
                Main.c.setUserName(str);
            }
        }
    }

    public void clear(){
        inputName.clear();
        error.setVisible(false);
    }
}
