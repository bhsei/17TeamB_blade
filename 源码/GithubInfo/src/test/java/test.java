import org.junit.Test;
import utils.ProgressUtil;
import utils.PropsUtil;

/**
 * Created by HMH on 2017/4/18.
 */
public class test {

    @Test
    public void testProgress() throws InterruptedException {
        ProgressUtil.show(39);
        ProgressUtil.show(26);
        ProgressUtil.show(6);
        ProgressUtil.show(6);

    }
}
