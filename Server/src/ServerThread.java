import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ServerThread extends Thread {
    private final Socket socket;
    private BufferedWriter out;
    private BufferedReader in;

    private String userName;
    private boolean connect_again = false;
    private ServerThread receiver;
    private String nameReceiver;

    public ServerThread(Socket socket){
        this.socket = socket;
        this.userName = null;
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

    public String getNameReceiver() {
        return nameReceiver;
    }

    public void run(){
        try {
            String[] message;
            while (true) {
                String input = in.readLine();
                message = input.split("/", 2);
                switch (message[0]) {
                    case "@ClientName":
                        if (!Server.checkUserExit(message[1])) {
                            setUserName(message[1]);
                            sendToClient("@UserConnected");
                            if(!connect_again) {
                                Server.list_user.add(this);
                                connect_again = true;
                            } else {
                                sendToSender("@ServerMessage1/Người Dùng Kết Nối Lại");
                            }
                            Server.sendToAllClient(Server.getListUserName());
                        } else {
                            sendToClient("@ServerMessage/Người Dùng Đã Tồn Tại");
                        }
                        break;
                    case "@ClientReceive":
                        ServerThread st = Server.getReceiver(message[1]);
                        setReceiver(st);
                        nameReceiver = st.userName;
                        break;
                    case "@ClientMessage":
                        if(receiver.socket.isClosed() || receiver.getUserName() == null)
                            sendToClient("@ServerMessage1/Người Dùng Không Khả dụng");
                        else
                            sendToReceiveClient(input);
                        break;
                    case "@ClientLogout":
                        sendToSender("@ServerMessage1/Người Dùng Không Khả dụng");
                        setUserName(null);
                        Server.sendToAllClient(Server.getListUserName());
                        break;
                    case "@NullReceive" :
                        receiver = null;
                        nameReceiver = null;
                }
            }
        } catch (NullPointerException | SocketException e) {
            try {

                System.out.println("Disconnect from " + socket.getInetAddress());
                Server.list_user.remove(this);
                Server.sendToAllClient(Server.getListUserName());
                sendToSender("@ServerMessage1/Người Dùng Không Khả dụng");
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendToClient(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToSender(String str){
        List<ServerThread> list = Server.getSender(userName);
        for(int i = 0; i < list.size(); i++){
            list.get(i).sendToClient(str);
        }
    }
    public void setReceiver(ServerThread serverThread){
        this.receiver = serverThread;
    }

    public void sendToReceiveClient(String message){
        receiver.sendToClient(message);
    }

    public ServerThread getReceive(){
        return receiver;
    }
}


