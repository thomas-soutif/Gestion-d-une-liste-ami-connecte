package database.CLASSES;

import java.util.Date;

public class FriendRelation {

    private Integer id;
    private AccountUser firstUser;
    private AccountUser secondUser;
    private Date date;


    public AccountUser getFirstUser() {
        return firstUser;
    }

    public AccountUser getSecondUser() {
        return secondUser;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstUser(AccountUser firstUser) {
        this.firstUser = firstUser;
    }

    public void setSecondUser(AccountUser secondUser) {
        this.secondUser = secondUser;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
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

    public boolean IsInRelation(int userId)
    {
        return firstUser.getId() == userId || secondUser.getId() == userId;
    }
}
