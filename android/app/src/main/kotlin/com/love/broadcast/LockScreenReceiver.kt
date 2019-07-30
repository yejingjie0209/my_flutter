package com.love.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.love.service.NotifyService
import com.xdandroid.hellodaemon.DaemonEnv
import android.support.v4.content.ContextCompat.startForegroundService
import android.os.Build.VERSION_CODES.O
import android.os.Build.VERSION.SDK_INT



/**
 * @author Jason
 * @description:
 * @date :2019-07-24 17:10
 */
class LockScreenReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("jason", "收到广播${intent?.action}")
//        if (Intent.ACTION_SCREEN_ON.equals(intent?.action)) {
//
//        }
        DaemonEnv.initialize(
                context!!,  //Application Context.
                NotifyService::class.java, //刚才创建的 Service 对应的 Class 对象.
                1000 * 20) //定时唤醒的时间间隔(ms), 默认 6 分钟.
        if (SDK_INT >= O) {
            context?.startForegroundService(Intent(context, NotifyService::class.java))
        } else {
            context?.startService(Intent(context, NotifyService::class.java))
        }
    }
}