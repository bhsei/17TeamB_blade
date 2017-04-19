package org.hmh.service;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import org.hmh.model.Event;
import org.hmh.model.user;

/**
 * Created by HMH on 2017/4/19.
 */
@Service
public class EventService {
    @Inject
    private ActiveRecord activeRecord;
    public Event getActiveEvent(user user) {
        Take take = new Take(Event.class).eq("state", 1).eq("user_id",user.getId());
        Event event = activeRecord.one(take);
        return event;
    }

    public Event save(Event event) {
        activeRecord.save(event);

        return event;
    }

    public Event update(Event event) {
        activeRecord.update(event);
        return event;
    }
}
