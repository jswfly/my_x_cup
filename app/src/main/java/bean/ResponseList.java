package bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 殇痕 on 2017/4/2.
 */

public class ResponseList<T> {
    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private List<T> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
