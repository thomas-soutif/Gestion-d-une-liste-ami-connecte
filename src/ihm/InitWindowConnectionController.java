package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import network.Client.Handler.HandlerAuthClient;

import java.io.IOException;

public class InitWindowConnectionController {

    public TextField pseudoTextField;
    public Button buttonToShowMainWindow;

    public TextField textFieldPseudo;
    public PasswordField passwordFieldMdp;

    private static InitWindowConnectionController instance;

    @FXML
    private Hyperlink hyperLinkToShowSignUp;

    public static InitWindowConnectionController getInstance() {
        return instance;
    }

    public void gotoMainWindowInterface () {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            InterfaceClient.getMainStage().setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        instance = this;
    }


    @FXML
    private void hyperLinkToShowSignUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InitWindowSignUp.fxml"));
        InterfaceClient.getMainStage().setScene(new Scene(root));
    }

    public void actionConnexionClick(ActionEvent actionEvent) {
        HandlerAuthClient.handlerUserConnexionRequest(textFieldPseudo.getText(), passwordFieldMdp.getText());
    }
}
