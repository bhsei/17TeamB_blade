package org.hmh.service;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import org.hmh.model.User;
import org.hmh.repository.UserRepository;

/**
 * Created this one by liuye on 17-5-5.
 */
@Service
public class UserService {
    @Inject
    private UserRepository userRepository;

    private int id;
    private String name;
    public void setId(int _id){
        id = _id;
    }
    public void setName(String _name){
        name = _name;
    }

    public UserService(){
    }
    public boolean storeToDB(String username,String password) throws Exception {
        return userRepository.addUser(username,password);
    }
    public boolean removeFromDB(String username){
        return userRepository.removeUser(username);
    }
    public User getUser(String username){
        return userRepository.getUser(username);
    }
}
