package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import network.Client.Handler.HandlerAuthClient;
import network.Client.SocketClient;
import network.Common.Request;
import network.Common.TypeRequest;

import java.io.IOException;

public class InitWindowController {
    @FXML
    private Button buttonSendRequest;
    @FXML
    private Button buttonSendResponse;
    @FXML
    private Button buttonTestConnexionMichel;

    @FXML
    public void initialize() {

    }

    @FXML
    private void connexionMichel(ActionEvent event) throws IOException {
        System.out.println("Bouton Michel");
        HandlerAuthClient.handlerUserConnexionRequest("Free Couscous","123456");
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        InterfaceClient.getMainStage().setScene(new Scene(root));
    }

    @FXML
    private void sendRequestTest(ActionEvent event){
        System.out.println("Bouton Test Request");
        HandlerAuthClient.handlerUserInscriptionRequest("Binou","abc","Aub","G");
        buttonSendRequest.setText("Salut");
    }

    @FXML
    private void sendResponseTest(ActionEvent event){
        System.out.println("Bouton Test Response");
        Request request = new Request(TypeRequest.TOKEN_AUTHENTICATION);
        SocketClient.sendPacketAsyncStatic(request);
    }
}
