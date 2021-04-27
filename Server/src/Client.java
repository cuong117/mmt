import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public void connect(){
        try {
            while (true) {
                Socket socket = new Socket("192.168.1.2", 117);
                Scanner sc = new Scanner(System.in);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                System.out.print("NHap thong diep: ");
                out.write(sc.next());
                out.newLine();
                out.flush();
                String input = in.readLine();
                if (input != null) {
                    System.out.println("Trả lời từ Server: " + input.toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Client c = new Client();
        c.connect();
    }
}
