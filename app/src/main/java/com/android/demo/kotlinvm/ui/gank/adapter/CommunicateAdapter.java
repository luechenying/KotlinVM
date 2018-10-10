package com.android.demo.kotlinvm.ui.gank.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.android.demo.kotlinvm.R;
import com.android.demo.kotlinvm.ui.gank.model.bean.GankBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Des:
 */
public class CommunicateAdapter extends BaseQuickAdapter<GankBean.GankItem, BaseViewHolder> {

    public CommunicateAdapter(@Nullable List<GankBean.GankItem> data) {
        super(R.layout.gank_item_communicate, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankBean.GankItem item) {


        helper.setText(R.id.tv_title, item.getDesc() + "");
        helper.setText(R.id.tv_tag, dateFormat(item.getPublishedAt() + ""));

        RequestOptions requestOptionsIvCover = new RequestOptions().centerInside().placeholder(R.drawable.placeholder_banner).error(R.drawable.placeholder_banner);
        ImageView ivCover = helper.getView(R.id.iv_video_small_card);
        if (item.getImages() != null && item.getImages().size() > 0) {
            Glide.with(mContext).applyDefaultRequestOptions(requestOptionsIvCover).load(item.getImages().get(0)).thumbnail(0.3f).into(ivCover);
        } else {
            Glide.with(mContext).load(R.drawable.placeholder_banner).into(ivCover);

        }

    }

    public static String dateFormat(String timestamp) {
        if (timestamp == null) {
            return "unknown";
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(timestamp);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return "unknown";
        }
    }

}
