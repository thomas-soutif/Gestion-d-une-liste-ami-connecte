package ihm;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import database.CLASSES.AccountUser;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
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
            JSONArray list_friend_request = object.getJSONArray("list_friend_request");
            int i=0;
            for( Object friend_request : list_friend_request){
                JSONObject friend_request_json = (JSONObject) friend_request;
                System.out.println(friend_request);

                Button button_accept = new Button("Accept");
                Button button_refuse = new Button("Refuse");
                button_accept.getProperties().put("idFriendRequest",friend_request_json.getInt("request_id"));
                button_refuse.getProperties().put("idFriendRequest",friend_request_json.getInt("request_id"));

                HBox hBox = new HBox();
                Label label_name= new Label(friend_request_json.getString("firstname") + " " + ((JSONObject) friend_request).getString("name"));
                hBox.getChildren().add(label_name);
                hBox.getChildren().add(button_accept);
                hBox.getChildren().add(button_refuse);

                this.listViewFriendRequest.getItems().add(hBox);

                button_accept.setOnAction((ActionEvent event) ->{
                    Button button = (Button) event.getSource();
                    System.out.println(button.getProperties());
                    Object object_friend_request_id= button.getProperties().get("idFriendRequest");
                    int friend_request_id = (int) object_friend_request_id;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("friend_request_id",friend_request_id);
                    SocketClient.sendPacketAsyncStatic(new Request(TypeRequest.ACCEPT_FRIEND_REQUEST,jsonObject));
                });


            }
        });

    }

    public void setUIAfterFriendRequestHasBeenAcceptedByServer(JSONObject object){
        Platform.runLater(() -> {
            System.out.println("here");
            int friend_request_id = object.getInt("friend_request_id");
            ObservableList list_hbox = this.listViewFriendRequest.getItems();
            list_hbox.forEach((item) -> {
                HBox hbox = (HBox) item;
                ObservableList<Node> list_nodes = hbox.getChildren();
                list_nodes.forEach((node_element) ->{
                    if(node_element instanceof Button){
                        System.out.println(node_element.getProperties().get("idFriendRequest"));
                        int current_friend_request_id = (int) node_element.getProperties().get("idFriendRequest");
                        if (current_friend_request_id == object.getInt("friend_request_id")){
                            // We are on the right button where we must delete the parent hbox because the request was accepted
                            // TODO: A continuer : il faut enlever des friend request la hbox et ajouter la nouvelle relation dans la liste friendList, en pensant à changer le paramètre de cette fonction avec object qui ns renvoie aussi le nom de la personne avec qui l'utlisateur connecté est ami
                        }
                    }
                });
            });


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

    private addAFriendListHboxInFriendList(){
        // TODO: A continuer
    }
}
