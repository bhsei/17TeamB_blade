package repository;

import model.ClassificationEntity;
import model.CommitEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMH on 2017/4/18.
 */
public class CommitRepository {
    static List<CommitEntity> commitList;

    static{
        commitList = new ArrayList<>();
    }

    public static void add(CommitEntity commit) {
        commitList.add(commit);
    }

    public static void remove(CommitEntity commit) {
        commitList.remove(commit);
    }

    public static CommitEntity getCommitByMessage(String message) {
        for(int i=0;i<commitList.size();i++) {
            if (commitList.get(i).getMessage().equals(message)) {
                return commitList.get(i);
            }
        }
        return null;
    }

    public static void updateCommit(CommitEntity commitEntity) {
        for(int i=0;i<commitList.size();i++) {
            if (commitList.get(i).getMessage().equals(commitEntity.getMessage())) {
                commitList.set(i,commitEntity) ;
            }
        }
    }
}
