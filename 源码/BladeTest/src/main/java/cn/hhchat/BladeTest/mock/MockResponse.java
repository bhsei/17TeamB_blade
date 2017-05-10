package cn.hhchat.BladeTest.mock;

import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.view.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created this one by HMH on 2017/5/10.
 */
public class MockResponse implements Response{
    @Override
    public HttpServletResponse raw() {
        return null;
    }

    @Override
    public int status() {
        return 0;
    }

    @Override
    public Response status(int status) {
        return null;
    }

    @Override
    public Response badRequest() {
        return null;
    }

    @Override
    public Response unauthorized() {
        return null;
    }

    @Override
    public Response notFound() {
        return null;
    }

    @Override
    public Response conflict() {
        return null;
    }

    @Override
    public String contentType() {
        return null;
    }

    @Override
    public Response contentType(String contentType) {
        return null;
    }

    @Override
    public String header(String name) {
        return null;
    }

    @Override
    public Response header(String name, String value) {
        return null;
    }

    @Override
    public Response cookie(Cookie cookie) {
        return null;
    }

    @Override
    public Response cookie(String name, String value) {
        return null;
    }

    @Override
    public Response cookie(String name, String value, int maxAge) {
        return null;
    }

    @Override
    public Response cookie(String name, String value, int maxAge, boolean secured) {
        return null;
    }

    @Override
    public Response cookie(String path, String name, String value, int maxAge, boolean secured) {
        return null;
    }

    @Override
    public Response removeCookie(Cookie cookie) {
        return null;
    }

    @Override
    public Response removeCookie(String name) {
        return null;
    }

    @Override
    public Response text(String text) {
        return null;
    }

    @Override
    public Response html(String html) {
        return null;
    }

    @Override
    public Response json(String json) {
        return null;
    }

    @Override
    public Response json(Object bean) {
        return null;
    }

    @Override
    public Response xml(String xml) {
        return null;
    }

    @Override
    public OutputStream outputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter writer() throws IOException {
        return null;
    }

    @Override
    public Response render(String view) {
        return null;
    }

    @Override
    public Response render(ModelAndView modelAndView) {
        return null;
    }

    @Override
    public void redirect(String path) {

    }

    @Override
    public void go(String path) {

    }

    @Override
    public boolean isWritten() {
        return false;
    }
}
