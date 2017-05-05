package cn.hhchat.BladeTest.exception;

/**
 * Created this one by HMH on 2017/5/5.
 */
public class BeanUnAvailableException extends Exception{
    public BeanUnAvailableException(String message) {
        super(message);
    }

    public BeanUnAvailableException(Throwable cause) {
        super(cause);
    }
}
