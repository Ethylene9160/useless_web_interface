package com.example.web;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WebController {
    //define the default server address and port here
    public static String SERVER_ADDRESS = "127.0.0.1";
    public static final int SERVER_PORT = 8888;
    
    private WebInterface web;
    private Socket serverSocket;
    private Sender sender;
    private Receiver receiver;

    private Thread receiveThread;

    public WebController(Socket serverSocket, WebInterface web){
        creatWebController(serverSocket,web);
    }

    public WebController(WebInterface web){
        creatWebController(web);
    }
    public void send(String message){
        sender.send(message);
    }

    public Socket getSocket(){
        return this.serverSocket;
    }
    public void creatWebController(Socket serverSocket, WebInterface web){
        if(this.receiveThread != null) return;//might be better to throw an exception
        this.web = web;
        initController(serverSocket);
    }

    public void creatWebController(WebInterface web){
        try {
            this.creatWebController(new Socket(SERVER_ADDRESS, SERVER_PORT), web);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * initialize the controller.
     * @param serverSocket
     */
    public void initController(Socket serverSocket){
        if(this.web == null) return;
        this.serverSocket = serverSocket;
        this.receiver = new Receiver(serverSocket, web);
        this.receiveThread = new Thread(receiver);
        receiveThread.start();
        this.sender = new Sender(serverSocket);
    }
}

class Sender implements Closeable{
    private DataOutputStream outputStream;

    /**
     * send string to the Server.
     * @param str
     */
    void send(String str) {
        try {
            this.outputStream.writeUTF(str);
            this.outputStream.flush();
        } catch (IOException e) {
            WebUtil.closeAll(this.outputStream);
            e.printStackTrace();
        }
    }

    public Sender(Socket client) {
        try {
            this.outputStream = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            WebUtil.closeAll(this.outputStream, client);
            e.printStackTrace();
        }

    }

    @Override
    public void close() throws IOException {
        this.outputStream.close();
    }
}

class Receiver implements Runnable, Closeable {
    private DataInputStream inputStream;

    private boolean flag = true;
    private WebInterface web;
    public String getData;

    public Receiver(Socket client, WebInterface web) {
        this.web = web;
        try {
            this.inputStream = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            this.flag = false;
            WebUtil.closeAll(this.inputStream, client);
        }

    }

    public DataInputStream getInputStream() {
        return this.inputStream;
    }

    private String getMessage() {
        try {
            return this.inputStream.readUTF();
        } catch (IOException var3) {
            this.flag = false;
            WebUtil.closeAll(this.inputStream);
            var3.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        while (this.flag) {
            this.getData = this.getMessage();
            this.web.webAction(getData);
            System.out.println("ck Receiver: " + this.getData);
        }

    }

    @Override
    public void close() throws IOException {
        WebUtil.closeAll(inputStream);
    }
}
