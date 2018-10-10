package com.android.baselibrary.base

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.android.baselibrary.R
import com.android.baselibrary.base.type.FootRequestType
import com.android.baselibrary.base.viewmodel.BaseListViewModel
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.base_no_refresh_fragment.*


/**
 * Des: 上拉加载更多通用Activity类
 */
abstract class BaseNoRefreshRvListActivity<VM : BaseListViewModel, DATA> : BaseViewModelActivity<VM>() {


    protected lateinit var mBaseAdapter: BaseQuickAdapter<DATA, *>


    override fun getLayoutId(): Int = R.layout.base_no_refresh_fragment


    override fun initView() {

        //必须调用
        super.initView()

        //设置Adapter
        mBaseAdapter = getListAdapter()
        mBaseRecyclerView.layoutManager = getLayoutManager()
        mBaseRecyclerView.adapter = mBaseAdapter
        mBaseStatusView.setOnRetryClickListener { getContentData() }
        if (isLoadMore()) {
            mBaseAdapter.setOnLoadMoreListener({ getMoreData() }, mBaseRecyclerView)
        }
        //设置Foot状态的observe
        mViewModel.getFootRequestTypeLiveData().observe(this, Observer {
            when (it) {
                FootRequestType.TYPE_NO_MORE_AND_GONE_VIEW -> {
                    //没有更多数据,并且隐藏
                    mBaseAdapter.loadMoreEnd(true)
                }
                FootRequestType.TYPE_SUCCESS -> {
                    //加载成功
                    mBaseAdapter.loadMoreFail()
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
     * 设置是否需要加载更多数据
     */
    protected open fun isLoadMore(): Boolean {

        return true
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


}