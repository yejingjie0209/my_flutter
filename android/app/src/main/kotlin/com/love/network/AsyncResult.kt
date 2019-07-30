package com.love.network

/**
 * @author Jason
 * @description:
 * @date :2019-07-29 15:18
 */
typealias OnErrorCallback = (Int, String) -> Unit

typealias OnFinishCallback = () -> Unit

class AsyncResult {
    var onErrorCallback: OnErrorCallback? = null
    var onFinishCallback: OnFinishCallback? = null

    fun onError(onErrorCallback: OnErrorCallback): AsyncResult {
        this.onErrorCallback = onErrorCallback
        return this

    }

    fun onFinish(onFinishCallback: OnFinishCallback) {
        this.onFinishCallback = onFinishCallback
    }
}