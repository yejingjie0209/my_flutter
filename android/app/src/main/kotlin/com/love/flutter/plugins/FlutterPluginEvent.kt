package com.love.flutter.plugins

import android.util.Log
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.PluginRegistry.Registrar


/**
 * @author Jason
 * @description:
 * @date :2019-07-25 19:01
 */
class FlutterPluginEvent private constructor() : EventChannel.StreamHandler {
    companion object {
        var CHANNEL = "com.love.event/plugin"
        const val EVENT_GET_TODAY_WEATHER = "event_get_today_weather"
        const val EVENT_GET_TOMORROW_WEATHER = "event_get_today_weather"
        val instance: FlutterPluginEvent by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FlutterPluginEvent()
        }
    }

    var eventSink: EventChannel.EventSink? = null

    fun registerWith(registrar: Registrar) {
        val channel = EventChannel(registrar.messenger(), CHANNEL)
        channel.setStreamHandler(instance)
    }

    override fun onListen(p0: Any?, eventSink: EventChannel.EventSink?) {
        this.eventSink = eventSink
    }

    override fun onCancel(p0: Any?) {
        Log.d("FlutterPluginCounter", "FlutterPluginCounter:onCancel")
    }
}