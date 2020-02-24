public class ClickerServer {
    public static void main(String[] args) {
        int port;
        if (args.length == 0) {
            System.out.println("There were no commandline arguments passed!");
        } else {
            for (String env : args) {
                String[] x = env.split("=");
                System.out.println(x[1]);
                port = Integer.parseInt(x[1]);
                GameServer.Serve(port);
            }

        }
    }


}
