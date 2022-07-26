package client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class ClientHandler {
    public static final int TCP = 54444;
    public static final int UDP = 57890;

    public static Client client = new Client();
    public Callback callback;

    public ClientHandler(Callback callback) {
        this.callback = callback;
        init();
    }

    public void init() {
        try {
            client.getKryo().register(String.class);
            client.start();
            client.connect(3000, "localhost", TCP, UDP);

            client.addListener(new Listener() {
                @Override
                public void connected(Connection connection) {
                }

                @Override
                public void disconnected(Connection connection) {
                    super.disconnected(connection);
                }

                @Override
                public void received(Connection connection, Object object) {
                    if (object instanceof String) {
                        callback.callback(object);
                    }
                }

                @Override
                public void idle(Connection connection) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
