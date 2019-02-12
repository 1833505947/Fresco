package com.example.fresco20190212;


import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;
import java.util.concurrent.Executor;


public class MainActivity extends AppCompatActivity {

    private SimpleDraweeView fresco;
    List<Drawable> backgroundsList;
    List<Drawable> overlaysList;
    private Button yuanxing,yuanjiao,bili,jianjinshi,cipan,donghua,jianting,okhttp;
    private GenericDraweeHierarchy hierarchy;
    private GenericDraweeHierarchyBuilder builder;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uri = Uri.parse("http://img0.imgtn.bdimg.com/it/u=4233134103,1682387629&fm=26&gp=0.jpg");
        initView();
        initData();
        fresco.setImageURI(uri);
        builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(300)
                .setPlaceholderImage(R.mipmap.ic_launcher)
                .build();
        fresco.setHierarchy(hierarchy);

    }

    private void initData() {
        hierarchy = fresco.getHierarchy();
        yuanjiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoundingParams roundingParams = RoundingParams.fromCornersRadius(20);
                roundingParams.setRoundAsCircle(false);
                roundingParams.setCornersRadius(100);
                fresco.getHierarchy().setRoundingParams(roundingParams);


            }
        });
        yuanxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoundingParams roundingParams = RoundingParams.fromCornersRadius(20);
                roundingParams.setRoundAsCircle(true);
                fresco.getHierarchy().setRoundingParams(roundingParams);
            }
        });
        bili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fresco.setAspectRatio(1.2f);
            }
        });
        jianjinshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setProgressiveRenderingEnabled(true)
                        .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(imageRequest)
                        .setOldController(fresco.getController())
                        .build();
                fresco.setController(controller);
            }
        });
        cipan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                boolean inMemoryCache = imagePipeline.isInBitmapMemoryCache(uri);
                DataSource<Boolean> a = imagePipeline.isInDiskCache(uri);
                DataSubscriber<Boolean> subscriber = new BaseDataSubscriber<Boolean>() {


                    @Override
                    protected void onNewResultImpl(com.facebook.datasource.DataSource<Boolean> dataSource) {
                        if (!dataSource.isFinished()){
                            return;
                        }
                        boolean result = dataSource.getResult();
                    }

                    @Override
                    protected void onFailureImpl(com.facebook.datasource.DataSource<Boolean> dataSource) {

                    }


                };
                Executor executor = null;

               a.subscribe(subscriber,executor);
            }
        });
        donghua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControllerListener controllerListener = new BaseControllerListener<ImageInfo>(){
                    @Override
                    public void onFinalImageSet(String id,ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (animatable!=null){
                            animatable.start();
                        }
                    }
                };
                Uri uri2 = Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549986722986&di=73739c4c0f095e52b15fe34cbb74982f&imgtype=0&src=http%3A%2F%2Fhiphotos.baidu.com%2Ffeed%2Fpic%2Fitem%2Ff9dcd100baa1cd11945e92a5b312c8fcc2ce2d4f.jpg");
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setControllerListener(controllerListener)
                        .setUri(uri2)
                        // other setters
                        .build();
                fresco.setController(controller);
            }


        });
        jianting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControllerListener controllerListener = new BaseControllerListener<ImageInfo>(){
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo==null){
                            return;
                        }
                        QualityInfo qualityInfo = imageInfo.getQualityInfo();
                        Log.e("111","成功");
                    }

                    /*@Override
                    public void onIntermediateImageSet(String id, @javax.annotation.Nullable ImageInfo imageInfo) {
                        super.onIntermediateImageSet(id, imageInfo);
                    }*/

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        super.onFailure(id, throwable);
                        Log.e("111","失败");
                    }
                };


                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setControllerListener(controllerListener)
                        .setUri(uri)
                        // other setters
                        .build();
                fresco.setController(controller);
            }
        });
        okhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"okhttp成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        fresco = findViewById(R.id.fresco);
        yuanjiao = findViewById(R.id.yuanjiao);
        yuanxing = findViewById(R.id.yuanxing);
        bili = findViewById(R.id.bili);
        jianjinshi = findViewById(R.id.jianjinshi);
        cipan = findViewById(R.id.cipan);
        donghua = findViewById(R.id.donghua);
        jianting = findViewById(R.id.jianting);
        okhttp = findViewById(R.id.okhttp);
    }

}
