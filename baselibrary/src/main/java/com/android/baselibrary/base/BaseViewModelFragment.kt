package com.android.baselibrary.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.android.baselibrary.R
import com.android.baselibrary.base.type.RequestType
import com.android.baselibrary.base.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.base_status_fragment.view.*
import java.lang.reflect.ParameterizedType

/**
 * Des: 特别注意BaseViewModel是Fragment自己的ViewModel ！！！
 * 如果需要实现Fragment直接的共享，需要ViewModel通过getActivity()用ViewModelProviders.of(activity!!)实现
 */
abstract class BaseViewModelFragment<VM : BaseViewModel> : BaseFragment() {

    lateinit var mViewModel: VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val genericSuperclass = javaClass.genericSuperclass as ParameterizedType
        val type = genericSuperclass.actualTypeArguments[0]
        mViewModel = ViewModelProviders.of(this).get(type as Class<VM>)
    }

    override fun getLayoutId(): Int = R.layout.base_status_fragment

    override fun initView() {
        initDefaultLoading()
    }

    /**
     * 可以重写，根据不同业务实现不同网络请求返回的状态
     */
    fun initDefaultLoading() {
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
        mRootView.mBaseStatusView.showLoading()
    }

    protected open fun dismissLoading() {
        mRootView.mBaseStatusView.showContent()
    }

    protected open fun showEmpty() {
        mRootView.mBaseStatusView.showEmpty()
    }

    protected open fun showNetError() {
        mRootView.mBaseStatusView.showNoNetwork()
    }

    protected open fun showError() {
        mRootView.mBaseStatusView.showError()
    }


}