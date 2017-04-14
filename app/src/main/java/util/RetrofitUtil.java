package util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 殇痕 on 2017/3/27.
 *
 */

public class RetrofitUtil {

    public static String  baseUrl = "http://123.206.111.204/app/";
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            //增加返回值为Gson的支持(以实体类返回)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static AppService service = retrofit.create(AppService.class);
}
