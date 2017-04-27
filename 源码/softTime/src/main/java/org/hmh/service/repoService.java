package org.hmh.service;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import org.hmh.model.Event;
import org.hmh.model.repo;
import org.hmh.model.user;

import java.util.List;

/**
 * Created this one by HMH on 2017/4/19.
 */
@Service
public class repoService {
    @Inject
    private ActiveRecord activeRecord;

    public repo getRepoById(int id) {
        Take take = new Take(repo.class).eq("id", id);
        return activeRecord.one(take);
    }

    public List<user> getAllUser(int id) {
        Take take = new Take(user.class).eq("repo_id", id);
        return activeRecord.list(take);
    }
}
