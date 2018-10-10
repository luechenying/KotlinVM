package com.android.demo.kotlinvm.ui.gank.model

import com.android.baselibrary.net.GankRetrofitManager
import com.android.demo.kotlinvm.ui.gank.model.bean.GankBean
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Des:
 *
 */
class GankModel {

    /**
     * 获取分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android
     * 模拟延迟加载
     */
    fun getGankData(category: String, count: Int, page: Int): Observable<GankBean> {
        return GankRetrofitManager.instance.create(GankApi::class.java).getGankData(category, count, page).compose(SchedulerUtils.ioToMain())
    }

}