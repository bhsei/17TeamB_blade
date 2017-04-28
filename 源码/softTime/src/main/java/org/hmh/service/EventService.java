package org.hmh.service;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import org.hmh.dto.dtoWorkTime;
import org.hmh.model.Event;
import org.hmh.model.repo;
import org.hmh.model.user;

import java.util.List;

/**
 * Created this one by HMH on 2017/4/19.
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

    public void save(Event event) {
        activeRecord.save(event);
    }

    public void update(Event event) {
        activeRecord.update(event);
    }

    public List<dtoWorkTime> getEventList(repo repo) {
        String sql = "  SELECT\n" +
                "    e.`id`                                                                AS `event_id`,\n" +
                "    u.`nickname`                                                           AS `nickname`,\n" +
                "    e.`message`                                                           AS `message`,\n" +
                "    e.`some_thing_wrong`                                                  AS `some_thing_wrong`,\n" +
                "    concat(format((((((e.`end_time` - e.`start_time`) / 1000) / 3600) / 8) * 100), 2),\n" +
                "           '%')                                                                            AS `percentage`,\n" +
                "    concat(format((((((e.`end_time` - e.`start_time`) / 1000) / 3600) / 8) *\n" +
                "                   e.`work_time_of_day`), 2), 'h')                        AS `act_time`,\n" +
                "    e.`plan`                                                              AS `plan`,\n" +
                "    e.`state`                                                             AS `state`,\n" +
                "    date_format(from_unixtime((e.`start_time` / 1000)), '%Y-%c-%d %H:%i') AS `start_time`,\n" +
                "    date_format(from_unixtime((e.`end_time` / 1000)), '%Y-%c-%d %H:%i')   AS `end_time`\n" +
                "  FROM (softTime.event as e\n" +
                "    JOIN `softTime`.`user` as u)\n" +
                "  WHERE (e.`user_id` IN\n" +
                "         (SELECT `id`\n" +
                "                                          FROM `softTime`.`user` as u2\n" +
                "                                          WHERE (u2.`repo_id` = (SELECT `softTime`.`repo`.`id`\n" +
                "                                                                                FROM `softTime`.`repo`\n" +
                "                                                                                WHERE (`softTime`.`repo`.`id` = ?))))\n" +
                "         AND (e.`state` = 2) AND (NOT ((e.`message` LIKE '%测试%'))) AND\n" +
                "         (e.`user_id` = u.`id`)\n" +
                "  )\n" +
                "  ORDER BY e.`id`;\n";
        return activeRecord.list(dtoWorkTime.class, sql, repo.getId());
    }

    public List<dtoWorkTime> getEventListBetween(repo repo,long start,long end) {
        String sql = "  SELECT\n" +
                "    e.`id`                                                                AS `event_id`,\n" +
                "    u.`nickname`                                                           AS `nickname`,\n" +
                "    e.`message`                                                           AS `message`,\n" +
                "    e.`some_thing_wrong`                                                  AS `some_thing_wrong`,\n" +
                "    concat(format((((((e.`end_time` - e.`start_time`) / 1000) / 3600) / 8) * 100), 2),\n" +
                "           '%')                                                                            AS `percentage`,\n" +
                "    concat(format((((((e.`end_time` - e.`start_time`) / 1000) / 3600) / 8) *\n" +
                "                   e.`work_time_of_day`), 2), 'h')                        AS `act_time`,\n" +
                "    e.`plan`                                                              AS `plan`,\n" +
                "    e.`state`                                                             AS `state`,\n" +
                "    date_format(from_unixtime((e.`start_time` / 1000)), '%Y-%c-%d %H:%i') AS `start_time`,\n" +
                "    date_format(from_unixtime((e.`end_time` / 1000)), '%Y-%c-%d %H:%i')   AS `end_time`\n" +
                "  FROM (softTime.event as e\n" +
                "    JOIN `softTime`.`user` as u)\n" +
                "  WHERE (e.`user_id` IN\n" +
                "         (SELECT `id`\n" +
                "                                          FROM `softTime`.`user` as u2\n" +
                "                                          WHERE (u2.`repo_id` = (SELECT `softTime`.`repo`.`id`\n" +
                "                                                                                FROM `softTime`.`repo`\n" +
                "                                                                                WHERE (`softTime`.`repo`.`id` = ?))))\n" +
                "         AND (e.`state` = 2) AND (NOT ((e.`message` LIKE '%测试%'))) AND\n" +
                "         (e.`user_id` = u.`id`) AND (e.start_time BETWEEN ? AND ?)\n" +
                "  )\n" +
                "  ORDER BY e.`id`;\n";
        return activeRecord.list(dtoWorkTime.class, sql, repo.getId(),start,end);
    }

    public List<dtoWorkTime> getEvents() {
        String sql = "  SELECT\n" +
                "    e.`id`                                                                AS `event_id`,\n" +
                "    u.`nickname`                                                           AS `nickname`,\n" +
                "    e.`message`                                                           AS `message`,\n" +
                "    e.`some_thing_wrong`                                                  AS `some_thing_wrong`,\n" +
                "    concat(format((((((e.`end_time` - e.`start_time`) / 1000) / 3600) / 8) * 100), 2),\n" +
                "           '%')                                                                            AS `percentage`,\n" +
                "    concat(format((((((e.`end_time` - e.`start_time`) / 1000) / 3600) / 8) *\n" +
                "                   e.`work_time_of_day`), 2), 'h')                        AS `act_time`,\n" +
                "    e.`plan`                                                              AS `plan`,\n" +
                "    e.`state`                                                             AS `state`,\n" +
                "    date_format(from_unixtime((e.`start_time` / 1000)), '%Y-%c-%d %H:%i') AS `start_time`,\n" +
                "    date_format(from_unixtime((e.`end_time` / 1000)), '%Y-%c-%d %H:%i')   AS `end_time`\n" +
                "  FROM (softTime.event as e\n" +
                "    JOIN `softTime`.`user` as u)\n" +
                "  WHERE (e.`state` = 2) AND (NOT ((e.`message` LIKE '%测试%'))) AND\n" +
                "         (e.`user_id` = u.`id`) "+"  ORDER BY e.`id`;";
        return activeRecord.list(dtoWorkTime.class, sql);
    }
}
