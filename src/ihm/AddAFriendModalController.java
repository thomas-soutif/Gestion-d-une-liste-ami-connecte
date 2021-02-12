package ihm;

import database.CLASSES.AccountUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class AddAFriendModalController {
    public ListView ListExistingUser;




    @FXML
    public void initialize() {
        HBox hbox = new HBox();

        Button button_add = new Button("Ajouter en ami");
        AccountUser user = new AccountUser();
        user.setId(45);
        button_add.getProperties().put("idFriendRequest",user);
        Label label_name = new Label("Kevin");
        hbox.getChildren().add(label_name);
        hbox.getChildren().add(button_add);
        this.ListExistingUser.getItems().add(hbox);

    }
    }
