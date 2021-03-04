package ihm;

import database.CLASSES.AccountUser;
import javafx.application.Platform;
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
import network.Common.Response;
import network.Common.TypeRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.text.StyledEditorKit;
import javax.xml.stream.FactoryConfigurationError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MainWindowController {

    private static MainWindowController instance;
    @FXML
    public Label pseudoLabel;
    @FXML
    private ListView listViewFriendRequest;
    @FXML
    private ListView friendList;
    @FXML
    private Button addAFriendButton;
    private String accept_button_text = "Accept";
    private String refuse_button_text = "Refuse";
    private Boolean not_have_friend_list;
    private Boolean not_have_friend_request;
    @FXML
    public void initialize() {
        System.out.println("aaa");
        instance = this;
        this.not_have_friend_list = false;
        this.not_have_friend_request = false;
        System.out.println("ayui");
        JSONObject jsonObject = new JSONObject();
        System.out.println("Demande de la liste d'ami");
        Request request2 = new Request(TypeRequest.FRIEND_REQUEST_LIST,jsonObject);
        SocketClient.sendPacketStatic(request2);
        this.friendList.getItems().add(new Label("Récupération de la liste de vos amis ..."));
        Request request = new Request(TypeRequest.FRIENDLIST, jsonObject);
        //SocketClient.sendPacketAsyncStatic(request);
        SocketClient.sendPacketStatic(request);

        Request request3 = new Request(TypeRequest.GET_USER_INFO_FOR_MAIN_WINDOWS,new JSONObject());
        SocketClient.sendPacketStatic(request3);

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
            System.out.println("Ajout de la liste d'ami");
            this.friendList.getItems().remove(0); // On retire le message définit dans l'init
            addFriendsOnListOnUI(object);
            return;



        });

    }


    public void addFriendsOnListOnUI(JSONObject object){

        JSONArray list_user = object.getJSONArray("list");
        System.out.println(list_user);

        if (this.not_have_friend_list){
            this.friendList.getItems().removeAll(this.friendList.getItems());
        }
        for( Object user_object : list_user){
            this.not_have_friend_list = false;
            JSONObject user = (JSONObject) user_object;
            HBox hBox = new HBox();
            Label label_name= new Label(user.getString("firstname") + " " + user.getString("name"));
            label_name.getProperties();
            Button button_remove_friend = new Button("Remove");
            button_remove_friend.getProperties().put("friend_relation_id",user.getInt("friend_relation_id"));
            button_remove_friend.setMaxHeight(70);
            hBox.getChildren().add(label_name);
            hBox.getChildren().add(button_remove_friend);

            this.friendList.getItems().add(hBox);
            System.out.println(user);


            button_remove_friend.setOnAction((ActionEvent event) ->{
                Button button = (Button) event.getSource();
                Object object_user_id= button.getProperties().get("friend_relation_id");
                int user_id = (int) object_user_id;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("friend_relation_id",user_id);
                SocketClient.sendPacketAsyncStatic(new Request(TypeRequest.REMOVE_FRIEND,jsonObject));
            });
        }
        System.out.println(this.friendList.getItems());
        if (this.friendList.getItems().isEmpty() && !this.not_have_friend_list ){
            HBox hBox = new HBox();
            Label label_name= new Label("VOUS N'AVEZ PAS D'AMIS ");
            hBox.getChildren().add(label_name);
            this.friendList.getItems().add(hBox);
            this.not_have_friend_list = true;
            if(this.not_have_friend_request){
                HBox hBox2 = new HBox();
                hBox2.getChildren().add(new Label("Ah, vous n'avez pas non plus de reqûetes d'amis on dirait.."));

                this.friendList.getItems().add(hBox2);
            }
        }

    }

    public void setFriendRequestListOnUI(JSONObject object){
        Platform.runLater(() -> {
            System.out.println("setFriendRequestListOnUi");
            JSONArray list_friend_request = object.getJSONArray("list_friend_request");
            int i=0;
            if (this.not_have_friend_request){
                this.listViewFriendRequest.getItems().removeAll(this.listViewFriendRequest.getItems());
            }
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

                button_refuse.setOnAction((ActionEvent event) ->{
                    Button button = (Button) event.getSource();
                    System.out.println(button.getProperties());
                    Object object_friend_request_id= button.getProperties().get("idFriendRequest");
                    int friend_request_id = (int) object_friend_request_id;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("friend_request_id",friend_request_id);
                    SocketClient.sendPacketAsyncStatic(new Request(TypeRequest.REFUSE_FRIEND_REQUEST,jsonObject));
                });


            }

            if (this.listViewFriendRequest.getItems().isEmpty() && !this.not_have_friend_request ){
                HBox hBox = new HBox();
                Label label_name= new Label("Vous n'avez pas de reqûetes - Tout vient à point à celui qui sait attendre (= ");
                hBox.getChildren().add(label_name);
                this.listViewFriendRequest.getItems().add(hBox);
                this.not_have_friend_request = true;
            }

        });

    }

    public void setPseudoOnWindowsUi(JSONObject jsonObject){
        Platform.runLater(() -> {
            System.out.println("=set pseudo");
            this.pseudoLabel.setText(jsonObject.getString("pseudo"));
            
        });
    }

    public void addOneFriendRelationOnUi(JSONObject jsonObject){
        Platform.runLater(()->{

            if(this.not_have_friend_list){
                this.friendList.getItems().removeAll(this.friendList.getItems());
            }

            HBox hBox = new HBox();
            Label label_name= new Label(jsonObject.getString("firstname") + " " + jsonObject.getString("name"));
            label_name.getProperties();
            Button button_remove_friend = new Button("Remove");
            button_remove_friend.getProperties().put("friend_relation_id",jsonObject.getInt("friend_relation_id"));
            button_remove_friend.setMaxHeight(70);
            hBox.getChildren().add(label_name);
            hBox.getChildren().add(button_remove_friend);

            this.friendList.getItems().add(hBox);

            button_remove_friend.setOnAction((ActionEvent event) ->{
                Button button = (Button) event.getSource();
                Object object_user_id= button.getProperties().get("friend_relation_id");
                int user_id = (int) object_user_id;
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("friend_relation_id",user_id);
                SocketClient.sendPacketAsyncStatic(new Request(TypeRequest.REMOVE_FRIEND,jsonObject2));
            });

        });
    }

    public void addFriendRequestOnUi(JSONObject jsonObject) {
        Platform.runLater(() -> {

            if (this.not_have_friend_request){
                this.listViewFriendRequest.getItems().removeAll(this.listViewFriendRequest.getItems());
            }

            Button button_accept = new Button("Accept");
            Button button_refuse = new Button("Refuse");
            button_accept.getProperties().put("idFriendRequest", jsonObject.getInt("request_id"));
            button_refuse.getProperties().put("idFriendRequest", jsonObject.getInt("request_id"));

            HBox hBox = new HBox();
            Label label_name = new Label(jsonObject.getString("firstname") + " " + jsonObject.getString("name"));
            hBox.getChildren().add(label_name);
            hBox.getChildren().add(button_accept);
            hBox.getChildren().add(button_refuse);

            this.listViewFriendRequest.getItems().add(hBox);

            button_accept.setOnAction((ActionEvent event) -> {
                Button button = (Button) event.getSource();
                System.out.println(button.getProperties());
                Object object_friend_request_id = button.getProperties().get("idFriendRequest");
                int friend_request_id = (int) object_friend_request_id;
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("friend_request_id", friend_request_id);
                SocketClient.sendPacketAsyncStatic(new Request(TypeRequest.ACCEPT_FRIEND_REQUEST, jsonObject2));
            });

            button_refuse.setOnAction((ActionEvent event) -> {
                Button button = (Button) event.getSource();
                System.out.println(button.getProperties());
                Object object_friend_request_id = button.getProperties().get("idFriendRequest");
                int friend_request_id = (int) object_friend_request_id;
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("friend_request_id", friend_request_id);
                SocketClient.sendPacketAsyncStatic(new Request(TypeRequest.REFUSE_FRIEND_REQUEST, jsonObject2));
            });

        });
    }

    public void setUIAfterFriendRequestHasBeenAcceptedByServer(JSONObject object){
        Platform.runLater(() -> {
            System.out.println("here");
            System.out.println(object);
            int friend_request_id = object.getInt("friend_request_id");
            ObservableList list_hbox = this.listViewFriendRequest.getItems();

            var ref_item_to_remove = new Object() {
                Node item_to_remove = null;
                public void set_item(Node item){
                    this.item_to_remove = item;
                }
            };

            list_hbox.forEach((item) -> {

                HBox hbox = (HBox) item;
                ObservableList<Node> list_nodes = hbox.getChildren();


                list_nodes.forEach((node_element) ->{

                    if(node_element instanceof Button){
                        System.out.println(node_element.getProperties().get("idFriendRequest"));
                        int current_friend_request_id = (int) node_element.getProperties().get("idFriendRequest");
                        if (current_friend_request_id == object.getInt("friend_request_id") && ((Button) node_element).getText().equals(this.getAccept_button_text())){
                            // We are on the right button where we must delete the parent hbox because the request was accepted
                            List<JSONObject> list_to_send = new ArrayList<>();
                            list_to_send.add(object);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("list", list_to_send);
                            addFriendsOnListOnUI(jsonObject); // On réutilise les fonctions de notre classe pour set l'interface
                            ref_item_to_remove.set_item(node_element.getParent());


                        }

                    }

                });

            });

            if (ref_item_to_remove.item_to_remove != null){
                this.listViewFriendRequest.getItems().remove(ref_item_to_remove.item_to_remove);
            }



        });

    }



    public void setUIAfterFriendRequestHasBeenRefusedByServer(JSONObject object){
        Platform.runLater(() -> {
            System.out.println(object);
            int friend_request_id = object.getInt("friend_request_id");
            ObservableList list_hbox = this.listViewFriendRequest.getItems();

            var ref_item_to_remove = new Object() {
                Node item_to_remove = null;
                public void set_item(Node item){
                    this.item_to_remove = item;
                }
            };

            list_hbox.forEach((item) -> {

                HBox hbox = (HBox) item;
                ObservableList<Node> list_nodes = hbox.getChildren();


                list_nodes.forEach((node_element) ->{

                    if(node_element instanceof Button){
                        int current_friend_request_id = (int) node_element.getProperties().get("idFriendRequest");
                        if (current_friend_request_id == object.getInt("friend_request_id") && ((Button) node_element).getText().equals(this.getRefuse_button_text())){
                            // We are on the right button where we must delete the parent hbox because the request was refused
                            ref_item_to_remove.set_item(node_element.getParent());
                        }
                    }
                });

            });

            if (ref_item_to_remove.item_to_remove != null){
                this.listViewFriendRequest.getItems().remove(ref_item_to_remove.item_to_remove);
            }



        });

    }

    public void setUIAfterFriendRemove(JSONObject object){
        Platform.runLater(() -> {
            int friend_relation_id = object.getInt("friend_relation_id");
            ObservableList list_hbox = this.friendList.getItems();

            var ref_item_to_remove = new Object() {
                Node item_to_remove = null;
                public void set_item(Node item){
                    this.item_to_remove = item;
                }
            };

            list_hbox.forEach((item) -> {

                HBox hbox = (HBox) item;
                ObservableList<Node> list_nodes = hbox.getChildren();


                list_nodes.forEach((node_element) ->{

                    if(node_element instanceof Button){
                        int current_friend_relation_id = (int) node_element.getProperties().get("friend_relation_id");
                        if (current_friend_relation_id == object.getInt("friend_relation_id")){
                            // We are on the right button where we must delete the parent hbox because we removed the friend
                            ref_item_to_remove.set_item(node_element.getParent());
                        }
                    }
                });

            });
            if (ref_item_to_remove.item_to_remove != null){
                this.friendList.getItems().remove(ref_item_to_remove.item_to_remove);
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

    public String getAccept_button_text() {
        return accept_button_text;
    }

    public String getRefuse_button_text() {
        return refuse_button_text;
    }

    public static MainWindowController getInstance() {
        return instance;
    }

    private void addAFriendListHboxInFriendList(){
        // TODO: A continuer
    }
}
