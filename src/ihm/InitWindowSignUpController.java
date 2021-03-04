package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import network.Client.Handler.HandlerAuthClient;

import java.io.IOException;

public class InitWindowSignUpController {

    public Hyperlink hyperLinkBackToConnection;
    public TextField textFieldPseudo;
    public TextField textFieldPrenom;
    public TextField textFieldNom;
    public PasswordField passwordFieldMdp;
    public PasswordField passwordFieldConfirmMdp;


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

    public void ActionSignUp(ActionEvent actionEvent) {
        String mdpConfirmation = passwordFieldConfirmMdp.getText();
        String mdp = passwordFieldMdp.getText();
        if (textFieldPseudo.getText().isBlank()) {
            //TODO afficher message d'erreur
            //TODO seconde condition, mettre un else if pour comparer si
        }
       else if (mdpConfirmation.equals(mdp)){
            HandlerAuthClient.handlerUserInscriptionRequest(textFieldPseudo.getText(), mdp, textFieldPrenom.getText(), textFieldNom.getText());
        }
    }
}
//TODO récupérer les valeurs des champs et confirmer les inscriptions!