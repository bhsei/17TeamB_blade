package org.hmh.service;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import org.hmh.model.user;

/**
 * Created this one by HMH on 2017/4/19.
 */
@Service
public class userService {
    @Inject
    private ActiveRecord activeRecord;

    public user getUser(String username,String password) {
        Take take = new Take(user.class).eq("name",username).eq("password",password);
        return activeRecord.one(take);
    }

    public user getUser(int userId) {
        Take take = new Take(user.class).eq("id",userId);
        return activeRecord.one(take);
    }


}
