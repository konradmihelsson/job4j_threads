package ru.job4j.pool;

public class User {

    private final String userName;
    private final String eMail;

    public User(String userName, String eMail) {
        this.userName = userName;
        this.eMail = eMail;
    }

    public String getUserName() {
        return userName;
    }

    public String geteMail() {
        return eMail;
    }
}
