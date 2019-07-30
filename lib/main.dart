import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:local_notifications/local_notifications.dart';

import 'manager/jump_manager.dart';
import 'manager/receive_native_manager.dart';
import 'manager/weather_manager.dart';
import 'memo.dart';

void main() => runApp(new ManmanMain());

class ManmanMain extends StatelessWidget {
//  MaterialColor themeColor = Colors.pink[300];

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "aa",
      theme: ThemeData(primarySwatch: Colors.pink),
      home: MainPage(title: "bb"),
    );
  }
}

class MainPage extends StatefulWidget {
  final String title;

  MainPage({Key key, this.title}) : super(key: key);

  @override
  State createState() {
    return new MainPageState();
  }
}

class MainPageState extends State<MainPage> {
  WeatherManager mWeatherManager = WeatherManager();
  String weather;

  @override
  void initState() {
    super.initState();
    mWeatherManager.requestWeather((weatherModel) {
      setState(() {
        weather =
            "jason：\n${mWeatherManager.getWeather(weatherModel.result, WeatherTime.TODAY)}\n"
            "${mWeatherManager.getWeather(weatherModel.result, WeatherTime.TOMORROW)}";
      });
    }, (error) {
//      Fluttertoast.showToast(msg: ("获取天气预报出错:${error.toString()}"));
    });

    var manager = ReceiveNativeManager();
    manager.registerSubscription((event) {
      print("jason,event$event");
      if(event is Map){

      }
      if (event == "event_get_today_weather" ||
          event == "event_get_tommorrow_weather") {
        JumpManager.sendWeatherInfo(";很好附近可垃圾房卡的积分离开大家发的啦");
//        mWeatherManager.requestWeather((weatherModel) {
//          if (event == "event_get_weather") {
//            JumpManager.sendWeatherInfo(mWeatherManager.getWeather(
//                weatherModel.result, WeatherTime.TODAY));
//          } else {
//            JumpManager.sendWeatherInfo(mWeatherManager.getWeather(
//                weatherModel.result, WeatherTime.TOMORROW));
//          }
//        }, (error) {
//          Fluttertoast.showToast(msg: error.toString());
//        });
      }
    }, (error) {
      Fluttertoast.showToast(msg: ("接收native错误：" + error.toString()));
    });
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
        actions: <Widget>[
          PopupMenuButton<int>(
            onSelected: (int value) {
              print("jason,value=$value");
              if (value == 1) {
                JumpManager.setAutoStart();
                Fluttertoast.showToast(
                    msg: "权限->自启动->开启本应用的自启动", toastLength: Toast.LENGTH_LONG);
              }
            },
            itemBuilder: (BuildContext context) => <PopupMenuEntry<int>>[
              PopupMenuItem(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: <Widget>[Text('设置自启动'), Icon(Icons.settings)],
                ),
                value: 1,
              )
            ],
          )
        ],
      ),
      body: Center(
          child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Text("$weather"),
          RaisedButton(
              child: Text("jason的备忘录"),
              onPressed: () {
                Navigator.push(
                  context,
                  new MaterialPageRoute(builder: (context) => new Memo()),
                );
              }),
          RaisedButton(
              child: Text("jason的小游戏"),
              onPressed: () {
//                notify();
//                Navigator.push(
//                  context,
//                  new MaterialPageRoute(builder: (context) => new Game()),
//                );
              }),
        ],
      )),
    );
  }

  static const AndroidNotificationChannel channel =
      const AndroidNotificationChannel(
    id: 'some_channel_id',
    name: 'My app feature that requires notifications',
    description:
        'Grant this app the ability to show notifications for this app feature',
    importance: AndroidNotificationChannelImportance.HIGH,
    // default value for constructor
    vibratePattern:
        AndroidVibratePatterns.DEFAULT, // default value for constructor
  );

//  void notify() async {
//    await LocalNotifications.createAndroidNotificationChannel(channel: channel);
//    await LocalNotifications.createNotification(
//        title: "Basic",
//        content: "Notification",
//        id: 0,
//        androidSettings: new AndroidSettings(channel: channel));
//  }

//  void initNotification() {
//    var initializationSettingsAndroid =
//        new AndroidInitializationSettings('ic_launcher');
//    var initializationSettings =
//        new InitializationSettings(initializationSettingsAndroid, null);
//
//    flutterLocalNotificationsPlugin.initialize(initializationSettings,
//        onSelectNotification: onSelectNotification);
//
//  }

//  Future onSelectNotification(String payload) async {
//    if (payload != null) {
//      debugPrint('notification payload: ' + payload);
//    }
////payload 可作为通知的一个标记，区分点击的通知。
//    if (payload != null && payload == "complete") {
//      await Navigator.push(
//        context,
//        new MaterialPageRoute(builder: (context) => new Game()),
//      );
//    }
//  }
//
//  Future showNotification() async {
//    //安卓的通知配置，必填参数是渠道id, 名称, 和描述, 可选填通知的图标，重要度等等。
//    var androidPlatformChannelSpecifics = new AndroidNotificationDetails(
//        'your channel id', 'your channel name', 'your channel description',
//        importance: Importance.Max, priority: Priority.High);
//    //IOS的通知配置
////    var iOSPlatformChannelSpecifics = new IOSNotificationDetails();
//    var platformChannelSpecifics =
//        new NotificationDetails(androidPlatformChannelSpecifics, null);
//    //显示通知，其中 0 代表通知的 id，用于区分通知。
//    await flutterLocalNotificationsPlugin.show(
//        0, 'title', 'content', platformChannelSpecifics,
//        payload: 'complete');
//  }
}
