package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.io.IOException;

public class InitWindowConnectionController {
    public TextField pseudoTextField;
    public Button buttonToShowMainWindow;
    //public Hyperlink hyperLinkToShowSignUp;
    // @FXML
    // private Button buttonSendRequest;
    //@FXML
    //private Button buttonSendResponse;
    //@FXML
    //private Button buttonTestConnexionMichel;

    @FXML
    private Hyperlink hyperLinkToShowSignUp;

    //@FXML
    //private Button buttonToShowMainWindow;

    @FXML
    public void initialize() {

    }

    //@FXML
    //private void connexionMichel(ActionEvent event) throws IOException {
        //System.out.println("Bouton Michel");
        //HandlerAuthClient.handlerUserConnexionRequest("michel","1234");
        //Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        //InterfaceClient.getMainStage().setScene(new Scene(root));
    //}

    //@FXML
    //private void sendRequestTest(ActionEvent event){
        //System.out.println("Bouton Test Request");
        //Request request = new Request(TypeRequest.TOKEN_AUTHENTICATION);
        //SocketClient.sendPacketAsyncStatic(request);
        //buttonSendRequest.setText("Salut");
    //}

    //@FXML
    //private void sendResponseTest(ActionEvent event){
        //System.out.println("Bouton Test Response");
        //Request request = new Request(TypeRequest.TOKEN_AUTHENTICATION);
        //SocketClient.sendPacketAsyncStatic(request);
    //}


    @FXML
    private void hyperLinkToShowSignUp(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("InitWindowSignUp.fxml"));
    InterfaceClient.getMainStage().setScene(new Scene(root));
    }

    @FXML
    private void buttonToShowMainWindow(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
    InterfaceClient.getMainStage().setScene(new Scene(root));
    }
}

// TODO créer dans initialize la variable Static et indiquer this pour récupérer les informations une à une.
// TODO important vérifier que les mots de passes sont identiques.