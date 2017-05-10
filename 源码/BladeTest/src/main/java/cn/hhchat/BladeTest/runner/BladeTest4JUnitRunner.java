package cn.hhchat.BladeTest.runner;

import cn.hhchat.BladeTest.adapter.InteceptorAdapter;
import cn.hhchat.BladeTest.exception.InterceptorError;
import cn.hhchat.BladeTest.factory.BladeTestFactory;
import cn.hhchat.BladeTest.adapter.IOCAdapter;
import cn.hhchat.BladeTest.exception.BeanUnAvailableException;
import cn.hhchat.BladeTest.exception.NoConfigurationFindException;
import cn.hhchat.BladeTest.Statement.InterceptorStatement;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created this one by HMH on 2017/5/3.
 */
public class BladeTest4JUnitRunner extends BlockJUnit4ClassRunner {

    private static Logger logger = LoggerFactory.getLogger(BladeTest4JUnitRunner.class);

    public BladeTest4JUnitRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {

        //初始化IOC容器
        IOCAdapter iocAdapter = BladeTestFactory.getIocAdapter();
        try {
            iocAdapter.init(test);
        } catch (BeanUnAvailableException e) {
            logger.error("IOC初始化失败：", e);
        } catch (NoConfigurationFindException e) {
            logger.error("配置错误：");
        } catch (IllegalAccessException e) {
            logger.error("字段访问权限不足: ",e);
        }catch (Exception e) {
            logger.error("发生错误: ",e);
        }

        //添加拦截器
        InterceptorStatement statement = new InterceptorStatement(super.methodInvoker(method, test));
        InteceptorAdapter inteceptorAdapter = BladeTestFactory.getInteceptorAdapter();
        try {
            inteceptorAdapter.init(statement, test);
        } catch (InterceptorError interceptorError) {
            logger.error("拦截器错误："+inteceptorAdapter);
        }
        return statement;
    }

    @Override
    protected Statement withBefores(FrameworkMethod method, Object target, Statement statement) {

        logger.debug(" ===> 测试开始："+method.getName());
        return super.withBefores(method, target, statement);
    }
}
