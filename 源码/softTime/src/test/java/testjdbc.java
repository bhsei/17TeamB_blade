import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.ar.SampleActiveRecord;
import com.blade.jdbc.core.Take;
import com.blade.jdbc.ds.DataSourceFactory;
import com.blade.jdbc.model.PageRow;
import com.blade.jdbc.model.Paginator;
import com.blade.jdbc.tx.Tx;
import org.hmh.model.user;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMH on 2017/4/18.
 */
public class testjdbc {
    private static final Logger LOGGER = LoggerFactory.getLogger(testjdbc.class);

    private ActiveRecord activeRecord;

    protected DataSource testDefaultPool() {
        try {
            return DataSourceFactory.createDataSource("jdbc.properties");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Before
    public void before(){
        activeRecord = new SampleActiveRecord(testDefaultPool());
    }

    @Test
    public void testCount(){
        int count = activeRecord.count(new user());
        LOGGER.info("记录数：{}", count);
    }

    @Test
    public void testList(){
        List<user> personList = activeRecord.list( new user());
        LOGGER.info(personList.toString());
    }

    @Test
    public void testTakeList(){
        Take criteria = new Take(user.class);
        criteria.like("name", "Me%");
        List<user> user = activeRecord.list(criteria);
        LOGGER.info(user.toString());
    }

    @Test
    public void testSave(){
        user temp = new user();
        temp.setName("hello");
        temp.setPassword("hello");
        activeRecord.save(temp);
    }

    @Test
    public void testPage(){
        Paginator<user> page = activeRecord.page(new user(), 2, 10);
        LOGGER.info(page.toString());
    }

    @Test
    public void testListPage(){
        String sql = "select * from person";
        List<user> list = activeRecord.list(user.class, sql, new PageRow(1, 10, "created_at desc"));
        LOGGER.info(list.toString());
    }

    @Test
    public void testIn1(){
        List<user> list = activeRecord.list(new Take(user.class).in("id", 1, 2, 3));
        LOGGER.info(list.toString());
    }

    @Test
    public void testIn2(){
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        List<user> list = activeRecord.list(new Take(user.class).in("id", ids));
        LOGGER.info(list.toString());
    }

    @Test
    public void testOr(){
        Take take = new Take(user.class);
        take.eq("name", "Tarik");
        take.or("last_name", "=", "Tarik");
        int count = activeRecord.count(take);
        LOGGER.info(count+"");
    }

    @Test
    public void testTx(){
        user p1 = new user();
        Tx.begin();
        int c = 1/0;
        activeRecord.update(p1);
        Tx.commit();
    }
}
