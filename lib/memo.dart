import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';

class Memo extends StatelessWidget {
//  MaterialColor themeColor = Colors.pink[300];
  static const PREFS_MEMO = "prefs_memo";

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
//      title: "备忘录",
      theme: ThemeData(primarySwatch: Colors.pink),
      home: MemoPage(title: "jason的备忘录"),
    );
  }
}

class MemoPage extends StatefulWidget {
  final String title;

  MemoPage({Key key, this.title}) : super(key: key);

  @override
  MemoPageState createState() => new MemoPageState();
}

class MemoPageState extends State<MemoPage> with WidgetsBindingObserver {
  List<WidgetWrap> widgets = [];
  List<dynamic> strList = [];

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
    var future = get();
    future.then((list) {
      strList = list;
      setState(() {
        for (int i = 0; i < strList.length; i++) {
          widgets.add(getRow(i));
        }
        strList = [];
        widgets.add(getRow(strList.length));
      });
    });
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    save();
    super.dispose();
  }

  @override
  void didChangeDependencies() {
    // TODO: implement didChangeDependencies
    super.didChangeDependencies();
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> list = new List();
    widgets.forEach((item) {
      list.add(item.widget);
    });
    return new Scaffold(
      appBar: AppBar(title: Text(widget.title)),
      body: new ListView(children: list),
    );
  }

  WidgetWrap getRow(final int index) {
    WidgetWrap mWidgetWrap = new WidgetWrap();
    if (strList.length > index) {
      mWidgetWrap.mController.text = strList[index];
    }
//    final TextEditingController mController = new TextEditingController();
//    Widget widget;
    mWidgetWrap.widget = new GestureDetector(
      key: Key("$index"),
      child: new Row(
        children: <Widget>[
          new Expanded(
            flex: 5,
            child: new Padding(
              padding: EdgeInsets.all(10.0),
              child: new TextField(
                controller: mWidgetWrap.mController,
                decoration: new InputDecoration(hintText: "请jason输入备忘内容"),
              ),
            ),
          ),
          new Expanded(
              flex: 1,
              child: new GestureDetector(
                child: new Icon(Icons.remove_circle, color: Colors.pink[300]),
                onTap: () {
                  if (widgets.length > 1) {
                    setState(() {
                      widgets = new List.from(widgets);
                      widgets.remove(mWidgetWrap);
                      print("remove $this");
                    });
                  }
                },
              ))
        ],
      ),
    );

    mWidgetWrap.mController.addListener(() {
      final text = mWidgetWrap.mController.text.toLowerCase();
      if (text.isNotEmpty) {
        if (widgets.indexOf(mWidgetWrap) == widgets.length - 1) {
          setState(() {
            widgets = new List.from(widgets);
            widgets.add(getRow(widgets.length));
//            print("row $i");
          });
        }
      } else {}
    });

    return mWidgetWrap;
  }

  Future<List<dynamic>> get() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    var jsonStr = prefs.getString(Memo.PREFS_MEMO);
//    print("jason:get" + jsonStr);
    if (jsonStr != null && jsonStr.length > 0) {
      List<dynamic> list = jsonDecode(jsonStr);
      list.forEach((item) {
        print("jason:get:map,item:$item");
      });
      return list;
    }
    return new List<dynamic>();
  }

  void save() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    var list = [];
//    print("jason:save:");
    widgets.forEach((item) {
      var text = item.mController.text.toString();
      if (text != null && text.length > 0) {
        list.add(text);
//        print("jason:save:add:$text");
      }
    });
    //https://javiercbk.github.io/json_to_dart/
    if (list.length > 0) {
      var json = jsonEncode(list);
      print("jason:save:" + json);
      prefs.setString(Memo.PREFS_MEMO, json);
    } else {
      prefs.setString(Memo.PREFS_MEMO, null);
    }

//
  }

  /**
   * resumed - 应用程序可见并响应用户输入。这是来自Android的onResume
      inactive - 应用程序处于非活动状态，并且未接收用户输入。此事件在Android上未使用，仅适用于iOS
      paused - 应用程序当前对用户不可见，不响应用户输入，并在后台运行。这是来自Android的暂停
      suspending - 该应用程序将暂时中止。这在iOS上未使用

   */
  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
//    print("jason:@@@！！！");
    switch (state) {
      case AppLifecycleState.resumed:
        break;
      case AppLifecycleState.paused:
        save();
        break;
      case AppLifecycleState.inactive:
//        save();
        break;
      case AppLifecycleState.suspending:
//        save();
        break;
      default:
        break;
    }

//    setState(() {});
  }
}

class WidgetWrap {
  Widget widget;
  TextEditingController mController = new TextEditingController();
}
