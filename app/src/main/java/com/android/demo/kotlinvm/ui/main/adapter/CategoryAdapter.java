package com.android.demo.kotlinvm.ui.main.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.demo.kotlinvm.R;
import com.android.demo.kotlinvm.ui.main.model.bean.CategoryBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Des:
 */
public class CategoryAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {

    public CategoryAdapter(@Nullable List<CategoryBean> data) {
        super(R.layout.main_item_category, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {


        helper.setText(R.id.tv_category_name, item.getName());
        // 加载封页图
        RequestOptions requestOptionsIvCover = new RequestOptions().centerInside().placeholder(R.color.color_darker_gray);
        ImageView ivCover = helper.getView(R.id.iv_category);
        Glide.with(mContext).applyDefaultRequestOptions(requestOptionsIvCover).load(item.getBgPicture()).thumbnail(0.5f).into(ivCover);


    }
}
