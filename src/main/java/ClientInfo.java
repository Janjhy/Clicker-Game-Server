public class ClientInfo {

    private int clientID;
    private int clientPoints;

    public ClientInfo(int id) {
        clientPoints = 20;
        clientID = id;
    }

    public int getPoints() {
        return clientPoints;
    }

    public int getClientID() {
        return clientID;
    }

    public boolean resetPoints() {
        if (clientPoints == 0) {
            clientPoints = 20;
            return true;
        }
        return false;
    }

    public boolean reduce() {
        if (clientPoints == 0) {
            return false;
        }
        clientPoints--;
        return true;
    }

    public void addPoints(int amount) {
        clientPoints = clientPoints + amount;
    }

}
