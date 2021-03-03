package ihm;

import database.CLASSES.AccountUser;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import network.Client.SocketClient;
import network.Common.Request;
import network.Common.TypeRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddAFriendModalController {
    public ListView ListExistingUser;
    private static AddAFriendModalController instance;


    @FXML
    public void initialize() {
        instance = this;
        HBox hbox = new HBox();
        Button button_add = new Button("Ajouter en ami");
        AccountUser user = new AccountUser();
        user.setId(45);
        button_add.getProperties().put("idFriendRequest", user);
        Label label_name = new Label("Kevin");
        hbox.getChildren().add(label_name);
        hbox.getChildren().add(button_add);
        this.ListExistingUser.getItems().add(hbox);

        JSONObject jsonObject = new JSONObject();
        Request request = new Request(TypeRequest.USER_ADD_FRIEND_LIST, jsonObject);
        SocketClient.sendPacketAsyncStatic(request);
    }


    public void setFriendAddListOnUI(JSONObject object) {

        Platform.runLater(() -> {
            this.ListExistingUser.getItems().remove(0);
            System.out.println("setFriendAddListOnUI");
            try {
                JSONArray list_add_friend = object.getJSONArray("list_add_friend_list");
                for (Object user_add_friend : list_add_friend) {

                    JSONObject user_add_friend_json = (JSONObject) user_add_friend;
                    System.out.println(user_add_friend);

                    HBox hBox = new HBox();
                    Label label_name = new Label(user_add_friend_json.getString("firstName") + " " + user_add_friend_json.getString("name"));
                    label_name.getProperties().put("user_id", user_add_friend_json.getInt("id"));
                    Button button_add = new Button("Ajouter en ami");
                    button_add.getProperties().put("friend_add_request_id", user_add_friend_json.getInt("id"));
                    hBox.getChildren().add(label_name);
                    hBox.getChildren().add(button_add);

                    this.ListExistingUser.getItems().add(hBox);
                    System.out.println(user_add_friend_json);

                    button_add.setOnAction((ActionEvent event) -> {
                        Button button = (Button) event.getSource();
                        System.out.println(button.getProperties());
                        Object object_friend_add_request_id = button.getProperties().get("friend_add_request_id");
                        int friend_add_request_id = (int) object_friend_add_request_id;
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("friend_add_request_id", friend_add_request_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(jsonObject);
                        SocketClient.sendPacketAsyncStatic(new Request(TypeRequest.ADD_FRIEND_REQUEST, jsonObject));
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    public void setButtonAcceptIU(JSONObject object) {
        Platform.runLater(() -> {
            System.out.println("here");
            System.out.println(object);
            try {
                int friend_add_request_id = object.getInt("friend_add_request_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ObservableList list_hbox = this.ListExistingUser.getItems();

            var ref_item_to_remove = new Object() {
                Node item_to_remove = null;

                public void set_item(Node item) {
                    this.item_to_remove = item;
                }
            };

            list_hbox.forEach((item) -> {

                HBox hbox = (HBox) item;
                ObservableList<Node> list_nodes = hbox.getChildren();


                list_nodes.forEach((node_element) ->{
                    // A faire griser le button
                    if(node_element instanceof Button){
                        System.out.println(node_element.getProperties().get("friend_add_request_id"));
                        int current_add_friend_request_id = (int) node_element.getProperties().get("friend_add_request_id");
                        try {
                            if (current_add_friend_request_id == object.getInt("friend_add_request_id")){
                                ((Button) node_element).setDisable(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });

            });

        });
    }

    public static AddAFriendModalController getInstance() {
        return instance;
    }

}
