package com.android.baselibrary.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.android.baselibrary.R
import com.android.baselibrary.base.type.RequestType
import com.android.baselibrary.base.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.base_status_activity.*
import java.lang.reflect.ParameterizedType

/**
 * Des: ViewModel相关的基础Activity类
 */
abstract class BaseViewModelActivity<VM : BaseViewModel> : BaseActivity() {

    lateinit var mViewModel: VM

    override fun onCreateViewBefore(savedInstanceState: Bundle?) {
        val genericSuperclass = javaClass.genericSuperclass as ParameterizedType
        val type = genericSuperclass.actualTypeArguments[0]
        mViewModel = ViewModelProviders.of(this).get(type as Class<VM>)
    }

    override fun getLayoutId(): Int = R.layout.base_status_activity

    override fun initView() {
        initLoading()
    }

    /**
     * 可以重写，根据不同业务实现不同网络请求返回的状态
     */
    fun initLoading() {
        mViewModel.getRequestTypeLiveData().observe(this, Observer {
            when (it) {
                RequestType.TYPE_LOADING -> showLoading()
                RequestType.TYPE_SUCCESS -> dismissLoading()
                RequestType.TYPE_EMPTY -> showEmpty()
                RequestType.TYPE_NET_ERROR -> showNetError()
                RequestType.TYPE_ERROR -> showError()
            }
        })
    }


    protected open fun showLoading() {
        mBaseStatusView.showLoading()
    }

    protected open fun dismissLoading() {
        mBaseStatusView.showContent()
    }

    protected open fun showEmpty() {
        mBaseStatusView.showEmpty()
    }

    protected open fun showNetError() {
        mBaseStatusView.showNoNetwork()
    }

    protected open fun showError() {
        mBaseStatusView.showError()
    }


}