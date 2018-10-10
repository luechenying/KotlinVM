package com.android.demo.kotlinvm.ui.main.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.android.baselibrary.base.type.FootRequestType
import com.android.baselibrary.base.type.RequestType
import com.android.baselibrary.base.viewmodel.BaseListViewModel
import com.android.baselibrary.net.exception.ExceptionHandle
import com.android.demo.kotlinvm.ui.main.model.CategoryModel
import com.android.demo.kotlinvm.ui.main.model.FollowModel
import com.android.demo.kotlinvm.ui.main.model.bean.CategoryBean
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean

/**
 * Des:
 */
class DiscoveryViewModel : BaseListViewModel() {


    private val mFollowModel: FollowModel by lazy {
        FollowModel()
    }
    private val mCategoryModel: CategoryModel by lazy {
        CategoryModel()
    }

    private val mFollowBeanLiveData = MutableLiveData<List<HomeBean.Issue.Item>>()

    private val mFollowBeanMoreLiveData = MutableLiveData<List<HomeBean.Issue.Item>>()

    private val mCategoryBeanLiveData = MutableLiveData<List<CategoryBean>>()


    fun getFollowBeanLiveData(): LiveData<List<HomeBean.Issue.Item>> {

        return mFollowBeanLiveData
    }

    fun getMoreFollowBeanLiveData(): LiveData<List<HomeBean.Issue.Item>> {

        return mFollowBeanMoreLiveData
    }

    fun getCategoryBeanLiveData(): LiveData<List<CategoryBean>> {

        return mCategoryBeanLiveData
    }


    /**
     *  请求关注数据
     */
    fun requestFollowList() {


        //显示Loading界面
        mRequestTypeLiveData.value = RequestType.TYPE_LOADING

        mFollowModel.requestFollowList()
                .subscribe({


                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val bannerItemList = arrayListOf<HomeBean.Issue.Item>()
                    it.itemList.filter { item ->
                        item.type == "videoCollectionWithBrief"
                    }.forEach { item ->
                        //添加 item
                        bannerItemList.add(item)
                    }


                    //设置首次加载数据的
                    mFollowBeanLiveData.value = bannerItemList

                    //关闭Loading状态
                    mRequestTypeLiveData.value = RequestType.TYPE_SUCCESS

                    //  设置下一页URL
                    if (it.nextPageUrl.isNullOrEmpty()) {
                        //没有更多
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW
                    } else {
                        mNextPageLiveData.value = it.nextPageUrl
                    }


                }, {
                    mRequestTypeLiveData.value = RequestType.TYPE_NET_ERROR
                    mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW

                }).bindToLifecycle()
    }


    /**
     * 加载更多
     */
    fun loadMoreData() {

        mFollowModel.loadMoreData(mNextPageLiveData.value.toString())
                .subscribe({

                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val bannerItemList = arrayListOf<HomeBean.Issue.Item>()
                    it.itemList.filter { item ->
                        item.type == "videoCollectionWithBrief"
                    }.forEach { item ->
                        //添加 item
                        bannerItemList.add(item)
                    }

                    //设置加载更多的数据
                    mFollowBeanMoreLiveData.value = bannerItemList

                    //  设置下一页URL
                    if (it.nextPageUrl.isNullOrEmpty()) {//
                        //没有更多
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE

                    } else {
                        mNextPageLiveData.value = it.nextPageUrl
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_SUCCESS
                    }

                }, {
                    mFootRequestTypeLiveData.value = FootRequestType.TYPE_NET_ERROR
                }).bindToLifecycle()


    }


    /**
     * 获取分类
     */
    fun getCategoryData() {
        //显示Loading界面
        mRequestTypeLiveData.value = RequestType.TYPE_LOADING

        mCategoryModel.getCategoryData()
                .subscribe({
                    //关闭Loading状态
                    mRequestTypeLiveData.value = RequestType.TYPE_SUCCESS
                    //设置加载数据的
                    mCategoryBeanLiveData.value = it

                }, {
                    mRequestTypeLiveData.value = RequestType.TYPE_NET_ERROR

                }).bindToLifecycle()
    }


}