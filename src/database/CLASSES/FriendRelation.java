package database.CLASSES;

import java.util.Date;

public class FriendRelation {

    private int id;
    private String from_user;
    private String to_user;
    private Date date;

    public int getId() {
        return id;
    }

    public String getFrom_user() {
        return from_user;
    }

    public String getTo_user() {
        return to_user;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setFrom_user(String from_user) {
        this.from_user= from_user;
    }

    public void setTo_user(String to_user) {
        this.to_user = to_user;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
