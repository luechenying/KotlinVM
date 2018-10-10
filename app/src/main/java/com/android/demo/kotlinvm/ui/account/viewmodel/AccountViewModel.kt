package com.android.demo.kotlinvm.ui.account.viewmodel

import com.android.baselibrary.base.type.RequestType
import com.android.baselibrary.base.viewmodel.BaseViewModel
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Des: 模拟个别特殊加载
 */
class AccountViewModel : BaseViewModel() {


    /**
     * 通过LiveData控制界面逻辑的处理，实现逻辑和界面UI实现分离
     */
    fun getDailogLoading() {

        //显示不是通用的Loading界面
        mRequestTypeLiveData.value = RequestType.TYPE_LOADING
        //2秒后关闭Loading状态
        Observable.timer(2, TimeUnit.SECONDS).compose(SchedulerUtils.ioToMain())
                .subscribe { mRequestTypeLiveData.value = RequestType.TYPE_SUCCESS }
    }

}