Ce projet a été fait en collaboration avec : Aubin GARNIER, Jean-Michel DOSSOU et Fabien SAUL

C'est une application Java client / serveur qui permet de gérer une liste d'amis à la manière un peu des logiciels de gestions de jeux comme Steam.

Le focus principal a été fait sur la conception d'un code réutilisable pour gérer des requetes sur le réseau entre le client et le serveur et faire de la synchronisation entre les deux (via de l'écoute de chacun).

Vous pouvez retrouver les schemas UML à la racine, qui ont été généré avec le logiciel StarUML.

Le résultat de cette structure de code est le fruit d'une réflexion en amont, et d'une pratique axé le plus possible objet afin de permettre la réutilisation de cette base de code pour des gros projets de groupe.


**Structure du projet et configurations**

Il y'a 3 packages majeurs : 
ihm => Contient tout le code front en javaFX

database => Design pattern DAO

network => La gestion des sockets clients et serveurs.


Le code IHM utilise le package network.Client afin d'envoyer des reqûetes au serveur mais également faire de l'écoute sur le serveur.

Le serveur utilise le package network.Server et fait de l'écoute, et peut également envoyer des reqûetes au client.

Pour configurer l'adresse IP du serveur éditez le fichier network/Common/network.properties

Pour configurer l'adresse et le mot de passe de votre base de données, éditez le fichier database/ConnexionPostGreSQL.java



**Access à la base de données**

Si vous êtes curieux de voir à quoi ressemble la base de données, voiçi les identifiants (lecture seulement) :

Lien : https://phppgadmin.alwaysdata.com/
Identifiant : xelar_project-list-ami
Password : 2fmjWLrf@YjVKBt



**Gestion des erreurs personnalisés**

Dans le package database.EXCEPTIONS se trouve les classes personnalisés qui héritent de Exception. Pour ajouter un type d'erreur à gérer, il faut ajouter l'enum dans ErrorType. Les autres fichiers n'ont pas besoin d'être modifier. Voici un example de bonne utilisation de cette gestion d'erreur dans le code :

```
// Le fichier où l'on va lever l'exception personnalisée

public FriendRelation insert(FriendRelation obj) throws SQLException, CustomException {
        
        throw new CustomException("Les utilisateurs spécifiés sont déja amis", ErrorType.FRIEND_RELATION_ALREADY_EXIST);
    }
```

// Le main où l'on try catch notre fonction insert et gère notre exception personnalisée

```
class FriendRelationTestDAO {
    public static void main(String[] args) {

        FriendRelationDAO dao = new FriendRelationDAO();
        try{
            dao.insert(new FriendRelation());

        }catch(CustomException e){
            System.out.println(e.getErrorType()); // Affiche le type de l'erreur, ici FRIEND_ALREADY_EXIST
        }
        
    }
}
```


**Pour executer l'interface IHM, rajouter dans le run / Debug Configuration , cette ligne dans VM option :**

--module-path "C:\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml

Avec C:\javafx-sdk-11.0.2\lib le chemin de votre librairie javafx à vous
