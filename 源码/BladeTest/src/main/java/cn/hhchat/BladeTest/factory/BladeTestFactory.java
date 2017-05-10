package cn.hhchat.BladeTest.factory;

import cn.hhchat.BladeTest.adapter.IOCAdapter;
import cn.hhchat.BladeTest.adapter.InteceptorAdapter;

/**
 * Created this one by HMH on 2017/5/5.
 */
public class BladeTestFactory {
    private static IOCAdapter iocAdapter;
    private static InteceptorAdapter inteceptorAdapter;

    public static IOCAdapter getIocAdapter() {
        if (iocAdapter == null) {
            iocAdapter = new IOCAdapter();
        }else {
            iocAdapter.clear();
        }
        return iocAdapter;
    }

    public static InteceptorAdapter getInteceptorAdapter() {
        if (inteceptorAdapter == null) {
            inteceptorAdapter = new InteceptorAdapter();
        }else {
            inteceptorAdapter.clear();
        }
        return inteceptorAdapter;
    }
}
