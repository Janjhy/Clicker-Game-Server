import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ClientEndpoint
@ServerEndpoint(value = "/game/")
public class GameWebSocket {
    CommandInterpreter cI;

    @OnMessage
    public void onText(Session session, String message) throws IOException {
        System.out.println("Message received:" + message);
        if (session.isOpen()) {
            cI.handleMessage(message);
        }
    }

    @OnOpen
    public void onConnect(Session session) throws IOException {
        System.out.println(session.getBasicRemote() + " connected!");
        cI = new CommandInterpreter(session);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println(session.getBasicRemote() + " closed!");
    }

    @OnError
    public void onError(Throwable cause) {
        cause.printStackTrace(System.err);
    }
}

