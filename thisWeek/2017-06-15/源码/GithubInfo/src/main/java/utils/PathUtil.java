package utils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * Created this one by HMH on 2017/6/13.
 */
public class PathUtil {
    /**
     * 获取桌面路径
     * @return 桌面绝对路径
     */
    public static String getDesktopPath() {
        return FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"/Desktop";
    }
}
