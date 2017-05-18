package org.hmh;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<File> lists = new ArrayList<>();
        String rootPath;
        if (args == null || args.length == 0) {
            System.out.println("请输入项目根路径");
            return;
        }else {
            rootPath = args[0];
        }
        rootPath = FileUtil.dealPath(rootPath);
        File rootDic = new File(rootPath);

        WeekScanner scanner = new WeekScanner(lists,"thisWeek","target","更新日志");
        scanner.scan(rootDic);

        String thisWeek = rootPath + "/thisWeek";
        File thisWeekDic = new File(thisWeek);
        if (thisWeekDic.exists()) {
            FileUtil.deleteFile(thisWeekDic);
        }
        for (File f : lists) {
            String newFilePath = thisWeek + "/" + DateUtil.getModifiedTime(f) + FileUtil.getRelativePath(f, rootDic);
            File newFile = new File(newFilePath);
            FileUtil.copyFile(f, newFile);
        }

        String log = LogUtil.generateLog(lists, rootDic);
        String logFile = rootPath + "/更新日志.txt";
        LogUtil.logToFile(logFile, log);
        System.out.println(log);
    }
}
