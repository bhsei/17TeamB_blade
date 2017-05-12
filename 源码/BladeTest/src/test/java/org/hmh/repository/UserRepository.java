package org.hmh.repository;

import com.blade.ioc.annotation.Component;
import org.hmh.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created this one by liuye on 17-5-5.
 */
@Component
public class UserRepository {
    private static Map<String, User> userMap=new HashMap<>();

    public  static  void start(){
        userMap.clear();
        userMap.put("hello", new User("hello", "hello"));
    }
    public  UserRepository(){
        start();
    }
    public User getUser(String username){
        return userMap.get(username);
    }
    public boolean addUser(String name,String password) throws Exception {
        if (userMap.get(name) != null) {
            throw new Exception("重复注册");
        } else {
            User user = new User();
            user.setUsername(name);
            user.setPassword(password);
            userMap.put(name, user);
            return true;
        }
    }
    public boolean removeUser(String name){
        User user = userMap.get(name);
        if (user != null) {
            userMap.remove(user);
        }
        return true;
    }
}
