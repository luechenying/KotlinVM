package com.android.demo.kotlinvm.ui.main.model

import com.android.baselibrary.net.RetrofitManager
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Des: 关注Model
 */
class FollowModel {

    /**
     * 获取关注信息
     */
    fun requestFollowList(): Observable<HomeBean.Issue> {

        return RetrofitManager.instance.create(MainApi::class.java).getFollowInfo().compose(SchedulerUtils.ioToMain())

    }

    /**
     * 加载更多
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {

        return RetrofitManager.instance.create(MainApi::class.java).getIssueData(url).compose(SchedulerUtils.ioToMain())

    }
}