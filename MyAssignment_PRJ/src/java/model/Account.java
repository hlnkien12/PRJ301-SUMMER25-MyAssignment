package model;

public class Account {

    private int aid;
    private String username;
    private String password;
    private String displayname;
    private int eid;

    public Account() {
    }

    public Account(int aid, String username, String password, String displayname) {
        this.aid = aid;
        this.username = username;
        this.password = password;
        this.displayname = displayname;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }
}
