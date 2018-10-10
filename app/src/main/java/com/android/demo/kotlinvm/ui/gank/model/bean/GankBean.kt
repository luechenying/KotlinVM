package com.android.demo.kotlinvm.ui.gank.model.bean

/**
 * Des:干货集中营bean
 */
data class GankBean(var error: Boolean, var results: ArrayList<GankItem>) {
    data class GankItem(
            var _id: String,
            var createdAt: String,
            var desc: String?,
            var publishedAt: String,
            var source: String?,
            var type: String,
            var url: String,
            var used: Boolean,
            var who: String?,
            var images: List<String>?
    )
}