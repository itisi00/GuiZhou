package com.itisi.guizhou.common;

import com.itisi.guizhou.mvp.model.http.exception.ApiException;
import com.itisi.guizhou.mvp.model.http.response.GankResponse;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * **********************
 * 功 能:RX 转换类
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/6 10:42
 * 修改人:itisi
 * 修改时间: 2017/7/6 10:42
 * 修改内容:itisi
 * *********************
 */

public class RxUtil {
    /**
     * compose简化线程 统一线程处理
     * FlowableTransformer
     * @param <T>
     * @return
     */
    public static <T>ObservableTransformer<T,T> rxSchedulerObservableHelper(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }

//            @Override
//            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
//                return upstream.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
        };
    }

    public static <T>FlowableTransformer<T,T> rxSchedulerFlowableHelper(){
        return new FlowableTransformer<T, T>() {

            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 处理Gank 接口返回的结果
     * FlowableTransformer
     * @param <T>
     * @return
     */
    public static <T>ObservableTransformer<GankResponse<T>,T>handlerGankResult(){
        return new ObservableTransformer<GankResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<GankResponse<T>> upstream) {

                return upstream.flatMap(new Function<GankResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull GankResponse<T> tGankResponse) throws Exception {
                        if (!tGankResponse.isError()) {

                            return createObservable(tGankResponse.getResults());
                        } else {
                            return Observable.error(new ApiException("服务器返回错误"));
                        }
                    }
                });
            }
            //  @Override
//            public Publisher<T> apply(@NonNull Flowable<GankResponse<T>> upstream) {
//                return upstream.flatMap(new Function<GankResponse<T>, Publisher<T>>() {
//                    @Override
//                    public Publisher<T> apply(@NonNull GankResponse<T> tGankResponse) throws Exception {
//                        if (!tGankResponse.isError()){
//                            return createFlowable(tGankResponse.getResults());
//                        }else{
//                            return Flowable.error(new ApiException("服务器返回错误"));
//                        }
//                    }
//                });
//            }
        };
    }


    // TODO: 2017/7/6 处理其他接口返回的结果

    /**
     * 生成Flowable
     * @param results
     * @param <T>
     * @return
     */
    private static <T> Publisher<T> createFlowable(final T results) {
        return  Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<T> e) throws Exception {
               try {
                   e.onNext(results);
                   e.onComplete();
               }catch (Exception ex){
                   e.onError(ex);
               }
            }
        }, BackpressureStrategy.BUFFER);
    }

    private static <T> ObservableSource<T> createObservable(final T results) {
        return  Observable.create(new ObservableOnSubscribe<T>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                try {
                    e.onNext(results);
                    e.onComplete();
                }catch (Exception ex){
                    e.onError(ex);
                }
            }
        });
    }

}
