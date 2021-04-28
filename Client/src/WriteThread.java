import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread{
    private Socket socket;
    private BufferedWriter out;
    private Scanner sc = new Scanner(System.in);

    public WriteThread(Socket socket){
        this.socket = socket;
        try {
            this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                String message = sc.nextLine();
                if (message != null) {
                    out.write(message);
                    out.newLine();
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
