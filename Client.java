import java.io.*;
import java.net.*;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client() {
        try {
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connected to Client");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        Runnable r1 = () -> {
            System.out.println("User started");
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg == null || msg.equalsIgnoreCase("exit")) {
                        System.out.println("User disconnected");
                        socket.close();
                        break;
                    }
                    System.out.println("User: " + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("Writer started");
            try {
                BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    String content = console.readLine();
                    out.println(content);
                    if (content.equalsIgnoreCase("exit")) {
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        new Client();
    }
}
