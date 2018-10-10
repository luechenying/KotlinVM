package com.android.baselibrary.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * Des:
 */
object DisplayUtil {


    fun getDisplayMetrics(context: Context): DisplayMetrics {
        return context.resources.displayMetrics
    }


    fun dpToPx(context: Context, dpValue: Int): Int {
        return (dpValue * getDisplayMetrics(context).density + 0.5f).toInt()
    }

    fun dpToPx(context: Context, dp: Float): Float {
        return dp * getDisplayMetrics(context).density
    }

    fun dpToPx(context: Context, dpValue: Double): Int {
        return (dpValue * getDisplayMetrics(context).density + 0.5f).toInt()
    }

    fun spToPx(context: Context, sp: Float): Float {
        val metrics = getDisplayMetrics(context)
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics)
    }

    fun getScreenHeight(context: Context): Float {
        return getDisplayMetrics(context).heightPixels.toFloat()
    }

    fun getScreenWidth(context: Context): Float {
        return getDisplayMetrics(context).widthPixels.toFloat()
    }



}