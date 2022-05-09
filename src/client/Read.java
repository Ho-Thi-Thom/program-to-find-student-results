package client;

import encode_decode.AES;
import encode_decode.SHA;
import java.net.Socket;

public class Read extends Thread {

    private Socket client;
    private static String tmp = null;
    AES aes = new AES();
    SHA sha = new SHA();

    public Read(Socket client) {
        this.client = client;
    }

    public static String getTmp() {
        return tmp;
    }

    public static void setTmp(String tmp) {
        Read.tmp = tmp;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String[] key = client.getInetAddress().toString().split("/");
                key[1] = sha.toHexString(sha.getSHA(key[1])) + sha.toHexString(sha.getSHA(String.valueOf(client.getLocalPort()*client.getPort())))+ "SGU";
                String receive = Client.dis.readUTF();
                System.out.println("Server send:" + receive);
                String decrypt = aes.decrypt(receive, key[1]);
                setTmp(decrypt);
                System.out.println("Read:" + decrypt);
            }
        } catch (Exception e) {
            System.out.println("Ngắt kêt nối");
        }
    }

}
