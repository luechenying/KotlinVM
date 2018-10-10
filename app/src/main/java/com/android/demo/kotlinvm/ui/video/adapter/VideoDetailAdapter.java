package com.android.demo.kotlinvm.ui.video.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.android.demo.kotlinvm.R;
import com.android.demo.kotlinvm.ui.video.model.bean.VideoBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Des:
 */
public class VideoDetailAdapter extends BaseMultiItemQuickAdapter<VideoBean.Issue.Item, BaseViewHolder> {


    public static final int ITEM_TYPE_TEXT_CARD = 0;
    public static final int ITEM_TYPE_SMALL_CARD = 1;


    public VideoDetailAdapter(@Nullable List<VideoBean.Issue.Item> data) {
        super(data);
        addItemType(ITEM_TYPE_TEXT_CARD, R.layout.video_item_text_card);    //textCard
        addItemType(ITEM_TYPE_SMALL_CARD, R.layout.video_item_small_card);         //videoSmallCard

    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean.Issue.Item item) {

        switch (helper.getItemViewType()) {
            case ITEM_TYPE_TEXT_CARD:
                helper.setText(R.id.tv_text_card, item.getData().getText());
                break;
            case ITEM_TYPE_SMALL_CARD:
                helper.setText(R.id.tv_title, item.getData().getTitle());
                helper.setText(R.id.tv_tag, item.getData().getCategory() + durationFormat(item.getData().getDuration()));

                RequestOptions requestOptionsIvCover = new RequestOptions().centerInside().placeholder(R.drawable.placeholder_banner);
                ImageView ivCover = helper.getView(R.id.iv_video_small_card);
                Glide.with(mContext).applyDefaultRequestOptions(requestOptionsIvCover).load(item.getData().getCover().getDetail()).into(ivCover);
                break;
        }


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


}
