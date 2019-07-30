package com.love

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import com.love.helper.CacheHelper
import com.love.service.NotifyService
import com.xdandroid.hellodaemon.DaemonEnv
import io.flutter.app.FlutterApplication

/**
 * @author Jason
 * @description:
 * @date :2019-07-24 18:00
 */
class MyApplication : FlutterApplication() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        CacheHelper.init(this)
        DaemonEnv.initialize(
                this,  //Application Context.
                NotifyService::class.java, //刚才创建的 Service 对应的 Class 对象.
                1000 * 20) //定时唤醒的时间间隔(ms), 默认 6 分钟.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, NotifyService::class.java))
        } else {
            startService(Intent(this, NotifyService::class.java))
        }
    }
}