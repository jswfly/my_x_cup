package util;

import java.util.Map;

import bean.Comment;
import bean.ModelList;
import bean.Response;
import bean.ResponseList;
import bean.User;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by 殇痕 on 2017/3/27.
 *
 */

public interface AppService {
    //首页list
    @GET("modellist")
    Call<ResponseList<ModelList>> getHomeList(@Query("offset") int offset);

//    @POST("loginff")
//    @FormUrlEncoded
//    Call<Response<User>> toLogin(@Field("telephone") String telephone,
//                                 @Field("password") String password);

    //登陆
    @GET("login")
    Call<Response<User>> toLogin(@Query("telephone") String telephone,
                                 @Query("password") String password);

    //用户信息
    //customerid是自己，owerid是作者
    @GET("customermessage")
    Call<Response<User>> getCustomMessage(@Query("ownerid") int ownerid, @Query("customerid") int customerid);

    //文章详情
    @GET("modelitem")
    Call<Response<User>> getArticleMessage(@Query("modelid") int modelid);

    //评论详情
    @GET("comment")
    Call<ResponseList<Comment>> getAllComment(@Query("modelid") int modelid);

    //插入评论
    @GET("incomment")
    Call<Response<String>> insertComment(@Query("ownerid") int ownerid, @Query("modelid") int modelid,
                                          @Query("content") String content);

    //添加关注
    @GET("adfans")
    Call<Response<String>> insertLike(@Query("ownerid") int ownerid, @Query("followedid") int followedid,
                                         @Query("method") int method);

    /**
     * 上传一张图片
     * @param description
     * @param imgs
     * @return
     */
    @Multipart
    @POST("upload")
    Call<Response<String>> uploadImage(@Part("fileName") String description,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs);

    /**
     * 简便写法
     * @param description
     * @param imgs1
     * @return
     */
    @Multipart
    @POST("upload")
    Call<Response<String>> uploadImage(@Part("description") String description,@PartMap
            Map<String, RequestBody> imgs1);



}
