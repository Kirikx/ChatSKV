package code;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) throws InterruptedException {

        try (ServerSocket server = new ServerSocket(3345)) {
            Socket client = server.accept();
            System.out.println("Есть контакт!");
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());
            while (!client.isClosed()) {
                String entry = in.readUTF();
                System.out.println("Получено сообщение: " + entry);
                if (entry.equalsIgnoreCase("/end")) {             // Проверка на завершение коннекта
                    out.writeUTF("ЭХО - " + entry);
                    out.flush();
                    break;
                }
              //  out.writeUTF("ЭХО - " + entry);
                out.flush();
            }
            System.out.println("Клиент отключился");
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}