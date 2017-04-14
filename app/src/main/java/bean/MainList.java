package bean;

/**
 * Created by 殇痕 on 2017/3/11.
 */

public class MainList {

    //@SerializedName("headimg")
    private int imgHead;
   // @SerializedName("customername")
    private  String author;
   // @SerializedName("motto")
    private  String authorMotto;
    //@SerializedName("articleimg")
    private int imgArticleShow;
   // @SerializedName("title")
    private String title;
    //@SerializedName("content")
    private String introduction;

    //Total
    public MainList(String author, String authorMotto, int imgHead, int imgArticleShow, String title, String introduction){
        this.author = author;
        this.authorMotto = authorMotto;
        this.imgHead = imgHead;
        this.imgArticleShow = imgArticleShow;
        this.title = title;
        this.introduction = introduction;
    }

    //Head
    public MainList(String author, String authorMotto, int imgHead){
        this.author = author;
        this.authorMotto = authorMotto;
        this.imgHead = imgHead;
    }

    //Body
    public MainList(int imgArticleShow, String title, String introduction){
        this.imgArticleShow = imgArticleShow;
        this.title = title;
        this.introduction = introduction;
    }

    public int getImgHead() {
        return imgHead;
    }

    public void setImgHead(int imgHead) {
        this.imgHead = imgHead;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getImgArticleShow() {
        return imgArticleShow;
    }

    public void setImgArticleShow(int imgArticalShow) {
        this.imgArticleShow = imgArticalShow;
    }

    public String getAuthorMotto() {
        return authorMotto;
    }

    public void setAuthorMotto(String articalMotto) {
        this.authorMotto = authorMotto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

}
