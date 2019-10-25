package code;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static final int PORT = 8189;

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Сервер создан, ожидаем подключения клиента");
            Socket client = server.accept();
            System.out.println("Есть контакт!");
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            new Thread(() -> {
                try {
                    while (!client.isClosed()) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        String clientCommand = br.readLine();
                        out.writeUTF(clientCommand);
                        out.flush();
                        System.out.println("Я: " + clientCommand);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            while (!client.isClosed()) {
                String mes = in.readUTF();
                System.out.println("Клиент: " + mes);
                if (mes.equalsIgnoreCase("/end")) {             // Проверка на завершение коннекта
                    out.writeUTF(mes);
                    out.flush();
                    break;
                }
//                out.writeUTF("ЭХО: " + mes);
//                out.flush();
            }
            System.out.println("Клиент отключился!");
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}