/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;
import javax.crypto.SecretKey;

/**
 *
 * @author Phat
 */
public class Client {

    private String host;
    private int port;
    public static Socket client;
    public static DataInputStream dis = null;
    public static DataOutputStream dos = null;
    public static Read read;
    //public static Write write;
    public static boolean flag;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void excute() throws IOException {
        client = new Socket(host, port);
        System.out.println("Connected");
        flag = true;
        dis = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
        read = new Read(client);
        read.start();

    }

}
