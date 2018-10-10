package com.android.baselibrary.base

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.baselibrary.R
import com.android.baselibrary.base.type.FootRequestType
import com.android.baselibrary.base.type.RequestType
import com.android.baselibrary.base.viewmodel.BaseListViewModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.base_recyclerview_activity.*


/**
 * Des:上下拉通用Activity类
 */
abstract class BaseRvListActivity<VM : BaseListViewModel, DATA> : BaseViewModelActivity<VM>() {


    protected var isRefresh = false


    protected lateinit var mBaseAdapter: BaseQuickAdapter<DATA, *>


    override fun getLayoutId(): Int = R.layout.base_recyclerview_activity


    override fun initView() {

        //不能调用super.initView()

        //设置刷新头部
        val materialHeader = MaterialHeader(this)
        mBaseRefresh.setRefreshHeader(materialHeader)
        mBaseRefresh.setEnableOverScrollDrag(false)
        mBaseRefresh.setHeaderInsetStart(-5f)
        mBaseRefresh.isEnableLoadMore = false
        //设置Adapter
        mBaseAdapter = getListAdapter()
        mBaseRecyclerView.layoutManager = getLayoutManager()
        mBaseRecyclerView.adapter = mBaseAdapter
        mBaseRefresh.setOnRefreshListener {
            isRefresh = true
            getContentData()
        }
        mBaseStatusView.setOnRetryClickListener { getContentData() }

        mBaseAdapter.setOnLoadMoreListener({ getMoreData() }, mBaseRecyclerView)

        //设置网络请求的observe
        mViewModel.getRequestTypeLiveData().observe(this, Observer {
            when (it) {
                RequestType.TYPE_LOADING -> {
                    if (!isRefresh) {
                        //不是下拉刷新的时候才要显示 Loading
                        showLoading()
                    }
                }
                RequestType.TYPE_SUCCESS -> {
                    if (!isRefresh) {
                        dismissLoading()
                    } else {
                        mBaseRefresh.finishRefresh()
                    }
                }
                RequestType.TYPE_ERROR -> {
                    if (!isRefresh) {
                        showNetError()
                    } else {
                        showError()
                    }
                }
                RequestType.TYPE_NET_ERROR -> {
                    if (!isRefresh) {
                        showNetError()
                    } else {
                        mBaseRefresh.finishRefresh()
                    }
                }
                RequestType.TYPE_EMPTY -> {
                    showEmpty()
                }
            }
        })

        //设置Foot状态的observe
        mViewModel.getFootRequestTypeLiveData().observe(this, Observer {
            when (it) {
                FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW -> {
                    //没有更多数据,并且隐藏
                    mBaseAdapter.loadMoreEnd(true)
                }
                FootRequestType.TYPE_SUCCESS -> {
                    //加载成功
                    mBaseAdapter.loadMoreComplete()
                }

                FootRequestType.TYPE_NET_ERROR -> {
                    //加载失败
                    mBaseAdapter.loadMoreFail()
                }

                FootRequestType.TYPE_ERROR -> {
                    //加载失败
                    mBaseAdapter.loadMoreFail()
                }

                FootRequestType.TYPE_NO_MORE -> {
                    //没有更多
                    mBaseAdapter.loadMoreEnd()
                }
            }
        })

    }

    override fun initData() {
        getContentData()
    }

    /**
     * 设置LayoutManager
     */
    protected open fun getLayoutManager(): RecyclerView.LayoutManager {

        return LinearLayoutManager(this)
    }

    /**
     * 设置Adapter
     */
    protected abstract fun getListAdapter(): BaseQuickAdapter<DATA, *>

    /**
     * 初始加载数据
     */
    protected open fun getContentData() {}

    /**
     * 加载更多数据
     */
    protected open fun getMoreData() {}


    override fun showLoading() {
        mBaseStatusView.showLoading()
    }

    override fun dismissLoading() {
        mBaseStatusView.showContent()
    }

    override fun showEmpty() {
        mBaseStatusView.showEmpty()
    }

    override fun showNetError() {
        mBaseStatusView.showNoNetwork()
    }

    override fun showError() {
        mBaseStatusView.showError()
    }

}