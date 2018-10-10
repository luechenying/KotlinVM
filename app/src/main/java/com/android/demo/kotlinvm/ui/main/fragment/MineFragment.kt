package com.android.demo.kotlinvm.ui.main.fragment

import android.view.View
import com.android.baselibrary.base.BaseViewModelFragment
import com.android.baselibrary.utils.StatusBarUtil
import com.android.baselibrary.utils.startActivity
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.account.AbountActivity
import com.android.demo.kotlinvm.ui.gank.CommunicateActivity
import com.android.demo.kotlinvm.ui.gank.GankAndroidActivity
import com.android.demo.kotlinvm.ui.main.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.main_fragment_mine.*
import kotlinx.android.synthetic.main.main_fragment_mine.view.*

/**
 * Des: 我的
 */
class MineFragment : BaseViewModelFragment<MineViewModel>(), View.OnClickListener {


    override fun getLayoutId(): Int = R.layout.main_fragment_mine

    override fun initView() {
        super.initView()
        StatusBarUtil.setPaddingSmart(context, mRootView.toolbar)

        mRvShow.setOnClickListener(this)
        mCommunicate.setOnClickListener(this)
        mDifLoading.setOnClickListener(this)
    }

    override fun initData() {
    }


    override fun onClick(v: View?) {
        when {

            v?.id == R.id.mRvShow -> startActivity<GankAndroidActivity>()
            v?.id == R.id.mCommunicate -> startActivity<CommunicateActivity>()
            v?.id == R.id.mDifLoading -> startActivity<AbountActivity>()

        }
    }


}