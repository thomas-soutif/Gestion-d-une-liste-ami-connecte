package Ihm;

import Reseau.Client.SocketClient;
import Reseau.Common.Request;
import Reseau.Common.Response;
import Reseau.Common.TypeRequest;
import Reseau.Common.TypeResponse;
import Reseau.Server.MainServer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class InterfaceClient extends Application {

    private static SocketClient socketClient;

    private javafx.scene.control.Button buttonSendRequest;
    private javafx.scene.control.Button buttonSendResponse;

    public static void main(String[] args) {
        try {
            Properties servProperties = new Properties();
            servProperties.load(MainServer.class.getResourceAsStream("../Common/network.properties"));
            String adresse = servProperties.getProperty("adresse");
            Integer port = Integer.valueOf(servProperties.getProperty("port"));
            socketClient = new SocketClient(adresse,port);
            System.out.println("Test1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Test2");
            Parent root = FXMLLoader.load(getClass().getResource("TestClient.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Interface Test");
            primaryStage.setScene(scene);
            primaryStage.show();

            buttonSendRequest = (javafx.scene.control.Button) scene.lookup("#buttonTest");
            buttonSendResponse = (javafx.scene.control.Button) scene.lookup("#buttonTest2");
            buttonSendRequest.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("Click");
                    socketClient.sendPacket(new Request(TypeRequest.TOKEN_AUTHENTIFICATION));
                }
            });
            buttonSendResponse.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("Click");
                    socketClient.sendPacket(new Response(TypeResponse.TOKEN_AUTHENTIFICATION,200));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
