package com.android.demo.kotlinvm.ui.category.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.android.demo.kotlinvm.R;
import com.android.demo.kotlinvm.ui.main.adapter.FollowHorizontalAdapter;
import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Des:
 */
public class CategoryDetailAdapter extends BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {


    public CategoryDetailAdapter(@Nullable List<HomeBean.Issue.Item> data) {
        super(R.layout.category_item_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.Issue.Item item) {


        HomeBean.Issue.Item.Data itemData = item.getData();
        String cover = itemData.getCover().getFeed();


        // 加载封页图
        RequestOptions requestOptionsIvCover = new RequestOptions().placeholder(R.drawable.placeholder_banner);
        ImageView ivCover = helper.getView(R.id.iv_image);
        Glide.with(mContext).applyDefaultRequestOptions(requestOptionsIvCover).load(cover).thumbnail(0.2f).into(ivCover);

        helper.setText(R.id.tv_title, itemData.getTitle());

        // 格式化时间
        String timeFormat = durationFormat(itemData.getDuration());

        helper.setText(R.id.tv_tag, "#" + itemData.getCategory() + "/" + timeFormat);

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
