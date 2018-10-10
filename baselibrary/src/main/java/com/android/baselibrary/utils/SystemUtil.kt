package com.android.baselibrary.utils

import android.os.Build

/**
 * Des:
 */
object SystemUtil {


    fun getMobileModel(): String {
        var model: String? = Build.MODEL
        model = model?.trim { it <= ' ' } ?: ""
        return model
    }


}