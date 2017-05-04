package util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by 殇痕 on 2017/4/15.
 */

public class GlideConfig implements GlideModule {

    private int diskSize = 1024 * 1024 * 50;  // 50 Mb sd卡(本地)缓存的最大值
//    private int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        //设置磁盘缓存目录（和创建的缓存目录相同）
//        File storageDirectory = Environment.getExternalStorageDirectory();
//        String downloadDirectoryPath = storageDirectory+"/GlideCache";
//        File downloadDirectory = new File(downloadDirectoryPath);
//        Log.d(TAG, "applyOptions: "+downloadDirectoryPath);
//        Log.d(TAG, "applyOptions: "+context.getExternalCacheDir());
        // 定义缓存大小和位置
        //InternalCacheDiskCacheFactory应用内部存储
        //ExternalCacheDiskCacheFactory外部存储
        //自定义位置，则需要使用DiskLruCacheFactory
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "/GlideCache", diskSize));  // 内部sd卡中
        // builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", diskSize)); // sd卡中

        // 默认内存和图片池大小
         MemorySizeCalculator calculator = new MemorySizeCalculator(context);
         int defaultMemoryCacheSize = calculator.getMemoryCacheSize(); // 默认内存大小
         int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 默认图片池大小
         builder.setMemoryCache(new LruResourceCache((int) (0.8 * defaultMemoryCacheSize)));
         builder.setBitmapPool(new LruBitmapPool((int) (0.8 * defaultBitmapPoolSize)));

        // 自定义内存和图片池大小
//        builder.setMemoryCache(new LruResourceCache(memorySize)); // 自定义内存大小
//        builder.setBitmapPool(new LruBitmapPool(memorySize)); // 自定义图片池大小

        // 定义图片格式
        // builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565); // 默认


    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
