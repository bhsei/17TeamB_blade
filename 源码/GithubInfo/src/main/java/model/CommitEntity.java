package model;

import utils.DateUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by HMH on 2017/4/18.
 */
public class CommitEntity {
    String message;
    Date date;
    UserEntity user;
    List<ClassificationEntity> classificationEntityList = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateStr() {
        return DateUtil.convert2Str(date,"yyyy年MM月dd日 HH:mm");
    }

    public void setDate(String date) throws ParseException {
        this.date = DateUtil.convert2Date(date, "yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("England/London"));
    }

    public Date getDate() {
        return this.date;
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
        return getDateStr() + " --- " + user.getName() + " : " + message  +  "  --- "+ classificationEntityList;
    }
}
