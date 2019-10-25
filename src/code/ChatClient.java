package code;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

    public static final int PORT = 8189;
    public static final String SERVERHOST = "localhost";


    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVERHOST, PORT);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream());
        ) {

            new Thread(() -> {
                try {
                    while (true) {
                        //                   if (in.readBoolean()) {
                        String w = in.readUTF();
                        if (w.equalsIgnoreCase("/end")) break;
                        System.out.println(w);
                        //                   }
                    }
                } catch (Exception e) {
                    System.out.println("Соединение завершено!");
                }
            }).start();

            while (!socket.isOutputShutdown()) {
                if (br.ready()) {
                    String clientCommand = br.readLine();
                    out.writeUTF(clientCommand);
                    out.flush();
                    System.out.println("Отправлено сообщение: " + clientCommand);
//                    if (clientCommand.equalsIgnoreCase("/end")) {
//                        //        if (in.readBoolean()) {
//                        String mes = in.readUTF();
//                        System.out.println(mes);
//                        //        }
//                        break;
//                    }
//                    //    if (in.readBoolean()) {
//                    String mes = in.readUTF();
//                    System.out.println(mes);
                    //   }
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}