package ClientThread;

import java.io.*;
import java.net.Socket;

public class Client {
    public Socket socket;
    private ReadThread reader;
    private Writer writer;
    private String userName;

    public Client(){
        try {
            socket = new Socket("localhost", 2001);
            reader = new ReadThread(socket);
            reader.start();
            writer = new Writer(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void close(){
        try {
            socket.isInputShutdown();
            socket.isOutputShutdown();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        this.writer.sendMessage(message);
    }

}
