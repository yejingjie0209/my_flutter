package com.love.broadcast

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.love.MyApplication
import com.love.activity.R
import com.love.extension.toast
import com.love.helper.CacheHelper
import com.love.helper.WeatherHelper
import com.love.model.WeatherTime
import com.love.service.NotifyService
import com.love.utils.PermissionUtils
import java.util.*

/**
 * @author Jason
 * @description:
 * @date :2019-07-30 20:00
 */
class AlarmReceive : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action
        Log.d("jason","action=$action")
        if ("com.live.alarm_action" == action) {
            if (PermissionUtils.checkNotify(MyApplication.instance)) {
                getTime()
            }
        }
    }

    private fun getTime() {
        val now = Calendar.getInstance()
//        Log.d("jason", "年: " + now.get(Calendar.YEAR))
//        Log.d("jason", "月: " + (now.get(Calendar.MONTH) + 1) + "")
//        Log.d("jason", "日: " + now.get(Calendar.DAY_OF_MONTH))
//        Log.d("jason", "时: " + now.get(Calendar.HOUR_OF_DAY))
//        Log.d("jason", "分: " + now.get(Calendar.MINUTE))
//        Log.d("jason", "秒: " + now.get(Calendar.SECOND))

        val hour = now.get(Calendar.HOUR_OF_DAY)
        if (hour == NotifyService.AM) {
            Log.d("jason", "AM时: " + now.get(Calendar.HOUR_OF_DAY))
            if (CacheHelper.get("${CacheHelper.NOTIFY_TIME}${NotifyService.AM}", true) == true) {
                WeatherHelper.instance.getWeather(WeatherTime.TODAY, { weather ->
                    sendNotification(weather)
                    Log.d("jason", "AM时:$weather ")
                    CacheHelper.save {
                        putBoolean("${CacheHelper.NOTIFY_TIME}${NotifyService.AM}", false)
                    }
                }, {
                    MyApplication.instance.toast("天气获取失败：$it")
                })
            }
        } else if (hour == NotifyService.PM) {
            Log.d("jason", "PM时: " + now.get(Calendar.HOUR_OF_DAY))
            if (CacheHelper.get("${CacheHelper.NOTIFY_TIME}${NotifyService.PM}", true) == true) {
                WeatherHelper.instance.getWeather(WeatherTime.TOMORROW, { weather ->
                    Log.d("jason", "PM时:$weather ")
                    sendNotification(weather)
                    CacheHelper.save {
                        putBoolean("${CacheHelper.NOTIFY_TIME}${NotifyService.PM}", false)
                    }
                }, {
                    MyApplication.instance.toast("天气获取失败：$it")
                })

            }

        } else {
            Log.d("jason", "时: " + now.get(Calendar.HOUR_OF_DAY))
            CacheHelper.save {
                putBoolean("${CacheHelper.NOTIFY_TIME}${NotifyService.AM}", true)
            }
            CacheHelper.save {
                putBoolean("${CacheHelper.NOTIFY_TIME}${NotifyService.PM}", true)
            }
        }
    }

    private fun sendNotification(msg: String) {
        val notificationManager = MyApplication.instance.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //26及以上
            val notificationChannel = NotificationChannel("id", "name", NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.canBypassDnd()//可否绕过请勿打扰模式
            notificationChannel.canShowBadge()//桌面lanchener显示角标
            notificationChannel.enableLights(true)//闪光
            notificationChannel.shouldShowLights()//闪光
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET//锁屏显示通知
            notificationChannel.enableVibration(true)//是否允许震动
            notificationChannel.vibrationPattern = longArrayOf(100, 100, 200)//设置震动方式（事件长短）
            notificationChannel.audioAttributes//获取系统响铃配置
            notificationChannel.group//获取消息渠道组
            notificationChannel.setBypassDnd(true)
            notificationChannel.description = "description"
            notificationChannel.lightColor = Color.GREEN//制定闪灯是灯光颜色
            notificationChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(notificationChannel)

            val builder = Notification.Builder(MyApplication.instance.applicationContext, "id")
            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setAutoCancel(true)
            builder.setChannelId("id")
            builder.setWhen(System.currentTimeMillis())
            builder.setContentTitle("标题")
            builder.setContentText(msg)
            builder.setNumber(3)
//            val intent = Intent(this, SecondActivity::class.java)
//            val pendingIntent = PendingIntent.getActivity(this, PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            builder.setContentIntent(pendingIntent)

            notificationManager.notify(NotifyService.noticeId++, builder.build())
        } else {
            val builder = NotificationCompat.Builder(MyApplication.instance.applicationContext)
            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setAutoCancel(true)
            builder.setWhen(System.currentTimeMillis())
            builder.setContentTitle("标题")
            builder.setContentText(msg)
//            val intent = Intent(this, SecondActivity::class.java)
//            val pendingIntent = PendingIntent.getActivity(this, PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//            builder.setContentIntent(pendingIntent)
            notificationManager.notify(2, builder.build())
        }

    }
}