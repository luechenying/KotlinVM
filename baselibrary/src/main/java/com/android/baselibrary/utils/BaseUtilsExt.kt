package com.android.baselibrary.utils

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import org.jetbrains.anko.internals.AnkoInternals

/**
 *
 *  扩展一般工具类
 *
 */

/**
 * 设置跳转Activity
 */
inline fun <reified T : Activity> Fragment.startActivity(vararg params: Pair<String, Any?>) {
    this?.activity?.let {
        AnkoInternals.internalStartActivity(it, T::class.java, params)
    }
}

