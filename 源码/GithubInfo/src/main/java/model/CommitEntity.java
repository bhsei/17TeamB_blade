package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMH on 2017/4/18.
 */
public class CommitEntity {
    String message;
    String date;
    UserEntity user;
    List<ClassificationEntity> classificationEntityList = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void addClass(ClassificationEntity ce) {
        classificationEntityList.add(ce);
    }

    @Override
    public String toString() {
        return date + " --- " + user.getName() + " : " + message  +  "  --- "+ classificationEntityList;
    }
}
