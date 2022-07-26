package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private ClientHandler clientHandler;

    @FXML
    TextArea chatArea;

    @FXML
    TextField textArea;

    public Controller() {

    }

    public void sendMassage(ActionEvent actionEvent) {
        String text = textArea.getText();
        textArea.clear();
        textArea.requestFocus();
        clientHandler.client.sendTCP(text);
    }

    public void initialize(URL location, ResourceBundle resources) {
        clientHandler = new ClientHandler(new Callback() {
            public void callback(Object... args) {
                chatArea.appendText((String) args[0] + "\n");
            }
        });
    }
}
