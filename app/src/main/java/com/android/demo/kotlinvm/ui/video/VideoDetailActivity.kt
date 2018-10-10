package com.android.demo.kotlinvm.ui.video

import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import com.android.baselibrary.base.BaseNoRefreshRvListActivity
import com.android.baselibrary.utils.DisplayUtil
import com.android.baselibrary.widget.showToast
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.android.demo.kotlinvm.ui.video.adapter.VideoDetailAdapter
import com.android.demo.kotlinvm.ui.video.model.bean.VideoBean
import com.android.demo.kotlinvm.ui.video.viewholder.VideoInfoViewHolder
import com.android.demo.kotlinvm.ui.video.viewmodel.VideoDetailViewModel
import com.android.demo.kotlinvm.utils.CleanLeakUtils
import com.android.demo.kotlinvm.utils.VideoListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.safframework.log.L
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.NetworkUtils
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.video_activity_detail.*

/**
 * Des:  视频详情
 */
class VideoDetailActivity : BaseNoRefreshRvListActivity<VideoDetailViewModel, VideoBean.Issue.Item>() {

    companion object {
        @JvmField
        val KEY_VIDEO_DATA = "video_data"
        @JvmField
        val KEY_TRANSITION = "transition"
        const val IMG_TRANSITION = "IMG_TRANSITION"

    }

    /**
     * 视频信息
     */
    private lateinit var mVideoInfoViewHolder: VideoInfoViewHolder

    /**
     * Item 详细数据
     */
    private lateinit var itemData: HomeBean.Issue.Item


    /**
     * 是否执行过渡动画
     */
    private var isTransition: Boolean = false

    private var isPlay: Boolean = false

    private var isPause: Boolean = false

    private var transition: Transition? = null

    private var orientationUtils: OrientationUtils? = null


    override fun getLayoutId(): Int = R.layout.video_activity_detail

    override fun isLoadMore(): Boolean = false

    override fun getListAdapter(): BaseQuickAdapter<VideoBean.Issue.Item, *> = VideoDetailAdapter(null)

    override fun initArguments(mBundle: Bundle) {
        itemData = mBundle.getSerializable(KEY_VIDEO_DATA) as HomeBean.Issue.Item
        isTransition = mBundle.getBoolean(KEY_TRANSITION, false)
    }


