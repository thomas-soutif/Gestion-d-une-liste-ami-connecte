package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import network.Client.Handler.HandlerAuthClient;

import java.io.IOException;



public class InitWindowSignUpController {

    private static InitWindowSignUpController instance;

    public Hyperlink hyperLinkBackToConnection;
    public TextField textFieldPseudo;
    public TextField textFieldPrenom;
    public TextField textFieldNom;
    public PasswordField passwordFieldMdp;
    public PasswordField passwordFieldConfirmMdp;


    @FXML
    private Button buttonSignUp;

    public static InitWindowSignUpController getInstance() {
        return instance;
    }

    public void  gotoPageConnection() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("InitWindowConnection.fxml"));
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
    private void setButtonConnection2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        InterfaceClient.getMainStage().setScene(new Scene(root));
    }

    @FXML
    private void hyperLinkBackToConnection(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InitWindowConnection.fxml"));
        InterfaceClient.getMainStage().setScene(new Scene(root));
    }

    public void ActionSignUp(ActionEvent actionEvent) {
        String mdpConfirmation = passwordFieldConfirmMdp.getText();
        String mdp = passwordFieldMdp.getText();
        if (textFieldPseudo.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez rentrer un pseudo !");
            alert.show();
        }
        else if (!mdp.equals(mdpConfirmation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Votre mot de passe n'est pas identique !");
            alert.show();
        }
        else {
            HandlerAuthClient.handlerUserInscriptionRequest(textFieldPseudo.getText(), mdp, textFieldPrenom.getText(), textFieldNom.getText());
        }
    }
}
