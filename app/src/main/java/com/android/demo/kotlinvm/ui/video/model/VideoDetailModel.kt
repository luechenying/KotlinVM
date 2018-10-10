package com.android.demo.kotlinvm.ui.video.model

import com.android.baselibrary.net.RetrofitManager
import com.android.demo.kotlinvm.ui.video.model.bean.VideoBean
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Des:
 */
class VideoDetailModel {


    fun requestRelatedData(id: Long): Observable<VideoBean.Issue> {

        return RetrofitManager.instance.create(VideoApi::class.java).getRelatedData(id).compose(SchedulerUtils.ioToMain())

    }


}