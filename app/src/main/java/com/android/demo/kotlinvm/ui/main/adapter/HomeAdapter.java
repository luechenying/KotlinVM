package com.android.demo.kotlinvm.ui.main.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.demo.kotlinvm.R;

import com.android.demo.kotlinvm.ui.main.model.bean.HomeBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Des:
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {


    public static final int ITEM_TYPE_TEXT_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;


    public HomeAdapter(@Nullable List<HomeBean.Issue.Item> data) {
        super(data);
        addItemType(ITEM_TYPE_TEXT_HEADER, R.layout.main_item_home_header);    //textHeader
        addItemType(ITEM_TYPE_CONTENT, R.layout.main_item_home);         //item

    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.Issue.Item item) {

        switch (helper.getItemViewType()) {
            case ITEM_TYPE_TEXT_HEADER:
                convertHeader(helper, item);
                break;
            case ITEM_TYPE_CONTENT:
                convertContent(helper, item);
                break;
        }


    }

    private void convertHeader(BaseViewHolder helper, HomeBean.Issue.Item item) {
        helper.setText(R.id.tvHeader, item.getData().getText() + "");
    }

    private void convertContent(BaseViewHolder helper, HomeBean.Issue.Item item) {


        HomeBean.Issue.Item.Data itemData = item.getData();
        String cover = itemData.getCover().getFeed();
        String avatar = itemData.getAuthor().getIcon();
        String tagText = "#";

        // 作者出处为空，就显获取提供者的信息
        if (TextUtils.isEmpty(avatar)) {
            avatar = itemData.getProvider().getIcon();
        }
        // 加载封页图
        RequestOptions requestOptionsIvCover = new RequestOptions().centerInside().placeholder(R.drawable.placeholder_banner);
        ImageView ivCover = helper.getView(R.id.iv_cover_feed);
        Glide.with(mContext).applyDefaultRequestOptions(requestOptionsIvCover).load(cover).into(ivCover);

        // 如果提供者信息为空，就显示默认
        int defAvatar = R.mipmap.default_avatar;
        RequestOptions requestOptionsAvatar = new RequestOptions().circleCrop().placeholder(defAvatar).error(defAvatar);
        ImageView ivAvatar = helper.getView(R.id.iv_avatar);
        Glide.with(mContext).applyDefaultRequestOptions(requestOptionsAvatar).load(avatar).into(ivAvatar);

        helper.setText(R.id.tv_title, itemData.getTitle());


        //遍历标签
        for (int i = 0; i < itemData.getTags().size() && i <= 4; i++) {
            tagText += itemData.getTags().get(i).getName() + "/";
        }

        // 格式化时间
        String timeFormat = durationFormat(itemData.getDuration());

        tagText += timeFormat;

        helper.setText(R.id.tv_tag, tagText);

        helper.setText(R.id.tv_category, "#" + itemData.getCategory());


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
