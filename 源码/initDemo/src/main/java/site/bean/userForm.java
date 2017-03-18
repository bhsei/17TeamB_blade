package site.bean;

/**
 * Created by HMH on 2017/3/13.
 */
public class userForm {
    String username;
    String password;

    public userForm() {
    }

    public userForm(String username, String password) {
        this.username = username;
        this.password = password;
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
