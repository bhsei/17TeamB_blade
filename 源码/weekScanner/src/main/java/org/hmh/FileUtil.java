package org.hmh;

import java.io.*;
import java.util.List;

/**
 * Created this one by HMH on 2017/5/15.
 */
public class FileUtil {

    public static String dealPath(String path) {
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    public static void copyFile(File src, File dist) throws IOException {
        File dic = dist.getParentFile();
        if(!dic.exists()) dic.mkdirs();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src)); BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dist))) {
            byte[] bytes = new byte[1024];
            int len;
            while (-1 != (len = bis.read(bytes))) {
                bos.write(bytes, 0, len);
            }
            bos.flush();
        }
    }

    public static String getRelativePath(File file, File root) {
        String absPath = file.getAbsolutePath();
        return absPath.substring(root.getAbsolutePath().length());
    }
    public static void deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件
                    deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        } else {
            System.out.println("所删除的文件不存在");
        }
    }


}
