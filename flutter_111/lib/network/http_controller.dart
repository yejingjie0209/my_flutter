import 'dart:io';
import 'package:dio/dio.dart';

//要查网络请求的日志可以使用过滤<net>
class HttpController {
  static const String GET = "get";
  static const String POST = "post";

  static String baseUrl;

  //get请求
  static void get(String url, Map<String, String> params,
      {Function callBack, Function errorCallBack}) async {
    request(url, callBack,
        method: GET, params: params, errorCallBack: errorCallBack);
  }

  //post请求
  static void post(String url, Map<String, String> params,
      {Function callBack, Function errorCallBack}) async {
    request(url, callBack,
        method: POST, params: params, errorCallBack: errorCallBack);
  }

  //具体的还是要看返回数据的基本结构
  //公共代码部分
  static void request(String url, Function callBack,
      {String method,
      Map<String, String> params,
      Function errorCallBack}) async {
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
      url = baseUrl + url;
    }
    print("<net> url :<" + method + ">" + url);

    if (params != null && params.isNotEmpty) {
      print("<net> params :" + params.toString());
    }

    String errorMsg = "";
    int statusCode;

    try {
      Response response;
      if (method == GET) {
        //组合GET请求的参数
        if (params != null && params.isNotEmpty) {
          StringBuffer sb = new StringBuffer("?");
          params.forEach((key, value) {
            sb.write("$key" + "=" + "${Uri.encodeComponent(value)}" + "&");
          });
          String paramStr = sb.toString();
          paramStr = paramStr.substring(0, paramStr.length - 1);
          url += paramStr;
        }
        response = await Dio().get(url);
      } else {
        if (params != null && params.isNotEmpty) {
          response = await Dio().post(url, data: params);
        } else {
          response = await Dio().post(url);
        }
      }

      statusCode = response.statusCode;

      //处理错误部分
      if (statusCode < 0) {
        errorMsg = "网络请求错误,状态码:" + statusCode.toString();
        _handError(errorCallBack, errorMsg);
        return;
      }

      if (callBack != null) {
        callBack(response.data);
        print("<net> response data:" + response.data);
      }
    } catch (exception) {
      _handError(errorCallBack, exception.toString());
    }
  }

  //处理异常
  static void _handError(Function errorCallback, String errorMsg) {
    if (errorCallback != null) {
      errorCallback(errorMsg);
    }
    print("<net> errorMsg :" + errorMsg);
  }
}
//class HttpController {
//  static void getHttp<T>(Function callback, Function errCallback) async {
//    try {
////      Response response = await Dio()
////          .get<T>("http://apis.juhe.cn/simpleWeather/query", queryParameters: {
////        "city": "%E4%B8%8A%E6%B5%B7",
////        "key": "dd1b2fbe2610bb4acb3a7a065697a7c0"
////      });
//      Response response = await Dio()
//          .get<T>("http://apis.juhe.cn/simpleWeather/query?city=${Uri.encodeComponent("上海")}&key=dd1b2fbe2610bb4acb3a7a065697a7c0");
//      if (response.statusCode == HttpStatus.ok) {
//        callback(response.data);
//      } else {
//        errCallback(response.statusCode, "错误状态${response.statusCode}");
//      }
//    } on DioError catch (e) {
//      errCallback(e.type, e.message);
//    }
//  }
//}
