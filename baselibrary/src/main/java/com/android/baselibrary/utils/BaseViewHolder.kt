package com.android.baselibrary.utils

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View

/**
 * Des:
 */
abstract class BaseViewHolder constructor(val mContext: Context) {

    var mView: View

    init {
        mView = LayoutInflater.from(mContext).inflate(getLayoutId(), null)
        initView()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView()
}