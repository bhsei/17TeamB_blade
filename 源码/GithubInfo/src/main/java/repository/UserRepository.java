package repository;

import meta.model.author;
import model.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMH on 2017/4/18.
 */
public class UserRepository {

    static List<UserEntity> userList;

    static{
        userList = new ArrayList<>();
    }

    public static void add(UserEntity user) {
        userList.add(user);
    }

    public static void remove(UserEntity user) {
        userList.remove(user);
    }

    public static UserEntity getUser(author userEntity) {
        for(int i=0;i<userList.size();i++) {
            if (userList.get(i).getEmail().equals(userEntity.getEmail())||userList.get(i).getName().equals(userEntity.getName())) {
                return userList.get(i);
            }
        }
        return null;
    }

    public static void updateUser(UserEntity userEntity) {
        for(int i=0;i<userList.size();i++) {
            if (userList.get(i).getEmail().equals(userEntity.getEmail())) {
                userList.set(i,userEntity) ;
                return ;
            }
        }
        add(userEntity);
    }

    public static List<UserEntity> getAll() {
        return userList;
    }

}
