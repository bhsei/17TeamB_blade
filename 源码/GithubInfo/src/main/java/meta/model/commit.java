package meta.model;

/**
 * Created by HMH on 2017/4/18.
 */
public class commit {
    author author;
    committer committer;
    tree tree;
    String message;
    String url;
    int comment_count;

    public commit() {
    }

    public meta.model.tree getTree() {
        return tree;
    }

    public void setTree(meta.model.tree tree) {
        this.tree = tree;
    }

    public meta.model.author getAuthor() {
        return author;
    }

    public void setAuthor(meta.model.author author) {
        this.author = author;
    }

    public meta.model.committer getCommitter() {
        return committer;
    }

    public void setCommitter(meta.model.committer committer) {
        this.committer = committer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
}
