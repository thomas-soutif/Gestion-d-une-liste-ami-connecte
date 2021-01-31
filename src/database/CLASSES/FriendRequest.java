package database.CLASSES;

import java.util.Date;

public class FriendRequest {

    private Integer id;
    private AccountUser from_user;
    private AccountUser to_user;
    private Date date;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFrom_user(AccountUser from_user) {
        this.from_user = from_user;
    }

    public void setTo_user(AccountUser to_user) {
        this.to_user = to_user;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public AccountUser getFrom_user() {
        return from_user;
    }

    public AccountUser getTo_user() {
        return to_user;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", from_user=" + from_user +
                ", to_user=" + to_user +
                ", date=" + date +
                '}';
    }
}
