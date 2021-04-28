import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ServerThread extends Thread {
    private final Socket socket;
    private BufferedWriter out;
    private BufferedReader in;
    private String[] result;
    private String userName;

    public ServerThread(Socket socket){
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }

    public void run(){
        try {
            do {
                sendToClient("Nhập tên của bạn: ");
                String userName = in.readLine();
                if (!Server.checkUserExit(userName)) {
                    setUserName(userName);
                    Server.list_user.add(this);
                    break;
                }
                sendToClient(" Tên đã tồn tại.");
            } while (true);
            Server.sendToAllClient(userName + " Connected");
            sendToClient("Nhập tin nhắn theo cấu trúc: [user Receive] [message] ");
            sendToClient(Server.getListUserName());
            String input;
            String output;
            ServerThread receiveThread;

            do {
                input = in.readLine();
                result = input.split(" ",2);
                receiveThread = Server.getReceiver(result[0]);
                if(receiveThread == null){
                    sendToClient("Người dùng không tồn tại");
                    continue;
                }
                output = "[" + userName + "]: " + result[1];
                sendToReceiveClient(output,receiveThread);
            } while (!input.equals("Bye"));

        }  catch (SocketException e){
            System.out.println("Disconect From: " + socket.getInetAddress());
            Server.list_user.remove(this);
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

    public void sendToClient(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToReceiveClient(String message,ServerThread serverThread){
        serverThread.sendToClient(message);
    }

}
