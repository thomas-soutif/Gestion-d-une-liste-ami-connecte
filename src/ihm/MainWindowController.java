package ihm;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import database.CLASSES.AccountUser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import network.Client.SocketClient;
import network.Common.Request;
import network.Common.TypeRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {

    private static MainWindowController instance;
    @FXML
    private ListView listViewFriendRequest;
    @FXML
    private ListView friendList;
    @FXML
    private Button addAFriendButton;


    @FXML
    public void initialize() {
        System.out.println("aaa");
        instance = this;
        System.out.println("ayui");
        JSONObject jsonObject = new JSONObject();
        System.out.println("Demande de la liste d'ami");
        this.friendList.getItems().add(new Label("Récupération de la liste de vos amis ..."));
        Request request = new Request(TypeRequest.FRIENDLIST, jsonObject);
        SocketClient.sendPacketAsyncStatic(request);
        Request request2 = new Request(TypeRequest.FRIEND_REQUEST_LIST,jsonObject);
        SocketClient.sendPacketAsyncStatic(request2);

    }

    public void addAFriend(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("AddAFriendModal.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Add a friend");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)actionEvent.getSource()).getScene().getWindow());
        stage.show();

    }

    public void testLinkUI(){
        Platform.runLater(() -> addAFriendButton.setText("Lien UI"));
    }


    public void setFriendListOnUI(JSONObject object){
        Platform.runLater(() -> {
            this.friendList.getItems().remove(0); // On retire le message définit dans l'init
            JSONArray list_user = object.getJSONArray("list");
            for( Object user_object : list_user){
                JSONObject user = (JSONObject) user_object;
                HBox hBox = new HBox();
                Label label_name= new Label(user.getString("firstName") + " " + user.getString("name"));
                label_name.getProperties().put("user_id",user.getInt("id"));
                hBox.getChildren().add(label_name);

                this.friendList.getItems().add(hBox);
                System.out.println(user);

            }




        });

    }

    public void setFriendRequestListOnUI(JSONObject object){
        Platform.runLater(() -> {
            System.out.println("setFriendRequestListOnUi");
            JSONArray list_user = object.getJSONArray("list");
            for( Object user_object : list_user){
                JSONObject user = (JSONObject) user_object;
                HBox hBox = new HBox();
                System.out.println(user);
                Label label_name= new Label(user.getString("firstName") + " " + user.getString("name"));
                label_name.getProperties().put("user_id",user.getInt("id"));
                hBox.getChildren().add(label_name);

                this.listViewFriendRequest.getItems().add(hBox);
                System.out.println(user);

            }
        });

    }
    public void nothing_for_the_moment(){
        HBox hbox = new HBox();

        Button button_accept = new Button("Accept");
        AccountUser user = new AccountUser();
        user.setId(45);
        user.setFirstName("Kevin");
        button_accept.getProperties().put("idFriendRequest",user);
        Button button_refuse = new Button("Refuse");
        Label label_name = new Label("Kevin");
        hbox.getChildren().add(label_name);
        hbox.getChildren().add(button_accept);
        hbox.getChildren().add(button_refuse);
        this.listViewFriendRequest.getItems().add(hbox);

        button_accept.setOnAction((ActionEvent event) ->{
            Button button = (Button) event.getSource();
            System.out.println(button.getProperties());
            AccountUser user2 = (AccountUser) button.getProperties().get("idFriendRequest");
            System.out.println(user2.getId());

            HBox hBox = new HBox();
            Label label_name2= new Label(user2.getFirstName());
            hBox.getChildren().add(label_name2);
            this.friendList.getItems().add(hBox);
            SocketClient.sendPacketAsyncStatic(new Request(TypeRequest.INSCRIPTION));
        });

    }

    public static MainWindowController getInstance() {
        return instance;
    }
}
