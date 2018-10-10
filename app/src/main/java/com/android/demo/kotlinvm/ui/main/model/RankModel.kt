package com.android.demo.kotlinvm.ui.main.model

import com.android.baselibrary.net.RetrofitManager
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean

import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * desc: 排行榜 Model
 */
class RankModel {




    /**
     * 获取排行榜
     */
    fun requestRankList(apiUrl:String): Observable<HomeBean.Issue> {

        return RetrofitManager.instance.create(MainApi::class.java).getIssueData(apiUrl).compose(SchedulerUtils.ioToMain())

    }


}
