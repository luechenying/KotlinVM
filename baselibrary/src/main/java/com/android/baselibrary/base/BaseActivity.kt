package com.android.baselibrary.base

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.baselibrary.R
import com.android.baselibrary.utils.StatusBarUtil

/**
 * Des: 最基础的BaseActivity，具可扩展性
 *
 */
abstract class BaseActivity : AppCompatActivity() {


    protected var mBundle: Bundle? = null

//--------------------------onCreate 方法

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateViewBefore(savedInstanceState)
        setContentView(getLayoutId())
        if (savedInstanceState != null) {
            onRestore(savedInstanceState)
        }
        mBundle = intent.extras
        if (mBundle != null) {
            initArguments(mBundle!!)
        }
        initWindow(initStatusBar())
        initView()
        initListener()
        initLifeCycles(lifecycle)
        initData()
    }

    /**
     *  加载Activity布局前操作
     */
    protected open fun onCreateViewBefore(savedInstanceState: Bundle?) {}

    /**
     *  加载Activity布局
     */
    abstract fun getLayoutId(): Int

    /**
     *  获取保存的状态
     */
    protected open fun onRestore(savedInstanceState: Bundle) {}

    /**
     *  获取参数信息
     */
    protected open fun initArguments(mBundle: Bundle) {}

    /**
     * 配置窗口沉浸栏
     */
    protected open fun initStatusBar(): StatusBarStyle {
        return StatusBarStyle.NORMAL
    }

    /**
     * 初始化View
     */
    abstract fun initView()

    /**
     * 初始化监听
     */
    protected open fun initListener() {}

    /**
     * 初始化绑定生命周期控件
     */
    protected open fun initLifeCycles(lifecycle: Lifecycle) {}

    /**
     * 初始化数据
     */
    abstract fun initData()

    private fun initWindow(statusBarStyle: StatusBarStyle) {
        when (statusBarStyle) {
            StatusBarStyle.FULL -> StatusBarUtil.immersiveStatusBar(this, 0f)
            StatusBarStyle.FULLDARK -> {
                StatusBarUtil.immersiveStatusBar(this, 0f)
                StatusBarUtil.setStatusBarDarkMode(this)
            }
            StatusBarStyle.NORMAL -> {
                StatusBarUtil.tintStatusBar(this, resources.getColor(R.color.system_bar_primary), 0f)
                StatusBarUtil.setStatusBarDarkMode(this)
            }
            StatusBarStyle.TRANSPARENT -> StatusBarUtil.tintStatusBar(this, resources.getColor(R.color.transparent), 0f)
            StatusBarStyle.TRANSPARENTDARK -> {
                StatusBarUtil.tintStatusBar(this, resources.getColor(R.color.transparent), 0f)
                StatusBarUtil.setStatusBarDarkMode(this)
            }
        }


    }

    protected enum class StatusBarStyle {
        NORMAL, FULL, FULLDARK, TRANSPARENT, TRANSPARENTDARK
    }


    //--------------------------onSaveInstanceState 方法

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        onSave(savedInstanceState)
    }

    /**
     * 保存状态
     */
    protected open fun onSave(outState: Bundle) {}


}