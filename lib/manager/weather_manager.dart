import 'package:my_flutter/model/weather.dart';
import 'package:my_flutter/network/http_controller.dart';

enum WeatherTime { TODAY, TOMORROW }

class WeatherManager {
  void requestWeather(
      void weatherCallback(Weather mWeather), void errorCallback(String s)) {
    var map = new Map<String, String>();
    map["city"] = "上海";
    map["key"] = "dd1b2fbe2610bb4acb3a7a065697a7c0";
    HttpController.get("http://apis.juhe.cn/simpleWeather/query", map,
        callBack: (response) {
      Weather weatherModel = Weather.fromJson(response);
      weatherCallback(weatherModel);
      print("jason," + response.toString());
    }, errorCallBack: (error) {
      print("jason," + error);
      errorCallback(error);
    });
  }

  String getWeather(Result result, WeatherTime time) {
    switch (time) {
      case WeatherTime.TODAY:
        WeatherFuture future = result.future[0];
        return "今天${future.weather}，${getWidMsg(future.wid)}${getTemperatureMsg(future.temperature, future.wid, time)}";
        break;
      case WeatherTime.TOMORROW:
        WeatherFuture future = result.future[1];
        return "明天${future.weather}，${getWidMsg(future.wid)}${getTemperatureMsg(future.temperature, future.wid, time)}";
        break;
    }
    return "";
  }

  String getTemperatureMsg(String temperature, Wid wid, WeatherTime time) {
    List<String> list = temperature.split("/");
    StringBuffer sb = new StringBuffer("气温");
    if (list.length == 1) {
      sb.write(list[0]);
    } else {
      sb.write(list[0] + "~" + list[1]);
    }

    if (time == WeatherTime.TOMORROW) {
      var t1 = list[1].replaceAll("℃", "");
      if (int.parse(t1) >= 32) {
        //晴天且温度大于
        sb.write(",天很热！");
        if (isFineOrloudy(int.parse(wid.day))) {
          sb.write("出门注意防晒！");
        }
      }

      var t0 = list[0].replaceAll("℃", "");
      if (int.parse(t0) < 0) {
        sb.write(",天很冷！出门多穿点衣服！");
      }
    }

    return sb.toString();
  }

  String getWidMsg(Wid wid) {
    StringBuffer sb = new StringBuffer();
    var day = int.parse(wid.day);
    var night = int.parse(wid.night);
    if (isRain(day) || isRain(night)) {
      //雨
      sb.write("记得带伞，");
    }

    if (isSnow(day) || isSnow(night)) {
      //雪
      sb.write("想陪你一起看雪，");
    }

    if ((day == 53 || night == 53)) {
      //霾
      sb.write("建议带好口罩出门，");
    }
    print("weather=$day");
    return "${sb.toString()}";
  }

  bool isRain(int wid) {
    return (wid >= 3 && wid <= 12) || wid == 19 || (wid >= 21 && wid <= 25);
  }

  bool isFineOrloudy(int wid) {
    return wid == 0 || wid == 1;
  }

  bool isSnow(int wid) {
    return (wid >= 13 && wid <= 17) || (wid >= 26 && wid <= 28);
  }
}

//{
//"reason": "查询成功",
//"result": [
//{
//"wid": "00",
//"weather": "晴"
//},
//{
//"wid": "01",
//"weather": "多云"
//},
//{
//"wid": "02",
//"weather": "阴"
//},
//{
//"wid": "03",
//"weather": "阵雨"
//},
//{
//"wid": "04",
//"weather": "雷阵雨"
//},
//{
//"wid": "05",
//"weather": "雷阵雨伴有冰雹"
//},
//{
//"wid": "06",
//"weather": "雨夹雪"
//},
//{
//"wid": "07",
//"weather": "小雨"
//},
//{
//"wid": "08",
//"weather": "中雨"
//},
//{
//"wid": "09",
//"weather": "大雨"
//},
//{
//"wid": "10",
//"weather": "暴雨"
//},
//{
//"wid": "11",
//"weather": "大暴雨"
//},
//{
//"wid": "12",
//"weather": "特大暴雨"
//},
//{
//"wid": "13",
//"weather": "阵雪"
//},
//{
//"wid": "14",
//"weather": "小雪"
//},
//{
//"wid": "15",
//"weather": "中雪"
//},
//{
//"wid": "16",
//"weather": "大雪"
//},
//{
//"wid": "17",
//"weather": "暴雪"
//},
//{
//"wid": "18",
//"weather": "雾"
//},
//{
//"wid": "19",
//"weather": "冻雨"
//},
//{
//"wid": "20",
//"weather": "沙尘暴"
//},
//{
//"wid": "21",
//"weather": "小到中雨"
//},
//{
//"wid": "22",
//"weather": "中到大雨"
//},
//{
//"wid": "23",
//"weather": "大到暴雨"
//},
//{
//"wid": "24",
//"weather": "暴雨到大暴雨"
//},
//{
//"wid": "25",
//"weather": "大暴雨到特大暴雨"
//},
//{
//"wid": "26",
//"weather": "小到中雪"
//},
//{
//"wid": "27",
//"weather": "中到大雪"
//},
//{
//"wid": "28",
//"weather": "大到暴雪"
//},
//{
//"wid": "29",
//"weather": "浮尘"
//},
//{
//"wid": "30",
//"weather": "扬沙"
//},
//{
//"wid": "31",
//"weather": "强沙尘暴"
//},
//{
//"wid": "53",
//"weather": "霾"
//}
//],
//"error_code": 0
//}
