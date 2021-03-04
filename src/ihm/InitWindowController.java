package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import network.Client.SocketClient;
import network.Common.Request;
import network.Common.TypeRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class InitWindowController {
    public Button buttonTestConnexionThomas;
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
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pseudo","Jym");
            jsonObject.put("password","1234");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new Request(TypeRequest.TOKEN_AUTHENTICATION, jsonObject);
        SocketClient.sendPacketAsyncStatic(request);
        System.out.println("bbbb");
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        InterfaceClient.getMainStage().setScene(new Scene(root));
    }

    @FXML
    private void sendRequestTest(ActionEvent event){
        System.out.println("Bouton Test Request");
        Request request = new Request(TypeRequest.TOKEN_AUTHENTICATION);
        SocketClient.sendPacketAsyncStatic(request);
        buttonSendRequest.setText("Salut");
    }

    @FXML
    private void sendResponseTest(ActionEvent event){
        
    }

    public void connexionThomas(ActionEvent actionEvent) throws IOException {
        System.out.println("Thomas");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pseudo","Xelèèèèèèèèèreeee");
            jsonObject.put("password","jemappelleThomas");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new Request(TypeRequest.TOKEN_AUTHENTICATION, jsonObject);
        SocketClient.sendPacketAsyncStatic(request);
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        InterfaceClient.getMainStage().setScene(new Scene(root));
    }
}
