package com.example.applicationtest2;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by 殇痕 on 2017/3/25.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    private static void initImageLoader(Context context) {
        //缓存文件夹路径
        File cachedir = StorageUtils.getOwnCacheDirectory(context, "Android/data/com.example.applicationtest2/cache/imageLoaderCache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                // 内存缓存的设置选项 (最大图片宽度,最大图片高度) 默认当前屏幕分辨率
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                // 设置显示图片线程池大小，默认为3
                .threadPoolSize(3) //线程池内加载的数量
                // 设置图片加载线程的优先级,默认为Thread.NORM_PRIORITY-1
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // 设置拒绝缓存在内存中一个图片多个大小 默认为允许,(同一个图片URL)根据不同大小的imageview保存不同大小图片
                .denyCacheImageMultipleSizesInMemory()
                // 设置硬盘缓存文件名生成规范
                // 默认为new HashCodeFileNameGenerator()使用HASHCODE对UIL进行加密命名
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                // 设置内存缓存 默认为一个当前应用可用内存的1/8大小的LruMemoryCache
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // 你可以通过自己的内存缓存实现
                // 设置内存缓存的最大大小 默认为一个当前应用可用内存的1/8
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                // 设置硬盘缓存的最大大小
                .diskCacheSize(50 * 1024 * 1024)  // 50 Mb sd卡(本地)缓存的最大值
                // 设置硬盘缓存的文件的最多个数
                .diskCacheFileCount(300)// 可以缓存的文件数量
                // 设置图片加载和显示队列处理的类型 默认为QueueProcessingType.FIFO
                .tasksProcessingOrder(QueueProcessingType.LIFO)//后进先出
                // 由原先的discCache -> diskCache
                // 默认为StorageUtils.getCacheDirectory(getApplicationContext())
                // 即/mnt/sdcard/android/data/包名/cache/
                .diskCache(new UnlimitedDiskCache(cachedir))//自定义缓存路径
                // 设置图片下载器
                // 默认为 DefaultConfigurationFactory.createBitmapDisplayer()
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                // 打印DebugLogs
                //.writeDebugLogs() // Remove for release app
                .build();
        //全局初始化此配置
        ImageLoader.getInstance().init(config);

    }
}
