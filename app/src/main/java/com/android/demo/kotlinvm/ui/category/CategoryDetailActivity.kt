package com.android.demo.kotlinvm.ui.category

import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.android.baselibrary.base.BaseNoRefreshRvListActivity
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.category.adapter.CategoryDetailAdapter
import com.android.demo.kotlinvm.ui.category.viewmodel.CategoryDetailViewModel
import com.android.demo.kotlinvm.ui.main.model.bean.CategoryBean
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.android.demo.kotlinvm.ui.video.VideoDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.category_activity_detail.*

/**
 * Des: 分类详情,展示不调用加载界面
 */
class CategoryDetailActivity : BaseNoRefreshRvListActivity<CategoryDetailViewModel, HomeBean.Issue.Item>() {

    companion object {
        val KEY_CATEGORY_DATA = "category_data"
    }

    private var mCategoryBean: CategoryBean? = null

    override fun initArguments(mBundle: Bundle) {
        mCategoryBean = mBundle.getSerializable(KEY_CATEGORY_DATA) as CategoryBean
    }

    override fun getLayoutId(): Int = R.layout.category_activity_detail

    override fun getListAdapter(): BaseQuickAdapter<HomeBean.Issue.Item, *> = CategoryDetailAdapter(null)

    override fun initView() {
        super.initView()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        // 加载headerImage
        val requestOptionsIvCover = RequestOptions()/*.centerInside()*/.placeholder(R.color.color_darker_gray)
        Glide.with(this).applyDefaultRequestOptions(requestOptionsIvCover).load(mCategoryBean?.headerImage).into(imageView)

        tv_category_desc.text = "#${mCategoryBean?.description}#"

        collapsing_toolbar_layout.title = mCategoryBean?.name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE) //设置还没收缩时状态下字体颜色
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK) //设置收缩后Toolbar上字体的颜色

        mViewModel.getCateDetailsLiveData().observe(this, Observer { mBaseAdapter.setNewData(it) })
        mViewModel.getMoreHmCateDetailsLiveData().observe(this, Observer { mBaseAdapter.addData(it!!) })

        mBaseAdapter.setOnItemClickListener { adapter, view, position -> goToVideoPlayer(view, mBaseAdapter.getItem(position)!!) }

    }

    /**
     * 初始获取当前分类列表
     */
    override fun getContentData() {
        mCategoryBean?.id?.let {
            mViewModel.getCategoryDetailList(it)
        }
    }

    /**
     * 上拉加载更多
     */
    override fun getMoreData() {
        super.getMoreData()
        mViewModel.loadMoreData()
    }

    /**
     * 跳转到视频详情页面播放
     */
    private fun goToVideoPlayer(view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(this, VideoDetailActivity::class.java)
        intent.putExtra(VideoDetailActivity.KEY_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.KEY_TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this, pair)
            ActivityCompat.startActivity(this, intent, activityOptions.toBundle())
        } else {
            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }

}