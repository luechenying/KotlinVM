package com.android.baselibrary.base

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Des: 最基础的BaseFragment，可扩展
 */
abstract class BaseFragment : Fragment() {


    protected lateinit var mRootView: View


    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false

    //--------------------------setUserVisibleHint 方法

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    //--------------------------onCreate 方法

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(getLayoutId(), container, false)
        onBindViewBefore(mRootView)
        return mRootView
    }


    /**
     *  加载Fragment布局
     */
    abstract fun getLayoutId(): Int

    /**
     *  加载Fragment布局前操作
     */
    protected open fun onBindViewBefore(mRoot: View?) {}


    //--------------------------onViewCreated 方法

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        initLifeCycles(lifecycle)
        initData()
        lazyLoadDataIfPrepared()
    }

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 初始化绑定生命周期控件
     */
    protected open fun initLifeCycles(lifecycle: Lifecycle) {}

    /**
     * 初始化数据
     */
    abstract fun initData()


    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            hasLoadData = true
            lazyLoad()
        }
    }

    /**
     * 懒加载
     */
    protected open fun lazyLoad() {}


}