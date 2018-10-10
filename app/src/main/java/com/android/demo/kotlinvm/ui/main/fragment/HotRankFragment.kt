package com.android.demo.kotlinvm.ui.main.fragment

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.android.baselibrary.base.BaseNoRefreshRvListFragment
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.main.adapter.HotRankAdapter
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.android.demo.kotlinvm.ui.main.viewmodel.HotViewModel
import com.android.demo.kotlinvm.ui.video.VideoDetailActivity
import com.chad.library.adapter.base.BaseQuickAdapter


/**
 * Des: 实现懒加载，Fragment不是一开始就初始化感觉没必要懒加载
 */
class HotRankFragment : BaseNoRefreshRvListFragment<HotViewModel, HomeBean.Issue.Item>() {


    private var mUrl: String? = null


    companion object {
        fun newInstance(url: String): HotRankFragment {
            val fragment = HotRankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mUrl = url
            return fragment

        }
    }

    override fun isLoadMore(): Boolean = false

    override fun getListAdapter(): BaseQuickAdapter<HomeBean.Issue.Item, *> = HotRankAdapter(null)

    override fun initView() {
        super.initView()
        mViewModel.getRankListLiveData().observe(this, Observer {
            mBaseAdapter.setNewData(it)
        })
        mBaseAdapter.setOnItemClickListener { adapter, view, position -> goToVideoPlayer(activity!!, view, mBaseAdapter.getItem(position)!!) }
    }

    override fun getContentData() {
    }

    override fun lazyLoad() {
        mViewModel.requestRankList(mUrl!!)
    }


    /**
     * 跳转到视频详情页面播放
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(VideoDetailActivity.KEY_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.KEY_TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }

}