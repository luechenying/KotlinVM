package com.android.baselibrary.widget

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.safframework.log.L

/**
 * Des: 吐司、dialog等相关的扩展类
 */


/**
 * 设置吐司
 */
fun Context.showToast(content: String): Toast? {
    val toast = Toast.makeText(this, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Fragment.showToast(content: String): Toast? {
    val toast = Toast.makeText(activity, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun View.showToast(content: String): Toast? {
    val toast = Toast.makeText(context, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}