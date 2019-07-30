package com.love.extension

import com.love.model.BaseResponse
import com.love.network.AsyncResult
import com.love.utils.DisposableBag
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Jason
 * @description:
 * @date :2019-07-29 14:54
 */
fun <T : BaseResponse> Observable<T>.doRequest(tag: String, success: (T) -> Unit): AsyncResult {
    val mAsyncResult = AsyncResult()
    this.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe({
        if (it == null) {
            mAsyncResult.onErrorCallback?.invoke(-2, "返回数据为空")
        }
        if (it.error_code == 0) {
            success.invoke(it)
        } else {
            mAsyncResult.onErrorCallback?.invoke(it.error_code, it.reason ?: "")
        }

    }, {
        it.printStackTrace()
        mAsyncResult.onErrorCallback?.invoke(-1, it.message ?: "")

    }).addToBag(tag)
    return mAsyncResult
}

fun Disposable.addToBag(tag: String) {
    DisposableBag.instance.add(this, tag)
}