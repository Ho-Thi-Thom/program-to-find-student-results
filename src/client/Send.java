/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import encode_decode.AES;
import encode_decode.SHA;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Phat
 */
public class Send extends Thread {

    private Socket client;
    private String input;
    AES aes = new AES();
    SHA sha = new SHA();

    public Send(Socket client, String input) {
        this.client = client;
        this.input = input;
    }

    @Override
    public void run() {
        try {
            String[] key = client.getLocalAddress().toString().split("/");  
            key[1] = sha.toHexString(sha.getSHA(key[1]))
                    + sha.toHexString(sha.getSHA(String.valueOf(client.getLocalPort()*client.getPort())))+ "SGU";
            String encrypt = aes.encrypt(input, key[1]);
            System.out.println(encrypt);
            Client.dos.writeUTF(encrypt);
            input = null;
        } catch (IOException | NoSuchAlgorithmException e) {
            System.out.println("Lỗi thuật toán mã hóa");
        }

    }

}
