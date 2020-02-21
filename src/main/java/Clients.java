import java.util.HashSet;


//A singleton Clients class, holds the current connected clients
public class Clients {

    private static Clients clients = new Clients();
    private HashSet<ClientInfo> clientsSet = new HashSet<>();

    private Clients() { }

    public ClientInfo newClient() {
        int id = clientsSet.size();
        while (checkId(id) == true) {
            id = id + 1;
        }
        ClientInfo client = new ClientInfo(id);
        clientsSet.add(client);
        return client;
    }

    public boolean checkId(int id) {
        for (ClientInfo i : clientsSet) {
            if (i.getClientID() == id) {
                return true;
            }

        }
        return false;
    }

    public ClientInfo getClient(int id) {
        for (ClientInfo i : clientsSet) {
            int x = i.getClientID();
            if (x == id) {
                return i;
            }
        }
        return null;
    }

    public static Clients getInstance() {
        return clients;
    }

}
