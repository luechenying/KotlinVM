package com.android.demo.kotlinvm.ui.main.model

import com.android.baselibrary.net.RetrofitManager
import com.android.demo.kotlinvm.ui.main.model.bean.TabInfoBean

import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * desc: 热门 Model
 */
class HotTabModel {

    /**
     * 获取 TabInfo
     */
    fun getTabInfo(): Observable<TabInfoBean> {

        return RetrofitManager.instance.create(MainApi::class.java).getRankList().compose(SchedulerUtils.ioToMain())


    }

}
