package com.love.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.love.MyApplication
import com.love.activity.MainActivity
import com.love.activity.R
import com.love.extension.doRequest
import com.love.extension.toast
import com.love.helper.CacheHelper
import com.love.helper.WeatherHelper
import com.love.model.Weather
import com.love.model.WeatherTime
import com.love.network.DataRequest
import com.xdandroid.hellodaemon.AbsWorkService
import java.util.*


/**
 * @author Jason
 * @description:
 * @date :2019-07-23 18:33
 */
class NotifyService : AbsWorkService() {
    private var mTimer2: Timer? = null
    private var mTask2: TimerTask? = null

    companion object {
        val NOTICE_ID = 100
        var noticeId = 0x1
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

        timer()
        return START_STICKY
    }

    private val handler = Handler(Looper.getMainLooper())

    fun timer() {
        var i = 0x123
        if (mTimer2 == null && mTask2 == null) {
            mTimer2 = Timer()
            mTask2 = object : TimerTask() {
                override fun run() {
                    Log.d("jason", "sendNotify")
                    getTime()
//                    getWeather(WeatherTime.TOMORROW)

//                    handler.post {
//                        FlutterPluginEvent.instance.eventSink?.success(EVENT_GET_TODAY_WEATHER)
//                    }

                }
            }
            mTimer2?.schedule(mTask2, 0, 1000 * 30)
        }
    }

    private fun getTime() {
        val now = Calendar.getInstance()
        Log.d("jason", "年: " + now.get(Calendar.YEAR))
        Log.d("jason", "月: " + (now.get(Calendar.MONTH) + 1) + "")
        Log.d("jason", "日: " + now.get(Calendar.DAY_OF_MONTH))
        Log.d("jason", "时: " + now.get(Calendar.HOUR_OF_DAY))
        Log.d("jason", "分: " + now.get(Calendar.MINUTE))
        Log.d("jason", "秒: " + now.get(Calendar.SECOND))

        val day = now.get(Calendar.DAY_OF_MONTH)
        val hour = now.get(Calendar.MINUTE)
        if (hour == 6) {
            CacheHelper.get {

            }

            CacheHelper.save {

            }

        }

        if (hour == 22) {

        }
    }

    private fun getWeather(time: WeatherTime) {
        DataRequest.create().getWeather().doRequest(WeatherHelper.TAG) {
            val weather = getWeather(it.result, time)
            sendNotification(weather)
        }.onError { i, s ->
            toast("获取天气$s")
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


    fun getWidMsg(wid: Weather.ResultBean.FutureBean.WidBean?): String {
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


    fun getTemperatureMsg(temperature: String?, wid: Weather.ResultBean.FutureBean.WidBean?, time: WeatherTime): String {
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

    fun isRain(wid: Int): Boolean {
        return wid in 3..12 || wid == 19 || wid in 21..25
    }

    fun isFineOrloudy(wid: Int): Boolean {
        return wid == 0 || wid == 1
    }

    fun isSnow(wid: Int): Boolean {
        return wid in 13..17 || wid in 26..28
    }

    override fun onDestroy() {
        super.onDestroy()
        mTask2?.cancel()
        mTask2 = null
        mTimer2 = null


        // 如果Service被杀死，干掉通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mManager.cancel(NOTICE_ID)
        }
        Log.d(TAG, "DaemonService---->onDestroy，前台service被杀死")
        // 重启自己
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, NotifyService::class.java))
        } else {
            startService(Intent(this, NotifyService::class.java))
        }
    }


    override fun onBind(intent: Intent?, alwaysNull: Void?): IBinder? {
        return null
    }

    override fun startWork(intent: Intent?, flags: Int, startId: Int) {
    }

    override fun isWorkRunning(intent: Intent?, flags: Int, startId: Int): Boolean {
        return false
    }

    override fun shouldStopService(intent: Intent?, flags: Int, startId: Int): Boolean {
        return false
    }

    override fun stopWork(intent: Intent?, flags: Int, startId: Int) {
    }

    override fun onServiceKilled(rootIntent: Intent?) {
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