    override fun initView() {
        super.initView()
        //过渡动画
        initTransition()
        initVideoViewConfig()

        //视频信息HEADER
        mVideoInfoViewHolder = VideoInfoViewHolder(this)
        mBaseAdapter.addHeaderView(mVideoInfoViewHolder.mView)

        mVideoInfoViewHolder.setVideoDetailInfo(itemData)

        mViewModel.getVideoItemLiveData().observe(this, Observer {
            mBaseAdapter.setNewData(it)
            mBaseRecyclerView.scrollToPosition(0)

        })
        //item 点击事件
        mBaseAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->

            val item = mBaseAdapter.getItem(position)
            when (item!!.itemType) {
                // item 视频的击事件
                VideoDetailAdapter.ITEM_TYPE_SMALL_CARD -> loadVideoInfo(null, item)
            }
        }

    }

    override fun getContentData() {
        mViewModel.requestRelatedVideo(itemData.data?.id ?: 0)
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtils)
        }
    }


    //--------------下面是视频配置。。。。。。。。。

    private fun initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
            loadVideoInfo(itemData)
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener() {
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(p0: Transition?) {
            }

            override fun onTransitionPause(p0: Transition?) {
            }

            override fun onTransitionCancel(p0: Transition?) {
            }

            override fun onTransitionStart(p0: Transition?) {
            }

            override fun onTransitionEnd(p0: Transition?) {
                L.d("onTransitionEnd()------")
                loadVideoInfo(itemData)
                transition?.removeListener(this)
            }

        })
    }


    /**
     * 初始化 VideoView 的配置
     */
    private fun initVideoViewConfig() {
        //设置旋转
        orientationUtils = OrientationUtils(this, mVideoView)
        //是否旋转
        mVideoView.isRotateViewAuto = false
        //是否可以滑动调整
        mVideoView.setIsTouchWiget(true)

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        val requestOptionsIvCover = RequestOptions().centerCrop()
        Glide.with(this).applyDefaultRequestOptions(requestOptionsIvCover).load(itemData.data?.cover?.feed).into(imageView)

        mVideoView.thumbImageView = imageView

        mVideoView.setStandardVideoAllCallBack(object : VideoListener {

            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils?.isEnable = true
                isPlay = true
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                L.d("***** onQuitFullscreen **** ")
                //列表返回的样式判断
                orientationUtils?.backToProtVideo()
            }
        })
        //设置返回按键功能
        mVideoView.backButton.setOnClickListener({ onBackPressed() })
        //设置全屏按键功能
        mVideoView.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            mVideoView.startWindowFullscreen(this, true, true)
        }
        //锁屏事件
        mVideoView.setLockClickListener(object : LockClickListener {
            override fun onClick(view: View?, lock: Boolean) {
                //配合下方的onConfigurationChanged
                orientationUtils?.isEnable = !lock
            }

        })
    }


    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else mVideoView
    }


    /**
     * 1.加载视频信息,
     */
    fun loadVideoInfo(itemInfo: HomeBean.Issue.Item?, iteminfo2: VideoBean.Issue.Item? = null) {

        val playInfo = itemInfo?.data?.playInfo
        val playInfo1 = iteminfo2?.data?.playInfo
        playInfo?.let {
            if (it.size > 1) {
                if (NetworkUtils.isWifiConnected(this)) {
                    it.filter { it.type == "high" }.forEach { setVideo(it.url) }
                } else {
                    it.filter { it.type == "normal" }.forEach { setVideo(it.url) }
                }
            } else {
//            设置播放视频 URL
                setVideo(itemInfo.data.playUrl)
            }
            //设置背景
            val backgroundUrl = itemInfo.data.cover.blurred + "/thumbnail/${DisplayUtil.getScreenHeight(this)!! - DisplayUtil.dpToPx(this, 250f)!!}x${DisplayUtil.getScreenWidth(this)}"
            backgroundUrl.let {
                val requestOptions = RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).centerCrop()
                Glide.with(this).applyDefaultRequestOptions(requestOptions).load(it).into(mVideoBackground)
            }


        }
        //点击item视频时候执行
        playInfo1?.let {
            if (it.size > 1) {
                if (NetworkUtils.isWifiConnected(this)) {
                    it.filter { it.type == "high" }.forEach { setVideo(it.url) }
                } else {
                    it.filter { it.type == "normal" }.forEach { setVideo(it.url) }
                }
            } else {
//            设置播放视频 URL
                setVideo(iteminfo2.data.playUrl)
            }
            //设置背景
            val backgroundUrl = iteminfo2.data.cover.blurred + "/thumbnail/${DisplayUtil.getScreenHeight(this)!! - DisplayUtil.dpToPx(this, 250f)!!}x${DisplayUtil.getScreenWidth(this)}"
            backgroundUrl.let {
                val requestOptions = RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).centerCrop()
                Glide.with(this).applyDefaultRequestOptions(requestOptions).load(it).into(mVideoBackground)
            }
            //
            mVideoInfoViewHolder.setVideoDetailInfo(iteminfo2)
            //
            mViewModel.requestRelatedVideo(iteminfo2.data?.id)
        }

    }

    /**
     * 设置播放视频 URL
     */
    fun setVideo(url: String) {
        L.d("playUrl:$url")
        mVideoView.setUp(url, false, "")
        //开始自动播放
        mVideoView.startPlayLogic()
    }


    /**
     * 监听返回键
     */
    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this))
            return
        //释放所有
        mVideoView.setStandardVideoAllCallBack(null)
        GSYVideoPlayer.releaseAllVideos()
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) run {
            super.onBackPressed()
        } else {
            finish()
            overridePendingTransition(R.anim.anim_out, R.anim.anim_in)
        }
    }

    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    override fun onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this)
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
    }

}