package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMH on 2017/4/18.
 */
public class UserEntity {
    String name;
    String email;
    List<CommitEntity> commitEntityList=new ArrayList<>();

    public UserEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserEntity() {
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

    public List<CommitEntity> getCommitEntityList() {
        return commitEntityList;
    }

    public void setCommitEntityList(List<CommitEntity> commitEntityList) {
        this.commitEntityList = commitEntityList;
    }

    @Override
    public String toString() {
        return getName()+" : "+getEmail()+" : 提交次数"+commitEntityList.size();
    }

    @Override
    public boolean equals(Object obj) {
        return getEmail().equals(((UserEntity)obj).getEmail());
    }
}
