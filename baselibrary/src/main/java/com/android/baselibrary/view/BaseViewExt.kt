package com.android.baselibrary.view

import android.view.View

/**
 * Des:  常用View的扩展
 *
 */


/**
 * 设置View是否可见
 */
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}