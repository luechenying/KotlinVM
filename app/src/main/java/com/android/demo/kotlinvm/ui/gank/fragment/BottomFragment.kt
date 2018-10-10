package com.android.demo.kotlinvm.ui.gank.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.android.baselibrary.base.BaseRvListFragment
import com.android.baselibrary.base.type.RequestType
import com.android.demo.kotlinvm.ui.gank.adapter.CommunicateAdapter
import com.android.demo.kotlinvm.ui.gank.model.bean.GankBean
import com.android.demo.kotlinvm.ui.gank.viewmodel.GankViewModel
import com.chad.library.adapter.base.BaseQuickAdapter


/**
 * Des:
 */
class BottomFragment : BaseRvListFragment<GankViewModel, GankBean.GankItem>() {

    lateinit var mCommumicateViewModel: GankViewModel

    var mType = "Android"


    override fun getListAdapter(): BaseQuickAdapter<GankBean.GankItem, *> = CommunicateAdapter(null)

    override fun initView() {
        super.initView()
        mCommumicateViewModel = ViewModelProviders.of(activity!!).get(GankViewModel::class.java)
        //Fragment之间的通信的监听
        //TopFragment点击事件后要求的加载监听
        mCommumicateViewModel.getRequestTypeLiveData().observe(this, Observer {
            when (it) {
                RequestType.TYPE_LOADING -> showLoading()
                RequestType.TYPE_SUCCESS -> dismissLoading()
                RequestType.TYPE_ERROR -> showEmpty()
            }
        })
        //TopFragment点击事件后传递的数据监听
        mCommumicateViewModel.getmGankItemsLiveData().observe(this, Observer {
            it?.let { mType = it[0].type }
            mBaseAdapter.setNewData(it)
            getRecyclerView().scrollToPosition(0)
        })
        //BottomFragment自己事件的监听
        mViewModel.getmGankItemsLiveData().observe(this, Observer { mBaseAdapter.setNewData(it) })
        mViewModel.getMoremGankItemsLiveData().observe(this, Observer { mBaseAdapter.addData(it!!) })
    }

    override fun getContentData() {
        super.getContentData()
        mViewModel.getGankData(mType)
    }

    override fun getMoreData() {
        super.getMoreData()
        mViewModel.loadMoreGankData(mType)
    }


}