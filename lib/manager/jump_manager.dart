import 'package:flutter/services.dart';

class JumpManager {
  static const jumpPlugin = const MethodChannel("com.love.flutter/plugin");

  static Future<Null> setAutoStart() async {
    String result = await jumpPlugin.invokeMethod("autoStart");
    print(result);
  }

  static Future<Null> sendWeatherInfo(String msg) async {
    String result = await jumpPlugin.invokeMethod("weatherInfo", msg);
    print(result);
  }
}