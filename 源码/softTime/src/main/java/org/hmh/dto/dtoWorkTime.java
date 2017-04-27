package org.hmh.dto;

import java.io.Serializable;

/**
 * Created this one by HMH on 2017/4/27.
 */
public class dtoWorkTime implements Serializable{
    private String event_id;
    private String nickname;
    private String message;
    private String some_thing_wrong;
    private String start_time;
    private String end_time;
    private String percentage;
    private String plan;
    private String act_time;
    private String work_time;
    private int state;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getAct_time() {
        return act_time;
    }

    public void setAct_time(String act_time) {
        this.act_time = act_time;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
