package com.android.demo.kotlinvm.ui.main.model

import com.android.baselibrary.net.RetrofitManager
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Des: 首页精选 model
 */
class HomeModel {
    /**
     * 获取首页 Banner 数据
     */
    fun requestHomeData(num: Int): Observable<HomeBean> {

        return RetrofitManager.instance.create(MainApi::class.java).getFirstHomeData(num).compose(SchedulerUtils.ioToMain())

    }

    /**
     * 加载更多
     */
    fun loadMoreData(url: String): Observable<HomeBean> {

        return RetrofitManager.instance.create(MainApi::class.java).getMoreHomeData(url).compose(SchedulerUtils.ioToMain())

    }


}