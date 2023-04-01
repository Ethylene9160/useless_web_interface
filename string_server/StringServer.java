package web.string_server;

import web.tools.WebController;
import web.tools.WebInterface;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static web.string_server.StringServer.SPLIT_REG;

public class StringServer{
    public static final char SEND_ID = 'a', RECEIVE_MESSAGE = 'b';
    public static String SPLIT_REG = "#";
    //
    //public static List<MoveChannel> list = new ArrayList<MoveChannel>();
    public static Map<Integer, MoveChannel> listMap = new HashMap<>();
    public static int webID;
    public static void main(String[] args) throws IOException {
        webID  = 100;//
        System.out.println("start");
        ServerSocket moveServer = new ServerSocket(8888);
        while(true){
            webID ++;
            Socket socket = moveServer.accept();
            //System.out.println(socket.getRemoteSocketAddress());
            //
            MoveChannel moveChannel = new MoveChannel(socket);
            moveChannel.ownID = webID;
            System.out.println("someone comes"+(listMap.size()+1));
            //
            new DataOutputStream(socket.getOutputStream()).writeUTF(SEND_ID+Integer.toString(webID));
            listMap.put(webID, moveChannel);
            //list.add(moveChannel);
            //
//            new Thread(moveChannel).start();
        }
    }
}

class MoveChannel implements WebInterface {
    int ownID;
    WebController controller;
    public MoveChannel(Socket clientSocket){
        controller = new WebController(clientSocket, this);
    }

    @Override
    public void webAction(String message) {
        String info[] = message.split(SPLIT_REG);
        Objects.requireNonNull(StringServer.listMap.get(Integer.parseInt(info[0]))).controller.send(info[1]);
    }
}


