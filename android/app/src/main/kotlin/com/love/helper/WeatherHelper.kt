package com.love.helper

import com.love.extension.doRequest
import com.love.model.Weather
import com.love.model.WeatherTime
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

    fun getWeather(time: WeatherTime, weatherResult: (String) -> Unit, errorInfo: (String) -> Unit) {
        DataRequest.create().getWeather().doRequest(WeatherHelper.TAG) {
            val weather = getWeather(it.result, time)
//            sendNotification(weather)
            weatherResult.invoke(weather)
        }.onError { i, s ->
            //            toast("获取天气$s")
            errorInfo.invoke(s)
        }
    }

    private fun getWeather(result: Weather.ResultBean?, time: WeatherTime): String {
        return when (time) {
            WeatherTime.TODAY -> {
                val future = result?.future?.get(0)
                "今天${future?.weather}，${getWidMsg(future?.wid)}${getTemperatureMsg(future?.temperature, future?.wid, time)}"
            }
            WeatherTime.TOMORROW -> {
                val future = result?.future?.get(1)
                "明天${future?.weather}，${getWidMsg(future?.wid)}${getTemperatureMsg(future?.temperature, future?.wid, time)}"
            }
        }
    }


    private fun getWidMsg(wid: Weather.ResultBean.FutureBean.WidBean?): String {
        val sb = StringBuffer()
        val day = (wid?.day)!!.toInt()
        val night = (wid.night)!!.toInt()
        if (isRain(day) || isRain(night)) {
            //雨
            sb.append("记得带伞，");
        }

        if (isSnow(day) || isSnow(night)) {
            //雪
            sb.append("想陪你一起看雪，");
        }

        if ((day == 53 || night == 53)) {
            //霾
            sb.append("建议带好口罩出门，");
        }
        print("weather=$day");
        return "$sb"
    }


    private fun getTemperatureMsg(temperature: String?, wid: Weather.ResultBean.FutureBean.WidBean?, time: WeatherTime): String {
        val list = temperature?.split("/");
        val sb = StringBuffer("气温");
        if (list?.size == 1) {
            sb.append(list[0])
        } else {
            sb.append(list?.get(0) + "~" + list?.get(1))
        }

        if (time == WeatherTime.TOMORROW) {
            var t1 = list?.get(1)?.replace("℃", "");
            if ((t1)!!.toInt() >= 32) {
                //晴天且温度大于
                sb.append(",天很热！");
                if (isFineOrloudy((wid?.day)!!.toInt())) {
                    sb.append("出门注意防晒！")
                }
            }

            var t0 = list?.get(0)?.replace("℃", "");
            if ((t0)!!.toInt() < 0) {
                sb.append(",天很冷！出门多穿点衣服！")
            }
        }

        return sb.toString();
    }


    private fun isRain(wid: Int): Boolean {
        return wid in 3..12 || wid == 19 || wid in 21..25
    }

    private fun isFineOrloudy(wid: Int): Boolean {
        return wid == 0 || wid == 1
    }

    private fun isSnow(wid: Int): Boolean {
        return wid in 13..17 || wid in 26..28
    }

}