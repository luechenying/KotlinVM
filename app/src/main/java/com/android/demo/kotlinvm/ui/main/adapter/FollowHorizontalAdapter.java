package com.android.demo.kotlinvm.ui.main.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.android.demo.kotlinvm.R;
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean;
import com.android.demo.kotlinvm.ui.video.VideoDetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.safframework.log.L;

import java.util.List;

/**
 * Des:
 */
public class FollowHorizontalAdapter extends BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {

    public FollowHorizontalAdapter(@Nullable List<HomeBean.Issue.Item> data) {
        super(R.layout.main_item_follow_horizontal, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HomeBean.Issue.Item item) {

        HomeBean.Issue.Item.Data itemData = item.getData();
        String cover = itemData.getCover().getFeed();
        String tagText = "#";


        // 加载封页图
        RequestOptions requestOptionsIvCover = new RequestOptions().centerInside().placeholder(R.drawable.placeholder_banner);
        ImageView ivCover = helper.getView(R.id.iv_cover_feed);
        Glide.with(mContext).applyDefaultRequestOptions(requestOptionsIvCover).load(cover).into(ivCover);

        //横向 RecyclerView 封页图下面标题
        helper.setText(R.id.tv_title, itemData.getTitle() + "");


        if (itemData.getTags().size() > 0) {
            tagText += itemData.getTags().get(0).getName() + "/";
        }

        // 格式化时间
        String timeFormat = durationFormat(itemData.getDuration());

        tagText += timeFormat;

        helper.setText(R.id.tv_tag, tagText);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToVideoPlayer(view, item);
                L.e("id: " + helper.getLayoutPosition());

            }
        });

    }


    String durationFormat(long duration) {
        long minute = duration / 60;
        long second = duration % 60;
        if (minute <= 9) {
            if (second <= 9) {
                return "0" + minute + "' 0" + second + "''";
            } else {
                return "0" + minute + "' " + second + "''";
            }
        } else {
            if (second <= 9) {
                return minute + "' 0" + second + "''";
            } else {
                return minute + "' " + second + "''";
            }
        }
    }


    private void goToVideoPlayer(View view, HomeBean.Issue.Item itemData) {


        Intent intent = new Intent(mContext, VideoDetailActivity.class);

        intent.putExtra(VideoDetailActivity.KEY_VIDEO_DATA, itemData);
        intent.putExtra(VideoDetailActivity.KEY_TRANSITION, true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Pair pair = new Pair(view, VideoDetailActivity.IMG_TRANSITION);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, pair);
            ActivityCompat.startActivity(mContext, intent, activityOptionsCompat.toBundle());
        } else {
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
        }


    }


}
