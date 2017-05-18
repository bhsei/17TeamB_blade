package org.hmh;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created this one by HMH on 2017/5/15.
 */
public class WeekScanner {

    private List<File> thisWeek;
    private String[] ignore;

    public WeekScanner(List<File> thisWeek,String... ignore) {
        this.thisWeek = thisWeek;
        this.ignore = ignore;
    }

    private void addFile(File file) {
        thisWeek.add(file);
    }

    public void scan(File rootDic) {
        if (rootDic.isDirectory()) {
            File[] files = rootDic.listFiles();
            for (File f : files) {
                if(checkIgnore(f)) continue;
                if (f.isDirectory()) {
                    scan(f);
                }
                if(f.isDirectory()) continue;
                if (checkWeek(f)) {
                    addFile(f);
                }
            }
        }
    }



    private boolean checkWeek(File file) {
        Date now = new Date(file.lastModified());
        return now.after(DateUtil.getFirstDayOfWeek()) && now.before(DateUtil.getLastDayOfWeek());
    }

    private boolean checkIgnore(File file) {
        if (ignore == null) {
            return false;
        }
        if (file.getName().startsWith(".")) {
            return true;
        }
        for (String anIgnore : ignore) {
            if (file.getAbsolutePath().contains(anIgnore)) {
                return true;
            }
        }
        return false;
    }


}
