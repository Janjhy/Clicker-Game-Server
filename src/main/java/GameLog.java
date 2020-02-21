//Singleton object, hold the server points and handles increase when played.
public class GameLog {
    private static GameLog gameLog = new GameLog();
    private int serverPoints = 0;

    private GameLog() {}

    public static GameLog getInstance() {
        return gameLog;
    }

    public int increase() {
        serverPoints++;
        System.out.println(serverPoints);
        return serverPoints;
    }

}
