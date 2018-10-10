package com.android.demo.kotlinvm.ui.main.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class FollowAdapter extends BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {
    public FollowAdapter(@Nullable List<HomeBean.Issue.Item> data) {
        super(R.layout.main_item_follow, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.Issue.Item item) {


        HomeBean.Issue.Item.Data.Header header = item.getData().getHeader();

        // 加载作者头像
        int defAvatar = R.mipmap.default_avatar;
        RequestOptions requestOptionsAvatar = new RequestOptions().circleCrop().placeholder(defAvatar).error(defAvatar);
        ImageView ivAvatar = helper.getView(R.id.iv_avatar);
        Glide.with(mContext).applyDefaultRequestOptions(requestOptionsAvatar).load(header.getIcon()).into(ivAvatar);

        helper.setText(R.id.tv_title, header.getTitle());
        helper.setText(R.id.tv_desc, header.getDescription());

        //设置嵌套水平的 RecyclerView
        RecyclerView recyclerView = helper.getView(R.id.fl_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(new FollowHorizontalAdapter(item.getData().getItemList()));

    }
}
