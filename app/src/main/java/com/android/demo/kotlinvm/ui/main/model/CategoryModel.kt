package com.android.demo.kotlinvm.ui.main.model

import com.android.baselibrary.net.RetrofitManager
import com.android.demo.kotlinvm.ui.main.model.bean.CategoryBean
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Des:分类数据模型
 */
class CategoryModel {

    /**
     * 获取分类信息
     */
    fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return RetrofitManager.instance.create(MainApi::class.java).getCategory().compose(SchedulerUtils.ioToMain())
    }
}