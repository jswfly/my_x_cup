package bean;

/**
 * Created by 殇痕 on 2017/4/22.
 */

public class ForumItem {
    private String username;
    private String title;
    private String content;
    private String time;
    private int url;
    private int headImg;
    private int authorId;

    public ForumItem(int headImg, String username, String title, String content, String time, int url){
        this.headImg = headImg;
        this.username = username;
        this.title = title;
        this.content = content;
        this.time = time;
        this.url = url;
    }

    public int getHeadImg() {
        return headImg;
    }

    public void setHeadImg(int headImg) {
        this.headImg = headImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
