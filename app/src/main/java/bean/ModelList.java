package bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 殇痕 on 2017/4/2.
 */

public class ModelList {

    @SerializedName("modelid")
    @Expose
    private int modelid;
    @SerializedName("ownerid")
    @Expose
    private int ownerid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("modelurl")
    @Expose
    private String modelurl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("modelidimg")
    @Expose
    private List<ModelImg> modelidimg = null;
    @SerializedName("ownermessage")
    @Expose
    private User ownermessage;
    @SerializedName("collection_cnts")
    @Expose
    private int collectionCnts;

    public  ModelList(int modelid,int ownerid, String title, String content, String modelurl, String createdAt,
                      String updatedAt, List<ModelImg> modelidimg, User ownermessage, int collectionCnts){
        this.modelid = modelid;
        this.ownerid = ownerid;
        this.title = title;
        this.content = content;
        this.modelurl = modelurl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.modelidimg = modelidimg;
        this.ownermessage = ownermessage;
        this.collectionCnts = collectionCnts;
    }

    public  ModelList(int modelid, String title, String content, String modelurl, List<ModelImg> modelidimg){
        this.modelid = modelid;
        this.title = title;
        this.content = content;
        this.modelurl = modelurl;
        this.modelidimg = modelidimg;
    }


    public int getModelid() {
        return modelid;
    }

    public void setModelid(int modelid) {
        this.modelid = modelid;
    }

    public int getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(int ownerid) {
        this.ownerid = ownerid;
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

    public String getModelurl() {
        return modelurl;
    }

    public void setModelurl(String modelurl) {
        this.modelurl = modelurl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ModelImg> getModelidimg() {
        return modelidimg;
    }

    public void setModelidimg(List<ModelImg> modelidimg) {
        this.modelidimg = modelidimg;
    }

    public User getOwnermessage() {
        return ownermessage;
    }

    public void setOwnermessage(User ownermessage) {
        this.ownermessage = ownermessage;
    }

    public int getCollectionCnts() {
        return collectionCnts;
    }

    public void setCollectionCnts(int collectionCnts) {
        this.collectionCnts = collectionCnts;
    }

}
