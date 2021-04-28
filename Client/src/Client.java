import java.io.*;
import java.net.Socket;

public class Client {
    public static void connect(){
        try {
            Socket socket = new Socket("192.168.1.2", 2001);
            new ReadThread(socket).start();
            new WriteThread(socket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Client.connect();
    }
}
