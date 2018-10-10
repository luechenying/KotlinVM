package com.android.demo.kotlinvm.ui.gank.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.android.baselibrary.base.type.FootRequestType
import com.android.baselibrary.base.type.RequestType
import com.android.baselibrary.base.viewmodel.BaseListViewModel
import com.android.demo.kotlinvm.ui.gank.model.GankModel
import com.android.demo.kotlinvm.ui.gank.model.bean.GankBean

/**
 * Des:
 *
 */
class GankViewModel : BaseListViewModel() {

    private val mGankModel: GankModel by lazy {
        GankModel()
    }

    private val mGankItemsLiveData = MutableLiveData<List<GankBean.GankItem>>()

    private val mGankItemsMoreLiveData = MutableLiveData<List<GankBean.GankItem>>()


    fun getmGankItemsLiveData(): LiveData<List<GankBean.GankItem>> {

        return mGankItemsLiveData
    }

    fun getMoremGankItemsLiveData(): LiveData<List<GankBean.GankItem>> {

        return mGankItemsMoreLiveData
    }


    fun getGankData(category: String) {
        //显示Loading界面
        mRequestTypeLiveData.value = RequestType.TYPE_LOADING
        mPageLiveData.value = 1
        mGankModel.getGankData(category, mDefaultCount, 1).subscribe({
            when {
                it.error -> {//加载失败显示服务端错误
                    mRequestTypeLiveData.value = RequestType.TYPE_ERROR
                }
                it.results.size < mDefaultCount -> { // 当前获取的count小于设定默认count数，说明没有更多数据
                    if (it.results.isEmpty()) {
                        //首次加载没数据
                        mRequestTypeLiveData.value = RequestType.TYPE_EMPTY
                    } else {
                        //填充数据
                        mGankItemsLiveData.value = it.results
                        //首次加载没有更多数据时候==隐藏底部的加载更多
                        mRequestTypeLiveData.value = RequestType.TYPE_SUCCESS
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW

                    }
                }
                else -> {
                    //填充数据
                    mGankItemsLiveData.value = it.results
                    mRequestTypeLiveData.value = RequestType.TYPE_SUCCESS
                }
            }
        }, {
            mRequestTypeLiveData.value = RequestType.TYPE_NET_ERROR
        }).bindToLifecycle()


    }

    fun loadMoreGankData(category: String) {

        mGankModel.getGankData(category, mDefaultCount, mPageLiveData.value!! + 1).subscribe({

            when {
                it.error -> {//加载失败显示在Recyclerview底部
                    mFootRequestTypeLiveData.value = FootRequestType.TYPE_NET_ERROR
                }
                it.results.size < mDefaultCount -> {
                    //填充数据
                    mGankItemsMoreLiveData.value = it.results
                    //加载更多没有更多数据时候==显示没有更多数据
                    mRequestTypeLiveData.value = RequestType.TYPE_SUCCESS
                    mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE
                }
                else -> {
                    //加载更多成功页数+1
                    mPageLiveData.value = mPageLiveData.value!! + 1
                    //填充数据
                    mGankItemsMoreLiveData.value = it.results
                    mFootRequestTypeLiveData.value = FootRequestType.TYPE_SUCCESS
                }
            }

        }, {
            mFootRequestTypeLiveData.value = FootRequestType.TYPE_NET_ERROR
        }).bindToLifecycle()

    }


}