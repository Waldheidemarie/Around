package site.shawnxxy.eventreporter.constructor;

/**
 * Created by ShawnX on 9/17/17.
 */

public class Comment {
    private String commentId;
    private String commenter;
    private String postId;

    private String description;
    private long time;
    private int good;
//    private int repost;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

//    public int getRepost() {
//        return repost;
//    }
//
//    public void setRepost(int repost) {
//        this.repost = repost;
//    }

}
