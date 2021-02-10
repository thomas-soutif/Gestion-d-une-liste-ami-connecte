package ihm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import network.Client.SocketClient;
import network.Common.Request;
import network.Common.TypeRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class testClientController{
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
    private void connexionMichel(ActionEvent event){
        System.out.println("Bouton Michel");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pseudo","Michel");
            jsonObject.put("password","1234");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new Request(TypeRequest.TOKEN_AUTHENTICATION, jsonObject);
        SocketClient.sendPacketAsyncStatic(request);
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
        System.out.println("Bouton Test Response");
        Request request = new Request(TypeRequest.TOKEN_AUTHENTICATION);
        SocketClient.sendPacketAsyncStatic(request);
    }
}
