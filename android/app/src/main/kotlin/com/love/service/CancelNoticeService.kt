package com.love.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import com.love.activity.MainActivity

import com.love.activity.R

/**
 * 描述:
 *
 *
 * Created by allens on 2018/1/31.
 */

class CancelNoticeService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {

            val CHANNEL_ONE_ID = "com.kjtech.app.N1"
            val CHANNEL_ONE_NAME = "Channel One"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(CHANNEL_ONE_ID,
                        CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.RED
                notificationChannel.setShowBadge(true)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(notificationChannel)


            }

            val icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            val notify = Notification.Builder(applicationContext)
                    .setContentTitle("主题")
                    .setContentText("内容")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(icon)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notify.setChannelId(CHANNEL_ONE_ID)
            }
            val notification = notify.build()

            val notificationIntent = Intent(applicationContext, MainActivity::class.java)
            notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            notification.contentIntent = PendingIntent.getActivity(applicationContext, 0, notificationIntent, 0)
            startForeground(NotifyService.NOTICE_ID, notification)
            // 开启一条线程，去移除DaemonService弹出的通知
            Thread(Runnable {
                // 延迟1s
                SystemClock.sleep(1000)
                // 取消CancelNoticeService的前台
                stopForeground(true)
                // 移除DaemonService弹出的通知
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.cancel(NotifyService.NOTICE_ID)
                // 任务完成，终止自己
                stopSelf()
            }).start()
        }
        return super.onStartCommand(intent, flags, startId)
    }
}