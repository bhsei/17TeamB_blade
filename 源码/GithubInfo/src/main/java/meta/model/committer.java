package meta.model;

/**
 * Created by HMH on 2017/4/18.
 */
public class committer {
    private String name;
    private String email;
    private String date;

    public committer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
