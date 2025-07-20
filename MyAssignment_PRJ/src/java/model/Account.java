package model;

public class Account {
    private int aid;
    private String username;
    private String password;
    private String displayname;
    private int eid; // Thêm dòng này

    // Getters & Setters cho eid
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }
    
    public Account(int aid, String username, String password, String displayname) {
        this.aid = aid;
        this.username = username;
        this.password = password;
        this.displayname = displayname;
        this.eid = eid;
    }

    // Getters và Setters
    public int getAid() { return aid; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getDisplayname() { return displayname; }
}
