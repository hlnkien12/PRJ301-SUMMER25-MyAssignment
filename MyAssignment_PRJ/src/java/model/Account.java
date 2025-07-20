package model;

public class Account {
    private int aid;
    private String username;
    private String password;
    private String displayname;

    public Account(int aid, String username, String password, String displayname) {
        this.aid = aid;
        this.username = username;
        this.password = password;
        this.displayname = displayname;
    }

    // Getters và Setters
    public int getAid() { return aid; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getDisplayname() { return displayname; }
}
