import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server extends Thread{

    public static ArrayList<ServerThread> list_user = new ArrayList<>();

    public static void serve() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("IP Server: " + inetAddress.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            ServerSocket server = new ServerSocket(2001);
            while (true) {
                Socket socket = server.accept();
                System.out.println("connect from" + socket.getInetAddress());
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void sendToAllClient(String message){
        for(int i = 0; i < list_user.size(); i++){
            list_user.get(i).sendToClient(message);
        }
    }

    public static String getListUserName(){
        String result = "Danh sách người dùng khả dụng:\n";
        for(int i = 0; i < list_user.size(); i++){
            result = result + list_user.get(i).getUserName() + "\n";
        }
        return result;
    }

    public static ServerThread getReceiver(String userName) {
        for(int i = 0; i < list_user.size(); i++){
            if (userName.equals(list_user.get(i).getUserName())){
                return list_user.get(i);
            }
        }
        return null;
    }

    public static boolean checkUserExit(String userName){
        for(int i = 0; i < list_user.size(); i++){
            if(userName.equals(list_user.get(i).getUserName()))
                return true;
        }
        return false;
    }
    public static void main(String[] args){
        Server.serve();
    }
}
