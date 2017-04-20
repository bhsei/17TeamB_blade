package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMH on 2017/4/18.
 */
public class ClassificationEntity {
    int id;
    String name;
    List<CommitEntity> commitEntityList = new ArrayList<>();

    public List<CommitEntity> getCommitEntityList() {
        return commitEntityList;
    }

    public void setCommitEntityList(List<CommitEntity> commitEntityList) {
        this.commitEntityList = commitEntityList;
    }

    public ClassificationEntity() {
    }

    public void clearCommit(){
        commitEntityList.clear();
    }

    public ClassificationEntity(String name) {
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

    @Override
    public String toString() {
        return name;
    }
}
