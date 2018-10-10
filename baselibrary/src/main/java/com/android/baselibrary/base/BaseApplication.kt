package com.android.baselibrary.base

import android.app.Application
import android.content.Context
import com.android.baselibrary.BuildConfig
import com.safframework.log.L
import kotlin.properties.Delegates

/**
 * Des:
 */
abstract class BaseApplication : Application() {

    companion object {
        var mBaseAppContext: Context by Delegates.notNull()
    }


    override fun onCreate() {
        super.onCreate()
        mBaseAppContext = applicationContext
        initBaseConfig()

    }

    private fun initBaseConfig() {
        if (BuildConfig.DEBUG) {
            L.init("KotlinVM")

            L.logLevel

        }
    }


}