package com.android.demo.kotlinvm.ui.main

import android.os.Bundle
import com.android.baselibrary.base.BaseActivity
import com.android.baselibrary.view.MainNavigateTabBar
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.main.fragment.DiscoveryFragment
import com.android.demo.kotlinvm.ui.main.fragment.HomeFragment
import com.android.demo.kotlinvm.ui.main.fragment.HotFragment
import com.android.demo.kotlinvm.ui.main.fragment.MineFragment
import kotlinx.android.synthetic.main.main_activity_main.*

class MainActivity : BaseActivity() {

    private val TAB_HOME = "每日精选"
    private val TAB_DISCOVERY = "发现"
    private val TAB_HOT = "热门"
    private val TAB_MINE = "我的"

    override fun getLayoutId(): Int = R.layout.main_activity_main

    override fun initStatusBar(): StatusBarStyle = StatusBarStyle.FULLDARK


    override fun initView() {
        //设置首页界面和Tab
        mNavigateTabBar.addTab(HomeFragment::class.java, MainNavigateTabBar.TabParam(R.mipmap.ic_home_normal, R.mipmap.ic_home_selected, TAB_HOME))
        mNavigateTabBar.addTab(DiscoveryFragment::class.java, MainNavigateTabBar.TabParam(R.mipmap.ic_discovery_normal, R.mipmap.ic_discovery_selected, TAB_DISCOVERY))
        mNavigateTabBar.addTab(HotFragment::class.java, MainNavigateTabBar.TabParam(R.mipmap.ic_hot_normal, R.mipmap.ic_hot_selected, TAB_HOT))
        mNavigateTabBar.addTab(MineFragment::class.java, MainNavigateTabBar.TabParam(R.mipmap.ic_mine_normal, R.mipmap.ic_mine_selected, TAB_MINE))
        mNavigateTabBar.init(supportFragmentManager)

    }

    override fun initData() {}

    override fun onSave(outState: Bundle) {
        super.onSave(outState)
        mNavigateTabBar.onSaveInstanceState(outState);
    }

    override fun onRestore(savedInstanceState: Bundle) {
        super.onRestore(savedInstanceState)
        mNavigateTabBar.onRestoreInstanceState(savedInstanceState)
    }

}
