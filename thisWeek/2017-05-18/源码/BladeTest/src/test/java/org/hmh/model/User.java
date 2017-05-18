package org.hmh.model;

/**
 * Created this one by HMH on 2017/5/12.
 */
public class User {
    String username;
    String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        User b = (User) obj;
        return this.username.equals(b.username) && this.password.equals(b.getPassword());
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
}
