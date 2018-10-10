package com.android.demo.kotlinvm.ui.main.fragment


import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.baselibrary.base.BaseRvListFragment
import com.android.baselibrary.utils.StatusBarUtil
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.main.adapter.HomeAdapter
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.android.demo.kotlinvm.ui.main.viewholder.HomeBannerViewHolder
import com.android.demo.kotlinvm.ui.main.viewmodel.HomeViewModel
import com.android.demo.kotlinvm.ui.video.VideoDetailActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.main_fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Des: 首页精选
 */
class HomeFragment : BaseRvListFragment<HomeViewModel, HomeBean.Issue.Item>() {


    /**
     * 轮播图
     */
    private lateinit var homeBannerViewHolder: HomeBannerViewHolder

    private val mSimpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }


    override fun getLayoutId(): Int = R.layout.main_fragment_home

    override fun getListAdapter(): BaseQuickAdapter<HomeBean.Issue.Item, *> = HomeAdapter(null)

    override fun initView() {
        super.initView()

        //初始化Banner
        activity?.let {
            //透明状态栏填充
            StatusBarUtil.setPaddingSmart(it, mRootView.mToolbar)
            homeBannerViewHolder = HomeBannerViewHolder(it)
        }

        //设置Adapte的头部Banner
        mBaseAdapter.addHeaderView(homeBannerViewHolder.mView)

        //填充Banner数据
        mViewModel.getHomeBannerBeanLiveData().observe(this, Observer {
            homeBannerViewHolder.setBGABanner(it!!)
        })

        //填充初始或者下拉数据
        mViewModel.getHomeBeanLiveData().observe(this, Observer {
            mBaseAdapter.setNewData(it)

        })

        //填充上拉加载更多数据
        mViewModel.getMoreHomeBeanLiveData().observe(this, Observer {
            mBaseAdapter.addData(it!!)
        })


        //item 点击事件
        mBaseAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->

            val item = mBaseAdapter.getItem(position)
            when (item!!.itemType) {
                //非日期点击事件
                HomeAdapter.ITEM_TYPE_CONTENT -> activity?.let {
                    goToVideoPlayer(it, view, item)
                }
            }
        }

        //RecyclerView滚动的时候调用,设置顶部日期
        mRootView.mBaseRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = (mRootView.mBaseRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    mRootView.mToolbar.setBackgroundColor(resources.getColor(R.color.color_translucent))
                    mRootView.mIvTitleSearch.setImageResource(R.mipmap.ic_action_search_white)
                    mRootView.mTvTitle.text = ""
                } else {
                    if (mBaseAdapter.data.size > 1) {
                        mRootView.mToolbar.setBackgroundColor(resources.getColor(R.color.color_title_bg))
                        mRootView.mIvTitleSearch.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mBaseAdapter.data
                        val item = itemList[currentVisibleItemPosition - 1]
//                        L.json(GsonConvert.toJson(item))
                        if (item.type == "textHeader") {
                            mRootView.mTvTitle.text = item.data?.text
                        } else {
                            mRootView.mTvTitle.text = mSimpleDateFormat.format(item.data?.date)
                        }
                    }
                }
            }

        })
    }


    /**
     * 下拉刷新而获取banner
     */
    override fun getContentData() {
        mViewModel.requestHomeData(1)
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