package meta.model;

import java.util.List;

/**
 * Created by HMH on 2017/4/18.
 */
public class CommitObject {
    String sha;
    commit commit;
    String url;
    String html_url;
    String comments_url;
    List<parents> parents;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public commit getCommit() {
        return commit;
    }

    public void setCommit(commit commit) {
        this.commit = commit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public List<meta.model.parents> getParents() {
        return parents;
    }

    public void setParents(List<meta.model.parents> parents) {
        this.parents = parents;
    }
}
