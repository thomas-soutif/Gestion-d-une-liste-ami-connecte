package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

import java.io.IOException;

public class InitWindowSignUpController {

    public Hyperlink hyperLinkBackToConnection;
    @FXML
    private Button buttonSignUp;

    @FXML
    public void initialize() {
    }

    @FXML
    private void setButtonConnection2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        InterfaceClient.getMainStage().setScene(new Scene(root));
    }

    @FXML
    private void hyperLinkBackToConnection(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InitWindowConnection.fxml"));
        InterfaceClient.getMainStage().setScene(new Scene(root));
    }
}
//TODO récupérer les valeurs des champs et confirmer les inscriptions!