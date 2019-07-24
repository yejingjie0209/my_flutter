// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'weather.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Weather _$WeatherFromJson(Map<String, dynamic> json) {
  return new Weather(
      json['reason'] as String,
      json['result'] == null
          ? null
          : new Result.fromJson(json['result'] as Map<String, dynamic>),
      json['error_code'] as int);
}

abstract class _$WeatherSerializerMixin {
  String get reason;
  Result get result;
  int get errorCode;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'reason': reason,
        'result': result,
        'error_code': errorCode
      };
}

Result _$ResultFromJson(Map<String, dynamic> json) {
  return new Result(
      json['city'] as String,
      json['realtime'] == null
          ? null
          : new Realtime.fromJson(json['realtime'] as Map<String, dynamic>),
      (json['future'] as List)
          ?.map((e) =>
              e == null ? null : new WeatherFuture.fromJson(e as Map<String, dynamic>))
          ?.toList());
}

abstract class _$ResultSerializerMixin {
  String get city;
  Realtime get realtime;
  List<WeatherFuture> get future;
  Map<String, dynamic> toJson() =>
      <String, dynamic>{'city': city, 'realtime': realtime, 'future': future};
}

Realtime _$RealtimeFromJson(Map<String, dynamic> json) {
  return new Realtime(
      json['temperature'] as String,
      json['humidity'] as String,
      json['info'] as String,
      json['wid'] as String,
      json['direct'] as String,
      json['power'] as String,
      json['aqi'] as String);
}

abstract class _$RealtimeSerializerMixin {
  String get temperature;
  String get humidity;
  String get info;
  String get wid;
  String get direct;
  String get power;
  String get aqi;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'temperature': temperature,
        'humidity': humidity,
        'info': info,
        'wid': wid,
        'direct': direct,
        'power': power,
        'aqi': aqi
      };
}

WeatherFuture _$FutureFromJson(Map<String, dynamic> json) {
  return new WeatherFuture(
      json['date'] as String,
      json['temperature'] as String,
      json['weather'] as String,
      json['wid'] == null
          ? null
          : new Wid.fromJson(json['wid'] as Map<String, dynamic>),
      json['direct'] as String);
}

abstract class _$FutureSerializerMixin {
  String get date;
  String get temperature;
  String get weather;
  Wid get wid;
  String get direct;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'date': date,
        'temperature': temperature,
        'weather': weather,
        'wid': wid,
        'direct': direct
      };
}

Wid _$WidFromJson(Map<String, dynamic> json) {
  return new Wid(json['day'] as String, json['night'] as String);
}

abstract class _$WidSerializerMixin {
  String get day;
  String get night;
  Map<String, dynamic> toJson() =>
      <String, dynamic>{'day': day, 'night': night};
}
