package com.android.demo.kotlinvm.ui.main.fragment

import android.arch.lifecycle.Observer
import com.android.baselibrary.base.BaseNoRefreshRvListFragment
import com.android.demo.kotlinvm.ui.main.adapter.FollowAdapter
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.android.demo.kotlinvm.ui.main.viewmodel.DiscoveryViewModel
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * Des:
 *
 */
class DiscoveryFollowFragment : BaseNoRefreshRvListFragment<DiscoveryViewModel, HomeBean.Issue.Item>() {


    companion object {
        fun newInstance(): DiscoveryFollowFragment {
            return DiscoveryFollowFragment()
        }
    }


    override fun getListAdapter(): BaseQuickAdapter<HomeBean.Issue.Item, *> = FollowAdapter(null)

    override fun initView() {
        super.initView()
        mViewModel.getFollowBeanLiveData().observe(this, Observer {
            mBaseAdapter.setNewData(it)
        })
        mViewModel.getMoreFollowBeanLiveData().observe(this, Observer {
            mBaseAdapter.addData(it!!)
        })



    }

    /**
     * 获取界面数据
     */
    override fun getContentData() {
        mViewModel.requestFollowList()
    }

    /**
     * 上拉加载更多
     */
    override fun getMoreData() {
        mViewModel.loadMoreData()
    }

}