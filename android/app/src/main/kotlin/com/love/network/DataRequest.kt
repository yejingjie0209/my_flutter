package com.love.network

import com.love.model.Weather
import com.love.network.ApiService.KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Jason
 * @description:
 * @date :2019-07-29 14:13
 */

interface DataRequest {

    @GET("simpleWeather/query")
    fun getWeather(@Query("city") city: String = "上海",
                   @Query("key") key: String = KEY): Observable<Weather>

    companion object {
        fun create(): DataRequest {
            return ApiService.createRetrofit().create(DataRequest::class.java)
        }
    }
}