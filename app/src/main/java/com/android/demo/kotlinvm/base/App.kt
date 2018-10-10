package com.android.demo.kotlinvm.base

import android.content.Context
import com.android.baselibrary.base.BaseApplication

/**
 * Des:
 */
class App : BaseApplication() {

    companion object {
        open fun getAppContext(): Context {
            return BaseApplication.mBaseAppContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        initAppConfig()
    }

    private fun initAppConfig() {


    }


}