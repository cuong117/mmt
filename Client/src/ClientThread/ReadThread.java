package ClientThread;

import interfaceController.Controller;
import interfaceController.MessageController;
import interfaceController.SceneController;
import interfaceController.list_control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ReadThread extends Thread{
    private Socket socket;
    private BufferedReader in;

    public ReadThread(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            String[] message;
            while (true) {
                String input = in.readLine();
                if(input == null)
                    break;
                message = input.split("/",2);
                switch (message[0]) {
                    case "@ServerMessage" :
                        Controller controller = SceneController.getLoader("start").getController();
                        controller.setError(message[1]);
                        break;
                    case "@UserConnected" :
                        SceneController.activeScene("select");
                        break;
                    case "@ListUser" :
                        Controller controller1 = SceneController.getLoader("start").getController();
                        controller1.clear();
                        list_control control = SceneController.getLoader("select").getController();
                        if (message.length > 1)
                            control.createList(message[1]);
                        break;
                    case "@ClientMessage" :
                        MessageController messageController = SceneController.getLoader("message").getController();
                        messageController.receiveMessage(message[1]);
                        break;
                    case "@ServerMessage1":
                        MessageController messageController1 = SceneController.getLoader("message").getController();
                        messageController1.getError(message[1]);
                        break;
                }
            }
        } catch (SocketException e){
            try {
                in.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
