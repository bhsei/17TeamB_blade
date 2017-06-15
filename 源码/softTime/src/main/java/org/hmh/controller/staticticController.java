package org.hmh.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.PathParam;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.http.wrapper.Session;
import org.hmh.dto.dtoWorkTime;
import org.hmh.model.Event;
import org.hmh.model.repo;
import org.hmh.model.user;
import org.hmh.service.EventService;
import org.hmh.service.repoService;
import org.hmh.service.userService;
import org.hmh.utils.dateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created this one by HMH on 2017/4/27.
 */
@Controller
public class staticticController {

    @Inject
    private userService userService;

    @Inject
    private repoService repoService;

    /**
     * 统计
     */
    @Route(value = "/statistic")
    public String getWorking(Session session, Request request, Response response) throws Exception, IOException {
        //获取表单信息
        int id = request.queryInt("id");
        user user = userService.getUser(id);
        if (user == null) {
            response.go("/login");
            return null;
        }
        repo repo = repoService.getRepoById(user.getRepo_id());
        List<dtoWorkTime> list = eventService.getEventList(repo);
        //获取本周
        List<dtoWorkTime> listThisWeek = eventService.getEventListBetween(repo, dateUtils.getWeekStartDate().getTime(),dateUtils.getWeekEndDate().getTime());
        List<dtoWorkTime> alllist = eventService.getEvents();
        session.attribute("work", list);
        session.attribute("workthisWeek", listThisWeek);
        session.attribute("alllist", alllist);
        return "statistic";
    }

    @Inject
    private EventService eventService;
}
