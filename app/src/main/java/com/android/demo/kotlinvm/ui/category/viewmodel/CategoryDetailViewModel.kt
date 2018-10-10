package com.android.demo.kotlinvm.ui.category.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.android.baselibrary.base.type.FootRequestType
import com.android.baselibrary.base.type.RequestType
import com.android.baselibrary.base.viewmodel.BaseListViewModel
import com.android.demo.kotlinvm.ui.category.model.CategoryDetailModel
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean

/**
 * Des:
 */
class CategoryDetailViewModel : BaseListViewModel() {


    private val mCategoryDetailModel by lazy {
        CategoryDetailModel()
    }

    private val mCateDetailsLiveData = MutableLiveData<List<HomeBean.Issue.Item>>()

    private val mCateDetailsMoreLiveData = MutableLiveData<List<HomeBean.Issue.Item>>()


    fun getCateDetailsLiveData(): LiveData<List<HomeBean.Issue.Item>> {

        return mCateDetailsLiveData
    }

    fun getMoreHmCateDetailsLiveData(): LiveData<List<HomeBean.Issue.Item>> {

        return mCateDetailsMoreLiveData
    }


    /**
     * 获取分类详情的列表信息
     */
    fun getCategoryDetailList(id: Long) {
        //可以进入界面不用loading
        mCategoryDetailModel.getCategoryDetailList(id)
                .subscribe({ issue ->

                    //设置首次加载数据的
                    mCateDetailsLiveData.value = issue.itemList

                    //  设置下一页URL
                    if (issue.nextPageUrl.isNullOrEmpty()) {
                        //没有更多
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW
                    } else {
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW

                        mNextPageLiveData.value = issue.nextPageUrl
                    }

                }, {
                    mRequestTypeLiveData.value = RequestType.TYPE_NET_ERROR
                    mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW
                }).bindToLifecycle()
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData() {

        mCategoryDetailModel.loadMoreData(mNextPageLiveData.value.toString())
                .subscribe({ issue ->
                    //设置加载更多的数据
                    mCateDetailsMoreLiveData.value = issue.itemList

                    //  设置下一页URL
                    if (issue.nextPageUrl.isNullOrEmpty()) {
                        //没有更多
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE
                    } else {
                        mNextPageLiveData.value = issue.nextPageUrl
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_SUCCESS

                    }
                }, {
                    mFootRequestTypeLiveData.value = FootRequestType.TYPE_NET_ERROR

                })

    }


}