package com.love.helper

import com.love.extension.doRequest
import com.love.network.DataRequest

/**
 * @author Jason
 * @description:
 * @date :2019-07-29 14:47
 */
class WeatherHelper private constructor() {
    companion object {
        const val TAG = "WeatherHelper"
        val instance: WeatherHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            WeatherHelper()
        }
    }

    fun getWeahter() {
        DataRequest.create().getWeather().doRequest(TAG) {

        }.onError { i, s ->  }
    }
}