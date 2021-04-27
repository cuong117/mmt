import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server extends Thread{
    private Socket socket;
    private String userName;
    public Server(){
    }
    public Server(Socket socket, String u){
        this.socket = socket;
        this.userName = u;
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        try {

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String input = in.readLine();
                if (input != null) {
                    System.out.println("thong diep tu client: " + input);
                    System.out.print("Tra loi: ");
                    out.write(sc.next());
                    out.newLine();
                    out.flush();
                }

        }  catch (SocketException e){
            System.out.println("Disconect From: " + socket.getInetAddress());
        }  catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void serve() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("IP Server: " + inetAddress.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            ServerSocket server = new ServerSocket(117);
            int count = 0;
            while (true) {
                Socket socket = server.accept();
                new Server(socket,String.valueOf(count)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        Server server = new Server();
        server.serve();
    }
}
