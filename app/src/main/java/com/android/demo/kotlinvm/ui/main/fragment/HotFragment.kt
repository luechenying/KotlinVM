package com.android.demo.kotlinvm.ui.main.fragment

import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import com.android.baselibrary.base.BaseViewModelFragment
import com.android.baselibrary.base.adapter.BaseFragmentAdapter
import com.android.baselibrary.utils.StatusBarUtil
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.main.model.bean.TabInfoBean
import com.android.demo.kotlinvm.ui.main.viewmodel.HotViewModel
import kotlinx.android.synthetic.main.main_fragment_home.view.*
import kotlinx.android.synthetic.main.main_fragment_hot.*

/**
 * Des: 热门
 *
 */
class HotFragment : BaseViewModelFragment<HotViewModel>() {


    override fun getLayoutId(): Int = R.layout.main_fragment_hot

    override fun initView() {
        super.initView()
        //透明状态栏填充
        StatusBarUtil.setPaddingSmart(context, mRootView.mToolbar)
        mViewModel.getTabInfoLiveData().observe(this, Observer {
            setTabInfo(it!!)
        })

    }

    override fun initData() {
        mViewModel.getTabInfo()
    }


    /**
     * 设置 TabInfo
     */
    fun setTabInfo(tabInfoBean: TabInfoBean) {

        val mTabTitleList = ArrayList<String>()
        val mFragmentList = ArrayList<Fragment>()
        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList) { it.name }
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) { HotRankFragment.newInstance(it.apiUrl) }

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, mFragmentList, mTabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)

    }


}