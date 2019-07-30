package com.love.activity

import android.os.Bundle
import com.love.flutter.plugins.FlutterPluginCallback
import com.love.flutter.plugins.FlutterPluginEvent
import com.love.utils.PermissionUtils
import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity : FlutterActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)
        FlutterPluginCallback.registerWith(this.registrarFor(FlutterPluginCallback.CHANNEL))
        FlutterPluginEvent.instance.registerWith(this.registrarFor(FlutterPluginEvent.CHANNEL))
    }

    override fun onResume() {
        super.onResume()
        if (PermissionUtils.checkNotify(this)) {
//            val intent = Intent(this, NotifyService::class.java)
//            startService(intent)
        }
//        OpenAutoStartUtil.jumpStartInterface(this)
    }
}
