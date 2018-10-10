package com.android.demo.kotlinvm.ui.main.model.bean

/**
 * desc: 热门的 tabInfo
 */

data class TabInfoBean(val tabInfo: TabInfo) {

    data class TabInfo(val tabList: ArrayList<Tab>)

    data class Tab(val id: Long, val name: String, val apiUrl: String)
}
