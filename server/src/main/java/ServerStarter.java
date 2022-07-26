import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerStarter {

    static int id = 0;

    public static final int TCP = 54444;
    public static final int UDP = 57890;

    static Server server;

    static Map<Integer, String> clients = new HashMap<Integer, String>();

    public static void main(String[] args) {
        server = new Server();
        server.getKryo().register(String.class);
        try {
            server.bind(TCP, UDP);
            server.start();
            server.addListener(new Listener() {
                @Override
                public void connected(Connection connection) {
                    server.sendToAllTCP("Client with name " + id + " connected");
                    clients.put(connection.getID(), id + "");
                    id++;
                }

                @Override
                public void disconnected(Connection connection) {
                    server.sendToAllTCP("Client with name " + clients.get(connection.getID()) + " disconnected");
                    clients.remove(connection.getID());
                }

                @Override
                public void received(Connection connection, Object object) {
                    if (object instanceof String)
                        if (((String) object).startsWith("/changename ")) {
                            server.sendToAllTCP("Name : " + ((String) object).substring(12));
                            clients.put(connection.getID(), ((String) object).substring(12));
                        } else {
                            server.sendToAllTCP("[ " + clients.get(connection.getID()) + " ] : " + object + "");
                        }

                }

                @Override
                public void idle(Connection connection) {
                    super.idle(connection);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
