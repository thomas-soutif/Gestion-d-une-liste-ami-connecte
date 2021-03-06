package network.Client.Handler;

import database.CLASSES.AccountUser;
import ihm.InitWindowConnectionController;
import ihm.InitWindowSignUpController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import network.Client.SocketClient;
import network.Common.Request;
import network.Common.Response;
import network.Common.TypeRequest;
import org.json.JSONException;
import org.json.JSONObject;

public final class HandlerAuthClient {
    private HandlerAuthClient(){}

    /**
     * @param pseudo Pseudo de l'utilisateur
     * @param motDePasse Mot de passe de l'utilisateur
     */
    public static void handlerUserConnexionRequest(String pseudo, String motDePasse){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pseudo",pseudo);
            jsonObject.put("password",motDePasse);
            Request request = new Request(TypeRequest.TOKEN_AUTHENTICATION, jsonObject);
            SocketClient.sendPacketAsyncStatic(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param response Reponse du serveur
     */
    public static void handlerUserConnexionResponse(Response response){
        if (response.getStatusResponse() == 200) {
            try {
                JSONObject jsonObject = new JSONObject(response.getContent()).getJSONObject("user");
                System.out.println(jsonObject);
                AccountUser accountUser = new AccountUser();
                accountUser.setPseudo(jsonObject.getString("pseudo"));
                accountUser.setName(jsonObject.getString("name"));
                accountUser.setFirstName(jsonObject.getString("firstName"));
                SocketClient.setUser(accountUser);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        InitWindowConnectionController.getInstance().gotoMainWindowInterface();
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erreur lors de la connexion de l'utilisateur");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Connexion refus??e");
                    alert.setHeaderText(null);
                    alert.setContentText("Une erreur lors de la connexion est survenue!");
                    alert.show();
                }
            });
        }
    }

    /**
     *
     * @param pseudo Pseudo de l'utilisateur
     * @param motDePasse Mot de passe de l'utilisateur
     * @param prenom Prenom de l'utilisateur
     * @param nom Nom de l'utilisateur
     */
    public static void handlerUserInscriptionRequest(String pseudo, String motDePasse, String prenom, String nom){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pseudo",pseudo);
            jsonObject.put("password",motDePasse);
            jsonObject.put("prenom", prenom);
            jsonObject.put("nom", nom);
            Request request = new Request(TypeRequest.INSCRIPTION, jsonObject);
            SocketClient.sendPacketAsyncStatic(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param response Reponse du serveur
     */
    public static void handlerUserInscriptionResponse(Response response){
        if (response.getStatusResponse() == 200) {

            try {
                JSONObject jsonObject = new JSONObject(response.getContent()).getJSONObject("user");
                System.out.println(jsonObject);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Inscription r??ussie");
                        alert.setHeaderText(null);
                        alert.setContentText("Vous ??tes dor??navant inscrit ?? l'application Liste d'Amis !");
                        alert.show();
                        InitWindowSignUpController.getInstance().gotoPageConnection();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Erreur lors de l'inscription de l'utilisateur");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Echec de l'inscription");
                    alert.setHeaderText(null);
                    alert.setContentText("Une erreur lors de l'inscription est survenue!");
                    alert.show();
                }
            });
        }
    }

}
