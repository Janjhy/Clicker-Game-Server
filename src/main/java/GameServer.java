import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    public static void Serve() {
        boolean listening = true;
        int port = 80;

        try (
                ServerSocket serverSocket = new ServerSocket(port);
        ) {
            while (listening) {
                Socket socket = serverSocket.accept();
                CommandInterpreter cI = new CommandInterpreter(socket.getInputStream(), socket.getOutputStream());
                Thread newThread = new Thread(cI);
                newThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
