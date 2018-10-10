package com.android.baselibrary.base.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.android.baselibrary.base.type.RequestType
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Des:
 */
open class BaseViewModel : ViewModel() {

    //==================设置网络请求状态

    protected val mRequestTypeLiveData = MutableLiveData<RequestType>()

    fun getRequestTypeLiveData(): LiveData<RequestType> {

        return mRequestTypeLiveData
    }


    //==================设置Rx绑定生命周期

    private var mCompositeDisposable = CompositeDisposable()


    fun addSubscription(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    /**
     * 不是必须调用，毕竟LiveData已经实行更新UI时候绑定生命周期，不过复杂的业务逻辑在ViewModel处理时候好像调用比较好。。。
     */
    fun Disposable.bindToLifecycle() {
        addSubscription(this)
    }

    override fun onCleared() {
        super.onCleared()
        //保证activity结束时取消所有正在执行的订阅
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.clear()
        }

    }


}