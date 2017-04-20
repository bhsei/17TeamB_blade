package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMH on 2017/4/18.
 */
public class RepoEntity {
    List<CommitEntity> commitEntityList=new ArrayList<>();
    List<UserEntity> userEntityList = new ArrayList<>();

    public RepoEntity() {
    }

    String path;
    String name;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CommitEntity> getCommitEntityList() {
        return commitEntityList;
    }

    public void setCommitEntityList(List<CommitEntity> commitEntityList) {
        this.commitEntityList = commitEntityList;
    }

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }

    public void updateUser(UserEntity userEntity) {
        for(int i=0;i<userEntityList.size();i++) {
            if (userEntityList.get(i).getEmail().equals(userEntity.getEmail())||userEntityList.get(i).getName().equals(userEntity.getName())) {
                userEntityList.set(i, userEntity);
                return;
            }
        }
        userEntityList.add(userEntity);
    }



    public void addcommit(CommitEntity commitEntity) {
        commitEntityList.add(commitEntity);
    }
}
