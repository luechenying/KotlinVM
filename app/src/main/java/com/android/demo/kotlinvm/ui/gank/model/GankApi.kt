package com.android.demo.kotlinvm.ui.gank.model

import com.android.demo.kotlinvm.ui.gank.model.bean.GankBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Des: 干货集中营 API
 * https://gank.io/api
 */
interface GankApi {

    /**
     * http://gank.io/api/data/Android/10/1
     *
     * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * count 最大 50
     */
    @GET("data/{category}/{count}/{page}")
    fun getGankData(@Path("category") category: String, @Path("count") count: Int, @Path("page") page: Int): Observable<GankBean>


}