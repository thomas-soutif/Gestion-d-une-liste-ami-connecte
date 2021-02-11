package ihm;

import database.CLASSES.AccountUser;
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

import java.io.IOException;

public class MainWindowController {


    public ListView listViewFriendRequest;
    public ListView friendList;


    @FXML
    public void initialize() {
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
        });

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
}
