package util;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import static com.nostra13.universalimageloader.core.assist.ImageScaleType.EXACTLY;

/**
 * Created by 殇痕 on 2017/3/25.
 */

public class ImageLoaderUtil {
    public static DisplayImageOptions buildOptions(int resource){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(resource) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(resource) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(resource) // 设置 图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
//                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .imageScaleType(EXACTLY )
                .build(); // 构建完成

        return options;
    }
 /*   public static DisplayImageOptions buildArticalShowOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.image_holder_default) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.image_holder_default) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.image_holder_default) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .imageScaleType(EXACTLY )
                .build(); // 构建完成
        return options;
    }*/

//    public static Bitmap getCacheImage(String uri){//这里的uri一般就是图片网址
//        List<String> memCacheKeyNameList = MemoryCacheUtil.findCacheKeysForImageUri(uri , ImageLoader.getInstance().getMemoryCache());
//        if(memCacheKeyNameList != null && memCacheKeyNameList.size() > 0){
//
//            for(String each:memCacheKeyNameList){
//            }
//
//            return ImageLoader.getInstance().getMemoryCache().get(memCacheKeyNameList.get(0));
//        }
//
//        return null;
//    }
}
