package org.hmh.repository;

import com.blade.ioc.annotation.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created this one by liuye on 17-5-5.
 */
@Component
public class UserRepository {
    private static  Map<Integer,String> names = new HashMap<Integer,String>();
    public  static  void start(){
        if(names.isEmpty()){
            for(int i=0;i<100;i++) {
                names.put(i, "User#"+i);
            }
        }
    }
    public  UserRepository(){
        start();
    }
    public String getUser(int id){
        return names.get(id);
    }
    public boolean addUser(int id, String name){
        if( names.get(id)!=null)
            return true;
        else {
            names.put(id,name);
            String _name = names.get(id);
          //  System.out.println(_name);
            return _name!= null;
        }
    }
    public boolean removeUser(int id){
        return names.remove(id)!=null;
    }
}
