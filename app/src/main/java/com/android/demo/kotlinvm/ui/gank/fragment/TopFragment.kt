package com.android.demo.kotlinvm.ui.gank.fragment

import android.arch.lifecycle.ViewModelProviders
import com.android.baselibrary.base.BaseFragment
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.gank.viewmodel.GankViewModel
import kotlinx.android.synthetic.main.gank_fragment_top.*

/**
 * Des:
 */
class TopFragment : BaseFragment() {


    lateinit var mCommumicateViewModel: GankViewModel

    override fun getLayoutId(): Int = R.layout.gank_fragment_top

    override fun initView() {
        mCommumicateViewModel = ViewModelProviders.of(activity!!).get(GankViewModel::class.java)

    }

    override fun initData() {
        mAndroid.setOnClickListener { mCommumicateViewModel.getGankData("Android") }
        miOS.setOnClickListener { mCommumicateViewModel.getGankData("iOS") }
    }
}