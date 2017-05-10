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
    public static  void init(){
        if(names.isEmpty()){
            for(int i=0;i<100;i++) {
                names.put(i, "User#"+i);
            }
        }
    }
    public String getUser(int id){
        return names.get(id);
    }
    public boolean addUser(int id, String name){
        if( names.get(id)!=null)
            return true;
        else
            return names.put(id,name)!=null;
    }
    public boolean removeUser(int id){
        return names.remove(id)!=null;
    }
}
