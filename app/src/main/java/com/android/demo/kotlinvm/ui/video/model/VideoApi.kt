package com.android.demo.kotlinvm.ui.video.model

import com.android.demo.kotlinvm.ui.video.model.bean.VideoBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Des:
 */
interface VideoApi {


    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<VideoBean.Issue>



}