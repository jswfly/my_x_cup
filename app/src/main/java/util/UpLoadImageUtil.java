package util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bean.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by 殇痕 on 2017/4/23.
 */

public class UpLoadImageUtil {
    private static final String TAG = "UpLoadImageUtil";

    /**
     *
     * @param paths
     * @param content
     */
    public static void uploadMany(final Context context, ArrayList<String> paths, String content){
        Map<String,RequestBody> photos = new HashMap<>();
        if (paths.size()>0) {
            for (int i=0;i<paths.size()-1;i++) {
                photos.put("photos"+i+"\"; filename=\"icon.png",  RequestBody.create(MediaType.parse("multipart/form-data"), new File(paths.get(i))));
            }
        }
        Call<Response<String>> stringCall = RetrofitUtil.service.uploadImage(content, photos);
        stringCall.enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                if(response.body()==null){
                    Toast.makeText(context, "请求出错", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(response.body().isStatus()) {
                    Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "发布失败", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "onResponse() called with: " + "call = [" + call + "], response = [" + response + "]");

            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable t) {
                Toast.makeText(context, "发布失败"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure() called with: " + "call = [" + call + "], t = [" + t + "]");
            }
        });
    }
}
