package com.example.fresco20190212.app;

import android.app.Application;
import android.os.Environment;


import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import java.util.HashSet;
import java.util.Set;

import okhttp3.OkHttpClient;

public class App extends Application {

    private ImagePipelineConfig config;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Set<RequestListener> listeners = new HashSet<>();
        listeners.add(new RequestLoggingListener());
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, mOkHttpClient)
                .setDownsampleEnabled(true)
                .setRequestListeners(listeners)
                .build();
        Fresco.initialize(this,config);

        //设置磁盘缓存    
        DiskCacheConfig diskCacheConfig=DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("images")
        .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
        .build();
        //设置磁盘缓存的配置,生成配置文件    
        config = ImagePipelineConfig.newBuilder(this)
        .setMainDiskCacheConfig(diskCacheConfig)
        .build();
        Fresco.initialize(this, config); //不设置默认传一个参数既可    

    }
}
