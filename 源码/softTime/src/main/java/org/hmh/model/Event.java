package org.hmh.model;

import java.io.Serializable;

/**
 * Created by HMH on 2017/4/18.
 */

public class Event implements Serializable{
    int id;
    int state; //0开始  1结束  2其他结束
    long start_time;
    long end_time;
    String message;
    String some_thing_wrong;
    double percent;
    double work_time_of_day =8;
    int user_id;
    int repo_id;
    String plan;

    public int getRepo_id() {
        return repo_id;
    }

    public void setRepo_id(int repo_id) {
        this.repo_id = repo_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSome_thing_wrong() {
        return some_thing_wrong;
    }

    public void setSome_thing_wrong(String some_thing_wrong) {
        this.some_thing_wrong = some_thing_wrong;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getWork_time_of_day() {
        return work_time_of_day;
    }

    public void setWork_time_of_day(double work_time_of_day) {
        this.work_time_of_day = work_time_of_day;
    }

}
