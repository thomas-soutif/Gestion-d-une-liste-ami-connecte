Pour accèder à la base de données Postgre depuis le navigateur :

https://phppgadmin.alwaysdata.com/

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
