package com.android.demo.kotlinvm.ui.main.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.android.baselibrary.base.type.FootRequestType
import com.android.baselibrary.base.type.RequestType
import com.android.baselibrary.base.viewmodel.BaseListViewModel
import com.android.demo.kotlinvm.ui.main.model.HomeModel
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.safframework.log.L

/**
 * Des:
 */
class HomeViewModel : BaseListViewModel() {


    private val mHomeModel: HomeModel by lazy {

        HomeModel()
    }


    private val mHomeBannerBeanLiveData = MutableLiveData<List<HomeBean.Issue.Item>>()

    private val mHomeBeanLiveData = MutableLiveData<List<HomeBean.Issue.Item>>()

    private val mHomeBeanMoreLiveData = MutableLiveData<List<HomeBean.Issue.Item>>()


    fun getHomeBannerBeanLiveData(): LiveData<List<HomeBean.Issue.Item>> {

        return mHomeBannerBeanLiveData
    }

    fun getHomeBeanLiveData(): LiveData<List<HomeBean.Issue.Item>> {

        return mHomeBeanLiveData
    }

    fun getMoreHomeBeanLiveData(): LiveData<List<HomeBean.Issue.Item>> {

        return mHomeBeanMoreLiveData
    }

    /**
     * 获取首页精选数据 banner 加 一页数据
     */
    fun requestHomeData(num: Int) {

        //显示Loading界面
        mRequestTypeLiveData.value = RequestType.TYPE_LOADING
        mHomeModel.requestHomeData(num)
                .flatMap { homeBean ->

                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val bannerItemList = homeBean.issueList[0].itemList
                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard" /*|| item.type == "textHeader"*/
                    }.forEach { item ->
                        //移除 item
                        bannerItemList.remove(item)
                    }
                    //记录第一页是当做 banner 数据，
//                    L.json(GsonConvert.toJson(bannerItemList))
                    //赋值banner 数据
                    mHomeBannerBeanLiveData.value = bannerItemList
                    //根据 nextPageUrl 请求下一页数据
                    mHomeModel.loadMoreData(homeBean.nextPageUrl)
                }
                .subscribe({ homeBean ->


                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val newBannerItemList = homeBean.issueList[0].itemList
                    newBannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard" /*|| item.type == "textHeader"*/
                    }.forEach { item ->
                        //移除 item
                        newBannerItemList.remove(item)
                    }

                    //设置首次加载数据的
                    mHomeBeanLiveData.value = newBannerItemList

                    //关闭Loading状态
                    mRequestTypeLiveData.value = RequestType.TYPE_SUCCESS

                    //  设置下一页URL
                    if (homeBean.nextPageUrl.isNullOrEmpty()) {
                        //没有更多
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW
                    } else {
                        mNextPageLiveData.value = homeBean.nextPageUrl
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

        L.e("nextPageUrl: ${mNextPageLiveData.value.toString()}")

        mHomeModel.loadMoreData(mNextPageLiveData.value.toString())
                .subscribe({ homeBean ->

                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val newItemList = homeBean.issueList[0].itemList
                    newItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard" /*|| item.type == "textHeader"*/
                    }.forEach { item ->
                        //移除 item
                        newItemList.remove(item)
                    }

                    //设置加载更多的数据
                    mHomeBeanMoreLiveData.value = newItemList

                    //  设置下一页URL
                    if (homeBean.nextPageUrl.isNullOrEmpty()) {
                        //没有更多
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_NO_MORE
                    } else {
                        mNextPageLiveData.value = homeBean.nextPageUrl
                        mFootRequestTypeLiveData.value = FootRequestType.TYPE_SUCCESS
                    }

                }, {
                    mFootRequestTypeLiveData.value = FootRequestType.TYPE_NET_ERROR

                }).bindToLifecycle()

    }


}












