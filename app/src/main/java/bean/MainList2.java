package bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 殇痕 on 2017/3/11.
 */

public class MainList2 {

    @SerializedName("headimg")
    private String imgHead;
    @SerializedName("customername")
    private  String author;
    @SerializedName("motto")
    private  String authorMotto;
    @SerializedName("articleimg")
    private String imgArticleShow;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String introduction;

    /**
     * 要的布局方式，Total为全部即item_list_main_total的布局
     *                Head为imgHead+author+articleMotto的布局即part_item_main_head的布局
     *                Body为imgArticleShow+title+introduction的布局part_item_main_body的布局
     * 默认为1
     */
    private Model model=Model.Total;
    public enum Model{
        Total, Head, Body
    };

    //Total
    public MainList2(String author, String authorMotto, String imgHead, String imgArticleShow, String title, String introduction){
        this.author = author;
        this.authorMotto = authorMotto;
        this.imgHead = imgHead;
        this.imgArticleShow = imgArticleShow;
        this.title = title;
        this.introduction = introduction;
        this.model=Model.Total;
    }

//    //Head
//    public MainList2(String author, String authorMotto, String imgHead){
//        this.author = author;
//        this.authorMotto = authorMotto;
//        this.imgHead = imgHead;
//        this.model = Model.Head;
//    }
//
//    //Body
//    public MainList2(String imgArticleShow, String title, String introduction){
//        this.imgArticleShow = imgArticleShow;
//        this.title = title;
//        this.introduction = introduction;
//        this.model = Model.Body;
//    }

    public String getImgHead() {
        return imgHead;
    }

    public void setImgHead(String imgHead) {
        this.imgHead = imgHead;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorMotto() {
        return authorMotto;
    }

    public void setAuthorMotto(String authorMotto) {
        this.authorMotto = authorMotto;
    }

    public String getImgArticleShow() {
        return imgArticleShow;
    }

    public void setImgArticleShow(String imgArticleShow) {
        this.imgArticleShow = imgArticleShow;
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
