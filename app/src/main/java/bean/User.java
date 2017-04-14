package bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 殇痕 on 2017/3/27.
 */

public class User {

    @SerializedName("customerid")
    @Expose
    private int customerid;

    @SerializedName("customername")
    @Expose
    private String customername;

    @SerializedName("motto")
    @Expose
    private String motto;

    @SerializedName("headimgurl")
    @Expose
    private String headimgurl;

    @SerializedName("fanscnt")
    @Expose
    private int fanscnt;

    @SerializedName("followedcnt")
    @Expose
    private int followedcnt;

    @SerializedName("modelcnt")
    @Expose
    private int modelcnt;

    @SerializedName("modelsmessage")
    @Expose
    private List<ModelList> modelsmessage = null;

    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;

    @SerializedName("modelidimg")
    @Expose
    private List<ModelImg> modelidimg = null;


    @SerializedName("followed")
    @Expose
    private boolean followed;

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public int getFanscnt() {
        return fanscnt;
    }

    public void setFanscnt(int fanscnt) {
        this.fanscnt = fanscnt;
    }

    public int getFollowedcnt() {
        return followedcnt;
    }

    public void setFollowedcnt(int followedcnt) {
        this.followedcnt = followedcnt;
    }

    public List<ModelList> getModelsmessage() {
        return modelsmessage;
    }

    public void setModelsmessage(List<ModelList> modelsmessage) {
        this.modelsmessage = modelsmessage;
    }

    public int getModelcnt() {
        return modelcnt;
    }

    public void setModelcnt(int modelcnt) {
        this.modelcnt = modelcnt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<ModelImg> getModelidimg() {
        return modelidimg;
    }

    public void setModelidimg(List<ModelImg> modelidimg) {
        this.modelidimg = modelidimg;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }
}
