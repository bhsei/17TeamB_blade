package cn.hhchat.BladeTest.exception;

/**
 * Created this one by HMH on 2017/5/5.
 */
public class NoConfigurationFindException extends Exception {
    public NoConfigurationFindException() {
    }

    public NoConfigurationFindException(String message) {
        super(message);
    }

    public NoConfigurationFindException(Throwable cause) {
        super(cause);
    }
}
