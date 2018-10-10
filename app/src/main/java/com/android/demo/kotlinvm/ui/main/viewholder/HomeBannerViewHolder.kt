package com.android.demo.kotlinvm.ui.main.viewholder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View

import com.android.baselibrary.utils.BaseViewHolder
import com.android.baselibrary.widget.showToast
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.android.demo.kotlinvm.ui.video.VideoDetailActivity

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Observable
import kotlinx.android.synthetic.main.main_holder_home_banner.view.*
import org.jetbrains.anko.toast


/**
 * Des: home界面的头部banner
 */
class HomeBannerViewHolder(mContext: Context) : BaseViewHolder(mContext) {


    override fun getLayoutId(): Int = R.layout.main_holder_home_banner

    override fun initView() {}

    fun setBGABanner(bannerList: List<HomeBean.Issue.Item>) {

        val bannerFeedList = ArrayList<String>()
        val bannerTitleList = ArrayList<String>()
        //取出banner 显示的 img 和 Title
        Observable.fromIterable(bannerList)
                .subscribe { list ->
                    bannerFeedList.add(list.data?.cover?.feed ?: "")
                    bannerTitleList.add(list.data?.title ?: "")
                }

        //设置 banner
        with(mView) {
            banner.run {
                setAutoPlayAble(bannerFeedList.size > 1)
                setData(bannerFeedList, bannerTitleList)
                setAdapter { banner, _, feedImageUrl, position ->
                    val requestOptionsIvCover = RequestOptions().centerInside().placeholder(R.drawable.placeholder_banner)
                    Glide.with(mContext).applyDefaultRequestOptions(requestOptionsIvCover).load(feedImageUrl).into(banner.getItemImageView(position))

                }

            }
            //没有使用到的参数在 kotlin 中用"_"代替
            banner.setDelegate { _, imageView, _, i ->

                goToVideoPlayer(mContext as Activity, imageView, bannerList[i])

            }
        }

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