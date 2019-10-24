package code;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    public static void main(String[] args) throws InterruptedException {
        try (Socket socket = new Socket("localhost", 3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream());) {
            while (!socket.isOutputShutdown()) {
                if (br.ready()) {
                    String clientCommand = br.readLine();
                    oos.writeUTF(clientCommand);
                    oos.flush();
                    System.out.println("Отправлено сообщение: " + clientCommand);
                    if (clientCommand.equalsIgnoreCase("/end")) {
                        if (ois.read() > -1) {
                            String in = ois.readUTF();
                            System.out.println(in);
                        }
                        break;
                    }
//                    if (ois.read() > -1) {
//                        String in = ois.readUTF();
//                        System.out.println(in);
//                    }
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}