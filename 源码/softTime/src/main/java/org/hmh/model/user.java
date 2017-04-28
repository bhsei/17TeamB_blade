package org.hmh.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created this one by HMH on 2017/4/18.
 */
public class user implements Serializable {

    private int id;
    private String name;
    private String password;
    private String nickname;
    private int repo_id;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public int getRepo_id() {
        return repo_id;
    }

    public void setRepo_id(int repo_id) {
        this.repo_id = repo_id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
