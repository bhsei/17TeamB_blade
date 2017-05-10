package cn.hhchat.BladeTest.mock;

import com.blade.kit.CollectionKit;
import com.blade.kit.IPKit;
import com.blade.kit.ObjectKit;
import com.blade.kit.StringKit;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.wrapper.ServletRequest;
import com.blade.mvc.http.wrapper.Session;
import com.blade.mvc.multipart.FileItem;
import com.blade.mvc.route.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Created this one by HMH on 2017/5/10.
 */
public class MockRequest implements Request {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletRequest.class);

    private static final String USER_AGENT = "user-agent";
    protected Route route;

    /**
     * path parameter eg: /user/12
     */
    private Map<String, String> pathParams = CollectionKit.newConcurrentHashMap(8);

    /**
     * query parameter eg: /user?name=jack
     */
    private Map<String, String> queryParams = CollectionKit.newConcurrentHashMap(8);

    private Map<String, FileItem> fileItems = CollectionKit.newConcurrentHashMap(8);

    private Session session = null;
    private boolean isAbort = false;

    public MockRequest() {
        init();
    }

    public void init() {
        parameterMap = CollectionKit.newConcurrentHashMap(8);
        pathParams=CollectionKit.newConcurrentHashMap(8);
        headers=CollectionKit.newConcurrentHashMap(8);
        parameter=CollectionKit.newConcurrentHashMap(8);
    }

    private String join(String[] arr) {
        StringBuilder ret = new StringBuilder();
        for (String item : arr) {
            ret.append(',').append(item);
        }
        if (ret.length() > 0) {
            return ret.substring(1);
        }
        return ret.toString();
    }

    @Override
    public HttpServletRequest raw() {
        return null;
    }

    private String host;

    public MockRequest host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public String host() {
        return host;
    }

    private String url;

    public MockRequest url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String url() {
        return url;
    }

    private String uri;

    public MockRequest uri(String uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public String uri() {
        return uri;
    }

    private String userAgent;

    public MockRequest userAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Override
    public String userAgent() {
        return userAgent;
    }

    private String pathInfo;

    public MockRequest pathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
        return this;
    }

    @Override
    public String pathInfo() {
        return pathInfo;
    }

    private String protocol;

    public MockRequest protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    @Override
    public String protocol() {
        return protocol;
    }

    private String servletPath;

    public MockRequest servletPath(String servletPath) {
        this.servletPath = servletPath;
        return this;
    }

    @Override
    public String servletPath() {
        return servletPath;
    }

    private String contextPath;

    public MockRequest contextPath(String contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    @Override
    public String contextPath() {
        return contextPath;
    }

    @Override
    public ServletContext context() {
        return null;
    }

    public MockRequest pathParams(String key,String value){
        pathParams.put(key, value);
        return this;
    }

    @Override
    public Map<String, String> pathParams() {
        return pathParams;
    }

    @Override
    public String pathParam(String name) {
        return pathParams.get(name);
    }

    @Override
    public String pathString(String name) {
        return pathParams.get(name);
    }

    @Override
    public String pathParam(String name, String defaultValue) {
        String val = pathParams.get(name);
        if (null == val) {
            val = defaultValue;
        }
        return val;
    }

    @Override
    public String pathString(String name, String defaultValue) {
        String val = pathParams.get(name);
        if (null == val) {
            val = defaultValue;
        }
        return val;
    }

    @Override
    public int pathParamAsInt(String name) {
        String value = pathString(name);
        if (StringKit.isNotBlank(value)) {
            return Integer.parseInt(value);
        }
        return 0;
    }

    @Override
    public int pathInt(String name) {
        String value = pathString(name);
        if (StringKit.isNotBlank(value)) {
            return Integer.parseInt(value);
        }
        return 0;
    }

    @Override
    public long pathParamAsLong(String name) {
        String value = pathString(name);
        if (StringKit.isNotBlank(value)) {
            return Long.parseLong(value);
        }
        return 0;
    }

    @Override
    public long pathLong(String name) {
        String value = pathString(name);
        if (StringKit.isNotBlank(value)) {
            return Long.parseLong(value);
        }
        return 0;
    }

    private String queryString;

    public MockRequest queryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    @Override
    public String queryString() {
        return queryString;
    }

    Map<String,String[]> parameterMap;

    public MockRequest parameterMap(String name, String[] value) {
        parameterMap.put(name, value);
        return this;
    }

    @Override
    public Map<String, String> querys() {
        Map<String, String> params = CollectionKit.newConcurrentHashMap(8);
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            params.put(entry.getKey(), join(entry.getValue()));
        }
        params.putAll(queryParams);
        return Collections.unmodifiableMap(params);
    }

    Map<String,String> parameter;

    public MockRequest parameter(String key,String value) {
        parameter.put(key, value);
        return this;
    }

    @Override
    public String query(String name) {
        return parameter.get(name);
    }

    @Override
    public String query(String name, String defaultValue) {
        String val = this.query(name);
        if (null == val) {
            val = defaultValue;
        }
        return val;
    }

    @Override
    public int queryAsInt(String name) {
        return queryInt(name);
    }

    @Override
    public int queryInt(String name) {
        return queryInt(name, 0);
    }

    @Override
    public int queryInt(String name, int defaultValue) {
        String value = query(name);
        if (StringKit.isBlank(value)) {
            return defaultValue;
        }
        return Integer.valueOf(value);
    }

    @Override
    public long queryAsLong(String name) {
        return queryLong(name);
    }

    @Override
    public long queryLong(String name) {
        return queryLong(name, 0);
    }

    @Override
    public long queryLong(String name, long defaultValue) {
        String value = query(name);
        if (StringKit.isBlank(value)) {
            return defaultValue;
        }
        return Long.valueOf(value);
    }

    @Override
    public double queryAsDouble(String name) {
        String value = query(name);
        if (StringKit.isBlank(value)) {
            return 0;
        }
        return Double.valueOf(value);
    }

    @Override
    public double queryDouble(String name) {
        return queryDouble(name, 0);
    }

    @Override
    public double queryDouble(String name, double defaultValue) {
        String value = query(name);
        if (StringKit.isBlank(value)) {
            return defaultValue;
        }
        return Double.valueOf(value);
    }

    private String method;

    public MockRequest method(String method) {
        this.method = method;
        return this;
    }

    @Override
    public String method() {
        return method;
    }

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.valueOf(method().toUpperCase());
    }

    @Override
    public String address() {
        return IPKit.getIpAddrByRequest(raw());
    }

    @Override
    public Session session() {
        return null;
    }

    @Override
    public Session session(boolean create) {
        return null;
    }

    private Map<String,Object> attribute;

    @Override
    public void attribute(String name, Object value) {
        attribute.put(name, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T attribute(String name) {
        Object object = attribute.get(name);
        if (null != object) {
            return (T) object;
        }
        return null;
    }

    @Override
    public Set<String> attributes() {
        Set<String> attrList = CollectionKit.newHashSet(8);
        attrList.addAll(attribute.keySet());
        return attrList;
    }

    private String contentType;

    public MockRequest contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public String contentType() {
        return contentType;
    }

    private int port;

    public MockRequest port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public int port() {
        return port;
    }

    private boolean isSecure;

    public MockRequest isSecure(boolean isSecure) {
        this.isSecure = isSecure;
        return this;
    }

    @Override
    public boolean isSecure() {
        return isSecure;
    }

    @Override
    public boolean isAjax() {
        return null != header("x-requested-with") && "XMLHttpRequest".equals(header("x-requested-with"));
    }

    @Override
    public Map<String, Cookie> cookies() {
        return null;
    }

    private Cookie map(Cookie servletCookie) {
        Cookie cookie = new Cookie(servletCookie.getName(), servletCookie.getValue());
        cookie.setMaxAge(servletCookie.getMaxAge());
        cookie.setHttpOnly(servletCookie.isHttpOnly());
        String path = servletCookie.getPath();
        if (null != path) {
            cookie.setPath(path);
        }
        String domain = servletCookie.getDomain();
        if (null != domain) {
            cookie.setDomain(domain);
        }
        cookie.setSecure(servletCookie.getSecure());
        return cookie;
    }

    @Override
    public String cookie(String name) {
        return this.cookie(name, null);
    }

    @Override
    public String cookie(String name, String defaultValue) {
        Cookie cookie = cookieRaw(name);
        if (null != cookie) {
            return cookie.getValue();
        }
        return defaultValue;
    }

    @Override
    public Cookie cookieRaw(String name) {
        return null;
    }

    private Map<String,String> headers;

    public MockRequest headers(String name, String value) {
        headers.put(name, value);
        return this;
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public String header(String name) {
        return headers.get(name);
    }

    @Override
    public String header(String name, String defaultValue) {
        String value = header(name);
        if (StringKit.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public void encoding(String encoding) {
//        ignore
    }

    @Override
    public void setRoute(Route route) {
        this.route = route;
        this.pathParams = route.getPathParams();
    }

    @Override
    public Route route() {
        return this.route;
    }

    @Override
    public void abort() {
        this.isAbort = true;
    }

    @Override
    public boolean isAbort() {
        return this.isAbort;
    }

    @Override
    public <T> T model(String slug, Class<? extends Serializable> clazz) {
        if (StringKit.isNotBlank(slug) && null != clazz) {
            return ObjectKit.model(slug, clazz, querys());
        }
        return null;
    }

    @Override
    public FileItem[] files() {
        return this.fileItems.values().toArray(new FileItem[fileItems.size()]);
    }

    @Override
    public Map<String, FileItem> fileItems() {
        return this.fileItems;
    }

    @Override
    public FileItem fileItem(String name) {
        return this.fileItems.get(name);
    }

    @Override
    public BodyParser body() {
        return null;
    }
}
