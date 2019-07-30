import 'dart:async';

import 'package:flutter/services.dart';

class ReceiveNativeManager {
  static const counterPlugin = const EventChannel('com.love.event/plugin');

  StreamSubscription subscription;

  void registerSubscription(
      void onEvent(Object event), void onError(Object error)) {
    //开启监听
    if (subscription == null) {
      subscription = counterPlugin
          .receiveBroadcastStream()
          .listen(onEvent, onError: onError);
    }
  }

  void cancel() {
    if (subscription != null) {
      subscription.cancel();
    }
  }
}
