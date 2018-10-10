package com.android.baselibrary.base.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.android.baselibrary.base.type.FootRequestType

/**
 * Des:
 */
abstract class BaseListViewModel : BaseViewModel() {


    protected val mFootRequestTypeLiveData = MutableLiveData<FootRequestType>()


    fun getFootRequestTypeLiveData(): LiveData<FootRequestType> {

        return mFootRequestTypeLiveData
    }


    //下一页url
    protected val mNextPageLiveData = MutableLiveData<String>()

    //当前显示的页数
    protected val mPageLiveData = MutableLiveData<Int>()

    //默认一页加载的条目
    protected val mDefaultCount = 10

}