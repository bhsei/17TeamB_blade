package cn.hhchat.BladeTest.Statement;

import cn.hhchat.BladeTest.interceptor.Interceptor;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created this one by HMH on 2017/5/5.
 */
public class InterceptorStatement extends Statement {

    private final Statement invoker;
    private List<Interceptor> interceptors = new ArrayList<>(2);

    public InterceptorStatement(Statement invoker) {
        this.invoker = invoker;
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    @Override
    public void evaluate() throws Throwable {
        for (Interceptor interceptor : interceptors)
        {
            interceptor.before();
        }
        invoker.evaluate();
        for (Interceptor interceptor : interceptors)
        {
            interceptor.after(interceptor);
        }
    }
}
