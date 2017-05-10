package cn.hhchat.BladeTest.mock;

import com.blade.Blade;
import com.blade.ioc.Ioc;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.route.Route;
import com.blade.mvc.route.RouteMatcher;
import com.blade.mvc.route.Routers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created this one by HMH on 2017/5/10.
 */
public class RouteMock {

    private Routers routers;
    private RouteMatcher matcher;
    private Ioc ioc;

    public void init() {
        Blade blade = Blade.$();
        routers = blade.routers();
        matcher = blade.routeMatcher();
        ioc = blade.ioc();
    }

    private Route getRoute(String path,HttpMethod method) {
        return matcher.lookupRoute(method.name(),path );
    }

    public Object Request(String url, HttpMethod httpMethod, Object... params) throws InvocationTargetException, IllegalAccessException {
        Route route = getRoute(url,httpMethod);
        assert route != null;
        Method method = route.getAction();
        route.getPath();
        Object target = route.getTarget();
        if (null == target) {
            Class<?> clazz = route.getAction().getDeclaringClass();
            target = ioc.getBean(clazz);
            route.setTarget(target);
        }
        return method.invoke(target,params);
    }

    public RouteMock() {
    }

    public void setRouters(Routers routers) {
        this.routers = routers;
    }

    public void setMatcher(RouteMatcher matcher) {
        this.matcher = matcher;
    }

    public RouteMock(Routers routers) {
        this.routers = routers;
        this.matcher = new RouteMatcher(routers);
        matcher.update();
    }
}
