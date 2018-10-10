package com.android.baselibrary.rx

import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions

/**
 * Des: Rx 相关扩展类
 */


fun <T> Observable<T>.execute(onNext: Consumer<in T>, onError: Consumer<in Throwable>): Disposable {

    return this.compose(SchedulerUtils.ioToMain()).subscribe(onNext,onError)
}

