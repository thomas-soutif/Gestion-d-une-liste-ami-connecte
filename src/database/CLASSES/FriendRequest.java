package database.CLASSES;

public class FriendRequest {

    private int id;
    private String user_1;
    private String user_2;

    public int getId() {
        return id;
    }

    public String getUser_1() {
        return user_1;
    }

    public String getUser_2() {
        return user_2;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setUser_1(String user_1) {
        this.user_1= user_1;
    }

    public void setUser_2(String user_2) {
        this.user_2 = user_2;
    }

}
