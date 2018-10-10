package com.android.demo.kotlinvm.ui.category.model

import com.android.baselibrary.net.RetrofitManager
import com.android.demo.kotlinvm.ui.main.model.MainApi
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Des: 分类详情的 Model
 *
 */
class CategoryDetailModel   {

    /**
     * 获取分类下的 List 数据
     */
    fun getCategoryDetailList(id: Long): Observable<HomeBean.Issue> {

        return RetrofitManager.instance.create(CategoryApi::class.java).getCategoryDetailList(id)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.instance.create(MainApi::class.java).getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }

}