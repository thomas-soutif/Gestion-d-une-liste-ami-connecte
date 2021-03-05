package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class InitWindowController {

    @FXML
    private Button buttonToShowConnectionPage;

    @FXML
    public void initialize() {

    }

    @FXML
    public void buttonToShowConnectionPage(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("InitWindowConnection.fxml"));
    InterfaceClient.getMainStage().setScene(new Scene(root));

    }
}
