package com.android.demo.kotlinvm.ui.main.fragment

import android.support.v4.app.Fragment
import com.android.baselibrary.base.BaseViewModelFragment
import com.android.baselibrary.base.adapter.BaseFragmentAdapter
import com.android.baselibrary.utils.StatusBarUtil
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.main.viewmodel.DiscoveryViewModel
import kotlinx.android.synthetic.main.main_fragment_discovery.*


/**
 * Des:  发现(和热门首页同样的布局）
 *
 */
class DiscoveryFragment : BaseViewModelFragment<DiscoveryViewModel>() {


    override fun getLayoutId(): Int = R.layout.main_fragment_discovery

    override fun initView() {
        super.initView()
        //透明状态栏填充
        StatusBarUtil.setPaddingSmart(context, mToolbar)


    }

    override fun initData() {

        //设置 TabInfo
        val mTabTitleList = ArrayList<String>()
        val mFragmentList = ArrayList<Fragment>()

        mTabTitleList.add("关注")
        mTabTitleList.add("分类")
        mFragmentList.add(DiscoveryFollowFragment.newInstance())
        mFragmentList.add(DiscoveryCategoryFragment.newInstance())

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, mFragmentList, mTabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)
    }


}