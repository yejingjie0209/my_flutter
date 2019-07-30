package com.love.network

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.love.MyApplication
import com.love.activity.R
import com.love.service.NotifyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Jason
 * @description:
 * @date :2019-07-29 11:57
 */
object ApiService {
    private var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BASIC
    var retrofit: Retrofit? = null

    const val BASE_URL: String = "http://apis.juhe.cn/"
    const val KEY = "dd1b2fbe2610bb4acb3a7a065697a7c0"


    fun createRetrofit(): Retrofit {
        if (retrofit != null) {
            return retrofit!!
        }
        retrofit = getRxAdapterRetrofit(BASE_URL)
        return retrofit!!
    }

    @JvmStatic
    fun getRxAdapterRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            //.addConverterFactory(JsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .client(createHttpClient())
            .build()

    private fun createHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.retryOnConnectionFailure(false)

        /**
         * 自定义错误处理拦截器
         */
        //httpClientBuilder.addInterceptor(RequestErrorInterceptor(context))

        //添加header，同步时间,
//        httpClientBuilder.addInterceptor(CommonInterceptor())


        /**
         * 添加log拦截，用来在logcat中查看log
         */
//        if (NetworkConfig.isDebug()) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = logLevel
        httpClientBuilder.addInterceptor(loggingInterceptor)
//        }
        /**
         * 添加HTTPS请求逻辑
         */
//        if(NetworkConfig.isHttps()){
//            if(sslSocketFactory == null){
//                createSSLSocketFactoryAndTrustManager()
//            }
//            httpClientBuilder.sslSocketFactory(sslSocketFactory!!,trustManager!!)
//        }
        return httpClientBuilder.build()
    }


}