package cn.hhchat.BladeTest.exception;

/**
 * Created this one by HMH on 2017/5/5.
 */
public class InterceptorError extends Exception {
    public InterceptorError() {
    }

    public InterceptorError(String message) {
        super(message);
    }

    public InterceptorError(String message, Throwable cause) {
        super(message, cause);
    }

    public InterceptorError(Throwable cause) {
        super(cause);
    }

    public InterceptorError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
