package com.android.demo.kotlinvm.ui.main.model

import com.android.demo.kotlinvm.ui.main.model.bean.CategoryBean
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.android.demo.kotlinvm.ui.main.model.bean.TabInfoBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Des:
 */
interface MainApi {

    /**
     * 首页精选
     * http://baobab.kaiyanapp.com/api/v2/feed?&num=1&udid=d2807c895f0348a180148c9dfa6f2feeac0781b5&deviceModel=HM%20NOTE%201LTE
     * @link http://baobab.kaiyanapp.com/api/v2/feed?&num=1&udid=d2807c895f0348a180148c9dfa6f2feeac0781b5&deviceModel=HM%20NOTE%201LTE
     *
     */
    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeBean>

    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>


    /**
     * 获取全部排行榜的Info（包括，title 和 Url）
     */
    @GET("v4/rankList")
    fun getRankList():Observable<TabInfoBean>

    /**
     * 获取更多的 Issue
     */
    @GET
    fun getIssueData(@Url url: String): Observable<HomeBean.Issue>


    /**
     * 关注
     */
    @GET("v4/tabs/follow")
    fun getFollowInfo():Observable<HomeBean.Issue>


    /**
     * 获取分类
     */
    @GET("v4/categories")
    fun getCategory(): Observable<ArrayList<CategoryBean>>





}