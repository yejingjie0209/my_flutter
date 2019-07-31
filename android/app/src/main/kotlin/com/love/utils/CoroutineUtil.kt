package com.love.utils

import android.util.Log
import kotlinx.coroutines.*

/**
 * @author Jason
 * @description:
 * @date :2019-07-30 18:03
 */
class CoroutineUtil {

    suspend fun launcheInIORetry(delay: Long, block: suspend CoroutineScope.() -> Unit) {
        while (true) {
            launchInIO(block)
            delay(delay)
        }

    }

    fun launchInIO(block: suspend CoroutineScope.() -> Unit) =
            GlobalScope.launch(Dispatchers.IO.plus(handler), CoroutineStart.DEFAULT, block)

    /**
     * 捕获协程运行期间产生的错误，防止引起crash
     */
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("jason", "CoroutineException:$exception")
    }

}