package database.CLASSES;

import java.util.Date;

public class FriendRelation {

    private Integer id;
    private UserTemp firstUser;
    private UserTemp secondUser;
    private Date date;


    public UserTemp getFirstUser() {
        return firstUser;
    }

    public UserTemp getSecondUser() {
        return secondUser;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstUser(UserTemp firstUser) {
        this.firstUser = firstUser;
    }

    public void setSecondUser(UserTemp secondUser) {
        this.secondUser = secondUser;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "FriendRelation{" +
                "id=" + id +
                ", firstUser=" + firstUser +
                ", secondUser=" + secondUser +
                ", date=" + date +
                '}';
    }
}
