import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CommandInterpreter implements Runnable {
    private Scanner input;
    private PrintStream output;
    private ClientInfo user;
    private Clients clients = Clients.getInstance();
    private GameLog gameLog = GameLog.getInstance();

    public CommandInterpreter(InputStream x, OutputStream y) {
        input = new Scanner(x);
        output = new PrintStream(y, true);
    }

    @Override
    public void run() {

        scanner:
        while (input.hasNext()) {
            String text = input.nextLine();
            String[] split = text.split(" ");
            System.out.println(text);
            switch (split[0]) {
                case "exit":
                    exit();
                    break scanner;
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
                    output.println("error");
                    break;
            }
        }
        exit();
    }

    public void newUser() { //Adds new user to client list and returns the id to client
        user = clients.newClient();
        output.printf(":id %d\n", user.getClientID());
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
            output.println(":nopoints");
            return;
        }
        int serverPoints = gameLog.increase();
        checkIfWin(serverPoints);

    }

    public void exit() {
        input.close();
        output.close();
    }

    public void checkIfWin(int points) {
        if ((points % 500) == 0) {
            user.addPoints(250);
            output.printf(":won %d\n", 250);
            output.printf(":next %d\n", 10);
        } else if ((points % 100) == 0) {
            user.addPoints(40);
            output.printf(":won %d\n", 40);
            output.printf(":next %d\n", 10);
        } else if ((points % 10) == 0) {
            user.addPoints(10);
            output.printf(":won %d\n", 10);
            output.printf(":next %d\n", 10);
        } else {
            int next = (10 - (points % 10));
            output.printf(":next %d\n", next);
        }
        output.printf(":points %d\n", user.getPoints());
    }

    public void resetUser() {
        if (user.resetPoints() == false) {
            output.println(":invalidreset");
        } else {
            output.printf(":points %d\n", user.getPoints());
        }
    }
}
