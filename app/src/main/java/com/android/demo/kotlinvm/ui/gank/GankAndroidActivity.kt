package com.android.demo.kotlinvm.ui.gank

import android.arch.lifecycle.Observer
import com.android.baselibrary.base.BaseRvListActivity
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.gank.adapter.AndroidAdapter
import com.android.demo.kotlinvm.ui.gank.model.bean.GankBean
import com.android.demo.kotlinvm.ui.gank.viewmodel.GankViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.gank_activity_android.*


/**
 * Des: 展示列表
 *
 */
class GankAndroidActivity : BaseRvListActivity<GankViewModel, GankBean.GankItem>() {


    override fun getLayoutId(): Int = R.layout.gank_activity_android

    override fun getListAdapter(): BaseQuickAdapter<GankBean.GankItem, *> = AndroidAdapter(null)


    override fun initView() {
        super.initView()
        mToolbar.setNavigationOnClickListener { finish() }
        //设置背景
        val backgroundUrl = "http://img.kaiyanapp.com/d24d5797b7ab2f865409e30eef13f563.jpeg?imageMogr2/quality/60/format/jpg/thumbnail/780.0x720.0"
        val requestOptions = RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).centerCrop()
        Glide.with(this).applyDefaultRequestOptions(requestOptions).load(backgroundUrl).into(mVideoBackground)

        mViewModel.getmGankItemsLiveData().observe(this, Observer { mBaseAdapter.setNewData(it) })
        mViewModel.getMoremGankItemsLiveData().observe(this, Observer { it?.let { mBaseAdapter.addData(it) } })
    }

    override fun getContentData() {
        mViewModel.getGankData("Android")
    }

    override fun getMoreData() {
        mViewModel.loadMoreGankData("Android")
    }


}