package code;

import java.io.*;
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
                        String mes = in.readUTF();
                        if (mes.equalsIgnoreCase("/end")) break;
                        System.out.println("Сервер: " + mes);
                    }
                    System.out.println("Соединение прерванно!");
                    in.close();
                    out.close();
                    socket.close();
                } catch (Exception e) {
                    System.out.println("Соединение завершено!");
                }
            }).start();

            while (!socket.isClosed()) {
                if (br.ready()) {
                    String clientCommand = br.readLine();
                    out.writeUTF(clientCommand);
                    out.flush();
                    System.out.println("Я: " + clientCommand);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}