package bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 殇痕 on 2017/3/30.
 */

public class Comment {
    @SerializedName("ownerid")
    @Expose
    private int customerId;

    @SerializedName("customername")
    @Expose
    private String customerName;

    @SerializedName("created_at")
    @Expose
    private String time;
    @SerializedName("content")
    @Expose
    private String comment;


    private boolean hasPraise=false;
    @SerializedName("headimgurl")
    @Expose
    private String headImg;
    @SerializedName("goodcnt")
    @Expose
    private int praiseNumber;

    public Comment(int customerId, int praiseNumber, String customerName, String time, String comment, String headImg, boolean hasPraise){
        this.customerId = customerId;
        this.praiseNumber = praiseNumber;
        this.customerName = customerName;
        this.time = time;
        this.comment = comment;
        this.headImg = headImg;
        this.hasPraise = hasPraise;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isHasPraise() {
        return hasPraise;
    }

    public void setHasPraise(boolean hasPraise) {
        this.hasPraise = hasPraise;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(int praiseNumber) {
        this.praiseNumber = praiseNumber;
    }
}
