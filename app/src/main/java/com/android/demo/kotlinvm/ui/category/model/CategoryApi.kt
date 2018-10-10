package com.android.demo.kotlinvm.ui.category.model

import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Des:
 */
interface CategoryApi {

    /**
     * 获取分类详情List
     */
    @GET("v4/categories/videoList?")
    fun getCategoryDetailList(@Query("id") id: Long): Observable<HomeBean.Issue>



}