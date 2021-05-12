package ClientThread;

import java.io.*;
import java.net.Socket;

public class Client {
    public Socket socket;
    public ReadThread reader;

    public String getUserName() {
        return userName;
    }

    public Writer writer;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String userName;

    public Client(){
        try {
            socket = new Socket("192.168.1.28", 2001);
            reader = new ReadThread(socket);
            reader.start();
            writer = new Writer(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
