import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{

    public static ArrayList<ServerThread> list_user = new ArrayList<>();
    private static final int port = 2001;

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
            if(list_user.get(i).getUserName() != null)
                list_user.get(i).sendToClient(message);
        }
    }

    public static String getListUserName(){
        String result = "@ListUser";
        for(int i = 0; i < list_user.size(); i++){
            if(list_user.get(i).getUserName() != null) {
                result = result + "/" + list_user.get(i).getUserName();
            }
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

    public static List<ServerThread> getSender(String userName){
        List<ServerThread> list = new ArrayList<>();
        for(int i = 0; i < list_user.size(); i++){
            if(list_user.get(i).getReceive() != null) {
                if (userName.equals(list_user.get(i).getNameReceiver()))
                    list.add(list_user.get(i));
            }
        }
        return list;
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
