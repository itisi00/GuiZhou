package com.itisi.guizhou.di.module;

import com.itisi.guizhou.BuildConfig;
import com.itisi.guizhou.app.Constants;
import com.itisi.guizhou.di.qualifier.GankUrl;
import com.itisi.guizhou.di.qualifier.MyUrl;
import com.itisi.guizhou.mvp.model.http.api.GankApi;
import com.itisi.guizhou.mvp.model.http.api.MyApi;
import com.itisi.guizhou.utils.SystemUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * **********************
 * 功 能:网络请求module
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 17:19
 * 修改人:itisi
 * 修改时间: 2017/7/5 17:19
 * 修改内容:itisi
 * *********************
 */
@Module
public class HttpModule {
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder(){
        return new OkHttpClient.Builder();
    }
    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder){
        if (BuildConfig.DEBUG){//调试模式
            HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(interceptor);
        }
        File cacheFile=new File(Constants.PATH_SDCARD);
        Cache cache=new Cache(cacheFile,1024*1024*58);
        Interceptor interceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request=chain.request();
                if (!SystemUtil.isNetworkConnected()){
                    request=request.newBuilder()
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .build();
                }
                Response response=chain.proceed(request);
                if (SystemUtil.isNetworkConnected()){
                    int maxAge=0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();

                }else{
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                Logger.i(response.body().toString());
                return response;
            }
        };
        //设置统一的请求头部参数
//        builder.addInterceptor(apikey);
        //设置缓存
        builder.addNetworkInterceptor(interceptor);
        builder.addInterceptor(interceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();

    }


    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String host) {
        return builder.baseUrl(host)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                ;

    }

    @Singleton
    @Provides
    @GankUrl
    Retrofit provideGankRetrofit(Retrofit.Builder builder, OkHttpClient client){
        return createRetrofit(builder,client, GankApi.HOST);
    }

    @Singleton
    @Provides
    GankApi provideGankApiService(@GankUrl Retrofit retrofit){
        return retrofit.create(GankApi.class);
    }

    @Singleton
    @Provides
    @MyUrl
    Retrofit provideMyRetrofit(Retrofit.Builder builder, OkHttpClient client){
        return createRetrofit(builder,client, MyApi.HOST);
    }
    @Singleton
    @Provides
    MyApi provideMyApiService(@MyUrl Retrofit retrofit){
        return retrofit.create(MyApi.class);
    }

}
