package ClientThread;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

public class Writer {
    public Socket socket;
    public BufferedWriter out;

    public Writer(Socket socket){
        this.socket = socket;
        try {
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        try {
            if(message != null && !message.equals("")) {
                out.write(message);
                out.newLine();
                out.flush();
            }
        } catch (SocketException e){
            try {
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
