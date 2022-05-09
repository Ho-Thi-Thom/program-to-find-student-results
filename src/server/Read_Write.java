package server;

import encode_decode.AES;
import encode_decode.SHA;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Phat
 */
public class Read_Write extends Thread {

    private Socket socket;
    AES aes = new AES();
    SHA sha = new SHA();

    public Read_Write(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            String c = null;
            Event ev = new Event();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String receive = dis.readUTF();
//                System.out.println(socket + " send " + receive);
                String key = socket.getInetAddress().toString().substring(1);
//                System.out.println(key);
                key = sha.toHexString(sha.getSHA(key)) + sha.toHexString(sha.getSHA(String.valueOf(socket.getLocalPort() * socket.getPort()))) + "SGU";
//                System.out.println("KEY: " + key);
                String decrypt = aes.decrypt(receive, key);
//                System.out.println("Decrypt:" + decrypt);
                if (decrypt.equals("exit")) {
                    Server.ListSocket.remove(socket);
                    for (Socket item : Server.ListSocket) {
//                        System.out.println("List socket:" + socket);
                    }
//                    System.out.println("Disconnected " + socket);
                    dis.close();
                    socket.close();
                }
                String encrypt = aes.encrypt(Event.Query(decrypt), key);
//                System.out.println(encrypt);
                dos.writeUTF(encrypt);
                 System.out.println(socket + " send " + receive);
            }
        } catch (IOException | NoSuchAlgorithmException | SQLException e) {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Disconnected");
            }
        }
    }

}
