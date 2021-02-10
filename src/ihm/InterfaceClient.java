package ihm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import network.Client.SocketClient;
import network.Server.MainServer;

import java.io.IOException;
import java.util.Properties;

public class InterfaceClient extends Application {
    private boolean isConnected;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        isConnected = serverConnection();
        super.init();
    }

    @Override
    public void start(Stage primaryStage) {
        if (isConnected){
            try {
                System.out.println("Connecté au serveur");
                System.out.println("Test2");
                Parent root = FXMLLoader.load(getClass().getResource("TestClient.fxml"));
                System.out.println("test 3");
                Scene scene = new Scene(root);
                primaryStage.getIcons().add(new Image("https://image.noelshack.com/fichiers/2015/19/1431246599-hap512.png"));
                primaryStage.setTitle("Interface Test");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La connexion avec le serveur a échoué");
            alert.showAndWait();
            Platform.exit();
        }

    }

    @Override
    public void stop() throws Exception {
        System.out.println("Fin de l'application");
        SocketClient.close();
        super.stop();
    }

    private static boolean serverConnection(){
        boolean connected;
        try {
            Properties servProperties = new Properties();
            servProperties.load(MainServer.class.getResourceAsStream("../Common/network.properties"));
            String address = servProperties.getProperty("address");
            int port = Integer.parseInt(servProperties.getProperty("port"));
            connected = SocketClient.create(address,port);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return connected;
    }



}
