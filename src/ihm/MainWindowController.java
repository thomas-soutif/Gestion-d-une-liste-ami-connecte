package ihm;

import database.CLASSES.AccountUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class MainWindowController {


    public ListView listViewFriendRequest;



    @FXML
    public void initialize() {
        HBox hbox = new HBox();

        Button button_accept = new Button("Accept");
        AccountUser user = new AccountUser();
        user.setId(45);
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
        });

    }
}
