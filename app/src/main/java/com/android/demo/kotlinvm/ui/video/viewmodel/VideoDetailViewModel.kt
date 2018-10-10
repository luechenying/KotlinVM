package com.android.demo.kotlinvm.ui.video.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.android.baselibrary.base.type.RequestType
import com.android.baselibrary.base.viewmodel.BaseListViewModel
import com.android.baselibrary.net.exception.ErrorStatus
import com.android.baselibrary.net.exception.ExceptionHandle
import com.android.demo.kotlinvm.ui.video.model.VideoDetailModel
import com.android.demo.kotlinvm.ui.video.model.bean.VideoBean
import com.safframework.log.L

/**
 * Des:
 */
class VideoDetailViewModel : BaseListViewModel() {


    private val mVideoDetailModel: VideoDetailModel by lazy {
        VideoDetailModel()
    }


    private val mVideoItemLiveData = MutableLiveData<List<VideoBean.Issue.Item>>()




    fun getVideoItemLiveData(): LiveData<List<VideoBean.Issue.Item>> {
        return mVideoItemLiveData
    }

    /**
     * 请求相关的视频数据
     */
    fun requestRelatedVideo(id: Long) {

//        mRequestTypeLiveData.value = RequestType.TYPE_LOADING
        mVideoDetailModel.requestRelatedData(id).subscribe({

            //关闭Loading状态
//            mRequestTypeLiveData.value = RequestType.TYPE_SUCCESS

            //设置加载数据的
            mVideoItemLiveData.value = it.itemList

        }, {
            //处理异常
            ExceptionHandle.handleException(it)
            L.e(ExceptionHandle.errorMsg)
            if (ExceptionHandle.errorCode == ErrorStatus.NETWORK_ERROR) {
                mRequestTypeLiveData.value = RequestType.TYPE_NET_ERROR
            } else {
                mRequestTypeLiveData.value = RequestType.TYPE_ERROR

            }
        }).bindToLifecycle()


    }


}