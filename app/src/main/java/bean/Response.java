package bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 殇痕 on 2017/3/27.
 */

public class Response<T> {
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private T data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
