package com.love.service

import android.app.*
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.*
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.love.MyApplication
import com.love.activity.MainActivity
import com.love.activity.R
import com.love.extension.toast
import com.love.helper.CacheHelper
import com.love.helper.CacheHelper.NOTIFY_TIME
import com.love.helper.WeatherHelper
import com.love.model.WeatherTime
import com.love.utils.PermissionUtils
import java.lang.Thread.sleep
import java.util.*
import android.app.AlarmManager.ELAPSED_REALTIME_WAKEUP
import android.app.PendingIntent.getBroadcast
import android.os.SystemClock.elapsedRealtime
import com.love.broadcast.AlarmReceive
import android.app.AlarmManager.ELAPSED_REALTIME_WAKEUP
import android.app.PendingIntent.getBroadcast
import android.os.SystemClock.elapsedRealtime




/**
 * @author Jason
 * @description:
 * @date :2019-07-23 18:33
 */
class NotifyService : Service() {
//    private var mTimer2: Timer? = null
//    private var mTask2: TimerTask? = null

    companion object {
        val NOTICE_ID = 100
        var noticeId = 0x1

        //        const val AM = 6
//        const val PM = 22
//        const val PERIOD = 5 * 60 * 1000

        //TES
        const val AM = 19
        const val PM = 20
        const val PERIOD = 30 * 1000L

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

    }

    val CHANNEL_ONE_ID = "com.love.my_flutter"
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val CHANNEL_ONE_ID = "com.kjtech.app.N1"
        val CHANNEL_ONE_NAME = "Channel One"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, IMPORTANCE_HIGH)
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


        startForeground(NOTICE_ID, notification)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 如果觉得常驻通知栏体验不好
            // 可以通过启动CancelNoticeService，将通知移除，oom_adj值不变
            val intent = Intent(this, CancelNoticeService::class.java)
            startForegroundService(intent)
        } else {
            val intent = Intent(this, CancelNoticeService::class.java)
            startService(intent)
        }

        Log.d("jason", "onStartCommand")
        timer()
        return START_STICKY
    }


    fun timer() {
//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val pendingIntent = PendingIntent(AlarmReceive::class.java)
//        pendingIntent.action = "com.live.alarm_action"
//        val pi = getBroadcast(this, 0x2331, pendingIntent,   PendingIntent.FLAG_UPDATE_CURRENT)
////        am.setRepeating(ELAPSED_REALTIME_WAKEUP, 5000, 60*1000, pi)
//
//        Log.d("jason","timer")
////        if (PermissionUtils.checkNotify(this@NotifyService)) {
////            getTime()
////        }
//        val anhour = 6 * 1000
//        val triggerAtMillis = SystemClock.elapsedRealtime() + anhour
//
////        alarmManager.cancel(pendingIntent)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//  4.4
//            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtMillis, pendingIntent);
//        } else {
//            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtMillis, pendingIntent);
//        }


    }


//        Log.d("jason", "timer")
////        if (mTimer2 == null && mTask2 == null) {
//        if (mTimer2 != null && mTask2 != null) {
//            mTimer2?.cancel()
//            mTask2?.cancel()
//        }
//        Log.d("jason", "timer进来 ")
//        mTimer2 = Timer()
//        mTask2 = object : TimerTask() {
//            override fun run() {
//                if (PermissionUtils.checkNotify(this@NotifyService)) {
//                    getTime()
//                }
//
//            }
//        }
//        mTimer2?.schedule(mTask2, 0, PERIOD)


//    private fun getTime() {
//        val now = Calendar.getInstance()
////        Log.d("jason", "年: " + now.get(Calendar.YEAR))
////        Log.d("jason", "月: " + (now.get(Calendar.MONTH) + 1) + "")
////        Log.d("jason", "日: " + now.get(Calendar.DAY_OF_MONTH))
////        Log.d("jason", "时: " + now.get(Calendar.HOUR_OF_DAY))
////        Log.d("jason", "分: " + now.get(Calendar.MINUTE))
////        Log.d("jason", "秒: " + now.get(Calendar.SECOND))
//
//        val hour = now.get(Calendar.HOUR_OF_DAY)
//        if (hour == AM) {
//            Log.d("jason", "AM时: " + now.get(Calendar.HOUR_OF_DAY))
//            if (CacheHelper.get("$NOTIFY_TIME$AM", true) == true) {
//                WeatherHelper.instance.getWeather(WeatherTime.TODAY, { weather ->
//                    sendNotification(weather)
//                    Log.d("jason", "AM时:$weather ")
//                    CacheHelper.save {
//                        putBoolean("$NOTIFY_TIME$AM", false)
//                    }
//                }, {
//                    toast("天气获取失败：$it")
//                })
//            }
//        } else if (hour == PM) {
//            Log.d("jason", "PM时: " + now.get(Calendar.HOUR_OF_DAY))
//            if (CacheHelper.get("$NOTIFY_TIME$PM", true) == true) {
//                WeatherHelper.instance.getWeather(WeatherTime.TOMORROW, { weather ->
//                    Log.d("jason", "PM时:$weather ")
//                    sendNotification(weather)
//                    CacheHelper.save {
//                        putBoolean("$NOTIFY_TIME$PM", false)
//                    }
//                }, {
//                    toast("天气获取失败：$it")
//                })
//
//            }
//
//        } else {
//            Log.d("jason", "时: " + now.get(Calendar.HOUR_OF_DAY))
//            CacheHelper.save {
//                putBoolean("$NOTIFY_TIME$AM", true)
//            }
//            CacheHelper.save {
//                putBoolean("$NOTIFY_TIME$PM", true)
//            }
//        }
//    }


    override fun onDestroy() {
        super.onDestroy()
//        mTask2?.cancel()
//        mTimer2?.cancel()
//        mTask2 = null
//        mTimer2 = null
//        handler?.removeCallbacksAndMessages(null)
//        handler = null
//        handlerThread?.interrupt()
//        handlerThread = null


        // 如果Service被杀死，干掉通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mManager.cancel(NOTICE_ID)
        }
        Log.d("jason", "DaemonService---->onDestroy，前台service被杀死")
        // 重启自己
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, NotifyService::class.java))
        } else {
            startService(Intent(this, NotifyService::class.java))
        }
    }


//    override fun onBind(intent: Intent?, alwaysNull: Void?): IBinder? {
//        return null
//    }
//
//    override fun startWork(intent: Intent?, flags: Int, startId: Int) {
//    }
//
//    override fun isWorkRunning(intent: Intent?, flags: Int, startId: Int): Boolean {
//        return false
//    }
//
//    override fun shouldStopService(intent: Intent?, flags: Int, startId: Int): Boolean {
//        return false
//    }
//
//    override fun stopWork(intent: Intent?, flags: Int, startId: Int) {
//    }
//
//    override fun onServiceKilled(rootIntent: Intent?) {
//    }


}