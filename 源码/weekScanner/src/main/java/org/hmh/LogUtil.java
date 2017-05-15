package org.hmh;

import java.io.*;
import java.util.List;

/**
 * Created this one by HMH on 2017/5/15.
 */
public class LogUtil {

    public static String generateLog(List<File> fileList,File rootDic) {
        fileList.sort((f1, f2) -> -f1.getAbsolutePath().compareTo(f2.getAbsolutePath()));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("记录更新日期:  ").append(DateUtil.getToday()).append("\n")
                .append("更新文件数量：  ").append(fileList.size()).append("\n");
        for(int i=0;i<fileList.size();i++) {
            stringBuilder
                    .append("文件 ").append(i + 1).append(" 最后更新日期： ").append(DateUtil.getModifiedTime(fileList.get(i))).append("  ")
                    .append("文件相对路径:  ").append(FileUtil.getRelativePath(fileList.get(i), rootDic)).append("\n");
        }
        stringBuilder.append("\n\n");
        return stringBuilder.toString();
    }

    public static void logToFile(String logPath,String log) throws IOException {
        File logFile = new File(logPath);
        File tempFile = new File(logPath + ".temp");
        if (tempFile.exists()) {
            FileUtil.deleteFile(tempFile);
        }
        if (!tempFile.exists()) {
            logFile.createNewFile();
        }
        FileUtil.copyFile(logFile, tempFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile)); BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            writer.write(log);
            String content;
            while (null != (content = reader.readLine())) {
                writer.write(content);
                writer.newLine();
            }
        }
        tempFile.deleteOnExit();
    }
}
