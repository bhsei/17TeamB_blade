package org.hmh.model;

import java.io.Serializable;

/**
 * Created by HMH on 2017/4/18.
 */
public class repo implements Serializable{
    int id;
    String name;

    public repo(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
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

}
