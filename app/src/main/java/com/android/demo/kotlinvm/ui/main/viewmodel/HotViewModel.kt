package com.android.demo.kotlinvm.ui.main.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.android.baselibrary.base.type.RequestType
import com.android.baselibrary.base.viewmodel.BaseListViewModel
import com.android.baselibrary.net.exception.ErrorStatus
import com.android.baselibrary.net.exception.ExceptionHandle
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean

import com.android.demo.kotlinvm.ui.main.model.HotTabModel
import com.android.demo.kotlinvm.ui.main.model.RankModel
import com.android.demo.kotlinvm.ui.main.model.bean.TabInfoBean
import com.safframework.log.L

/**
 * Des:
 *
 */
class HotViewModel : BaseListViewModel() {

    private val mHotTabModel by lazy { HotTabModel() }

    private val mRankModel by lazy { RankModel() }


    private val mHTabInfoLiveData = MutableLiveData<TabInfoBean>()

    private val mRankListBeanLiveData = MutableLiveData<List<HomeBean.Issue.Item>>()


    fun getTabInfoLiveData(): LiveData<TabInfoBean> {

        return mHTabInfoLiveData
    }


    fun getRankListLiveData(): LiveData<List<HomeBean.Issue.Item>> {

        return mRankListBeanLiveData
    }

    fun getTabInfo() {
        //显示Loading界面
        mRequestTypeLiveData.value = RequestType.TYPE_LOADING
        mHotTabModel.getTabInfo().subscribe({
            mHTabInfoLiveData.value = it
            //关闭Loading状态
            mRequestTypeLiveData.value = RequestType.TYPE_SUCCESS
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


    /**
     *  请求排行榜数据
     */
    fun requestRankList(apiUrl: String) {
        //显示Loading界面
        mRequestTypeLiveData.value = RequestType.TYPE_LOADING
        mRankModel.requestRankList(apiUrl)
                .subscribe({
                    mRankListBeanLiveData.value=it.itemList
                    mRequestTypeLiveData.value = RequestType.TYPE_SUCCESS
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