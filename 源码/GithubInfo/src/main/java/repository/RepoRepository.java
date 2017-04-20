package repository;

import model.RepoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMH on 2017/4/18.
 */
public class RepoRepository {
    static List<RepoEntity> repoList;

    static{
        repoList = new ArrayList<>();
    }

    public static void add(RepoEntity repo) {
        repoList.add(repo);
    }

    public static void remove(RepoEntity repo) {
        repoList.remove(repo);
    }

    public static RepoEntity getUser(String path) {
        for(int i=0;i<repoList.size();i++) {
            if (repoList.get(i).getPath().equals(path)) {
                return repoList.get(i);
            }
        }
        return null;
    }

    public static void updateRepo(RepoEntity repoEntity) {
        for(int i=0;i<repoList.size();i++) {
            if (repoList.get(i).getPath().equals(repoEntity.getPath())) {
                repoList.set(i,repoEntity) ;
            }
        }
    }
}
