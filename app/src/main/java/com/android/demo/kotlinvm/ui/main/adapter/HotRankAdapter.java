package com.android.demo.kotlinvm.ui.main.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;


import com.android.demo.kotlinvm.R;
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Des:
 */
public class HotRankAdapter extends BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {


    public HotRankAdapter(@Nullable List<HomeBean.Issue.Item> data) {
        super(R.layout.main_item_category_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.Issue.Item item) {
        HomeBean.Issue.Item.Data itemData = item.getData();
        String cover = itemData.getCover().getFeed();
        // 加载封页图
        RequestOptions requestOptionsIvCover = new RequestOptions().centerInside().placeholder(R.drawable.placeholder_banner);
        ImageView ivCover = helper.getView(R.id.iv_image);
        Glide.with(mContext).applyDefaultRequestOptions(requestOptionsIvCover).load(cover).into(ivCover);

        // 格式化时间
        String timeFormat = durationFormat(itemData.getDuration());

        helper.setText(R.id.tv_tag, "#" + itemData.getCategory() + "/" + timeFormat);
        helper.setText(R.id.tv_title, itemData.getTitle());


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
