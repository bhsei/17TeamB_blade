package org.hmh.service;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
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
    public void setUser(int _id,String _name){
        id = _id;
        name = _name;
    }
    public UserService(){
    }
    public boolean storeToDB(){
        return userRepository.addUser(id,name);
    }
    public boolean removeFromDB(){
        return userRepository.removeUser(id);
    }
    public String getName(){
        return userRepository.getUser(id);
    }
    public String getName(int id){
         return userRepository.getUser(id);
    }
}
