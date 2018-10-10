package com.android.demo.kotlinvm.ui.account

import android.graphics.Color
import com.android.baselibrary.base.BaseViewModelActivity
import com.android.demo.kotlinvm.R
import com.android.demo.kotlinvm.ui.account.viewmodel.AccountViewModel
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE
import kotlinx.android.synthetic.main.account_activity_about.*

/**
 * Des:关于
 *
 */
class AbountActivity : BaseViewModelActivity<AccountViewModel>() {

    lateinit var mZLoadingDialog: ZLoadingDialog

    override fun getLayoutId(): Int = R.layout.account_activity_about

    override fun initView() {
        super.initView()
        mToolbar.setNavigationOnClickListener { finish() }
        mZLoadingDialog = ZLoadingDialog(this)
        mZLoadingDialog.setLoadingBuilder(Z_TYPE.MUSIC_PATH)
//                .setHintTextSize(12f) // 设置字体大小 dp
//                .setLoadingColor(Color.RED)//颜色
                .setHintText("加载中")
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#BB111111")) // 设置背景色，默认白色

    }

    override fun initData() {
        mViewModel.getDailogLoading()

    }


    override fun showLoading() {
        mZLoadingDialog.show()
    }

    override fun dismissLoading() {
        mZLoadingDialog.dismiss()
    }
}