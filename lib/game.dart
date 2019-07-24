import 'package:flutter/material.dart';

class Game extends StatelessWidget {
//  MaterialColor themeColor = Colors.pink[300];

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "小游戏",
      theme: ThemeData(primarySwatch: Colors.pink),
      home: GamePage(title: "jason的小游戏（开发中，尽请期待）"),
    );
  }
}

class GamePage extends StatefulWidget {
  final String title;

  GamePage({Key key, this.title}) : super(key: key);

  @override
  GamePageState createState() => new GamePageState();
}

class GamePageState extends State<GamePage> {
  List<Widget> widgets = [];

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        appBar: AppBar(title: Text(widget.title)),
        body: Center(
          child: Container(
            height: 320.0,
            padding: EdgeInsets.only(left: 20, right: 20),
            decoration: BoxDecoration(
                border: Border.all(color: const Color(0xff6d9eeb))),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.start,
            ),
          ),
        ));
  }

  @override
  void dispose() {
    super.dispose();
  }

  /**
   * resumed - 应用程序可见并响应用户输入。这是来自Android的onResume
      inactive - 应用程序处于非活动状态，并且未接收用户输入。此事件在Android上未使用，仅适用于iOS
      paused - 应用程序当前对用户不可见，不响应用户输入，并在后台运行。这是来自Android的暂停
      suspending - 该应用程序将暂时中止。这在iOS上未使用

   */
  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    setState(() {});
  }
}
