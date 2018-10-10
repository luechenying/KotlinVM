package com.android.demo.kotlinvm.ui.video.viewholder

import android.content.Context
import com.android.baselibrary.utils.BaseViewHolder
import com.android.baselibrary.view.setVisible
import com.android.baselibrary.widget.showToast
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean
import com.android.demo.kotlinvm.ui.video.model.bean.VideoBean
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.video_holder_detail_info.view.*

/**
 * Des:
 */
class VideoInfoViewHolder(context: Context) : BaseViewHolder(context) {

    override fun getLayoutId(): Int = R.layout.video_holder_detail_info

    override fun initView() {

    }


    /**
     * 设置视频详情数据
     */
     fun setVideoDetailInfo(data: VideoBean.Issue.Item) {

        with(mView) {
            tv_title.text = data.data?.title
            //视频简介
            expandable_text.text = data.data?.description
            //标签
            tv_tag.text = "#${data.data?.category} / ${durationFormat(data.data?.duration)}"
            //喜欢
            tv_action_favorites.text = data.data?.consumption?.collectionCount.toString()
            //分享
            tv_action_share.text = data.data?.consumption?.shareCount.toString()
            //评论
            tv_action_reply.text = data.data?.consumption?.replyCount.toString()
            if (data.data?.author != null) {
                tv_author_name.text = data.data.author.name
                tv_author_desc.text = data.data.author.description
                val requestOptionsIvCover = RequestOptions().circleCrop().placeholder(R.mipmap.default_avatar)
                Glide.with(mContext).applyDefaultRequestOptions(requestOptionsIvCover).load(data.data.author.icon).into(iv_avatar)
            } else {
                layout_author_view.setVisible(true)
            }
            tv_action_favorites.setOnClickListener { showToast("喜欢") }
            tv_action_share.setOnClickListener { showToast("分享") }
            tv_action_reply.setOnClickListener { showToast("评论") }

        }

    }
    /**
     * 设置视频详情数据
     */
    fun setVideoDetailInfo(data: HomeBean.Issue.Item) {

        with(mView) {
            tv_title.text = data.data?.title
            //视频简介
            expandable_text.text = data.data?.description
            //标签
            tv_tag.text = "#${data.data?.category} / ${durationFormat(data.data?.duration)}"
            //喜欢
            tv_action_favorites.text = data.data?.consumption?.collectionCount.toString()
            //分享
            tv_action_share.text = data.data?.consumption?.shareCount.toString()
            //评论
            tv_action_reply.text = data.data?.consumption?.replyCount.toString()
            if (data.data?.author != null) {
                tv_author_name.text = data.data.author.name
                tv_author_desc.text = data.data.author.description
                val requestOptionsIvCover = RequestOptions().circleCrop().placeholder(R.mipmap.default_avatar)
                Glide.with(mContext).applyDefaultRequestOptions(requestOptionsIvCover).load(data.data.author.icon).into(iv_avatar)
            } else {
                layout_author_view.setVisible(true)
            }
            tv_action_favorites.setOnClickListener { showToast("喜欢") }
            tv_action_share.setOnClickListener { showToast("分享") }
            tv_action_reply.setOnClickListener { showToast("评论") }

        }

    }

    fun durationFormat(duration: Long?): String {
        val minute = duration!! / 60
        val second = duration % 60
        return if (minute <= 9) {
            if (second <= 9) {
                "0$minute' 0$second''"
            } else {
                "0$minute' $second''"
            }
        } else {
            if (second <= 9) {
                "$minute' 0$second''"
            } else {
                "$minute' $second''"
            }
        }
    }

}