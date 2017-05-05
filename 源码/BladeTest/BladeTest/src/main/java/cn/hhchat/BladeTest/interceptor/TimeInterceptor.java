package cn.hhchat.BladeTest.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created this one by HMH on 2017/5/5.
 */
public class TimeInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(Interceptor.class);

    private Date startTime;

    @Override
    public void before() throws Throwable {
        startTime = new Date();
    }

    @Override
    public void after(Interceptor interceptor) {
        Date endTime = new Date();
        logger.debug(" ===> 花费时间："+(endTime.getTime()-startTime.getTime())/1000+" 毫秒\n\n");
    }
}
