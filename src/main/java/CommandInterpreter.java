import javax.websocket.Session;
import java.io.IOException;

public class CommandInterpreter {
    private ClientInfo user;
    private Clients clients = Clients.getInstance();
    private GameLog gameLog = GameLog.getInstance();
    private Session session;

    public CommandInterpreter(Session x) {
        session = x;
    }

    public void handleMessage(String msg) {

        String text = msg;
        String[] split = text.split(" ");
        System.out.println(text);
        switch (split[0]) {
            case "exit":
                exit();
                break;
            case "noID":
                newUser();
                break;
            case "ID":
                setUser(Integer.parseInt(split[1]));
                break;
            case "play":
                play();
                break;
            case "reset":
                resetUser();
                break;
            default:
                sendMsg("error");
        }
    }

    public void sendMsg(String msg) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newUser() { //Adds new user to client list and returns the id to client
        user = clients.newClient();
        String text = String.format(":id %d", user.getClientID());
        sendMsg(text);
    }

    public boolean setUser(int id) {
        ClientInfo client = clients.getClient(id);
        if (client != null) {
            user = client;
            return true;
        }
        return false;
    }

    public void play() {
        if (user == null) {
            return;
        } else if (user.reduce() == false) { //If player has no points, nothing happens
            sendMsg(":nopoints");
            return;
        }
        int serverPoints = gameLog.increase();
        checkIfWin(serverPoints);

    }

    public void exit() {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkIfWin(int points) {
        if ((points % 500) == 0) {
            user.addPoints(250);
            sendMsg(String.format(":won %d", 250));
            sendMsg(String.format(":next %d", 10));
        } else if ((points % 100) == 0) {
            user.addPoints(40);
            sendMsg(String.format(":won %d", 40));
            sendMsg(String.format(":next %d", 10));
        } else if ((points % 10) == 0) {
            user.addPoints(10);
            sendMsg(String.format(":won %d", 10));
            sendMsg(String.format(":next %d", 10));
        } else {
            int next = (10 - (points % 10));
            sendMsg(String.format(":next %d", next));
        }
        sendMsg(String.format(":points %d", user.getPoints()));
    }

    public void resetUser() {
        if (user.resetPoints() == false) {
            sendMsg(":invalidreset");
        } else {
            sendMsg(String.format(":points %d", user.getPoints()));
        }
    }
}
