package com.love.flutter.plugins

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import com.love.MyApplication
import com.love.activity.R
import com.love.extension.toast
import com.love.service.NotifyService.Companion.noticeId
import com.love.utils.OpenAutoStartUtil
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry

/**
 * @author Jason
 * @description:
 * @date :2019-07-24 11:21
 */
class FlutterPluginCallback : MethodChannel.MethodCallHandler {
    companion object {
        var CHANNEL = "com.love.flutter/plugin"

        fun registerWith(registrar: PluginRegistry.Registrar) {
            val channel = MethodChannel(registrar.messenger(), CHANNEL)
            val instance = FlutterPluginCallback()
            channel.setMethodCallHandler(instance)
        }
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (call.method == "autoStart") {
            Log.d("jason", "autoStart")
            OpenAutoStartUtil.jumpStartInterface(MyApplication.instance.applicationContext)
//            MyApplication.instance.toast("权限->自启动->开启本应用的自启动")
        }
    }


}