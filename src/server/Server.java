package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Phat
 */
public class Server {
    private int port;
    public static ArrayList<Socket> ListSocket;

    public Server(int port) {
        this.port = port;
    }
    
    private void start() throws IOException{
        ExecutorService executor = Executors.newFixedThreadPool(3);
        ServerSocket server  = new ServerSocket(port);
        System.out.println("Server is running");
        while(true){
            Socket socket = server.accept();
            Server.ListSocket.add(socket);
//            System.out.println(socket.toString());
            Read_Write read = new Read_Write(socket);
            System.out.println("Connected " + socket);
//            read.start();
            executor.execute(read);
        }
    }
    public static void main(String[] args) throws IOException{
       
        Server.ListSocket = new ArrayList<>();
        Server server = new Server(5000);
        server.start();
    }
}
